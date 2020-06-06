
/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 4 HashTable Implementation with linear probing
 *
 * Andrew ID: xc4
 *
 * @author Xi Chen
 */
public class MyHashTable implements MyHTInterface {
    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * create DELETED item.
     */
    private static final DataItem DELETED = new DataItem("", 0);
    /**
     * Default capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * default load.
     */
    private static final double DEFAULT_LOAD = 0.5;
    /**
     * current load.
     */
    private double load;
    /**
     * current count.
     */
    private int count;
    /**
     * current collision.
     */
    private int collision = 0;

    // TODO implement constructor with no initial capacity
    /**
     * default constructor.
     */
    public MyHashTable() {
        hashArray = new DataItem[DEFAULT_CAPACITY];
        load = DEFAULT_LOAD;
        count = 0;
    }

    // TODO implement constructor with initial capacity
    /**
     * implement constructor with initial capacity.
     * @param capacity capacity for dataitem array
     */
    public MyHashTable(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("Please Put Positive Number");
        }
        hashArray = new DataItem[capacity];
        load = DEFAULT_LOAD;
        count = 0;
    }
    // TODO implement required methods

    /**
     * Instead of using String's hashCode,
     * you are to implement your own here. You
     * need to take the table length into your account in this method.
     *
     * In other words, you are to combine the
     *  following two steps into one step. 1.
     * converting Object into integer value 2.
     * compress into the table using modular
     * hashing (division method)
     *
     * Helper method to hash a string for English
     * lowercase alphabet and blank, we
     * have 27 total. But, you can assume that blank will not be added into your
     * table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's
     * method should be applied as
     * follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time,
     * I would encourage you to try with other constant
     * values than 27 and compare the results but it is not required.
     *
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        // TODO implement this
        int len = input.length();
        int hashVal = 0;
        for (int i = 0; i < len; i++) {
            hashVal = (hashVal * 27 + input.charAt(i) - 'a' + 1)
                    % hashArray.length;
        }
        return hashVal;
    }

    /**
     * doubles array length and rehash items
     * whenever the load factor is reached.
     * Note: do not include the number of deleted
     * spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        // TODO implement this
        int newCap = nextPrime(2 * hashArray.length);
        if (newCap == -1) {
            throw new RuntimeException("integer overflow");
        }
        System.out.println("Rehashing "
                + size() + " items, new length is " + newCap);
        count = 0;
        collision = 0;
        DataItem[] tmp = hashArray;
        hashArray = new DataItem[newCap];
        for (DataItem item : tmp) {
            if (item == null || item == DELETED) {
                continue;
            }
            for (int i = 1; i <= item.frequency; i++) {
                insert(item.value);
            }
        }
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        // TODO implement constructor and methods
        /**
         * DataItem constructor.
         *
         * @param s    value of string.
         * @param freq times appear.
         */
        DataItem(String s, int freq) {
            value = s;
            frequency = freq;
        }
    }

    @Override
    public void insert(String value) {
        // TODO Auto-generated method stub
        if (!isValid(value) || !isWord(value) || hasCapital(value)) {
            return;
        }
        int result = find(value);
        if (result == -1) {
            int hashVal = hashFunc(value);
            int tmpVal = hashVal;
            boolean flag = false;
            while (hashArray[hashVal] != null
                    && hashArray[hashVal] != DELETED) {
                if (hashFunc(hashArray[hashVal].value) == tmpVal
                        && !hashArray[hashVal].value.equals(value)) {
                    flag = true;
                }
                hashVal++;
                hashVal = hashVal % hashArray.length;
            }
            if (flag) {
                collision++;
            }
            DataItem item = new DataItem(value, 1);
            hashArray[hashVal] = item;
            count++;
            if (1.0 * count / hashArray.length > load) {
                rehash();
            }
        } else {
            (hashArray[result].frequency)++;
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return count;
    }

    @Override
    public void display() {
        // TODO Auto-generated method stub
        StringBuilder s = new StringBuilder();
        for (DataItem item : hashArray) {
            if (item == null) {
                s.append("**");
            } else if (item == DELETED) {
                s.append("#DEL#");
            } else {
                s.append("[").append(item.value)
                    .append(", ").append(item.frequency).append("]");
            }
            s.append(" ");
        }
        System.out.println(s);
    }

    @Override
    public boolean contains(String key) {
        // TODO Auto-generated method stub
        if (!isValid(key) || !isWord(key) || hasCapital(key)) {
            return false;
        }
        int hashVal = hashFunc(key);
        int numCheck = 0;
        while (hashArray[hashVal] != null && numCheck <= hashArray.length) {
            if (hashArray[hashVal].value.equals(key)) {
                return true;
            }
            hashVal++;
            hashVal = hashVal % hashArray.length;
            numCheck++;
            // System.out.println(numCheck);
        }
        return false;
    }

    @Override
    public int numOfCollisions() {
        return collision;
        // TODO Auto-generated method stub

    }

    @Override
    public int hashValue(String value) {
        // TODO Auto-generated method stub
        if (!isValid(value) || !isWord(value)) {
            return 0;
        }
        return hashFunc(value);
    }

    @Override
    public int showFrequency(String key) {
        // TODO Auto-generated method stub
        if (!isValid(key) || !isWord(key) || hasCapital(key)) {
            return 0;
        }
        int hashVal = find(key);
        if (hashVal == -1) {
            return 0;
        } else {
            return hashArray[hashVal].frequency;
        }
    }

    /**
     * find the key value.
     *
     * @param key key to find.
     * @return index of key
     */
    private int find(String key) {
        if (!isValid(key) || !isWord(key) || hasCapital(key)) {
            return -1;
        }

        int hashVal = hashFunc(key);
        int numCheck = 0;

        while (hashArray[hashVal] != null
                && hashArray[hashVal] != DELETED
                && numCheck <= hashArray.length) {
            if (hashArray[hashVal].value.equals(key)) {
                return hashVal;
            }
            hashVal++;
            hashVal = hashVal % hashArray.length;
            numCheck++;
        }
        return -1;
    }

    @Override
    public String remove(String key) {
        // TODO Auto-generated method stub
        if (!isValid(key) || !isWord(key) || hasCapital(key)) {
            return null;
        }
        int hashVal = hashFunc(key);
        int numCheck = 0;
        while (hashArray[hashVal] != null && numCheck <= hashArray.length) {
            if (hashArray[hashVal].value.equals(key)) {
                String tmp = hashArray[hashVal].value;
                hashArray[hashVal] = DELETED;
                count--;
                return tmp;
            }
            hashVal++;
            hashVal = hashVal % hashArray.length;
            numCheck++;
        }
        return null;
    }

    /**
     * check if word.
     *
     * @param text input string.
     * @return is word?
     */
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }

    /**
     * check if input string valid.
     *
     * @param value value.
     * @return is valid?
     */
    private boolean isValid(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * find next prime number.
     *
     * @param input value.
     * @return prime number.
     */
    public int nextPrime(int input) {
        boolean primeFlag = false;
        if (input == 1 || input == 2) {
            return 3;
        }
        double num = input / 2;
        while (input <= Integer.MAX_VALUE) {
            for (int i = 2; i <= num; i++) {
                primeFlag = true;
                if (input % i == 0) {
                    primeFlag = false;
                    break;
                }
            }
            if (primeFlag) {
                return input;
            } else {
                long result = ((long) input) + 1;
                if (result > Integer.MAX_VALUE) {
                    return -1;
                }
                input++;
            }
        }
        return -1;
    }

    /**
     * check if input contains capital letter.
     *
     * @param word input.
     * @return has?
     */
    public boolean hasCapital(String word) {
        if (word == null) {
            return false;
        }
        for (char a : word.toCharArray()) {
            return (a - 'a' < 0);
        }
        return false;
    }
}
