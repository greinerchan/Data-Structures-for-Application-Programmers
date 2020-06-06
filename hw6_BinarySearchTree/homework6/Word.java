import java.util.HashSet;
import java.util.Set;
/**
 * word class.
 * @author Xi Chen
 *
 */
public class Word implements Comparable<Word> {
    /**
     * value of word.
     */
    private String word;
    /**
     * index of word.
     */
    private Set<Integer> index;
    /**
     * frequency of word.
     */
    private int frequency;
    /**
     * construction for the word class.
     * @param s input string.
     */
    public Word(String s) {
        word = s;
        index = new HashSet<>();
        frequency = 1;
    }
    /**
     * add the line number to the word.
     * @param line line
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }
    /**
     * get the index of the word.
     * @return index of word.
     */
    public Set<Integer> getIndex() {
        return new HashSet<Integer>(index);
    }
    /**
     * get the word value.
     * @return word.
     */
    public String getWord() {
        return word;
    }
    /**
     * set the value of the word.
     * @param s s
     */
    public void setWord(String s) {
        word = s;
    }
    /**
     * get the frequency of the word.
     * @return frequency.
     */
    public int getFrequency() {
        return frequency;
    }
    /**
     * set the frequency.
     * @param freq freq.
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }
    /**
     * compare the word by nature.
     */
    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.word);
    }
    /**
     * print word format.
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(word).append(" ").append(frequency).append(" ").append(index);
        return b.toString();
    }

}
