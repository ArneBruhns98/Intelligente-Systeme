package tunables;

import java.util.List;

/**
 * Used to prevent the use of magic values and collect potential tunable values.
 */
public class Tunables {

    /**
     * Size of the matrix in x direction.
     */
    public static final int matrix_size_x = 6;

    /**
     * Size of the matrix in y direction.
     */
    public static final int matrix_size_y = 6;

    /**
     * The parameters of the matrix.
     */
    public static final List<String> matrix_params = List.of("approach", "bill_use", "prey_contact", "open_mouth", "ingest", "leave");

}
