package splitData;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static tunables.tunables.seed;
import static tunables.tunables.tileSize;

/**
 * Class which split the data in trainings and test data.
 */
public class DataSplitter {

    private List<List<Double>> data;
    private List<Tile> tiles = new LinkedList<>();

    /**
     * Creates a new object of the class Tile.
     * @param test all data points
     */
    public DataSplitter(List<List<Double>> test){
        data = test;
    }

    /**
     * Method which assigns the data to the tiles.
     */
    public void splitDates(){
        for(int i = 0; i < data.size() + tileSize; i += tileSize) {
            for(int j = 0; j < data.get(0).size() + tileSize; j += tileSize){
                tiles.add(new Tile(i, j, i > data.size() ? i - data.size(): tileSize, j > data.get(0).size() ? j - data.get(0).size() : tileSize));
            }
        }
    }

    /**
     * Method which randomly selects training data.
     * @return list of tiles which contains the training data.
     */
    public List<Tile> chooseRandomTrainingTiles(){
        List<Tile> chosenTiles = new LinkedList<>();
        Random random = new Random(seed);

        for(int i = 0; i < tiles.size() / 2 ; i++){
            Tile tile = tiles.get(random.nextInt(tiles.size() - 1));
            if(chosenTiles.contains(tile)) i--;
            else chosenTiles.add(tile);
        }

        return chosenTiles;
    }

    /**
     * Method which selects test data.
     * @param trainingTiles list of tiles which contains the trainings data
     * @return list of tiles which contains the test data.
     */
    public List<Tile> chooseTestTiles(List<Tile> trainingTiles){
        List<Tile> chosenTiles = new LinkedList<>();

        for(Tile tile: tiles) if(!trainingTiles.contains(tile)) chosenTiles.add(tile);

        return chosenTiles;
    }
}