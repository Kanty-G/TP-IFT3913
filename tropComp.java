import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class tropComp {

    private static tls tls = new tls();

    public static void main(String[] args) {

        String outputPath = "";
        int seuil = 100;
        int c = 0;

        for(String s : args){
            System.out.println(s);
        }

        String filePath   = args[c++];

        try{
            outputPath = args[c++];
        }catch(Exception e){
        }

        try{
            System.out.println(c);
            seuil = Integer.parseInt(args[c++]);
        }catch(Exception e){
        }
        

        try{

            //tls.calculateTLS(filePath,outputPath);

            tropcomp(filePath, outputPath, seuil);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void tropcomp(String filePath, String outputPath, int seuil){

        List<String[]> csvData = tls.calculateTLS(filePath, outputPath);
        List<String[]> complexData = new ArrayList<>();

        for(String[] row : csvData){
            if(isTopXPercent(csvData, 3, seuil, Double.parseDouble(row[3])) && (isTopXPercent(csvData, 5, seuil, Double.parseDouble(row[5])))){
                complexData.add(row);
            }
        }

        if(!outputPath.equals("")){
            tls.exportToCSV(complexData, outputPath);
        }
    }


    public static boolean isTopXPercent(List<String[]> csvData, int column, double x, double valueToCheck) {
        // Validate inputs
        if (csvData == null || column < 0 || x < 0 || x > 100) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        // Extract values from the specified column
        List<Double> columnValues = getColumnValues(csvData, column);

        // Sort the values in descending order
        Collections.sort(columnValues, Collections.reverseOrder());

        // Calculate the threshold value for the top x%
        int totalValues = columnValues.size();
        int thresholdIndex = (int) Math.ceil((x / 100.0) * totalValues);
        
        // Check if the given value is in the top x%
        if (thresholdIndex <= 0) {
            return false;  // All values are in the top 0%, including the given value
        } else if (thresholdIndex >= totalValues) {
            return true; // All values are in the top 100%, excluding the given value
        } else {
            double thresholdValue = columnValues.get(thresholdIndex - 1);
            return valueToCheck >= thresholdValue;
        }
    }

    private static List<Double> getColumnValues(List<String[]> csvData, int column) {
        // Extract numeric values from the specified column
        List<Double> columnValues = new ArrayList<>();
        for (String[] row : csvData) {
            if (column < row.length) {
                try {
                    double value = Double.parseDouble(row[column]);
                    columnValues.add(value);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric values in the column
                }
            }
        }
        return columnValues;
    }
}
