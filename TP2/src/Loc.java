import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Loc {

    public static int calculateLOC(File file) throws IOException {
        int loc = 0;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    loc += calculateLOC(f);
                }
            }
        } else if (file.getName().toLowerCase().contains("test") && file.getName().endsWith(".java")) {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                    loc++;
                }

            bufferedReader.close();
            fileReader.close();
        }

        return loc;
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

        int loc = calculateLOC(directory);
        System.out.println("Loc: "+ loc);
    }
}
