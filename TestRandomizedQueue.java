/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;
import java.util.Set;

public class TestRandomizedQueue<Item> {
    private Item[] items;
    private int[] spaces;
    private int size;

    public TestRandomizedQueue() {
        items = (Item[]) new Object[1];
        spaces = new int[1];
    }

    public void enqueue(Item item) {
        if (size == items.length) {
            resize(2 * size);
        }
        items[size] = item;
        spaces[size] = size;
        size++;
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

    private void update(int next, int n) {
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

    private int next(int n) {
        int index = n;
        int counter = size;
        while (counter-- > 0) {
            index++;
            if (index >= size) {
                index = 0;
            }
            if (spaces[index] == index) {
                break;
            }

        }
        return index;
    }

    Item deque() {
        int random = StdRandom.uniform(size);
        int n = random;
        Item item = items[n];
        int next;
        if (item == null) {
            n = next(n);
            item = items[n];
            items[n] = null;
            next = n + 1;
            if (next >= size) {
                next = 0;
            }
            next = spaces[next];
            update(next, n);
        } else {
            items[n] = null;
            next = next(n);
            if (next >= size) {
                next = 0;
            }
            next = spaces[next];
            update(next, n);
        }
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public static void main(String[] args) {
        TestRandomizedQueue<Integer> test = new TestRandomizedQueue<>();
        Set<Integer> set = new HashSet<Integer>();
        int counter = 10000;
        for (int i = 0; i < counter; i++) {
            test.enqueue(i);
        }
        for (int i = 0; i < counter; i++) {
            int item = test.deque();
            if (set.contains(item)) {
                System.out.println("error : " + item + " already fetch");
                break;
            }
            set.add(item);
            System.out.println("item : [" + item + "]");
        }
        if (set.size() != counter) {
            System.out.println("error not same size : " + counter + " vs " + set.size());
        }
    }
}
