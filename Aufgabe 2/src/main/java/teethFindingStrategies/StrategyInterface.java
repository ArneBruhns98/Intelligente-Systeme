package teethFindingStrategies;

import dataClasses.Tooth;
import splitData.Tile;

import java.util.List;

/**
 * Interface which methods all strategies have to implement.
 */
public interface StrategyInterface {

	/**
	 * Method to call for a prediction from a specific strategy
	 * @param data data to predict tooth locations
	 * @param tiles list of all split data tiles
	 * @return a list of predicted tooth positions
	 */
	List<Tooth> getPredictions(List<List<Double>> data, List<Tile> tiles);

	/**
	 * Method to get a string representation of the currently used strategies name
	 * @return the name of the strategy
	 */
	String getName();
}
