import java.util.List;
import java.util.ListIterator;

/**
 * Interface which has to be implemented by different parking strategies
 */
public abstract class ParkStrategy {

	final double totalMinimumDistanceToLeftAndRight = 800.0;

	final double minimumDistanceLeftOrRight = 400.0;

	/**
	 * Method which is called if a car arrives at the parking lot and wants to take up a spot
	 * @param parkList Represents the parking lot where a car needs to be parked
	 * @return True if car found a space to stay, false otherwise
	 */
	public abstract boolean parkCar(List<ParkingFragment> parkList);

	/**
	 * Method to split specific free space and insert car
	 * @param newCarToPark car to insert in the list
	 * @param freeSpace space to slice up
	 * @param parkList list to handle
	 * @param sizeLeft free space size to the left of the car
	 * @param sizeRight free space size to the right of the car
	 */
	void splitAndInsert(ParkingFragment newCarToPark, ParkingFragment freeSpace, List<ParkingFragment> parkList, double sizeLeft, double sizeRight) {
		ListIterator<ParkingFragment> listIterator = parkList.listIterator(parkList.indexOf(freeSpace));
		// Iterator pointer is now in front of biggestFreeSpace
		listIterator.add(new ParkingFragment(sizeLeft)); // adds a new space to the left of current iterator
		listIterator.add(newCarToPark);         // adds the car to be parked to the right of the freshly inserted free space
		listIterator.next();                     // move to insert new space to the right
		listIterator.add(new ParkingFragment(sizeRight)); // adds a new space to the right after move
		listIterator.previous();                // Remove fragment in the middle
		listIterator.previous();
		listIterator.remove();
	}

	/**
	 * Method which is mainly used to remove a car from its parking spot if its parking duration expired
	 * @param fragmentToRemove Parking fragment which represents the car to be removed
	 */
	void removeAndMerge(ParkingFragment fragmentToRemove, List<ParkingFragment> parkList) {
		ListIterator<ParkingFragment> listIterator = parkList.listIterator(parkList.indexOf(fragmentToRemove));
		// If there is a car parked, then there should always be a previous and next, because of the free space used
		// to separate cars
		double newSizeOfFreeSpace = fragmentToRemove.getWidth();
		newSizeOfFreeSpace += listIterator.previous().getWidth();   // move iterator to previous fragment and add size
		listIterator.remove();
		listIterator.next();                                       // iterator is back at origin
		listIterator.remove();
		newSizeOfFreeSpace += listIterator.next().getWidth();       // add following from origin to new total free size
		listIterator.remove();
		listIterator.add(new ParkingFragment(newSizeOfFreeSpace));  // add the new element
	}

}