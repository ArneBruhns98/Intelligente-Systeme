package resultProcessing;

import dataClasses.Tooth;
import splitData.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static tunables.tunables.toleranceRange;

/**
 * Class which represents the results of a search for teeth.
 * Calculates recall, precision and f-score.
 * Used to evaluate the current method for finding teeth
 */
public class Result {

	private double recall;
	private double precision;
	private double fScore;

	/**
	 * Predicate for determining whether a found tooth is a real (correct) tooth.
	 */
	private BiPredicate<List<Tooth>, Tooth> isPossibleToothCorrect = (list, element) -> list.stream()
			.anyMatch(listEntry -> Math.sqrt(Math.pow(element.getxPos() - listEntry.getxPos(), 2) + Math.pow(element.getyPos() - listEntry.getyPos(), 2)) <= toleranceRange);

	/**
	 * Constructor which calculates the results
	 * @param predictions list of predicted tooth locations
	 * @param labels list of actual tooth locations
	 */
	public Result(List<Tooth> predictions, List<Tooth> labels, List<Tile> tiles) {
		// Filtering labels for duplicates
		List<Tooth> cleanedLabels = new ArrayList<>();
		for (Tooth t : labels) {
			if (!cleanedLabels.contains(t)) {
				cleanedLabels.add(t);
			}
		}
		this.recall = calculateRecall(predictions, cleanedLabels, tiles);
		this.precision = calculatePrecision(predictions, cleanedLabels, tiles);
		this.fScore = calculateFScore();
	}

/**
 * Calculate the value of the recall.
 * @param predictions list of predicted tooth locations
 * @param labels list of actual tooth locations
 * @param tiles list of all split data tiles
 * @return the value of the recall
 */
	private double calculateRecall(List<Tooth> predictions, List<Tooth> labels, List<Tile> tiles) {
		List<Tooth> finalLabels = tiles == null ? labels : getTestLabels(labels, tiles);
		double labelSize = finalLabels.size();

		Set<Tooth> result = predictions.stream()
				.filter(p -> isPossibleToothCorrect.test(finalLabels, p))
				.collect(Collectors.toSet());

		return Math.round((result.size() / labelSize) * 10000) / 10000.0;

	}

	/**
	 * Calculate the value of the precision.
	 * @param predictions list of predicted tooth locations
	 * @param labels list of actual tooth locations
	 * @param tiles list of all split data tiles
	 * @return the value of the precision
	 */
	private double calculatePrecision(List<Tooth> predictions, List<Tooth> labels, List<Tile> tiles) {
		List<Tooth> finalLabels = tiles == null ? labels : getTestLabels(labels, tiles);
		double predictionSize = predictions.size();

		Set<Tooth> result = predictions.stream()
				.filter(p -> isPossibleToothCorrect.test(finalLabels, p))
				.collect(Collectors.toSet());

		return Math.round((result.size() / predictionSize) * 10000) / 10000.0;
	}

	/**
	 * Calculate the value of the f-score.
	 * @return the value of the f-score
	 */
	private double calculateFScore() {
		return Math.round(((2 * this.precision * this.recall) / (this.precision + this.recall)) * 10000) / 10000.0;
	}

	@Override
	public String toString() {
		return "Recall: " + recall + "\n" +
				"Precision: " + precision + "\n" +
				"F-score: " + fScore + "\n";
	}

	/**
	 * Method to get the calculated recall value
	 * @return double which represents recall
	 */
	public double getRecall() {
		return recall;
	}

	/**
	 * Method to get the calculated precision value
	 * @return double which represents precision
	 */
	public double getPrecision() {
		return precision;
	}

	/**
	 * Method to get the calculated fscore value
	 * @return double which represents fscore
	 */
	public double getfScore() {
		return fScore;
	}

	/**
	 *	Method to get the labels in the trainings regions.
	 *
	 * @param labels list of actual tooth locations
	 * @param tiles list of all tiles which is use for th training
	 * @return list of all tooth locations considering the data from the tiles.
	 */
	private List<Tooth> getTestLabels(List<Tooth> labels, List<Tile> tiles){
		List<Tooth> testLabels = new LinkedList<>();

		for(Tooth tooth : labels)
			for (Tile tile : tiles)
				if (tile.getStartX() <= tooth.getxPos() && tooth.getxPos() <= tile.getStartX() + tile.getSizeX() && tile.getStartY() <= tooth.getyPos() && tooth.getyPos() <= tile.getStartY() + tile.getSizeY())
					testLabels.add(tooth);

		return testLabels;
	}
}
