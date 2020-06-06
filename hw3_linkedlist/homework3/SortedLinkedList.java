/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 3 Write a sorted Linkedlist
 * Andrew ID: xc4
 * @author Xi Chen
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * define a Node.
     */
    private static class Node {
        /**
         * Node has data field.
         */
        private String data;
        /**
         * Node has next field.
         */
        private Node next;
        /**
         *
         * @param d parameter for data
         * @param n parameter for next node
         */
        Node(String d, Node n) {
            data = d;
            next = n;
        }
    }
    /**
     * head node variable.
     */
    private Node head;
    /**
     * default constructor.
     */
    public SortedLinkedList() {
        head = null;
    }
    /**
     * another constructor for get string array.
     * @param unsorted string array
     */
    public SortedLinkedList(String[] unsorted) {
        int idx = 0;
        int maxIdx = unsorted.length - 1;
        addSingleHelper(unsorted, idx, maxIdx);
    }
    /**
     * separate each word for add method.
     * @param unsorted unsorted array
     * @param idx index for unsorted array
     * @param maxIdx maximum for index array
     */
    private void addSingleHelper(String[] unsorted, int idx, int maxIdx) {
        // base 1, when index out of bound.
        if (idx > maxIdx) {
            return;
        }
        String word = unsorted[idx];
        add(word);
        addSingleHelper(unsorted, idx + 1, maxIdx);
    }
    /**
     *  overide add method.
     */
    @Override
    public void add(String value) {
        if (!checkString(value)) {
            return;
        }
        if (!isWord(value)) {
            return;
        }
        // initial the first condition
        Node prev = null;
        Node curr = head;
        addHelper(value, prev, curr);
    }
    /**
     *  Help add method add element in order.
     * @param word input string
     * @param prev previous word
     * @param curr current word
     */
    private void addHelper(String word, Node prev, Node curr) {
        //base 1, empty linked list
        if (head == null) {
            head = new Node(word, null);
            return;
        }
        // base 2, check if head duplicate
        if (head.data.compareTo(word) == 0) {
            return;
        }
        //base 3, add before head
        if (word.compareToIgnoreCase(head.data) < 0) {
            head = new Node(word, head);
            return;
        } else if (curr.data.compareToIgnoreCase(word) == 0) {
          //base 4, has duplicate string
            return;
        } else if (word.compareToIgnoreCase(curr.data) < 0) {
          //base 5, insert before
            prev.next = new Node(word, curr);
            return;
        } else if (curr.next == null) {
          //base 6, add last
            curr.next = new Node(word, null);
            return;
        }
        addHelper(word, curr, curr.next);
    }
    /**
     * override size method.
     */
    @Override
    public int size() {
        int count = 0;
        return sizeHelper(head, count);
    }
    /**
     * size helper to get size of list.
     * @param curr current node
     * @param count number of words
     * @return count
     */
    private int sizeHelper(Node curr, int count) {
        // base 1, at last node
        if (curr == null) {
            return count;
        }
        return sizeHelper(curr.next, count + 1);
    }
    /**
     * overrtide method for display.
     */
    @Override
    public void display() {
        StringBuilder list = new StringBuilder();
        int count = 0;
        displayHelper(head, list, count);
        System.out.println(list.toString());
    }
    /**
     * helper method for display the list of words.
     * @param curr current node
     * @param list StringBuilder to build the list
     * @param count Count number of element.
     */
    private void displayHelper(Node curr, StringBuilder list, int count) {
        // start with "["
        if (count == 0) {
            list.append("[");
        }
        // base 1, end with "]"
        if (curr == null) {
            list.append("]");
            return;
        }
        // add words
        if (curr != null) {
            list.append(curr.data);
        }
        // add ", "
        if (curr.next != null) {
            list.append(", ");
        }
        displayHelper(curr.next, list, count + 1);
    }
    /**
     * override method for contains.
     */

    @Override
    public boolean contains(String key) {
        if (!isWord(key)) {
            return false;
        }
        return containsHelper(key, head);
    }
    /**
     * helper method for contains.
     * @param key word want to check
     * @param curr current node
     * @return if the word exist or not
     */
    private boolean containsHelper(String key, Node curr) {
        // base 1, did not find
        if (curr == null) {
            return false;
        }
        // base 2, find
        if (curr.data.compareToIgnoreCase(key) == 0) {
            return true;
        }
        return containsHelper(key, curr.next);
    }
    /**
     * override method for isEmpty. check empty linkedlist.
     */
    @Override
    public boolean isEmpty() {
        return (head == null);
    }
    /**
     * Removes and returns the first String object of the list.
     */
    @Override
    public String removeFirst() {
        // base 1, empty
        if (head == null) {
            return null;
        }
        String headData = head.data;
        head = head.next;
        return headData;
    }
    /**
     * Removes and returns String object at the specified index.
     */
    @Override
    public String removeAt(int index) {
        int count = 0;
        Node prev = null;
        Node curr = head;
        return removeAtHelper(prev, curr, index, count);
    }
    /**
     * Removes and returns String object at the specified index.
     * @param prev previous node
     * @param curr current node
     * @param index index want to remove
     * @param count count number
     * @return string removed
     */
    private String removeAtHelper(Node prev, Node curr, int index, int count) {
        // base 1, index out bound or negative input
        if (index >= size() || index < 0) {
            throw new RuntimeException("Invalid Input");
        }
        // base 2, list is empty
        if (head == null) {
            return null;
        }
        // base 3, remove head
        if (index == 0) {
            String headData = head.data;
            head = head.next;
            return headData;
        }
        // base 4, find it
        if (index == count) {
            String currData = curr.data;
            prev.next = curr.next;
            return currData;
        }
        return removeAtHelper(curr, curr.next, index, count + 1);
    }
    /**
     *  check string is word or not.
     * @param s input string
     * @return word or not a word
     */
    public boolean checkString(String s) {
        if (s == null || s.trim() == "") {
            return false;
        }
        return true;
    }
    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }

}
