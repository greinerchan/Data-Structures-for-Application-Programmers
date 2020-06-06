import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 6
 * Building Index using BST
 *
 * A very simple interface: DO NOT MODIFY THIS!
 * @param <T> data type of objects
 * Andrew ID: xc4
 * @author Xi Chen
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * root of BST.
     */
    private Node<T> root;
    /**
     * the comparator pass to it.
     */
    private Comparator<T> comparator;
    /**
     * Default BST.
     */
    public BST() {
        this(null);
    }
    /**
     * BST consturctor pass the comparator.
     * @param comp comparator.
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }
    /**
     * Access the comparator.
     * @return comparator comparator.
     */
    public Comparator<T> comparator() {
        return comparator;
    }
    /**
     * get the root.
     * @return root data
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }
    /**
     * get the height of tree.
     * @return height of tree
     */
    public int getHeight() {
        return getHeightHelper(root);
    }
    /**
     * find the height of tree.
     * @param curr curr node
     * @return height of tree
     */
    private int getHeightHelper(Node<T> curr) {
        //case 0, the current node is null, height for current is 0.
        if (curr == null) {
            return 0;
        }
        //case 1, case 1, only root/leaf, still 0 height.
        if (curr.left == null && curr.right == null) {
            return 0;
        }
        int leftHeight = getHeightHelper(curr.left);
        int rightHeight = getHeightHelper(curr.right);
        // if the left tree is higher, add 1 to it(node to root)
        if (leftHeight >= rightHeight) {
           return leftHeight + 1;
        } else {
           return rightHeight + 1;
        }
    }
    /**
     * find the number of nodes in tree.
     * @return nodes
     */
    public int getNumberOfNodes() {
        return getNumberOfNodesHelper(root);
    }
    /**
     * find the number of nodes in tree.
     * @param curr initial root and then the current node.
     * @return nodes
     */
    private int getNumberOfNodesHelper(Node<T> curr) {
        //case 0, the current node is null, count it 0 node.
        if (curr == null) {
            return 0;
        }
        //case 1, only root/leaf, just one node.
        if (curr.left == null && curr.right == null) {
            return 1;
        }
        //go through left tree
        int leftNode = getNumberOfNodesHelper(curr.left);
        //go through right tree
        int rightNode = getNumberOfNodesHelper(curr.right);
        // add them up also the parent,which is 1.
        return leftNode + rightNode + 1;
    }

    /**
     * Given the value (object), tries to find it.
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, null.
     */
    @Override
    public T search(T toSearch) {
        if (toSearch == null) {
            return null;
        }
        return searchHelper(toSearch, root);
    }
    /**
     * search the element.
     * @param key key
     * @param curr curr
     * @return finded element's data
     */
    private T searchHelper(T key, Node<T> curr) {
        //case 0, empty tree
        if (root == null) {
            return null;
        }
        //case 1, not find the key
        if (curr == null) {
            return null;
        }
        //case 2, find it
        if ((comparator == null && curr.data.compareTo(key) == 0)
                || (comparator != null
                && comparator.compare(curr.data, key) == 0)) {
            return curr.data;
        }
        // if key bigger, should search right
        if ((comparator == null && curr.data.compareTo(key) < 0)
                || (comparator != null
                && comparator.compare(curr.data, key) < 0)) {
            return searchHelper(key, curr.right);
        } else {
            return searchHelper(key, curr.left);
        }
    }
    /**
     * Inserts a value (object) to the tree.
     * No duplicates allowed.
     * @param toInsert a value (object) to insert into the tree.
     */
    @Override
    public void insert(T toInsert) {
        if (toInsert == null) {
            return;
        }
        if (root == null) {
            root = new Node<T>(toInsert);
            return;
        }
        insertHelper(toInsert, root, null);
    }
    /**
     *  insert the element.
     * @param key key
     * @param curr curr
     * @param parent parent
     */
    private void insertHelper(T key, Node<T> curr, Node<T> parent) {
        // base 0, insert right of parent
        if ((comparator == null && curr == null
                &&  parent.data.compareTo(key) < 0)
                || (comparator != null && curr == null
                && comparator.compare(parent.data, key) < 0)) {
            parent.right = new Node<T>(key);
            return;
        }
        // base 1, insert left of parent
        if ((comparator == null && curr == null
                &&  parent.data.compareTo(key) > 0)
                || (comparator != null && curr == null
                && comparator.compare(parent.data, key) > 0)) {
            parent.left = new Node<T>(key);
            return;
        }
        // base 2, same key, do nothing.
        if ((comparator == null && curr.data.compareTo(key) == 0)
                || (comparator != null
                && comparator.compare(curr.data, key) == 0)) {
            return;
        }
        // if key bigger then node, go right
        if ((comparator == null && curr.data.compareTo(key) < 0
                || (comparator != null
                && comparator.compare(curr.data, key) < 0))) {
            insertHelper(key, curr.right, curr);
        } else if ((comparator == null && curr.data.compareTo(key) > 0
                || (comparator != null
                && comparator.compare(curr.data, key) > 0))) {
            insertHelper(key, curr.left, curr);
        }
    }
    /**
     * iterator of the BST.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }
    /**
     * iterator for the BST.
     * @author Xi Chen
     *
     */
    private class BSTIterator implements Iterator<T> {
        /**
         * create new stack.
         */
        private Stack<Node<T>> stack;
        /**
         * push the tree and push the element into stack.
         */
        BSTIterator() {
            stack = new Stack<Node<T>>();
            Node<T> curr = root;
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
        }
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node = stack.pop();
            Node<T> curr = node;
            if (curr.right != null) {
                curr = curr.right;
                while (curr != null) {
                    stack.push(curr);
                    curr = curr.left;
                }
            }
            return node.data;
        }
    }
    /**
     * Node for the BST.
     * @author Xi Chen
     *
     * @param <T> generic type
     */
    private static class Node<T> {
        /**
         * data.
         */
        private T data;
        /**
         * next left tree.
         */
        private Node<T> left;
        /**
         * next right tree.
         */
        private Node<T> right;
        /**
         * constructor of the node with only value.
         * @param d data
         */
        Node(T d) {
            this(d, null, null);
        }
        /**
         * Node element arrtibute.
         * @param d data
         * @param l left Node
         * @param r right Node
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
