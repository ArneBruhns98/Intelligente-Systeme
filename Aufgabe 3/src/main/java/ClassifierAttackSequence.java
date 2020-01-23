import fileHandling.FileImporter;
import probabilityDetermination.AssignAttackSequence;
import probabilityDetermination.MarkovMatrix;
import resultPresentation.Result;

import java.io.IOException;
import java.util.List;

/**
 * Main Class and entry point for exercise 3.
 * @author Felix Lohse & Arne Bruhns
 */
public class ClassifierAttackSequence {

    public static void main(String[] args) throws IOException {
        List<List<String>> dates_Marlin_train;
        List<List<String>> dates_Marlin_eval;
        List<List<String>> dates_Sailfish_train;
        List<List<String>> dates_Sailfish_eval;

        if(args.length == 4) {
            dates_Marlin_train = FileImporter.readData("./src/main/resources/" + args[0]);
            dates_Marlin_eval = FileImporter.readData("./src/main/resources/" + args[1]);
            dates_Sailfish_train = FileImporter.readData("./src/main/resources/" + args[2]);
            dates_Sailfish_eval = FileImporter.readData("./src/main/resources/" + args[3]);
        } else {
            System.err.println("Usage: java -jar filename.jar Marlin_train.txt Marlin_eval.txt Sailfish_train.txt Sailfish_eval.txt");
            return;
        }

        //Creates Markov Matrix
        MarkovMatrix matrix_Marlin = new MarkovMatrix();
        MarkovMatrix matrix_Sailfish = new MarkovMatrix();

        matrix_Marlin.insertDatesToFrequencyMatrix(dates_Marlin_train);
        matrix_Sailfish.insertDatesToFrequencyMatrix(dates_Sailfish_train);

        System.out.println(matrix_Marlin.probabilityMatrixToString());
        System.out.println(matrix_Sailfish.probabilityMatrixToString());

        //Assign Attack Sequences
        AssignAttackSequence assignAttackSequence_Marlin = new AssignAttackSequence();
        assignAttackSequence_Marlin.assignAttackSequences(dates_Marlin_eval, matrix_Marlin.getProbabilityMatrix(), matrix_Sailfish.getProbabilityMatrix());

        AssignAttackSequence assignAttackSequence_Sailfish = new AssignAttackSequence();
        assignAttackSequence_Sailfish.assignAttackSequences(dates_Sailfish_eval, matrix_Marlin.getProbabilityMatrix(), matrix_Sailfish.getProbabilityMatrix());

        //Print Result
        Result result = new Result(dates_Marlin_eval, dates_Sailfish_eval, assignAttackSequence_Marlin, assignAttackSequence_Sailfish);
        System.out.println(result);

    }

}