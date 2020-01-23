import org.uncommons.maths.random.GaussianGenerator;

import java.util.Random;

/**
 * Class to represent used and unused fragments which are organized and managed in the parking lot list.
 * You can see those fragments as free space between cars and used space where cars are parked.
 */
public class ParkingFragment {

	private double width;

	private boolean isOccupied = false;

	private int parkDuration = -1;

	private final double minimumWidth = 1500.0;

	private final double maximumWidth = 2700.0;

	private final int minimumParkduration = 0;

	private final int maximumParkduration = 2000;

	/**
	 * Constructor used if a car needs to be created for parking purpose.
	 */
	ParkingFragment(Random rnd) {
		GaussianGenerator widthGenerator = new GaussianGenerator(2050.0, 90.0, rnd);
		GaussianGenerator durationGenerator = new GaussianGenerator(900.0,200.0, rnd);
		this.width = widthGenerator.nextValue();
		while (this.width < minimumWidth || this.width > maximumWidth) this.width = widthGenerator.nextValue();
		this.parkDuration = durationGenerator.nextValue().intValue();
		while (this.parkDuration <= minimumParkduration || this.parkDuration > maximumParkduration) this.parkDuration = durationGenerator.nextValue().intValue();
		this.isOccupied = true;
	}

	/**
	 * Used if free space needs to be created.
	 * @param width the size of the free space
	 */
	ParkingFragment(double width) {
		this.width = width;
	}

	/**
	 * Method which is periodically called to decrease the remaining parking duration by 1 second each tick
	 * from the core loop
	 */
	void decParkingTimeIfCar() {
		if (this.parkDuration > 0) parkDuration--;
	}

	// Getter

	/**
	 * Method to get the current size of a parking fragment
	 * @return Returns the width as an int in millimeter
	 */
	double getWidth() {
		return width;
	}

	/**
	 * Method to check if the space is occupied by a car
	 * @return True if there is a car occupying, false if free space
	 */
	boolean isOccupied() {
		return isOccupied;
	}

	/**
	 * Method to retrieve the current rest parking duration
	 * @return Returns the remaining parking duration as an int interpreted as seconds
	 */
	int getParkDuration() {
		return parkDuration;
	}

	@Override
	public String toString() {
		return isOccupied ? "Car " + width + "/" + parkDuration + "   |"  : "Free " + width + "   |";
	}
}