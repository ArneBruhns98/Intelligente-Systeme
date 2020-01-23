package resultPresentation;

import probabilityDetermination.AssignAttackSequence;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to determine the result of the assignment.
 */
public class Result {

    private double correctDeterminationsMarlin;
    private double correctDeterminationsSailfish;
    private double correctDeterminations;

    /**
     * Constructor which calculates the result.
     * @param dates_Marlin_eval a list containing the correct attacks of the Marlin fish.
     * @param dates_Sailfish_eval a list containing the correct attacks of the Sailfish.
     * @param aas_Marlin an object belonging to the assignment of the Marlin fish.
     * @param aas_Sailfish an object belonging to the assignment of the Sailfish.
     */
    public Result(List<List<String>> dates_Marlin_eval, List<List<String>> dates_Sailfish_eval, AssignAttackSequence aas_Marlin, AssignAttackSequence aas_Sailfish) {
        correctDeterminationsMarlin = calculateCorrectDeterminationsMarlin(dates_Marlin_eval, aas_Marlin.getAssignMarlin());
        correctDeterminationsSailfish = calculateCorrectDeterminationsSailfish(dates_Sailfish_eval, aas_Sailfish.getAssignSailfish());
        correctDeterminations = calculateCorrectDeterminations();
    }

    /**
     * Calculate the correct assignment of the Marlin fish in percent.
     * @param dates_Marlin_eval a list containing the correct attacks of the Marlin fish.
     * @param determinesMarlin a list containing the assigned attacks of the Marlin fish.
     * @return the correctly assigned attacks in percent.
     */
    private double calculateCorrectDeterminationsMarlin(List<List<String>> dates_Marlin_eval, List<List<String>> determinesMarlin){
        List<List<String>> result = dates_Marlin_eval.stream()
                .filter(determinesMarlin::contains)
                .collect(Collectors.toList());

        return Math.round(((double) result.size() / dates_Marlin_eval.size() * 1000)) / 1000.0;
    }

    /**
     * Calculate the correct assignment of the Sailfish in percent.
     * @param dates_Sailfish_eval a list containing the correct attacks of the Sailfish.
     * @param determinesSailfish a list containing the assigned attacks of the Marlin fish.
     * @return the correctly assigned attacks in percent.
     */
    private double calculateCorrectDeterminationsSailfish(List<List<String>> dates_Sailfish_eval, List<List<String>> determinesSailfish){
        List<List<String>> result = dates_Sailfish_eval.stream()
                .filter(determinesSailfish::contains)
                .collect(Collectors.toList());

        return Math.round(((double) result.size() / dates_Sailfish_eval.size() * 1000)) / 1000.0;
    }

    /**
     * Calculate the correct assignment (total) in percent.
     * @return the correctly assigned attacks in percent.
     */
    private double calculateCorrectDeterminations(){
        return (correctDeterminationsSailfish + correctDeterminationsMarlin) / 2;
    }

    @Override
    public String toString(){
        return "Correct determinations (Marlin): " + correctDeterminationsMarlin + "\n"
                + "Correct determinations (Sailfish): " + correctDeterminationsSailfish + "\n"
                + "Correct determinations (Total): " + correctDeterminations;
    }
}