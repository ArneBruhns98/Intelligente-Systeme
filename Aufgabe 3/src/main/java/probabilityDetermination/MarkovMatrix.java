package probabilityDetermination;

import java.util.Arrays;
import java.util.List;

import static tunables.Tunables.*;

/**
 * Class to creates a probability matrix.
 */
public class MarkovMatrix {

    private int[][] frequencyMatrix = new int[matrix_size_x][matrix_size_y];
    private double[][] probabilityMatrix = new double[matrix_size_x][matrix_size_y];
    private String output_frequency = "";
    private String output_probability = "";

    /**
     * Method to fill the frequency matrix.
     * @param dates a list with a list of attack sequences.
     */
    public void insertDatesToFrequencyMatrix(List<List<String>> dates){
        Arrays.stream(frequencyMatrix).forEach(a -> Arrays.fill(a, 1));

        for(List<String> sequence : dates) {
            for(int i = 0; i < sequence.size() - 1; i++) {
                frequencyMatrix[matrix_params.indexOf(sequence.get(i))][matrix_params.indexOf(sequence.get(i+1))] += 1;
            }
        }

        determinesProbability();
    }

    /**
     * Method to determines the probability in the frequency matrix deeping on the result of the rows.
     */
    private void determinesProbability(){
        double rowTotalValue;

        for(int i = 0; i < matrix_size_x; i++){
            rowTotalValue = 0;
            for(int j = 0; j < matrix_size_y; j++)
                rowTotalValue += frequencyMatrix[i][j];
            for(int j = 0; j < matrix_size_y; j++)
                probabilityMatrix[i][j] = frequencyMatrix[i][j] / rowTotalValue;
        }
    }

    /**
     * Method to convert the frequency matrix in a table.
     * @return table containing the values of the frquency matrix.
     */
    public String frequencyMatrixToString(){
        if (output_frequency.length() != 0) return output_frequency;

        output_frequency = output_frequency.concat("The frequency matrix: (approach, bill_use, prey_contact, open_mouth, ingest, leave - rows and column designations)\n");

        for(int i = 0; i < matrix_size_x; i++){
            output_frequency = output_frequency.concat("\n");
            for(int j = 0; j < matrix_size_y; j++)
                output_frequency = output_frequency.concat("\t\t" + frequencyMatrix[i][j]);
        }

        return output_frequency.concat("\n");
    }

    /**
     * Method to convert the probability matrix in a table.
     * @return table containing the values of the probability matrix.
     */
    public String probabilityMatrixToString(){
        if (output_probability.length() != 0) return output_probability;

        output_probability = output_probability.concat("The probability matrix: (approach, bill_use, prey_contact, open_mouth, ingest, leave - rows and column designations)\n");

        for(int i = 0; i < matrix_size_x; i++){
            output_probability = output_probability.concat("\n");
            for(int j = 0; j < matrix_size_y; j++)
                output_probability = output_probability.concat(String.format("%.4f", probabilityMatrix[i][j]) + "\t\t");
        }

        return output_probability.concat("\n");
    }

    /**
     * Returns the probability matrix.
     * @return probability matrix.
     */
    public double[][] getProbabilityMatrix(){
        return probabilityMatrix;
    }

}