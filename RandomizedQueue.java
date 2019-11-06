/* *****************************************************************************
 *  Name: Sam Engleang
 *  Date: 11/03/2019
 *  Description: Randomized queue for handling queue API and get it back randomly
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int[] spaces;
    private int size = 0;
    private int length = size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        spaces = new int[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }


    // add the item
    public void enqueue(Item item) {
        if (size == items.length) {
            resize(2 * size);
        }
        items[size] = item;
        spaces[size] = size;
        size++;
        length = size;
    }


    // remove and return a random item
    public Item dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException("No such element");
        }
        int random = StdRandom.uniform(size);
        int n = spaces[random];
        Item item = items[n];
        int next;
        if (item == null) {
            n = findNext(n, spaces, length, this.items);
            item = items[n];
            items[n] = null;
            next = n + 1;
            if (next >= length) {
                next = 0;
            }
            next = spaces[next];
            update(next, n, this.spaces, length);
        } else {
            items[n] = null;
            next = n + 1;
            if (next >= length) {
                next = 0;
            }
            next = spaces[next];
            update(next, n, spaces, length);
        }
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
            length = size;
        }
        return item;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        int index = 0;
        spaces = new int[newSize];
        for (int i = 0; i < items.length; i++) {
            if (index >= newSize) {
                break;
            }
            if (items[i] != null) {
                copy[index] = items[i];
                spaces[index] = index;
                index++;
            }
        }
        items = copy;
    }

    private void update(int next, int n, int[] spaces, int size) {
        int index = n;
        int counter = size;
        while (spaces[index] == n && counter-- > 0) {
            spaces[index] = next;
            index--;
            if (index < 0) {
                index = size - 1;
            }
        }
    }

    private int findNext(int n, int[] spaces, int size, Item[] items) {
        int index = n + 1;
        int counter = size;
        while (counter-- > 0) {
            if (index >= size) {
                index = 0;
            }
            if (items[index] != null) {
                break;
            }
            index = spaces[index];
        }
        return index;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int random = StdRandom.uniform(size);
        int n = spaces[random];
        if (items[n] == null) {
            n = findNext(n, spaces, length, items);
        }
        return items[n];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        int s = size;
        int[] innerSpaces;
        Item[] copyItems;
        int length = size;

        RandomIterator() {
            innerSpaces = new int[spaces.length];
            copyItems = (Item[]) new Object[items.length];
            for (int i = 0; i < innerSpaces.length; i++) {
                innerSpaces[i] = spaces[i];
                copyItems[i] = items[i];
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported");
        }

        @Override
        public boolean hasNext() {
            return s > 0;
        }

        private void printSpace() {
            System.out.println();
            System.out.print("spaces : ");
            for (int i = 0; i < innerSpaces.length; i++) {
                System.out.print(" [" + i + ":" + innerSpaces[i] + "]");
            }
            System.out.println();
            System.out.print("items  :");
            for (int i = 0; i < copyItems.length; i++) {
                if (copyItems[i] == null) {
                    System.out.print(" null");
                } else {
                    System.out.print(" " + copyItems[i]);
                }
            }
            System.out.println();
        }

        @Override
        public Item next() {
            if (s == 0) {
                throw new NoSuchElementException("No element");
            }
            int random = StdRandom.uniform(s);
            int n = innerSpaces[random];
            Item item = copyItems[n];
            int next;
            if (item == null) {
                n = findNext(n, innerSpaces, length, this.copyItems);
                item = copyItems[n];
                copyItems[n] = null;
                next = n + 1;
                if (next >= s) {
                    next = 0;
                }
                next = innerSpaces[next];
                update(next, n, innerSpaces, length);
                printSpace();
            } else {
                copyItems[n] = null;
                next = n + 1;
                if (next >= length) {
                    next = 0;
                }
                next = innerSpaces[next];
                update(next, n, innerSpaces, length);

            }

            s--;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        for (int test = 0; test < 100; test++) {
            System.out.println("begin test " + test);
            int counter = StdRandom.uniform(10, 2000);
            System.out.println("counter : " + counter);

            RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
            for (int i = 0; i < counter; i++) {
                randomizedQueue.enqueue(i);
            }
            for (int i = 0; i < counter; i++) {
                Integer item = randomizedQueue.dequeue();
                if (item == null) {
                    throw new RuntimeException("found item null ");
                } else {
                    System.out.println("random queue :" + item);
                }

            }
        }

        System.out.println("**************************************************");
        for (int test = 0; test < 100; test++) {
            RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
            int counter = StdRandom.uniform(100, 2000);
            for (int i = 0; i < counter; i++) {
                randomizedQueue.enqueue(i);

            }
            for (int item : randomizedQueue) {
                System.out.println("iterator item : " + item);

            }
        }


    }


}
