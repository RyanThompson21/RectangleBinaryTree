/**
 * @author ryanjt5
 * @version 1-29-2020
 */
public class Rectangle {
    /**
     * Rectangle variables to hold attributes (name, x coordinate, y coordinate,
     * width, height)
     */
    private String name;
    private int x;
    private int y;
    private int w;
    private int h;


    /**
     * create a new Rectangle with given parameters
     * 
     * @param nm
     *            name
     * @param xcoord
     *            upper left x coordinate
     * @param ycoord
     *            upper left y coordinate
     * @param width
     *            width of rectangle
     * @param height
     *            height of rectangle
     */
    public Rectangle(String nm, int xcoord, int ycoord, int width, int height) {
        name = nm;
        x = xcoord;
        y = ycoord;
        w = width;
        h = height;
    }


    /**
     * getter for name
     * 
     * @return name
     */
    public String name() {
        return name;
    }


    /**
     * getter for x coordinate
     * 
     * @return x coord
     */
    public int x() {
        return x;
    }


    /**
     * getter for y coordinate
     * 
     * @return y coordinate
     */
    public int y() {
        return y;
    }


    /**
     * getter for width
     * 
     * @return width
     */
    public int w() {
        return w;
    }


    /**
     * getter for height
     * 
     * @return height
     */
    public int h() {
        return h;
    }


    /**
     * Calculates the length
     * 
     * @return length
     */
    public int xlength() {
        return x + w;
    }


    /**
     * Calculates the height
     * 
     * @return height
     */
    public int ylength() {
        return y + h;
    }


    /**
     * puts the rectangles information into a string
     * 
     * @return string of rectangle info
     */
    public String toString() {
        return "(" + name + ", " + x + ", " + y + ", " + w + ", " + h + ")";
    }


    /**
     * Checks to see if two rectangles intersect
     * 
     * @param rec2
     *            the rectangle to compare against
     * @return true if the two rectangles intersect, else false
     */
    public boolean intersect(Rectangle rec2) {
        return !(this.y() >= rec2.ylength() || rec2.y() >= this.ylength() ||
            this.x() >= rec2.xlength() || rec2.x() >= this.xlength());
    }

}
