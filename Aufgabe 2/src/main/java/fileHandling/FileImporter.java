package fileHandling;

import dataClasses.Tooth;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static tunables.tunables.notANumberReplacement;

/**
 * Class to handle io for csv files, reads them and creates usable data structures for further handling.
 * Also implements a static method to write predictions to a csv file for visualisation.
 */
public class FileImporter {

	/**
	 * Method to read data from csv needed to find tooth in, also prepares data to be handled as numeric values
	 * @param pathToCsv path to csv file
	 * @return a two dimensional list of doubles which represent
	 * @throws IOException if file is not present
	 */
	public static List<List<Double>> readData(String pathToCsv) throws IOException {
		List<List<Double>> values = new ArrayList<>();
		File csvFile = new File(pathToCsv);
		if (csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				Collections.replaceAll(Arrays.asList(data), "NaN", notANumberReplacement);
				values.add(Arrays.stream(data).map(Double::parseDouble).collect(Collectors.toList()));
			}
			csvReader.close();
		}
		System.out.println("Loading data finished.\n");
		return values;
	}

	/**
	 * Method to read labels from csv file for further processing and evaluating our results
	 * @param pathToCsv path to csv file
	 * @return a list of known teeth, by x and y pos
	 * @throws IOException if file is not present
	 */
	public static List<Tooth> readLabels(String pathToCsv) throws IOException {
		List<Tooth> values = new ArrayList<>();
		File csvFile = new File(pathToCsv);
		if (csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				List<Integer> temp = Arrays.stream(data).map(Integer::parseInt).collect(Collectors.toList());
				values.add(new Tooth(temp.get(0), temp.get(1)));
			}
			csvReader.close();
		}
		System.out.println("Loading labels finished.\n");
		return values;
	}

	/**
	 * Intended to use to write predicted tooth position to a file, same format as the labels
	 * @param predictionList list of predicted teeth
	 * @param strategyName name of strategy which was used to generate predictions
	 * @return the new file name where predictions are written to
	 * @throws IOException if file couldn't be written
	 */
	public static String writePredictions(List<Tooth> predictionList, String strategyName) throws IOException {
		String filename = strategyName + "-predictions" +
				new SimpleDateFormat(".yyyy-MM-dd_HH-mm-ss").format(new Date())
				+ ".csv";
		FileWriter csvWriter = new FileWriter(filename);

		for (Tooth predicted : predictionList) {
			csvWriter.append(String.valueOf(predicted.getxPos())).append(",").append(String.valueOf(predicted.getyPos()));
			csvWriter.append("\n");
		}
		System.out.println("Writing predictions finished.\n");
		csvWriter.flush();
		csvWriter.close();
		return filename;
	}
}
