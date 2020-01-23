package splitData;

/**
 * Class which represents the tiles over the dates.
 */
public class Tile {

    private int startX;
    private int startY;
    private int sizeX;
    private int sizeY;

    /**
     * Creates a new object of the class Tile.
     *
     * @param startX start position on the x coordinate axis.
     * @param startY start position on the y coordinate axis.
     * @param sizeX length of the interval on the x coordinate axis.
     * @param sizeY length of the interval on the y coordinate axis.
     */
    Tile(int startX, int startY, int sizeX, int sizeY){
        this.startX = startX;
        this.startY = startY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * Returns the start position of the x-coordinate.
     * @return x-coordinate
     */
    public int getStartX(){
        return startX;
    }

    /**
     * Returns the start position of the y-coordinate.
     * @return y-coordinate
     */
    public int getStartY(){
        return startY;
    }

    /**
     * Returns the size on the x-coordinate.
     * @return size on the x-coordinate
     */
    public int getSizeX(){
        return sizeX;
    }

    /**
     * Returns the size on the y-coordinate.
     * @return size on the y-coordinate
     */
    public int getSizeY(){
        return sizeY;
    }

}
