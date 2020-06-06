import java.util.Comparator;
/**
 * rank by frequency.
 * @author Xi Chen
 *
 */
public class Frequency implements Comparator<Word> {
    /**
     * compare the word frequency, bigger first.
     */
    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }

}
