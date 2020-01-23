import java.util.List;
import java.util.Random;

/**
 * Class which represents the strategy where if a car needs to be parked, this strategy uses
 * the biggest available free space and simulates parking the car in the middle of this free space
 * by handling the parking lots list.
 */
public class StrategyBiggestMiddle extends ParkStrategy {

	private Random rnd;

	StrategyBiggestMiddle(Random rnd) {
		this.rnd = rnd;
	}

	/**
	 * Method which is called if a car arrives at the parking lot and wants to take up a spot
	 * @param parkList Represents the parking lot where a car needs to be parked
	 * @return True if car found a space to stay, false otherwise
	 */
	@Override
	public boolean parkCar(List<ParkingFragment> parkList) {
		ParkingFragment newCarToPark = new ParkingFragment(this.rnd);
		ParkingFragment biggestFreeSpace = findBiggestFreeSegment(parkList);

		if ((newCarToPark.getWidth() + totalMinimumDistanceToLeftAndRight) <= biggestFreeSpace.getWidth()) {
			// Calculating space to the left and right of now parked car
			double sizeOfNewSpacesLeftAndRight = (biggestFreeSpace.getWidth() - (newCarToPark.getWidth())) / 2;

			splitAndInsert(newCarToPark, biggestFreeSpace, parkList, sizeOfNewSpacesLeftAndRight, sizeOfNewSpacesLeftAndRight);
			return true;
		}
		return false;
	}

	/**
	 * Finds the biggest unused free space
	 * @param parkList List to search in
	 * @return the biggest unused segment
	 */
	private ParkingFragment findBiggestFreeSegment(List<ParkingFragment> parkList) {
		ParkingFragment biggest = null;
		for (ParkingFragment parkingFragment : parkList) {
			if ((biggest == null || parkingFragment.getWidth() > biggest.getWidth()) && !parkingFragment.isOccupied()) {
				biggest = parkingFragment;
			}
		}
		return biggest;
	}

}