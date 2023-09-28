package metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tassert {
  public static int getTassert(String filePathString){
    int tassert = 0;
    try{
      File file = new File(filePathString);
      FileReader reader = new FileReader(file);
      BufferedReader buffer = new BufferedReader(reader);
      String line;


       
      Pattern Pattern = java.util.regex.Pattern.compile("\\b(assert(ArrayEquals|NotArrayEquals|Equals|NotEquals|True|False|NotNull|Null|Throws|Same|NotSame)?|fail)\\b");
      while ((line = buffer.readLine())!= null){
        Matcher matcher = Pattern.matcher(line);
        while (matcher.find()){
          tassert++;
        }
      }
      buffer.close();
    }catch (Exception e) {e.printStackTrace();}
  return tassert;
  }
  
  public static void main(String[] args) {

    try {
        Scanner scanner = new Scanner(System.in);
        Boolean hasArgs = true;
        String[] entries = new String[1];
        Boolean isValid = metrics.tloc.isValid(args);
            while(!isValid) {
                hasArgs = false;
                System.out.println("Enter a valid java file path:");
                entries[0] = scanner.nextLine();
                isValid    = metrics.tloc.isValid(entries);
            }
            scanner.close();
            String filePath = (hasArgs)? args[0]: entries[0];
            int  tassert  = getTassert(filePath);
            System.out.println(tassert);
        } catch (Exception e) {e.printStackTrace();}    
    }
}


