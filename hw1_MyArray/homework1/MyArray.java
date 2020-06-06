/**
 * 17683 Data Structures for Application Programmers.
 * Homework 1 MyArray
 * @author Xi Chen
 * ID: xc4
 */
public class MyArray {
    /**
     * size of the list.
     */
    private int len;
    /**
     * String array.
     */
    private String[] arr;
    /**
     * default string list capacity and size.
     * running time complexity is O(1)
     */
    public MyArray() {
        arr = new String[10];
        len = 0;
    }
    /**
     * Constructor for the new string list.
     * running time complexity is O(1)
     * @param initialCapacity initial capacity.
     */
    public MyArray(int initialCapacity) {
        if (initialCapacity < 0 || initialCapacity > Integer.MAX_VALUE) {
            arr = new String[10];
            len = 0;
        } else {
        arr = new String[initialCapacity];
        len = 0;
        }
    }
    /**
     * add string to the string list.
     * running time complexity is O(1)
     * @param text input string
     */
    public void add(String text) {
        if (text == null || text == ""
                || !checkWord(text) ||  text.length() == 0) {
            return;
        }
        if (len == arr.length) {
            String[] doubleSize = new String[2 * len];
            System.arraycopy(arr, 0, doubleSize, 0, len);
            arr = doubleSize;
        }
        if (len == 0) {
            arr = new String[1];
        }
        arr[len] = text;
        len = len + 1;
    }
    /**
     * check if String array contains the array.
     * running time complexity is O(n)
     * @param key input string
     * @return boolean
     */
    public boolean search(String key) {
        if (key == null || key == "") {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (arr[i].equals(key)) {
                return true;
            }
        }
        return false;
    }
    /**
     * find the size of string list.
     * running time complexity is O(1)
     * @return size of array
     */
    public int size() {
        return len;
    }
    /**
     * find the capacity of the string list.
     * running time complexity is O(1)
     * @return capacity of string list
     */
    public int getCapacity() {
        return arr.length;
    }
    /**
     * display the string list.
     * running time complexity is O(n)
     */
    public void display() {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < len; i++) {
            a.append(arr[i]).append(" ");
        }
        System.out.println(a);
    }
    /**
     * remove the duplicate element in string list.
     * running time complexity is O(n^3)
     */
    public void removeDups() {
        if (len == 1 || len == 0) {
            return;
        }
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i].equals(arr[j])) {
                    for (int k = j; k < len; k++) {
                        arr[k] = arr[k + 1];
                    }
                    len = len - 1;
                    j--;
                }
            }
        }
    }
    /**
     * check if it is a word (complete characters).
     * @param word input string
     * @return boolean
     */
    private boolean checkWord(String word) {
/*        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            boolean isWord = (ch >= 'a' && ch <= 'z')
            || (ch >= 'A' && ch <= 'Z');
            if (!isWord) {
                return false;
            }
        }
        return true;*/
            return word.matches("[A-Za-z]+");
        }
}
