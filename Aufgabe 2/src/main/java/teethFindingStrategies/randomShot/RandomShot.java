package teethFindingStrategies.randomShot;

import dataClasses.Tooth;
import splitData.Tile;
import teethFindingStrategies.StrategyInterface;

import java.util.LinkedList;
import java.util.List;


/**
 * Test strategy for testing io and result calculations
 */
public class RandomShot implements StrategyInterface {

	private static final String name = "RandomShot";


	public List<Tooth> getPredictions(List<List<Double>> data, List<Tile> tiles) {
		int totalValues = 0;
		for (List<Double> l : data) {
			totalValues += l.size();
		}
		totalValues *= data.size();


		List<Tooth> predictions = new LinkedList<>();
		for (int i = 0; i < totalValues/10000; i++) {
			int randomXPos = (int) (Math.random() * ((data.size()) + 1));
			int randomYPos = (int) (Math.random() * ((data.get(1).size()) + 1));
			predictions.add(new Tooth(randomXPos, randomYPos));
		}
		return predictions;
	}

	@Override
	public String getName() {
		return name;
	}
}
