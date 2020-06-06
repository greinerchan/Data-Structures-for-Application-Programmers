import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * 17683 Data Structures for Application Programmers.
 *
 * Homework Assignment 2 Solve Josephus problem
 * with different data structures
 * and different algorithms and compare running times
 *
 * Andrew ID: xc4
 * @author Xi Chen
 *
 * I think I prefer playWithAD method because it is the fastest
 * when the size is huge but rotation is not that huge.
 * I think the reason why playWithLLAt method is slow when size is
 * huge because to index a linked list is O(n) and the search time is O(n).
 * so the time complexity is O(n) for playWithLLAt method to remove
 * one element. When the size is huge it will waste a lot of time for
 * searching. But when rotation is much bigger than size playWithLLAt
 * is faster because search time is less than rearrange time for rest
 * two method. I think reasons why playWithAD is faster than playWithLL
 * has two reasons. First, ArrayDeque is not thread-safe. Second, every time the
 * linked list want to add a new element it needs to create a new node to
 * store the data while the ArrayDeque has fixed size so it do not need to
 * waste time on resizing. So the ArrayQueue is quicker than linked list
 * as queue.
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle.
     * The value has to be greater than 0
     * @return The position value of the survivor
     * @throws Exception
     */
    public int playWithAD(int size, int rotation) {
        // polymorphism, create a queue instance base on ArrayDeque
        Queue<Integer> queue = new ArrayDeque<Integer>(size);
        // input invalid throw a exception.
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        // give initial value.
        for (int i = 1; i <= size; i++) {
            queue.offer(i);
        }
        // circulate until just 1 left in queue
        while (size > 1) {
            // find the rotation when queue size is smaller so won't
            // repeat to circulate
            int rotationAfter = rotation % size;
            // when rotationAfter equal 0 should remove last one
            if (rotationAfter == 0) {
                rotationAfter = size;
            }
            for (int i = 1; i < rotationAfter; i++) {
                queue.offer(queue.poll());
            }
            queue.poll();
            size = size - 1;
        }
        return queue.peek();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle.
     * The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        // polymorphism, create a queue instance base on LinkedList
        Queue<Integer> queue = new LinkedList<Integer>();
        // input invalid throw a exception.
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        // give initial value.
        for (int i = 1; i <= size; i++) {
            queue.offer(i);
        }
        // circulate until just 1 left in queue
        while (size > 1) {
            // find the rotation when queue size is smaller so won't
            // repeat to circulate
            int rotationAfter = rotation % size;
            // when rotationAfter equal 0 should remove last one
            if (rotationAfter == 0) {
                rotationAfter = size;
            }
            for (int i = 1; i < rotationAfter; i++) {
                queue.offer(queue.poll());
            }
            queue.poll();
            size = size - 1;
        }
        return queue.peek();
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find
     * and remove a person to be executed in the circle
     *
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle.
     * The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
     // polymorphism, create a list instance base on LinkedList
        List<Integer> list = new LinkedList<Integer>();
        if (size <= 0 || rotation <= 0) {
            throw new RuntimeException();
        }
        // give initial value.
        int startIndex = 0;
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        while (size > 1) {
            //circular the list and find which one to delete
            int rotationAfter = (startIndex + rotation) % size;
            // when multiple of list size, delete last
            if (rotationAfter == 0) {
                list.remove(size - 1);
                //careful, delete last should begin with first
                startIndex = 0;
                size = size - 1;
            } else {
                int removeIndex = rotationAfter - 1;
                startIndex = removeIndex;
                list.remove(removeIndex);
                size = size - 1;
            }
        }
        return list.get(0);
    }

}
