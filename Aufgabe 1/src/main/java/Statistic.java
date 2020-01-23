import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class which contains statistic data and methods to calculate and log those.
 */
class Statistic {

	private ResultPresentation resultPresentation = new ResultPresentation();

	private List<Double> iList = new ArrayList<Double>();

	private Map<Integer, Integer> h = new TreeMap<Integer, Integer>();

	private final double freeSpaceSizeToUseInI = 2000.0;

	List<Double> getiList(){
		return iList;
	}

	Map<Integer, Integer> getH(){
		return h;
	}


	/**
	 * Maps occurrences of amounts of cars parked to the amount.
	 * @param parkList uses the parking lot list
	 */
	void updateH(List<ParkingFragment> parkList) {
		// Calculating H
		int carsParked = 0;
		for (ParkingFragment parkingFragment : parkList) {
			if (parkingFragment.isOccupied()) {
				carsParked++;
			}
		}
		if (this.h.containsKey(carsParked)) {
			this.h.put(carsParked, this.h.get(carsParked) + 1);
		} else {
			this.h.put(carsParked, 1);
		}
	}

	/**
	 * Checks for unusable free spaces which are wider than 2m, if a car could not be parked
	 * and adds the amount of those spots to the iList for further processing
	 * @param parkingFragmentList uses the parking lot list
	 */
	void updateI(List<ParkingFragment> parkingFragmentList) {
		double unusableCounter = 0;
		for (ParkingFragment parkingFragment : parkingFragmentList) {
			if (parkingFragment.getWidth() > freeSpaceSizeToUseInI && !parkingFragment.isOccupied()) {
				unusableCounter++;
			}
		}
		iList.add(unusableCounter);
	}

	/**
	 * Method which is called at each simulation pass to log results to console
	 */
	void logResults() {
		resultPresentation.logResult(h);
		resultPresentation.logResult(iList);
	}

}