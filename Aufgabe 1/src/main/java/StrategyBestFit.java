import java.util.List;
import java.util.Random;

/**
 * Class which represents the strategy where if a car needs to be parked, this strategy uses
 * the free space where if the car is parked the least amount of space is wasted. Leaves the minimum distance
 * of 400mm to the right and the wasted space is added on the right
 */
public class StrategyBestFit extends ParkStrategy {

	private Random rnd;

	StrategyBestFit(Random rnd) { this.rnd = rnd; }

	/**
	 * Method which is called if a car arrives at the parking lot and wants to take up a spot
	 * @param parkList Represents the parking lot where a car needs to be parked
	 * @return True if car found a space to stay, false otherwise
	 */
	@Override
	public boolean parkCar(List<ParkingFragment> parkList) {
		ParkingFragment newCarToPark = new ParkingFragment(this.rnd);
		ParkingFragment fittingSpace = findBestFittingSpace(parkList, newCarToPark.getWidth() + totalMinimumDistanceToLeftAndRight);

		if (fittingSpace != null) {
			double newSizeRight = fittingSpace.getWidth() - newCarToPark.getWidth() - minimumDistanceLeftOrRight;
			splitAndInsert(newCarToPark, fittingSpace, parkList, minimumDistanceLeftOrRight, newSizeRight);
			return true;
		}
		return false;
	}

	/**
	 * Finds free space with the least amount of wasted space if the car is parked
	 * @param parkList List to search in
	 * @return a free fragment, null if none is available
	 */
	private ParkingFragment findBestFittingSpace(List<ParkingFragment> parkList, double size) {
		ParkingFragment bestFoundYet = null;
		// Initializes the variable to the size of the parking lot that is the highest amount of possible waste
		double bestFoundYetWaste = parkList.stream().map(ParkingFragment::getWidth).reduce(0.0, Double::sum);

		for (ParkingFragment parkingFragment : parkList) {
			double currentWaste = parkingFragment.getWidth() - size;
			if ((currentWaste >= 0 && (currentWaste < bestFoundYetWaste)) && !parkingFragment.isOccupied()) {
				bestFoundYet = parkingFragment;
				bestFoundYetWaste = currentWaste;
			}
		}
		return bestFoundYet;
	}
}
