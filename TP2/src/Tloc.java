import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//légère modification de TLOC du TP1 pour qu'on puisse lui donner en paramètre un repertoire et il renvoie le 
//total de lignes de codes des fichiers tests
public class Tloc {

    public static int calculateTLOC(File file) throws IOException {
        int tloc = 0;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    tloc += calculateTLOC(f);
                }
            }
        } else if (file.getName().toLowerCase().contains("test") && file.getName().endsWith(".java")) {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Verify if the line does not start with comment characters
                if (!line.trim().startsWith("//") && !line.trim().startsWith("/*") && !line.trim().startsWith("*")) {
                    tloc++;
                }
            }
            bufferedReader.close();
            fileReader.close();
        }

        return tloc;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java Tloc <directory_path>");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path");
            return;
        }

        int tloc = calculateTLOC(directory);
        System.out.println("TLOC: " + tloc);
    }
}
