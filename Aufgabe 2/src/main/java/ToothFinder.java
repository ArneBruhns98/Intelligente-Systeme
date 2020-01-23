import dataClasses.Tooth;
import fileHandling.FileImporter;
import resultProcessing.Result;
import splitData.DataSplitter;
import splitData.Tile;
import teethFindingStrategies.StrategyInterface;
import teethFindingStrategies.naivIterating.NaivIterating;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Main Class and entry point for exercise 2
 * @author Arne Bruhns & Felix Lohse
 */
public class ToothFinder {

	public static void main(String[] args) throws IOException, InterruptedException {
		List<List<Double>> test;
		List<Tooth> teeth;
		List<Tile> tiles;
		DataSplitter dataSplitter;
		int selection;

		if (args.length == 3) {
			 test = FileImporter.readData(args[0]);
			 teeth = FileImporter.readLabels(args[1]);
			 selection = Integer.parseInt(args[2]);
		} else {
			System.out.println("Usage: java -jar filename.jar data.csv labels.csv [selection of analysis method]");
			return;
		}

		switch (selection) {
			case 0: //Split data and use trainings data
				dataSplitter = new DataSplitter(test);
				dataSplitter.splitDates();
				tiles = dataSplitter.chooseRandomTrainingTiles();
				break;
			case 1: //Split data and use test data
				dataSplitter = new DataSplitter(test);
				dataSplitter.splitDates();
				tiles = dataSplitter.chooseRandomTrainingTiles();
				tiles = dataSplitter.chooseTestTiles(tiles);
				break;
			case 2: //Use all data
				tiles = null;
				break;
			default:
				System.out.println("Use analysis method selection 0 for 'Split data and use trainings data' \n" +
						"Use analysis method selection 1 for 'Split data and use test data' \n" +
						"Use analysis method selection 2 for 'Use all data'");
				return;
		}

		StrategyInterface strat = new NaivIterating();
		List<Tooth> testPredicted = strat.getPredictions(test, tiles);

		Result r = new Result(testPredicted, teeth, tiles);

		String predictionOutput = FileImporter.writePredictions(testPredicted, strat.getName());

		// Code for visualisation
		String path = new File("").getAbsolutePath() + "/";
		String[] cmd = new String[]{"python", path + "visualize.py", path + args[0], path + args[1],
				path + predictionOutput, r.getRecall() + "", r.getPrecision() + "", r.getfScore() + ""};
		System.out.println("Calling for visualisation: \n\n" + String.join(" ", cmd) + "\n");
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		pb.redirectError(ProcessBuilder.Redirect.INHERIT);
		Process p = pb.start();
		p.waitFor();
		System.out.println(r);
	}
}
