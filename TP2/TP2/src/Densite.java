import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Densite {

    private static int totalTestClasses = 0;
    private static double totalCommentDensity = 0;

    private static void fileProcessing(File directory, String[] fileExtensions) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileProcessing(file, fileExtensions);
                } else {
                    if (isTestFile(file, fileExtensions)) {
                        double commentDensity = calculateCommentDensity(file);
                        totalCommentDensity += commentDensity;
                        totalTestClasses++;
                    }
                }
            }
        }
    }

    private static boolean isTestFile(File file, String[] extensions) {
        for (String extension : extensions) {
            if (file.getName().endsWith("." + extension) && file.getName().toLowerCase().contains("test")) {
                return true;
            }
        }
        return false;
    }

    private static double calculateCommentDensity(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int totalLOC = 0;
            int commentLines = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                totalLOC++;
                line = line.trim();

                if (line.startsWith("//") || line.startsWith("/*") || line.startsWith("*")) {
                    commentLines++;
                }
            }

            double commentDensity = totalLOC > 0 ? ((double) commentLines / totalLOC) * 100 : 0;
            return commentDensity;
        } catch (IOException e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
            return 0;
        }
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

        if (totalTestClasses > 0) {
            double CommentDensity = totalCommentDensity / totalTestClasses;
            System.out.println("Density: " + CommentDensity + "%");
        } else {
            System.out.println("No test classes found.");
        }
    }

}
