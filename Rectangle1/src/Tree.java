
// import java.util.Iterator;
import java.util.Stack;

/**
 * a generic key, value Binary tree
 * an iterator class is commented out in this file
 * 
 * @author ryanjt5
 * @version 2/17/2020
 * @param <Key>
 *            the lookup item
 * @param <Value>
 *            value associated with the key
 */
public class Tree<Key extends Comparable<Key>, Value> {
    private Stack<Value> nodes;
    private Stack<Integer> levels;
    private Node root;
    private int size;
    // implements Iterable<Key> put that back in when you need the iterator


    /**
     * gets the root node
     * 
     * @return root node
     */
    public Node getRoot() {
        return root;
    }


    /**
     * private node class for a key value BST
     * 
     * @author ryanjt5
     * @version 1-29-20
     */
    private class Node {
        private Key key;
        private Value value;
        private int level; // extra variable to handle duplicates
        private Node left;
        private Node right;


        /**
         * creates a new node with given key and value
         * 
         * @param k
         *            key
         * @param v
         *            value
         */
        public Node(Key k, Value v) {
            key = k;
            value = v;
            left = null;
            right = null;
            level = 0;
        }


        /**
         * gets the level (depth) of the node
         *
         */
        public int getLevel() {
            return level;
        }


        /**
         * sets the nodes key equal to given key
         * 
         * @param k
         *            new key
         */
        public void setKey(Key k) {
            key = k;
        }


        /**
         * sets nodes value to given value
         * 
         * @param v
         *            new value
         */
        public void setVal(Value v) {
            value = v;
        }


        /**
         * gets the node's key
         * 
         * @return the node's key
         */
        public Key getKey() {
            return key;
        }


        /**
         * gets the node's value
         * 
         * @return node's value
         */
        public Value getValue() {
            return value;
        }


        /**
         * gets the nodes left child
         *
         * @return node's left child
         */
        public Node getLeft() {
            return left;
        }


        /**
         * sets the node's left child to given node
         * 
         * @param child
         *            new left child
         */
        public void setLeft(Node child) {
            left = child;
        }


        /**
         * gets node's right child
         *
         * @return node's right child
         */
        public Node getRight() {
            return right;
        }


        /**
         * sets node's right child to given node
         * 
         * @param child
         *            new right child
         */
        public void setRight(Node child) {
            right = child;
        }
    } // END NODE CLASS

// 
// /**
// * iterator for the tree
// *
// * @author ryanjt5
// * @version 2/17/2020
// */
// public class iterator implements Iterator<Key> {
// private Stack<Node> stack;
//
//
// /**
// * creates a new iterator that contains all the tree elements
// *
// * @param tree
// * the tree iterator will be used for
// * @param root
// * the root of the tree
// */
// public iterator(Tree<Key, Value> tree, Node root) {
// stack = new Stack<Node>();
// push(root);
// }
//
//
// /**
// * pushes all nodes onto the stack
// *
// * @param node
// * current node
// */
// private void push(Node node) {
// while (node != null) {
// stack.push(node);
// node = node.getLeft();
// }
// }
//
//
// /**
// * checks if the iterator has another element to visit
// *
// * @return true if stack has no elements
// */
// public boolean hasNext() {
// return !stack.isEmpty();
// }
//
//
// /**
// * pops the next element off the stack
// *
// * @return the next element
// */
// public Key next() {
// if (!stack.isEmpty()) {
// Node node = stack.pop();
// push(node.getRight());
// return node.getKey();
// }
// else {
// return null;
// }
// }
// } // END ITERATOR CLASS


    /**
     * generates a new tree with null root and size 0
     */
    public Tree() {

        root = null;
        size = 0;
    }


// @Override
// /**
// * creates new iterator for tree
// *
// * @return new iterator
// */
// public Iterator<Key> iterator() {
// return new iterator(this, root);
// }

    /**
     * getter for size of tree
     * 
     * @return size of tree
     */
    public int getSize() {
        return size;
    }


    /**
     * checks to see if given key is in the tree
     * 
     * @param in
     *            given key
     * @return true if the key exists and false if not
     */
    public boolean contains(Key in) {
        return get(in) != null;
    }


    /**
     * searches for the value of given key
     * 
     * @param in
     *            given key
     * @return the value of the given key, null if invalid key
     */
    public Value get(Key in) {
        return get(root, in);
    }


