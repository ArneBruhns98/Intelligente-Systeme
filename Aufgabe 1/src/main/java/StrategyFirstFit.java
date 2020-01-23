import java.util.List;
import java.util.Random;

/**
 * Class which represents the strategy where if a car needs to be parked, this strategy uses
 * the first space that is big enough and leaves the minimum distance of 400mm to the left.
 */
public class StrategyFirstFit extends ParkStrategy {

	private Random rnd;

	StrategyFirstFit(Random rnd) { this.rnd = rnd; }

	/**
	 * Method which is called if a car arrives at the parking lot and wants to take up a spot
	 * @param parkList Represents the parking lot where a car needs to be parked
	 * @return True if car found a space to stay, false otherwise
	 */
	@Override
	public boolean parkCar(List<ParkingFragment> parkList) {
		ParkingFragment newCarToPark = new ParkingFragment(this.rnd);
		ParkingFragment fittingSpace = findFreeSpace(parkList, newCarToPark.getWidth() + totalMinimumDistanceToLeftAndRight);

		if (fittingSpace != null) {
			double newSizeRight = fittingSpace.getWidth() - newCarToPark.getWidth() - minimumDistanceLeftOrRight;
			splitAndInsert(newCarToPark, fittingSpace, parkList, minimumDistanceLeftOrRight, newSizeRight);
			return true;
		}

		return false;
	}

	/**
	 * Finds the first unused parking space with a minimum size
	 * @param parkList List to search in
	 * @return a free fragment, null if none is available
	 */
	private ParkingFragment findFreeSpace(List<ParkingFragment> parkList, double size) {
		for (ParkingFragment parkingFragment : parkList) {
			if (parkingFragment.getWidth() >= size) {
				return parkingFragment;
			}
		}
		return null;
	}
}
