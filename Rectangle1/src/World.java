import java.util.*;

/**
 * @author ryanjt5
 * @version 1-29-2020
 */
public class World {
    /**
     * Binary search tree where the rectangles are stored
     */
    private Tree<String, Rectangle> tree;
    /**
     * Stack of rectangle objects that are currently in the tree
     */
    private Stack<Rectangle> nodes;
    /**
     * Stack of integers representing the levels of the nodes in the tree
     */
    private Stack<Integer> levels;


    /**
     * creates a new world with an empty tree
     * and the two helper stacks
     */
    public World() {
        tree = new Tree<String, Rectangle>();
        levels = new Stack<Integer>();
        nodes = new Stack<Rectangle>();
    }


    /**
     * Getter for the BST
     * 
     * @return the reference to the binary search tree
     */
    public Tree<String, Rectangle> getTree() {
        return tree;
    }


    /**
     * Checks if the rectangle is allowed in the tree, then inserts it
     * 
     * @param nm
     *            name of rectangle
     * @param xcoord
     *            x coordinate
     * @param ycoord
     *            y coordinate
     * @param width
     *            width
     * @param height
     *            height
     * @return true if the rectangle was accepted and successfully inserted,
     *         else false
     */
    public boolean insert(
        String nm,
        int xcoord,
        int ycoord,
        int width,
        int height) {
        // check for valid name
        char first = nm.charAt(0);
        if (!Character.isLetter(first)) {
            return false;
        }
        if (width < 1 || height < 1) { // width and height need to be greater
                                       // than 0
            return false;
        }
        else if (xcoord + width > 1024 || ycoord + height > 1024) { 
            // make sure rectangle is fully in world
            return false;
        }
        else if (xcoord < 0 || ycoord < 0) { 
            return false;
        }
        else {
            Rectangle rec = new Rectangle(nm, xcoord, ycoord, width, height);
            tree.insert(nm, rec);
            return true;
        }
    }


    /**
     * Removes the rectangle with the specified name
     * 
     * @param name
     *            the key of the node to be removed
     * @return true if the rectangle was successfully removed, else false
     */
    public boolean removeName(String name) {
        if (tree.contains(name)) {
            tree.inOrder();
            Stack<Rectangle> tempNodes = tree.getNodes();
            while (!tempNodes.isEmpty()) {
                Rectangle temp = tempNodes.pop();
                if (temp.name().equals(name)) {
                    // remove temp from tree
                    tree.remove(temp.name(), temp);
                    return true;
                }
            }
            return false;
        }
        else {
            return false;
        }
    }


    /**
     * Removes the Rectangles with the specified dimensions from the tree
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @param w
     *            width
     * @param h
     *            height
     * @return true if the rectangle was successfully removed, else false
     */
    public boolean removeDimension(int x, int y, int w, int h) {
        tree.inOrder();
        Stack<Rectangle> tempNodes = tree.getNodes();
        while (!tempNodes.isEmpty()) {
            Rectangle temp = tempNodes.pop();
            if (temp.x() == x && temp.y() == y && temp.h() == h && temp
                .w() == w) {
                // remove temp from tree
                tree.remove(temp.name(), temp);
                return true;
            }
        }
        return false;
    }


    /**
     * Returns stack of rectangles in tree that have the key "name"
     * 
     * @param name
     *            name being searched for
     * @return stack of rectangle objects with key "name"
     */
    public Stack<Rectangle> search(String name) {
        tree.inOrder();
        Stack<Rectangle> temp = tree.getNodes();
        Stack<Rectangle> result = new Stack<Rectangle>();
        while (!temp.isEmpty()) {
            Rectangle rec = temp.pop();
            if (rec.name().equals(name)) {
                result.add(rec);
            }
        }
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }


    /**
     * Reports all rectangles that intersect with the rectangle
     * made by the given parameters
     * 
     * @param x
     *            x-coordinate of top left corner
     * @param y
     *            y-coordinate of top left corner
     * @param w
     *            width
     * @param h
     *            height
     * @return returns a stack of rectangles that intersect the query
     */
    public Stack<Rectangle> regionSearch(int x, int y, int w, int h) {
        Rectangle search = new Rectangle("temp", x, y, w, h);
        tree.inOrder(); // update the stack with all the nodes in it
        Stack<Rectangle> stack = tree.getNodes();
        Stack<Rectangle> ret = new Stack<Rectangle>();
        while (!stack.isEmpty()) {
            Rectangle rec = stack.pop();
            if (rec.intersect(search)) {
                ret.add(rec);
            }
        }
        return ret;
    }


    /**
     * Returns a printed list of the nodes and their depths
     */
    public void dump() {
        System.out.println("BST dump:");
        switchStacks();
        if (nodes.isEmpty()) { // dump an empty tree
            System.out.println("Node has depth 0, Value (null)");
        }
        while (!nodes.empty()) {
            Rectangle temp = nodes.pop();
            System.out.println("Node has depth " + levels.pop() + ", Value ("
                + temp.name() + ", " + temp.x() + ", " + temp.y() + ", " + temp
                    .w() + ", " + temp.h() + ")");
        }

        System.out.println("BST size is: " + tree.getSize());

    }


    /**
     * helper function that flips a stack to keep the inOrder
     * traversal when printing (since the nodes are loaded inOrder but would be
     * popped off the stack in the reverse order)
     * 
     * Function used in dump
     */
    private void switchStacks() {
        tree.inOrder();
        Stack<Rectangle> tempNodes = tree.getNodes();
        Stack<Integer> tempLevels = tree.getLevels();
        while (!tempNodes.isEmpty()) {
            nodes.add(tempNodes.pop());
            levels.add(tempLevels.pop());
        }
    }


    /**
     * produces a stack of all the unique pairs of intersecting rectangles
     * 
     * @return a stack of all intersecting rectangles
     */
    public Stack<Rectangle> intersections() {
        tree.inOrder();
        Stack<Rectangle> stack = tree.getNodes();
        Stack<Rectangle> ret = new Stack<Rectangle>();
        while (!stack.isEmpty()) {
            Rectangle out = stack.pop();
            Iterator<Rectangle> it = stack.iterator();
            for (Rectangle in : stack) {
                in = it.next();
                if (out.intersect(in)) {
                    ret.push(out);
                    ret.push(in);
                }
            }
        }
        return ret;
    }
}
