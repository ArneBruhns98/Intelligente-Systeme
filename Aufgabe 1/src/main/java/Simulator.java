import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.*;

/**
 * A parking simulation to evaluate different parking strategies.
 * This class contains the entry point and main loop
 * @author Felix Lohse, Arne Bruhns
 */
public class Simulator {

	private final static double probabilityForCarToArrive = 0.03;

	private final static int parkingLotSize = 98400;

	private static ResultPresentation resultPresentation = new ResultPresentation();

    public static void main(String[] args) {

	    Random rnd = new Random();

        Scanner scanner = new Scanner(System.in);

        System.out.print("How long should the simulation run? (in seconds | a day has 86400 seconds) ");
	    int duration = scanner.nextInt();

	    System.out.print("What strategy to use?\n" +
			    "1 : Biggest free space parked in the middle\n" +
			    "2 : First Fit always leaving 40cm to the left\n" +
			    "3 : Best Fit using the free space with least amount of waste\n" +
			    "\nYour Choice: ");
		int mode = scanner.nextInt();

	    System.out.print("How often do you want to run the simulation? ");
		int passes = scanner.nextInt();

        ParkingLot pLot = new ParkingLot(parkingLotSize, rnd, mode);

		List<Statistic> statistics = new LinkedList<>();

		for(int j = 0; j < passes; j++) {
			pLot.createStatistic();
			for (int i = 0; i < duration; i++) {
				pLot.checkForLeaving();
				if (rnd.nextDouble() < probabilityForCarToArrive) {
					pLot.parkRandomCar();
				}
			}
			System.out.println("\n" + (j + 1) + ". PASS:");
			statistics.add(pLot.endSimulation());
		}

		System.out.println("\nTOTAL RESULT:");
		calculateTotalResult(statistics);
    }

	/**
	 * Method which summarize the results of the passes.
	 * @param statistics A List of created statistics
	 */
	public static void calculateTotalResult(List<Statistic> statistics){
		Map<Integer, Double> h = new TreeMap<>();
    	double median = 0;
		double mean = 0;

    	for (Statistic stas: statistics) {
			median += new Median().evaluate(stas.getiList().stream().mapToDouble(d -> d).toArray());
			mean += new Mean().evaluate(stas.getiList().stream().mapToDouble(d -> d).toArray());

			stas.getH().forEach((k, v) ->{
				if(h.containsKey(k)) {
					h.put(k, h.get(k) + v);
				} else {
					h.put(k, v.doubleValue());
				}
			});
		}

		h.forEach((k, v) -> h.put(k, h.get(k)));

    	resultPresentation.logResult(h);
    	resultPresentation.logResult(median / statistics.size(), mean / statistics.size());
	}

}