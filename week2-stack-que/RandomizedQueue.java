import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array = null;
    private int count;
    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[1];
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    private void resize(int size) {
        Item[] newArray = (Item[]) new Object[size];
        for (int i = 0; i < count; i++)
            newArray[i] = array[i];
        array = newArray;
    }
    private void resize() {
        // only inc
        if (count == array.length) {
            resize(array.length * 2);
        } else if (1 < count && count <= array.length / 4) {
            resize(array.length / 2);
        }
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        array[count++] = item;
        resize();
    }

    // remove and return a random item
    public Item dequeue() {
        if (count == 0)
            throw new NoSuchElementException();

        int idx = StdRandom.uniformInt(0, count);
        Item item = array[idx];
        array[idx] = array[count - 1];
        array[count - 1] = null;
        count--;
        resize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (count == 0)
            throw new NoSuchElementException();

        int idx = StdRandom.uniformInt(0, count);
        return array[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

//    @Override
//    public String toString() {
//        return Arrays.toString(array);
//    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] idxs;
        int pos;
        public RandomizedQueueIterator() {
            idxs = new int[count];
            for (int i = 0; i < count; i++)
                idxs[i] = i;
            StdRandom.shuffle(idxs);
            pos = 0;
        }
        public boolean hasNext() { return pos < idxs.length; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (pos >= idxs.length)
                throw new NoSuchElementException();
            return array[idxs[pos++]];
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(0);
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        rq.enqueue(7);
        System.out.println(rq);

        for (Integer val: rq)
            System.out.print(val);
        System.out.println();

        for (Integer val: rq)
            System.out.print(val);
        System.out.println();

        System.out.println(rq.isEmpty());
        System.out.println(rq.sample());
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        System.out.println(rq);
        System.out.println(rq.size());
    }
}