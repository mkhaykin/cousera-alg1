import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        final private Item value;
        private Node next;
        private Node prev;
        Node(Item value) {
            this.value = value;
        }
    }
    // construct an empty deque
    private Node firstItem;
    private Node lastItem;
    private int count;
    public Deque() {
        firstItem = null;
        lastItem = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        count++;
        Node node = new Node(item);
        if (firstItem == null) {
            lastItem = node;
        } else {
            firstItem.prev = node;
            node.next = firstItem;
        }
        firstItem = node;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        count++;
        Node node = new Node(item);
        if (lastItem == null) {
            firstItem = node;
        } else {
            lastItem.next = node;
            node.prev = lastItem;
        }
        lastItem = node;
    }
        
    // remove and return the item from the front
    public Item removeFirst() {
        if (count == 0)
            throw new NoSuchElementException();

        Item item = firstItem.value;
        if (firstItem.next != null) {
            firstItem.next.prev = null;
            firstItem = firstItem.next;
        } else {
            firstItem = null;
            lastItem = null;
        }
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (count == 0)
            throw new NoSuchElementException();

        Item item = lastItem.value;
        if (lastItem.prev != null) {
            lastItem.prev.next = null;
            lastItem = lastItem.prev;
        } else {
            firstItem = null;
            lastItem = null;
        }
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new DequeIterator(); }

    private class DequeIterator implements Iterator<Item> {
        private Node node = firstItem;

        public boolean hasNext() { return (node != null); }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (node == null)
                throw new NoSuchElementException();

            Item item = node.value;
            node = node.next;
            return item;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item: this) {
            sb.append(item);
            sb.append(" ");
        }
        return sb.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deq = new Deque<>();

//        Deque<Integer> deq = new Deque<>();
//        for (int i = 0; i < 10; i++) {
//            deq.addFirst(i);
//            StdOut.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.removeFirst();
//            StdOut.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.addLast(i);
//            StdOut.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.removeFirst();
//            StdOut.println(deq);
//        }
        deq.addFirst("0");
        deq.addFirst("1");

        deq.addLast("2");
        deq.addLast("3");

        for (String s: deq)
            StdOut.print(s + " ");
        StdOut.println();

        StdOut.println(deq.toString());
        deq.addFirst("-1");
        StdOut.println(deq);
        deq.addFirst("-2");
        StdOut.println(deq.toString());

        for (String s: deq)
            StdOut.print(s.toString() + " ");
        StdOut.println();

//        deq.removeFirst();
//        StdOut.println(deq);
//        deq.removeFirst();
//        StdOut.println(deq);
        deq.removeLast();
        StdOut.println(deq);
        deq.removeLast();
        StdOut.println(deq);
        deq.removeLast();
        StdOut.println(deq);
        deq.removeFirst();
        StdOut.println(deq);
        deq.removeLast();
        StdOut.println(deq);
        StdOut.println(deq.size());
        deq.removeFirst();
        StdOut.println(deq);
        StdOut.println(deq.isEmpty());

        for (String s: deq)
            StdOut.print(s + " ");
        StdOut.println();

        deq.addFirst("0");
        StdOut.println(deq);

    }

}