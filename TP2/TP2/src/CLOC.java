
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CLOC{

    public static int calculateCLOC(String filePath) throws IOException {

        int cloc = 0;

        //we open the file in read mode
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            //verify if the line does not start with characters that starts 
            if (!line.trim().startsWith("/") && !line.trim().startsWith("*")) {
                cloc++;
            }
        }
        //we close the file
        bufferedReader.close();
        fileReader.close();
        return cloc;
    }
    
    /**
     * 
     * @param path: the input folder path
     * @return : boolean result of the validity
     */
    static boolean isValid(String[] path){
        Boolean valid = false;
        //we have to verify if the path entry is a java file
        try{ 
            File fileEntry   = new File(path[0]);
            Boolean hasPath   = (path.length == 1);
            Boolean isFile     = fileEntry.isFile();
            Boolean isJavaType = fileEntry.getName().contains(".java");
            valid      = hasPath && isFile && isJavaType;
        } catch (Exception e) {}
        return valid;
    }
    public static void main(String[] args) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            Boolean hasArgs = true;
            String[] entries = new String[1];
            Boolean isValid = isValid(args);
            while(!isValid) {
                hasArgs = false;
                System.out.println("Entrer un chemin  valide:");
                entries[0] = scanner.nextLine();
                isValid    = isValid(entries);
            }
            scanner.close();
            String filePath = (hasArgs)? args[0]: entries[0];
            int tloc  = calculateCLOC(filePath);
            System.out.println(tloc);
        } catch (Exception e) {e.printStackTrace();}       
    }
}
