package teethFindingStrategies.naivIterating;

import dataClasses.Tooth;
import splitData.Tile;
import teethFindingStrategies.StrategyInterface;

import java.util.ArrayList;
import java.util.List;

import static tunables.tunables.*;

public class NaivIterating implements StrategyInterface {

	private static final String name = "NaivIterating";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Tooth> getPredictions(List<List<Double>> data, List<Tile> tiles) {
		List<Tooth> foundHighest = new ArrayList<>();
		int rowCounter = 0;
		for (List<Double> doubleList : data) {
			for (Double dataPoint : doubleList) {
				if(dataPointIsTestDate(tiles, rowCounter, doubleList.indexOf(dataPoint)) && isHighestInNeighborhood(data, dataPoint, rowCounter, doubleList.indexOf(dataPoint)))
					foundHighest.add(new Tooth(rowCounter, doubleList.indexOf(dataPoint), dataPoint));
			}
			rowCounter++;
		}
		return foundHighest;
	}

	/**
	 * Determines if a data point is a tooth.
	 * @param data data to predict tooth locations
	 * @param dataPoint a data point from the data
	 * @param rowCounter current row value
	 * @param column current columns value
	 * @return true if the data point is a tooth
	 */
	private boolean isHighestInNeighborhood(List<List<Double>> data, Double dataPoint, int rowCounter, int column){
		int heightDifferences = 0;
		int startX;
		int endX;
		int startY = Math.max(rowCounter - areaOfSurroundingNeighbors, 0);
		int endY = Math.min(rowCounter + areaOfSurroundingNeighbors, data.size() - 1);

		int real_x;

		double gradient_temp_x = 0.0;
		double gradient_temp_y = 0.0;

		for(int y = startY; y < endY; y++) {
			startX = column - areaOfSurroundingNeighbors;
			endX = column + areaOfSurroundingNeighbors;
			for(int x = startX; x < endX; x++){
				real_x = x;
				if(x < 0) real_x = data.get(0).size() + x;
				if(x >= data.get(0).size()) real_x = x - data.get(0).size();

				if(data.get(y).get(real_x) > dataPoint) return false;
				if((dataPoint - data.get(y).get(real_x)) > heightDifferences) heightDifferences = Double.valueOf(dataPoint - data.get(y).get(real_x)).intValue();

				gradient_temp_x += dataPoint - data.get(y).get(real_x);
				if(x == column) gradient_temp_y += dataPoint - data.get(y).get(real_x);
			}
		}

		if((gradient_temp_y / (2 * areaOfSurroundingNeighbors)) < gradient) return false;
		if((gradient_temp_x / (2 * areaOfSurroundingNeighbors)) < gradient) return false;
		return heightDifferences >= minimalHeightDifference;
	}

	/**
	 * Determines whether a data point belongs to the list of the tiles.
	 * @param tiles list of all split data tiles
	 * @param rowCounter current row value
	 * @param column current column value
	 * @return true if the data point belongs to one of the tiles in the list.
	 */
	private boolean dataPointIsTestDate(List<Tile> tiles, int rowCounter, int column){
		if(tiles == null) return true;

		for(Tile tile: tiles) {
			if(tile.getStartX() <= rowCounter && rowCounter <= tile.getStartX() + tile.getSizeX() && tile.getStartY() <= column && column <= tile.getStartY() + tile.getSizeY()) {
				return true;
			}
		}

		return false;
	}
}
