import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 6
 * Building Index using BST
 *
 * A very simple interface: DO NOT MODIFY THIS!
 * @author Xi Chen
 * Andrew ID xc4
 */
public class Index {
    /**
     * input the file name.
     * @param fileName filename.
     * @return use the second buildIndex without any comparator
     */
    public BST<Word> buildIndex(String fileName) {
        return buildIndex(fileName, null);
    }
    /**
     *
     * @param fileName fileName
     * @param comparator comparator
     * @return return the words BST
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        BST<Word> words = new BST<Word>(comparator);
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
            int lines = 0;
            while (scanner.hasNextLine()) {
                lines++;
                String line = scanner.nextLine();
                String[] wordsFromText = line.split("\\W");
                for (String word : wordsFromText) {
                    if (!checkString(word) || !isWord(word)) {
                        continue;
                    }
                    if (comparator instanceof IgnoreCase) {
                        word = word.toLowerCase();
                    }
                    Word bstWord = new Word(word);
                    Word result = words.search(bstWord);
                    if (result == null) {
                        bstWord.addToIndex(lines);
                        words.insert(bstWord);
                    } else {
                        result.setFrequency(result.getFrequency() + 1);
                        result.addToIndex(lines);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return words;

    }
    /**
     * build the BST with the comparator.
     * @param list list
     * @param comparator comparator
     * @return words.
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        BST<Word> words = new BST<Word>(comparator);
        if (list == null) {
            return words;
        }
        for (Word objWord : list) {
            if (comparator instanceof IgnoreCase) {
                Word tmpWord = objWord;
                tmpWord.setWord(objWord.getWord().toLowerCase());
                words.insert(tmpWord);
            } else {
                words.insert(objWord);
            }
        }
        return words;
    }
    /**
     * sort by the alpha data.
     * @param tree tree
     * @return the list.
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.
         */
        ArrayList<Word> list = new ArrayList<Word>();
        if (tree == null) {
            return list;
        }
        Iterator<Word> words = tree.iterator();
        while (words.hasNext()) {
            list.add(words.next());
        }
        Collections.sort(list, new AlphaFreq());
        return list;
    }
    /**
     * sort by the frequency.
     * @param tree words tree
     * @return the list sort by high frequency.
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> list = new ArrayList<Word>();
        if (tree == null) {
            return list;
        }
        Iterator<Word> words = tree.iterator();
        while (words.hasNext()) {
            list.add(words.next());
        }
        Collections.sort(list, new Frequency());
        return list;
    }
    /**
     * get the highest frequency word.
     * @param tree BST words tree
     * @return highest word list.
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> list = new ArrayList<Word>();
        if (tree == null) {
            return list;
        }
        list = sortByFrequency(tree);
        if (list.size() == 0) {
            throw new RuntimeException("no file exsit");
        }
        int highestFreq = list.get(0).getFrequency();
        ArrayList<Word> highestList = new ArrayList<Word>();
        for (Word word : list) {
            if (word.getFrequency() != highestFreq) {
                continue;
            }
            highestList.add(word);
        }
        return highestList;
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
