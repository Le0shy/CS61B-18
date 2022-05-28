import java.util.Iterator;

/**
 * Deque based linked list
 * @param <T> type of items
 */
public class LinkedListDeque<T> implements deque<T>, Iterable<T> {
    private class Linkedlist {
        T item;
        Linkedlist prev;
        Linkedlist next;

        Linkedlist() {
            item = null;
            prev = null;
            next = null;
        }

        Linkedlist(T itemForNode) {
            item = itemForNode;
            prev = null;
            next = null;
        }
    }

    private int size;
    private final Linkedlist sentinel;

    /* create an empty deque */
    public LinkedListDeque() {
        size = 0;
        sentinel = new Linkedlist();
    }

    @Override
    public void addFirst(T item) {
        Linkedlist node = new Linkedlist(item);
        node.prev = sentinel;
        if (size == 0) {
            sentinel.next = node;
            sentinel.prev = node;
            node.next = sentinel;
        } else {
            node.next = sentinel.next;
            sentinel.next.prev = node;
            sentinel.next = node;
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Linkedlist node = new Linkedlist(item);
        node.next = sentinel;
        if (size == 0) {
            sentinel.next = node;
            sentinel.prev = node;
            node.prev = sentinel;
        } else {
            node.prev = sentinel.prev;
            sentinel.prev.next = node;
            sentinel.prev = node;
        }
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == null;
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
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T item = sentinel.next.item;
        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
        }
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        T item = sentinel.prev.item;
        if (size == 1) {
            sentinel.next = null;
            sentinel.prev = null;
        } else {
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
        }
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        if (size <= index) {
            return null;
        }
        Linkedlist ptr;
        if (index < size / 2) {
            ptr = sentinel.next;
            for (int i = 0; i < index; i += 1) {
                ptr = ptr.next;
            }
        } else {
            ptr = sentinel.prev;
            /* debug 1 */
            for (int i = 0; i < size - 1 - index; i += 1) {
                ptr = ptr.prev;
            }
        }
        return ptr.item;
    }

    /**
     * recursive version of get();
     *
     * @param index 0 as the first node, so forth
     * @return result of getR()
     */
    public T getRecursive(int index) {
        if (size <= index) {
            return null;
        }
        /* debug2 */
        return getR(index, sentinel.next);
    }

    private T getR(int index, Linkedlist node) {
        if (index == 0) {
            return node.item;
        }
        node = node.next;
        index -= 1;
        return getR(index, node);
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

    public Iterator<T> iterator() {
        return new DequeIterator(sentinel);
    }

    /**
     * Nested class for deque iterator.
     */
    private class DequeIterator implements Iterator<T> {

        private int index;
        private Linkedlist ptr;

        DequeIterator(Linkedlist sentinel) {
            index = 0;
            ptr = sentinel;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            /* debug 3, a serious bug! */
            index += 1;
            ptr = ptr.next;
            return ptr.item;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> lls = new LinkedListDeque<>();
        lls.addFirst(1);
        lls.addLast(2);
        lls.addLast(3);
        lls.addFirst(4);
        System.out.println(lls.get(2));
        System.out.println(lls.getRecursive(3));
        System.out.println(lls);
    }
}
