import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class which represents the parking lot including a list of used and unused parking space
 */
public class ParkingLot {

	private Statistic stats;

	private ParkStrategy parkStrategy;

	private List<ParkingFragment> parkingFragmentList;

	private Random rnd;

	/**
	 * Constructor to create a parking lot with a specific width
	 * @param size Width in millimeter for the parking lot
	 */
	ParkingLot(int size, Random rnd, int mode) {
		this.parkingFragmentList =
				new LinkedList<>(Collections.singletonList(new ParkingFragment(size)));
		this.rnd = rnd;
		switch (mode) {     // may be expanded for more strategies
			case 1:
				this.parkStrategy = new StrategyBiggestMiddle(rnd);
				break;
			case 2:
				this.parkStrategy = new StrategyFirstFit(rnd);
				break;
			case 3:
				this.parkStrategy = new StrategyBestFit(rnd);
				break;
			default:
				this.parkStrategy = new StrategyBiggestMiddle(rnd);
				break;
		}
	}

	void createStatistic(){
	    stats = new Statistic();
    }

	/**
	 * Method which checks if there exists a car with expired parking duration and calls the
	 * strategy to remove it.
	 */
	void checkForLeaving() {
		List<ParkingFragment> listToRemove = new LinkedList<ParkingFragment>();
		// Finding candidates to remove
		for (ParkingFragment parkingFragment : parkingFragmentList) {
			parkingFragment.decParkingTimeIfCar();
			if (parkingFragment.getParkDuration() == 0) {
				listToRemove.add(parkingFragment);
			}
		}
		// Removing candidates and merging via strategy
		for (ParkingFragment parkingFragment : listToRemove) {
			parkStrategy.removeAndMerge(parkingFragment, parkingFragmentList);
		}
	}

	/**
	 * Method to park a random car, called from the core loop also updates statistics depending on the outcome of
	 * parkCar()
	 * If a car wasn't parked I is updated to look for barely not fitting spaces
	 * Otherwise H is updated because the parking lot changed
	 */
	void parkRandomCar() {
		if (!parkStrategy.parkCar(this.parkingFragmentList)) {
			stats.updateI(this.parkingFragmentList);
			stats.updateH(this.parkingFragmentList);
		}
	}

	/**
	 * Called when the simulation ends to log results to console
	 */
	Statistic endSimulation() {
	    stats.logResults();
	    return stats;
	}
}