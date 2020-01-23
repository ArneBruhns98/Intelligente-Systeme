package fileHandling;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to handle io for text files, reads them and creates usable data structures for further handling.
 */
public class FileImporter {

    /**
     * Method to read data from text files and prepares them for further handling.
     * @param pathToFile path to the text file.
     * @return a two dimensional list of strings which represent the action of the attack sequence.
     * @throws IOException if file is not present
     */
    public static List<List<String>> readData(String pathToFile) throws IOException {
        List<List<String>> dates = new ArrayList<>();
        File file = new File(pathToFile);

        if(file.isFile()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(pathToFile));
            String row;
            while((row = fileReader.readLine()) != null) {
                String[] data = row.split(" ");
                dates.add(Arrays.stream(data).collect(Collectors.toList()));
            }
        } else {
            System.err.println("Four files are required. You have to enter them in the resources directory.");
        }

        dates.remove(0);
        return dates;
    }

}