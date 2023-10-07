
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class tls {

    public static void main(String[] args){
        try {
            tls.calculateTLS(args[0], "");
        } catch (Exception e) {
           System.out.println(e.getMessage()); 
        }
    }

    public static List<String[]> calculateTLS(String modulePath, String outputPath) {

        //get a list of java files 
        List<File> javaFiles = listJavaFiles(new File(modulePath));

        //create a list to export all the data in csv
        List<String[]> csvData = new ArrayList<>();

        //get the path of the current directory
        String currentDirectory = new File(".").getAbsolutePath();

        
        for (File javaFile : javaFiles) {
            String filePath = javaFile.getAbsolutePath();
            String relativePath = getRelativePath(currentDirectory, filePath); 

            String packageName = getPackageName(javaFile);
            String className = getClassName(javaFile);
            
            //we specify that we evaluate only the test java files
            if(!className.toUpperCase().endsWith("TEST")){
                continue;
            }

            tloc tloc = new tloc();
            tassert tassert = new tassert();

            int tloc_value = 0;
            int tassert_value = 0;

            try{
                tloc_value = tloc.calculateTLOC(filePath);
                tassert_value = tassert.calculateTassert(filePath);

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

        
            double tcmp = (double) tloc_value / tassert_value;

            //we add the data to the list
            String[] rowData = { relativePath, packageName, className, String.valueOf(tloc_value), String.valueOf(tassert_value), String.format("%.2f", tcmp).replace(',' , '.') };
            csvData.add(rowData);
            
            String info = "";
            for(String s: rowData){
                info += s + ',';
            }

            System.out.println(info.substring(0, info.length() - 1));

        }

        // Exportez les données en CSV
        if(outputPath.equals("")|| outputPath.equals(null)){
            return csvData;
        }
        exportToCSV(csvData, outputPath);
        
        return csvData;
    }

    private static String getRelativePath(String currentDirectory, String filePath) {
        Path file1 = Paths.get(currentDirectory);
        Path file2 = Paths.get(filePath);

        // Calculate the relative path
        Path relativePath = file1.relativize(file2);

        // Convert the relative path to a string
        String relativePathStr = relativePath.toString();
        return relativePathStr;
    }


    // Fonction pour lister les fichiers Java dans un répertoire
    private static List<File> listJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    javaFiles.addAll(listJavaFiles(file));
                } else if (file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
        return javaFiles;
    }

    // Fonction pour extraire le nom du paquet à partir d'un fichier Java
    private static String getPackageName(File javaFile) {
        
        //we assume that the package name is the name of the parent directory
        String packageName = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // search for the line that starts with "package"
                if (line.trim().startsWith("package")) {
                
                    packageName = line.trim().substring("package".length()).trim();
    
                    if (packageName.endsWith(";")) {
                        packageName = packageName.substring(0, packageName.length() - 1).trim();
                    }
                    
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packageName;
    }

    //function to extract the name of the class from a java file
    private static String getClassName(File javaFile) {
        String fileName = javaFile.getName();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    //function to extract data from the csv file
    public static  void exportToCSV(List<String[]> data, String outputPath) {
        try (PrintWriter writer = new PrintWriter(new File(outputPath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("path,");
            sb.append("package,");
            sb.append("class,");
            sb.append("bloc,");
            sb.append("tassert,");
            sb.append("tloc/tassert\n");

            for (String[] rowData : data) {
                sb.append(String.join(",", rowData));
                sb.append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.err.println("Erreur lors de l'exportation vers le fichier CSV : " + e.getMessage());
        }
    }
}
