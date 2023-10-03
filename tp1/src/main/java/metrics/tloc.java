/**
 * This is a class that calculate the size metric of non empty lines of code that are not comments
 * @authors: Kanty Louange Gakima(20184109) && Yann-Sibril Saah()
 */
package metrics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class tloc {
    /**
     * 
     * @param filePathString
     * @return: integer that represents the TLOC
     */
    public static int getTloc(String filePathString){

        //number of lines
        int tloc = 0; 
        int loc = 0; //number of lines of code
        int cloc = 0; //number of comments

        try{
            File file = new File(filePathString);
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine())!= null){
                String trimmedLine = line.trim();
                if(!trimmedLine.isEmpty()){
                    loc++;
                    if(trimmedLine.startsWith("*") || trimmedLine.startsWith("/")){
                        cloc++;
                    }
                } 
            }
            tloc = loc-cloc;
            buffer.close();
            }catch(Exception e){e.printStackTrace();}
        return tloc;
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
                System.out.println("Enter a valid java file path:");
                entries[0] = scanner.nextLine();
                isValid    = isValid(entries);
            }
            scanner.close();
            String filePath = (hasArgs)? args[0]: entries[0];
            int tloc    = getTloc(filePath);
            System.out.println(tloc);
        } catch (Exception e) {e.printStackTrace();}       
    }
}
