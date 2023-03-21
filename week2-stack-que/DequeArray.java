import java.util.NoSuchElementException;
import java.util.Iterator;

public class DequeArray<Item> implements Iterable<Item> {

    private int li = 0;
    private int ri = 0;
    private Item[] array = null;
    private int arrayCount = 0;
    // construct an empty deque
    public DequeArray() {
        arrayCount = 3;
        li = 0;
        ri = 0;
        array = (Item[]) new Object[arrayCount];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return li == ri;
    }

    // return the number of items on the deque
    public int size() {
        if (ri >= li)
            return ri - li;
        else
            return arrayCount - li + ri;
    }

    private void resize() {
        // only inc
        if (arrayCount - size() <= 1) {
            Item[] newArray = (Item[]) new Object[arrayCount * 2];
            if (li <= ri) {
                for (int i = li; i <= ri; i++)
                    newArray[i] = array[i];
            } else {
                for (int i = 0; i <= ri; i++)
                    newArray[i] = array[i];
                for (int i = li; i < arrayCount; i++)
                    newArray[arrayCount + i] = array[i];
                li += arrayCount;
            }
            array = newArray;
            arrayCount *= 2;
        } else if (size() >= 1 && size() <= arrayCount / 4) {
            Item[] newArray = (Item[]) new Object[arrayCount / 2];
            if (li <= ri) {
                for (int i = li; i <= ri; i++)
                    newArray[i - li] = array[i];
            } else {
                for (int i = li; i < arrayCount; i++)
                    newArray[i - li] = array[i];
                for (int i = 0; i <= ri; i++)
                    newArray[i + arrayCount - li] = array[i];
            }
            ri = size();
            li = 0;
            array = newArray;
            arrayCount /= 2;
        }
    }
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        resize();
        if (--li == -1)
            li = arrayCount - 1;
        array[li] = item;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        resize();
        array[ri] = item;
        if (++ri == arrayCount)
            ri = 0;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = array[li];
        array[li] = null;
        if (++li == arrayCount)
            li = 0;
        resize();
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (--ri == -1)
            ri = arrayCount - 1;
        Item item = array[ri];
        array[ri] = null;
        resize();
        return item;
    }

//    @Override
//    public String toString() {
//        return String.join(" ", new String[] {"li = " + li, "ri = " + ri, Arrays.toString(array)});
//    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = li;

        public boolean hasNext() { return Math.abs(ri - i) > 0; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (Math.abs(ri - i) == 0)
                throw new NoSuchElementException();

            Item item = array[i];
            if (++i == array.length) i = 0;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        DequeArray<String> deq = new DequeArray<>();

//        Deque<Integer> deq = new Deque<>();
//        for (int i = 0; i < 10; i++) {
//            deq.addFirst(i);
//            System.out.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.removeFirst();
//            System.out.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.addLast(i);
//            System.out.println(deq);
//        }
//        for (int i = 0; i < 10; i++) {
//            deq.removeFirst();
//            System.out.println(deq);
//        }
        deq.addFirst("0");
        deq.addFirst("1");

        deq.addLast("2");
        deq.addLast("3");

        for (String s: deq)
            System.out.print(s + " ");
        System.out.println();

        System.out.println(deq);
        deq.addFirst("-1");
        System.out.println(deq);
        deq.addFirst("-2");
        System.out.println(deq);

        for (String s: deq)
            System.out.print(s + " ");
        System.out.println();

//        deq.removeFirst();
//        System.out.println(deq);
//        deq.removeFirst();
//        System.out.println(deq);
        deq.removeLast();
        System.out.println(deq);
        deq.removeLast();
        System.out.println(deq);
        deq.removeLast();
        System.out.println(deq);
        deq.removeFirst();
        System.out.println(deq);
        deq.removeLast();
        System.out.println(deq);
        System.out.println(deq.size());
        deq.removeFirst();
        System.out.println(deq);
        System.out.println(deq.isEmpty());

        for (String s: deq)
            System.out.print(s + " ");
        System.out.println();

        deq.addFirst("0");
        System.out.println(deq);
    }
}