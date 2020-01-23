package tunables;

/**
 * Used to prevent the use of magic values and collect potential tunable values.
 */
public class tunables {

	/**
	 * String which is used to replace NaN values in data read from csv files
	 */
	public static final String notANumberReplacement = "0.0";

	/**
	 * Size of the tiles.
	 */
	public static final int tileSize = 200;

	/**
	 * Minimum gradient of data points.
	 */
	public static final double gradient = 4.3;

	/**
	 * Area of surrounding neighbors of a possible tooth.
	 */
	public static final int areaOfSurroundingNeighbors = 16;

	/**
	 * Minimum difference of height to the surrounding neighbors.
	 */
	public static final int minimalHeightDifference = 6;

	/**
	 * The seed for the random generator for the data splitting.
	 */
	public static final int seed = 42;

	/**
	 * The value how far a possible tooth may be from a correct tooth
	 */
	public static final int toleranceRange = 5;
}