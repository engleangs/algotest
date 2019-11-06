/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueueBackup<Item> implements Iterable<Item> {
    int size  = 0;
    private Item[] items ;
    private Node[] nodes;

    private static class Node{
        Node next;
        int index;
        Node prev;

    }
    // construct an empty randomized queue
    public RandomizedQueueBackup(){
        items = (Item[]) new Object[ 1];
        nodes = new Node[ 1];
    }

    private void resize(int newSize){
        Item[]copy  = (Item[]) new Object[ newSize];
        nodes = new Node[ newSize ];
        int index = 0;
        for(int i = 0;i< items.length;i++){
            if( index >= newSize) {
                break;
            }
            if( items[i] !=null) {
                copy[index] = items[i];
                Node node = new Node();
                node.index = index;
                nodes[index] = node;
                if( index > 0) {
                    nodes[ index -1].next = node;
                    node.prev = nodes[ index -1];
                    node.next = nodes[0];
                }
                index++;
            }
        }
        items = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if( size == items.length) {
            resize( 2  * size);
        }
        Node node = new Node();
        items[ size ] = item;
        nodes[size] = node;
        node.index = size;
        if( size > 0) {
            nodes[ size - 1].next = node;
            node.next = nodes[0];
        }
        size++;
    }


    private Node find(Node node){
        while ( items[node.index] == null) {
            if( node.index  == node.next.index ){
                break;
            }
            node = node.next;
        }
        return node;
    }
    // remove and return a random item
    public Item dequeue() {
        if( size ==0){
            throw  new NoSuchElementException("No element");
        }

        int n = StdRandom.uniform( size);
        Node current = nodes[ n];
        Item item = items[n];
        size --;
        if( item == null) {
            if( size > 0) {
                current = find(current.next);
                item =  items[current.next.index];
                items[ current.next.index] = null;
                Node node = find( current.next.next);
                current.next = node;//point all to the next;
                current.next.next = node;
                if( current.prev !=null) {
                    current.prev.next = node;
                }


            }
          }
        else {
            items[ n] = null;
            if( size > 0) {
                Node node = find(current.next);
                current.next = node;
                if( current.prev != null) {
                    current.prev.next = node;
                }

            }

        }
        if( item == null) {
            System.out.println("to check");
        }

        if( size > 0 && size == items.length/4) {
            resize( items.length  /2 );
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        return null;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueueBackup<Integer> randomizedQueueBackup = new RandomizedQueueBackup<>();
        for(int i = 1;i<10;i++){
            randomizedQueueBackup.enqueue(i);
        }
        System.out.println("finish enquue");
        for(int i = 1;i<10;i++) {
            System.out.println(randomizedQueueBackup.dequeue());
        }

        //System.out.println(randomizedQueueBackup.dequeue());



    }

}