    /**
     * helper for search
     * 
     * @param x
     *            root of current subtree
     * @param in
     *            key being searched for
     * @return null if either key or node is null, or the value of the key
     */
    private Value get(Node x, Key in) {
        if (in == null || x == null) {
            return null;
        }
        int comp = in.compareTo(x.key);
        if (comp < 0) {
            return get(x.left, in);
        }
        else if (comp > 0) {
            return get(x.right, in);
        }
        else {
            return x.getValue();
        }
    }


    /**
     * inserts new node into the tree
     * 
     * @param k
     *            key of node
     * @param val
     *            value of node
     */
    public void insert(Key k, Value val) {
        root = put(root, k, val);
        size++;
    }


    /**
     * helper for insert
     * 
     * @param x
     *            node of current subtree
     * @param k
     *            key of node to be inserted
     * @param val
     *            value to be inserted
     * @return root of subtree containing new node
     */
    private Node put(Node x, Key k, Value val) {
        if (x == null) {
            return new Node(k, val);
        }
        if (x.getKey().compareTo(k) == 0 || x.getKey().compareTo(k) > 0) {
            x.setLeft(put(x.left, k, val));
        }
        else {
            x.setRight(put(x.right, k, val));
        }
        return x;
    }


    /**
     * Removes node from the tree with specified key and value
     * 
     * @param k
     *            key to be removed
     * @param v
     *            value to be removed
     */
    public void remove(Key k, Value v) {
        root = delete(root, k, v);
        size--;
    }


    /**
     * Helper for remove; recursively deletes the specified node
     * 
     * @param x
     *            root of current subtree
     * @param k
     *            key to be deleted
     * @param v
     *            value of the node to be deleted
     * @return the node associated with the given key
     */
    private Node delete(Node x, Key k, Value v) {
        if (size == 1) {
            root = null;
        }
        Node res = x;
        if (x == null) {
            return null;
        }
        // searching for the node with the correct name
        int comp = k.compareTo(x.key);
        if (comp < 0) {
            x.left = delete(x.left, k, v);
        }
        else if (comp > 0) {
            x.right = delete(x.right, k, v);
        }
        else {
            // found the correct name, now need the correct dimensions
            if (v.equals(x.value)) {
                if (x.left == null) {
                    return x.right;
                }
                if (x.right == null) {
                    return x.left;
                }
                else {
                    Node temp = findMax(x.left);
                    x.setKey(temp.key);
                    x.setVal(temp.value);
                    x.left = deleteMax(x.left);
                }
            }
            else {
                // correct name but not correct dimensions so keep looking
                // traverses down the left subtree because duplicates are
                // inserted to the left
                // in the tree
                x.left = delete(x.left, k, v);
            }

        }
        return res;
    }


    /**
     * finds the maximum node in the current subtree
     * 
     * @param node
     *            root of current subtree
     * @return the max of the entire tree
     */
    private Node findMax(Node node) {
        if (node == null) {
            return node;
        }
        else if (node.right == null) {
            return node;
        }
        else {
            return findMax(node.right);
        }
    }


    /**
     * Removes the maximum node for the current subtree Used in remove
     * 
     * @param node
     *            root of current subtree
     * @return the node to be removed
     */
    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.right;
        }
        node.right = deleteMax(node.right);
        return node;
    }


    /**
     * Creates 2 stacks from the tree contents 
     * nodes stack holds the tree nodes in order 
     * levels stack holds the levels of the tree in order
     */
    public void inOrder() {
        nodes = new Stack<Value>();
        levels = new Stack<Integer>();
        setLevels(root, 0);
        inOrder(root);
    }


    /**
     * Getter for the nodes stack
     * 
     * @return a stack representing the tree nodes in order
     */

    public Stack<Value> getNodes() {
        return nodes;
    }


    /**
     * Getter for the levels stack
     * 
     * @return a stack full of ints that represents the depth of all the nodes
     *         in
     *         the tree
     */
    public Stack<Integer> getLevels() {
        return levels;
    }


    /**
     * puts all the nodes in the tree in a stack in-order uses a post order
     * traversal because of how stacks work
     * 
     * @param node
     *            node to start traversal from
     */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        nodes.add(node.value);
        levels.add(node.level);
        inOrder(node.right);
    }


    /**
     * Helper function the fill the levels stack with the depths of the nodes in
     * order (so it matches with the in order nodes stack)
     * 
     * @param node
     *            starting node (root)
     * @param level
     *            starting level (0)
     */
    private void setLevels(Node node, int level) {
        if (node == null) {
            return;
        }
        setLevels(node.left, level + 1);
        node.level = level;
        setLevels(node.right, level + 1);

    }


    /**
     * makes tree empty by setting the root to null
     */
    public void empty() {
        root = null;
        size = 0;
    }
}
