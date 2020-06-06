import java.util.Comparator;
/**
 * compare the words ignore the case.
 * @author Xi Chen
 *
 */
public class IgnoreCase implements Comparator<Word> {
    /**
     * compare the words ingnore the case.
     */
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().toLowerCase().compareTo(o2.getWord().toLowerCase());
    }

}
