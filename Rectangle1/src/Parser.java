import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author ryanjt5
 * @version 1-29-2020
 *
 */
public class Parser {
    /**
     * Variables -- file holds input file, 
     * scan is for reading in the entire file
     * lineScanner is used for traversing an individual line 
     * world is where the magic happens
     */
    private File file;
    private Scanner scan;
    private Scanner lineScanner;
    private World world;


    /**
     * Constructor that takes in input file and initializes variables needed to
     * parse file and execute commands
     * 
     * @param fileName
     *            name of input file
     * @throws FileNotFoundException
     *             thrown if the file is invalid
     */
    public Parser(String fileName) throws FileNotFoundException {
        file = new File(fileName);
        scan = new Scanner(file);
        world = new World();
    }


    /**
     * Parses one line of the file at a time and executes the command for that
     * line
     * 
     * @param wholeLine
     *            one line of the input file given as a String
     */
    public void parseLine(String wholeLine) {
        lineScanner = new Scanner(wholeLine);
        String[] lineContents = new String[10];

        int counter = 0;
        while (lineScanner.hasNext()) {
            lineContents[counter] = lineScanner.next().trim();
            counter++;
        }

        String command = lineContents[0];

        if (command.equalsIgnoreCase("insert")) {
            String name = lineContents[1];
            int x = Integer.parseInt(lineContents[2]);
            int y = Integer.parseInt(lineContents[3]);
            int w = Integer.parseInt(lineContents[4]);
            int h = Integer.parseInt(lineContents[5]);
            if (world.insert(name, x, y, w, h)) {
                System.out.println("Rectangle accepted:(" + name + ", " + x
                    + ", " + y + ", " + w + ", " + h + ")");
            }
            else {
                System.out.println("Rectangle rejected:(" + name + ", " + x
                    + ", " + y + ", " + w + ", " + h + ")");
            }
        }

        else if (command.equalsIgnoreCase("remove")) {
            if (counter == 2) {
                // this is remove by name since there are only 2 parameters read
                // in
                String name = lineContents[1];
                if (!world.removeName(name)) {
                    System.out.println("Rectangle rejected " + name);
                }
            }
            else {
                // this is remove by dimension
                int x = Integer.parseInt(lineContents[1]);
                int y = Integer.parseInt(lineContents[2]);
                int w = Integer.parseInt(lineContents[3]);
                int h = Integer.parseInt(lineContents[4]);

                if (!world.removeDimension(x, y, w, h)) {
                    System.out.println("Rectangle rejected: (" + x + ", " + y
                        + ", " + w + ", " + h + ")");
                }
            }
        }

        else if (command.equalsIgnoreCase("regionsearch")) {
            int x = Integer.parseInt(lineContents[1]);
            int y = Integer.parseInt(lineContents[2]);
            int w = Integer.parseInt(lineContents[3]);
            int h = Integer.parseInt(lineContents[4]);
            Stack<Rectangle> results = world.regionSearch(x, y, w, h);
            if (!results.isEmpty()) {
                System.out.println("Rectangles intersecting region (" + x + ", "
                    + y + ", " + w + ", " + h + "):");
                if (results != null) {
                    while (!results.isEmpty()) {
                        System.out.println(results.pop().toString());
                    }
                }
            }
        }

        else if (command.equalsIgnoreCase("intersections")) {
            System.out.println("Intersection pairs:");
            Stack<Rectangle> pairs = world.intersections();
            if (pairs != null) {
                while (!pairs.isEmpty()) {
                    Rectangle temp1 = pairs.pop();
                    Rectangle temp2 = pairs.pop();
                    System.out.println(temp1.toString() + " : " + temp2
                        .toString());
                }
            }
        }

        else if (command.equalsIgnoreCase("search")) {
            String name = lineContents[1];
            Stack<Rectangle> result = world.search(name);
            if (result != null) {
                while (!result.isEmpty()) {
                    Rectangle temp = result.pop();
                    System.out.println("Rectangle found: " + temp.toString());
                }
            }
            else {
                System.out.println("Rectangle not found: " + name);
            }
        }

        else if (command.equalsIgnoreCase("dump")) {
            world.dump();
        }

        else {
            System.out.println("Error: command not recognized");
        }

        lineScanner.close();
    }


    /**
     * Parses the entire file 1 line at a time
     */
    public void parse() {
        while (scan.hasNextLine()) {
            String wholeLine = scan.nextLine().trim();
            if (!wholeLine.isEmpty()) {
                parseLine(wholeLine);
            }
        }
        scan.close();
        file = null;
        lineScanner = null;
    }
}
