import java.util.*;

/**
 * @author ryanjt5
 * @version 1-29-2020
 */
public class WorldTest extends student.TestCase {

    private World world;


    /**
     * sets up each test with an empty world
     */
    public void setUp() {
        world = new World();
    }


    /**
     * tests remove by dimension
     */
    public void testRemoveDimension() {
        // Removing invalid rectangle
        assertFalse(world.removeDimension(-4, 2, 4, 2));

        // Removing non existent rectangle
        assertFalse(world.removeDimension(4, 2, 23, 1));

        // Removing root when it's the only node in tree
        assertTrue(world.insert("rec1", 0, 0, 1, 1));
        assertTrue(world.removeDimension(0, 0, 1, 1));
        assertEquals(world.getTree().getSize(), 0);

        // Removing leaf (rec2)
        assertTrue(world.insert("rec5", 0, 0, 1, 1)); // valid
        assertTrue(world.insert("rec3", 13, 43, 1, 1)); // valid
        assertTrue(world.insert("rec2", 12, 43, 3, 3)); // valid
        assertTrue(world.insert("rec4", 31, 54, 1, 1)); // valid
        assertTrue(world.insert("rec7", 32, 4, 1, 1)); // valid
        assertTrue(world.insert("rec6", 6, 23, 1, 1)); // valid
        assertTrue(world.removeDimension(12, 43, 3, 3));
        assertEquals(world.getTree().getSize(), 5);
        // world.dump();

        // Removing node with one child (rec7), attempting to remove it a second
        // time
        assertTrue(world.removeDimension(32, 4, 1, 1));
        assertEquals(world.getTree().getSize(), 4);
        // world.dump();
        assertFalse(world.removeDimension(32, 4, 1, 1));
        // world.dump();

        // Removing node with 2 children (rec3)
        assertTrue(world.insert("rec2", 5, 34, 52, 2));
        assertTrue(world.removeDimension(13, 43, 1, 1));
        assertEquals(world.getTree().getSize(), 4);
        // world.dump();

        // Removing root (rec5)
        assertTrue(world.removeDimension(0, 0, 1, 1));
        assertEquals(world.getTree().getSize(), 3);
        // world.dump();

        // Remove correct node when there are duplicates
        assertTrue(world.insert("rec2", 43, 34, 1, 3));
        // world.dump();
        assertTrue(world.removeDimension(43, 34, 1, 3));
        // world.dump();
    }


    /**
     * tests remove by name
     */
    public void testRemoveName() {
        assertTrue(world.insert("rec1", 0, 0, 1, 1)); // valid
        assertFalse(world.insert("rec2", 0, 0, 0, 1)); // incorrect width
        assertTrue(world.removeName("rec1")); // valid
        assertFalse(world.removeName("rec2")); // not in tree
        assertFalse(world.removeName("rec1")); // already removed
        // add 3 nodes
        assertTrue(world.insert("b", 1, 1, 1, 1));
        assertTrue(world.insert("a", 1, 1, 1, 1));
        assertTrue(world.insert("c", 1, 1, 1, 1));

        // remove left node
        assertTrue(world.removeName("a"));
        assertEquals(world.getTree().getSize(), 2);

        // remove root
        assertTrue(world.removeName("b"));
        assertEquals(world.getTree().getSize(), 1);

        // try to remove the left node again
        assertFalse(world.removeName("a"));
        assertEquals(world.getTree().getSize(), 1);

        // remove final node in tree
        assertTrue(world.removeName("c"));
        assertEquals(world.getTree().getSize(), 0);
    }


    /**
     * tests search by name
     */
    public void testsearch() {
        Stack<Rectangle> empty = world.search("rec1");
        assertNull(empty); // test for empty stack
        world.insert("rec1", 0, 0, 1, 1);
        world.insert("rec1", 1, 0, 1, 1);
        world.insert("rec2", 1, 0, 1, 1); // all valid inserts
        world.insert("aec1", 1, 0, 1, 1);
        world.insert("zec1", 1, 0, 1, 1);
        Stack<Rectangle> stack = world.search("rec1"); // is also the root
        assertEquals("(rec1, 1, 0, 1, 1)", stack.pop().toString());
        assertEquals("(rec1, 0, 0, 1, 1)", stack.pop().toString());
        assertTrue(stack.isEmpty()); // multiple rectangles
        assertNull(world.search("name")); // empty search
    }


    /**
     * tests search by region
     */
    public void testRegionSearch() {
        world.insert("a", 1, 1, 3, 3);
        world.insert("b", 7, 1, 3, 3);
        world.insert("c", 7, 7, 3, 3);
        world.insert("d", 1, 7, 3, 3);
        world.insert("e", 4, 4, 3, 3); // totally inside
        world.insert("f", 2, 3, 7, 6); // duplicate rectangle
        world.insert("g", 4, 2, 3, 1); // only on x edge
        world.insert("h", 9, 4, 3, 1); // only on x+w edge
        world.insert("i", 4, 9, 3, 1); // only on y+h edge
        world.insert("j", 1, 4, 1, 3); // only on y edge
        Stack<Rectangle> stack = world.regionSearch(2, 3, 7, 6);
        assertEquals(6, stack.size()); // check to make sure the right amount of
                                       // nodes are present
        assertEquals("a", stack.pop().name());
        assertEquals("b", stack.pop().name());
        assertEquals("c", stack.pop().name());
        assertEquals("d", stack.pop().name());
        assertEquals("e", stack.pop().name());
        assertEquals("f", stack.pop().name());
        assertTrue(stack.isEmpty());
        Stack<Rectangle> temp = world.intersections();
        assertEquals(10, temp.size());
    }


    /**
     * tests insert
     */
    public void testInsert() {
        assertFalse(world.insert("4d", 5, 5, 5, 5));
        assertTrue(world.insert("rec1", 0, 0, 1, 1)); // valid
        assertFalse(world.insert("rec2", 0, 0, 0, 1)); // incorrect width
        assertFalse(world.insert("rec2", 0, 0, 1, 0)); // incorrect height
        assertFalse(world.insert("rec3", 1023, 1023, 2, 1)); // outside world x
        assertFalse(world.insert("rec3", 1023, 1023, 1, 2)); // outside world y
        assertFalse(world.insert("rec", -1, 0, 1, 1)); // negative x
        assertFalse(world.insert("rec", 0, -1, 1, 1)); // negative y
    }
}
