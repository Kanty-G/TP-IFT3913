import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Densite {

    private static int totalLOC = 0;
    private static int totalCommentLines = 0;

    //méthode pour parcourir tous les fichier du répertoire, et calcule le nombre de lignes qui sont des
    //commentaires que pour les fichiers tests seulement
    
    private static void fileProcessing(File directory, String[] fileExtensions) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileProcessing(file, fileExtensions);
                } else {
                    if (isTestFile(file, fileExtensions)) {
                        totalCommentLines += calculateCommentLines(file);
                    }
                }
            }
        }
    }

    //méthode qui vérifie si un fichier est un fichier Test
    private static boolean isTestFile(File file, String[] extensions) {
        for (String extension : extensions) {
            if (file.getName().endsWith("." + extension) && file.getName().toLowerCase().contains("test")) {
                return true;
            }
        }
        return false;
    }

    //on calcule le nombre de lignes qui sont des commentaires
    private static int calculateCommentLines(File file) {
        int commentLines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalLOC++;
                line = line.trim();

                if (line.startsWith("//") || line.startsWith("/*") || line.startsWith("*")) {
                    commentLines++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
        }
        return commentLines;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java CommentDensityCalculator <directory_path>");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);
        String[] fileExtensions = {"java"};

        fileProcessing(directory, fileExtensions);

        if (totalLOC > 0) {
            double CommentDensity = ((double) totalCommentLines / totalLOC) * 100;
            System.out.println("Densité: " + CommentDensity + "%");
        } else {
            System.out.println("No test classes found.");
        }
    }
}
