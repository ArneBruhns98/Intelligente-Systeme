package dataClasses;

/**
 * Class which represents actual teeth with position as label
 * or predicted teeth for further evaluation
 */
public class Tooth {
	private int xPos;
	private int yPos;
	private double height;

	/**
	 * Constructor if used as a label
	 * @param xPos x-Position the tooth is located at
	 * @param yPos y-Position the tooth is located at
	 */
	public Tooth(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Constructor if used as a predicted tooth
	 * @param xPos x-Position the tooth is located at
	 * @param yPos y-Position the tooth is located at
	 * @param height teeth's height
	 */
	public Tooth(int xPos, int yPos, double height) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.height = height;
	}

	/**
	 * Return the x-Position of the Position, where the tooth is located.
	 * @return x-Position
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * Return the y-Position of the Position, where the tooth is located.
	 * @return y-Position
	 */
	public int getyPos() {
		return yPos;
	}

	@Override
	public String toString() {
		return "XPos: " + xPos + " YPos: " + yPos + " Height: " + height + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tooth) return ((Tooth) obj).xPos == this.xPos && ((Tooth) obj).yPos == this.yPos;
		else return false;
	}
}
