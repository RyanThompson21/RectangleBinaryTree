import java.io.FileNotFoundException;

/**
 * @author ryanjt5
 * @version 1-29-2020
 *
 */
public class Rectangle1 {

    /**
     * runs the parser on a given input file
     * 
     * @param args
     *             input file
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Parser p = new Parser(args[0]);
        p.parse();
    }
}
