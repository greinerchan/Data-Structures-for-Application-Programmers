import java.util.Comparator;
/**
 * first compare Alpha and then frequency.
 * @author Xi Chen
 *
 */

public class AlphaFreq implements Comparator<Word> {
    /**
     * compare words.
     */
    @Override
    public int compare(Word o1, Word o2) {
        int alphaResult = o1.getWord().compareTo(o2.getWord());
        if (alphaResult != 0) {
            return alphaResult;
        }
        return Integer.compare(o1.getFrequency(), o2.getFrequency());
       }
    }
