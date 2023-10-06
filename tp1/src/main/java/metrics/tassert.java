/*
 * Authors: Kanty Louange Gakima && Yann-Sibril Saah
 */


package metrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class tassert {

    public static int calculateTassert(String filePath) throws IOException {
        int tassert = 0;
      
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.trim().isEmpty() && !line.trim().startsWith("//")) {
                continue;
            }
            if (line.contains("assert") || (line.contains("fail"))) {
                tassert++;
            }
        }
        bufferedReader.close();
        fileReader.close();

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
            int  tassert  = calculateTassert(filePath);
            System.out.println(tassert);
        } catch (Exception e) {e.printStackTrace();}    
    }
}


