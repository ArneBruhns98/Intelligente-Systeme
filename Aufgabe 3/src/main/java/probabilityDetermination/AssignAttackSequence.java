package probabilityDetermination;

import java.util.LinkedList;
import java.util.List;

import static tunables.Tunables.matrix_params;

/**
 * Class to assign the attack sequences to one of the fish.
 */
public class AssignAttackSequence {

    private List<List<String>> assignSailfish;
    private List<List<String>> assignMarlin;

    /**
     * Method to assign the attack sequences to one of the fish.
     * @param dates a list containing a list of attack sequences.
     * @param matrix_Marlin a matrix which represented the markov matrix of the Marlin fish.
     * @param matrix_Sailfish a matrix which represented the markov matrix of the Sailfish fish.
     */
    public void assignAttackSequences(List<List<String>> dates, double[][] matrix_Marlin, double[][] matrix_Sailfish) {
        assignSailfish = new LinkedList<>();
        assignMarlin = new LinkedList<>();
        double probability_Sailfish;
        double probability_Marlin;

        for(List<String> sequence : dates) {
            probability_Marlin = determinesProbability(sequence, matrix_Marlin);
            probability_Sailfish = determinesProbability(sequence, matrix_Sailfish);

            if(probability_Marlin > probability_Sailfish) assignMarlin.add(sequence);
            else if(probability_Marlin < probability_Sailfish) assignSailfish.add(sequence);
            else {
                assignSailfish.add(sequence);
                assignMarlin.add(sequence);
            }
        }
    }

    /**
     * Method to determine to which species a sequence belongs.
     * @param sequence a list with a attack sequence.
     * @param matrix a markov matrix of one of the species.
     * @return the probability that the attack sequence belongs to one of the fish.
     */
    private double determinesProbability(List<String> sequence, double[][] matrix){
        double determination = 1.0;

        for(int i = 0; i < sequence.size() - 1; i++)
            if(matrix[matrix_params.indexOf(sequence.get(i))][matrix_params.indexOf(sequence.get(i + 1))] != 0)
                determination *= matrix[matrix_params.indexOf(sequence.get(i))][matrix_params.indexOf(sequence.get(i + 1))];

        return determination;
    }

    /**
     * Returns a list contains the attack sequences which was assign to the Sailfish.
     * @return a list withe the attack sequences.
     */
    public List<List<String>> getAssignSailfish(){
        return assignSailfish;
    }

    /**
     * Returns a list contains the attack sequences which was assign to the Marlin fish.
     * @return a list withe the attack sequences.
     */
    public List<List<String>> getAssignMarlin(){
        return assignMarlin;
    }

}
