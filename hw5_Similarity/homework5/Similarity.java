import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 5 Similarity
 *
 * I use HashMap to store the word and its frequency. Firstly,
 * in perfect case, we can insert and find the element in O(1)
 * time complexity, which we insert and find element a lot in the
 * homework. Secondly, we don't need know the order of the data,
 * so we can use HashMap to store them.
 *
 * Andrew ID: xc4
 * @author Xi Chen
 */
public class Similarity {
   /**
    * total line of the file or string.
    */
    private int lines;
/**
 * total words of the file or string.
 */
    private BigInteger words;
    /**
     * map of frequency of words.
     */
    private Map<String, BigInteger> freqOfWords;
    /**
     * Find the similartiy for input string.
     * @param string string.
     */
    public Similarity(String string) {
        if (!checkString(string)) {
            freqOfWords = new HashMap<String, BigInteger>();
            lines = 0;
            words = BigInteger.ZERO;
            return;
        }
        freqOfWords = new HashMap<String, BigInteger>();
        lines = 0;
        words = BigInteger.ZERO;
        readLine(string);
    }
    /**
     * create frequency hashmap for input string.
     * @param string string
     */
    private void readLine(String string) {
        String[] wordsFromText = string.split("\\W");
        for (String word : wordsFromText) {
            if (!checkString(word) || !isWord(word)) {
                continue;
            }
            word = word.toLowerCase();
            BigInteger frequency = freqOfWords.get(word);
            if (frequency == null) {
                frequency = BigInteger.ONE;
            } else {
                frequency = frequency.add(BigInteger.ONE);
            }
            freqOfWords.put(word, frequency);
            words = words.add(BigInteger.ONE);
        }
    }
    /**
     * create frequency hashmap for input file.
     * @param file file
     */
    public Similarity(File file) {
        if (file == null) {
            freqOfWords = new HashMap<String, BigInteger>();
            lines = 0;
            words = BigInteger.ZERO;
            return;
        }
        freqOfWords = new HashMap<String, BigInteger>();
        lines = 0;
        words = BigInteger.ZERO;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                readLine(line);
                lines++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    /**
     * total number of lines.
     * @return lines
     */
    public int numOfLines() {
        return lines;

    }
    /**
     * total number of words.
     * @return words
     */
    public BigInteger numOfWords() {
        return words;

    }
    /**
     * total number of no duplicate words.
     * @return number of no duplicate words
     */
    public int numOfWordsNoDups() {
        return freqOfWords.size();

    }
    /**
     * find euclideanNorm for current object.
     * @return euclideanNorm
     */
    public double euclideanNorm() {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger value : freqOfWords.values()) {
            sum = sum.add(value.multiply(value));
        }
        return Math.sqrt(sum.doubleValue());
    }
    /**
     * find euclideanNorm for another object.
     * @return euclideanNorm
     * @param map map
     */
    private double euclideanNorm(Map<String, BigInteger> map) {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger value : map.values()) {
            sum = sum.add(value.multiply(value));
        }
        return Math.sqrt(sum.doubleValue());
    }
    /**
     * At beginning, time complexity will be O(n) to travel all
     * the keys in map. And then, map.containsKey() will take O(1)
     * if there not too conflict. If there many repeated hash values,
     * the complexity is O(log n). The same as map.get() method. So, the
     * worst case time complexity is n(log n + (2 * log n) = 3n * log n,
     *  which is O(nlog n). It is less than O(n^2).
     * @param map map
     * @return dotproduct value.
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null || map.isEmpty()) {
            return 0;
        }
        BigInteger product = BigInteger.ZERO;
        for (String key : freqOfWords.keySet()) {
            if (!map.containsKey(key) || !checkString(key)) {
                continue;
            }
            product = product.add(freqOfWords.get(key).multiply(map.get(key)));
        }
        return product.doubleValue();

    }
    /**
     * find similarity between two hashmap.
     * @param map another map.
     * @return similarity
     */
    public double distance(Map<String, BigInteger> map) {
        if (freqOfWords == null || freqOfWords.isEmpty()) {
            return Math.PI / 2;
        }
        if (map == null || map.isEmpty()) {
            return Math.PI / 2;
        }
        if (freqOfWords.equals(map)) {
            return 0;
        }
        return Math.acos(dotProduct(map)
                / (euclideanNorm() * euclideanNorm(map)));

    }
    /**
     * return a copy of current object.
     * @return copy of current object
     */
    public Map<String, BigInteger> getMap() {
        return new HashMap<String, BigInteger>(freqOfWords);
    }
    /**
     * find the input word is valid.
     * @param word word
     * @return valid
     */
    private boolean checkString(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return true;
    }
    /**
     * find the input is word or not.
     * @param text text
     * @return is word?
     */
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }
}
