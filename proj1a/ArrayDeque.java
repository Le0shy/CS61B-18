
import java.util.Iterator;

/**
 * Deque based array
 *
 * @param <T> type of item
 */
public class ArrayDeque<T> implements deque<T>, Iterable<T> {
    private T[] elem;
    private int capacity = 8;
    private int size, nextFirst, nextLast;

    /**
     * Returns an empty ArrayDeque, the default capacity
     * is 8, size is zero. The index of first item is 4,
     * which is default value of nextFirst and nextLast.
     */
    ArrayDeque() {
        elem = (T[]) new Object[capacity];
        size = 0;
        nextFirst = capacity / 2;
        nextLast = capacity / 2;
    }

    /**
     * Return a new ArrayDeque containing the T item in ARGS.
     *
     * @param args T items
     * @return a new ArrayDeque constructed by args
     */
    public static ArrayDeque of(Object... args) {
        ArrayDeque<Object> result = new ArrayDeque();
        if (args.length > 0) {
            result.addLast(args[0]);
        } else {
            return result;
        }
        for (int k = 1; k < args.length; k += 1) {
            result.addLast(args[k]);
        }
        return result;
    }

    /**
     * ArrayDeque expands when size of items equals to capacity,
     * and the capacity of array redouble.
     */
    private void expand() {
        int index = capacity >> 1;
        int capacityNew = capacity << 1;
        T[] elemResized = (T[]) new Object[capacityNew];
        for (T e : this) {
            elemResized[index] = e;
            index += 1;
        }
        elem = elemResized;
        nextFirst = (capacity >> 1) - 1;
        nextLast = capacity + (capacity >> 1);
        capacity = capacityNew;
    }

    /**
     * ArrayDeque shrinks when size <= 0.25 * capacity,
     * and the capacity is half of the former.
     */
    private void shrink() {
        int capacityNew = capacity >> 1;
        int index = capacityNew >> 2;
        T[] elemResized = (T[]) new Object[capacityNew];
        for (T e : this) {
            elemResized[index] = e;
            index += 1;
        }
        /* debug3 */
        elem = elemResized;
        nextFirst = (capacityNew >> 2) - 1;
        nextLast = (capacityNew >> 2) + (capacityNew >> 1);
        capacity = capacityNew;
    }

    /**
     * @return the first index of ArrayDeque
     */
    private int getFirstIndex() {
        if (nextFirst == capacity - 1) {
            return 0;
        } else {
            return nextFirst + 1;
        }
    }

    /**
     * @return the last index of ArrayDeque
     */
    private int getLastIndex() {
        if (nextLast == 0) {
            return capacity - 1;
        } else {
            return nextLast - 1;
        }
    }

    @Override
    public void addFirst(T item) {
        elem[nextFirst] = item;
        nextFirst -= 1;
        if (size == 0) {
            nextLast += 1;
        } else if (nextFirst < 0) {
            nextFirst = capacity - 1;
        }
        size += 1;
        /* debug 1 */
        if (size == capacity) {
            expand();
        }
    }

    @Override
    public void addLast(T item) {
        elem[nextLast] = item;
        nextLast += 1;
        if (size == 0) {
            nextFirst -= 1;
        } else if (nextLast == capacity) {
            nextLast = 0;
        }
        size += 1;
        if (size == capacity) {
            expand();
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (T i : this) {
            string.append(i);
            string.append(" ");
        }
        return string.toString();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T e = elem[getFirstIndex()];
        /* debug4 */
        if (size == 1){
            nextLast = getFirstIndex();
        }
        /* debug 2 */
        nextFirst = getFirstIndex();
        size -= 1;
        if (capacity >= 16 && size < (capacity >> 2)) {
            shrink();
        }
        return e;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T e = elem[getLastIndex()];
        if (size == 1){
            nextFirst = getLastIndex();
        }
        nextLast = getLastIndex();
        size -= 1;
        if (capacity >= 16 && size <= (capacity >> 2)) {
            shrink();
        }
        return e;
    }

    @Override
    public T get(int index) {
        if (size <= index) {
            return null;
        }
        if (index < capacity - getFirstIndex()) {
            return elem[getFirstIndex() + index];
        } else {
            return elem[getFirstIndex() + index - capacity];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    /**
     * nested class for deque iterator.
     */
    private class DequeIterator implements Iterator<T> {
        private int index;

        DequeIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            index += 1;
            return get(index - 1);
        }
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(0);
        System.out.println(ad.get(0));
        ad.addFirst(2);
        System.out.println(ad.removeFirst());
        System.out.println(ad.removeLast());
        ad.addFirst(5);
        System.out.println(ad.removeLast());
    }
}
