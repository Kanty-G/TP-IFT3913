/*
 * Authors: Kanty Louange Gakima && Yann-Sibril Saah
 */

package metrics;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class tls {

    public List<String[]> calculateTLS(String modulePath, String outputPath) {

        //get a list of java files 
        List<File> javaFiles = this.listJavaFiles(new File(modulePath));

        //list to store all the file to export in csv
        List<String[]> csvData = new ArrayList<>();

        
        String currentDirectory = new File(".").getAbsolutePath();

        //we need to pass through all the java test files to get all the informations
        for (File javaFile : javaFiles) {
            String filePath = javaFile.getAbsolutePath();
            String relativePath = getRelativePath(currentDirectory, filePath); 

            String packageName = getPackageName(javaFile);
            String className = getClassName(javaFile);

            if(!className.toUpperCase().endsWith("TEST")){
                continue;
            }


            int tloc_value = 0;
            int tassert_value = 0;

            try{
                tloc_value = metrics.tloc.calculateTLOC(filePath);
                tassert_value = metrics.tassert.calculateTassert(filePath); 

            }catch(IOException e){
                System.out.println(e.getMessage());
            }

            double tcmp = (double) tloc_value / tassert_value;

            //add all the informations for the current test java file in the csbv
            String[] rowData = { relativePath, packageName, className, String.valueOf(tloc_value), String.valueOf(tassert_value), String.format("%.2f",tcmp).replace(',' , '.') };
            csvData.add(rowData);
            
            String info = "";
            for(String s: rowData){
                info += s + ',';
            }

            System.out.println(info.substring(0, info.length() - 1));

        }

        //Export the data into csv
        if(outputPath.equals("")|| outputPath.equals(null)){
            return csvData;
        }
        exportToCSV(csvData, outputPath);
        
        return csvData;
    }

    private String getRelativePath(String currentDirectory, String filePath) {
        Path file1 = Paths.get(currentDirectory);
        Path file2 = Paths.get(filePath);

        Path relativePath = file1.relativize(file2);


        String relativePathStr = relativePath.toString();
        return relativePathStr;
    }

    private List<File> listJavaFiles(File directory) {
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

    //function to extract the package names 
    private String getPackageName(File javaFile) {
      
        String packageName = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(javaFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Recherchez la ligne qui commence par "package"
                if (line.trim().startsWith("package")) {
                    // Divisez la ligne pour obtenir le nom du paquet
                    packageName = line.trim().substring("package".length()).trim();
                    // Supprimez le point-virgule s'il est présent
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

    // function to extract the class name
    private String getClassName(File javaFile) {
        String fileName = javaFile.getName();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    //function to extract data into csv
    public  void exportToCSV(List<String[]> data, String outputPath) {
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
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java tls <modulePath> <outputPath>");
            return;
        }

        String modulePath = args[0];
        String outputPath = args[1];

        tls tlsCalculator = new tls();
        List<String[]> csvData = tlsCalculator.calculateTLS(modulePath, outputPath);

        // Print the first line of the output CSV file
        if (!csvData.isEmpty()) {
            String[] firstRow = csvData.get(0);
            StringBuilder firstLine = new StringBuilder();
            for (String data : firstRow) {
                firstLine.append(data).append(",");
            }

            System.out.println(firstLine.substring(0, firstLine.length() - 1));
        } else {
            System.out.println("this file is empty"); 
        }
    }
}

