import java.util.Iterator;
import java.util.NoSuchElementException;

/* *****************************************************************************
 *  Name: Sam Engleang
 *  Date:
 *  Description:
 **************************************************************************** */
public class Deque<Item>  implements Iterable<Item>{
    private  int size = 0;
    private Node<Item> front;
    private Node<Item>rear;

    private void checkNull(Item item) {
        if( item == null){
            throw  new IllegalArgumentException("Invalid item");
        }
    }


    private  class Node<Item>{
        private Item item;
        private Node<Item>next;
        private Node<Item>prev;
    }

    // construct an empty deque
    public Deque(){
        front = new Node<Item>();
        rear = new Node<Item>();
        front.next = rear;
        rear.prev = front;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull( item );
        Node<Item>node = new Node<Item>();
        node.item = item;
        node.next = front.next;
        front.next.prev = node;
        front.next = node;
        node.prev = front;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        checkNull( item );
        Node<Item>node = new Node<Item>();
        node.item = item;
        rear.prev.next = node;
        node.prev = rear.prev;
        rear.prev = node;
        node.next = rear;
        size ++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if( isEmpty()) {
            throw new NoSuchElementException("No element");
        }
        Node<Item>temp = front.next;
        front.next = temp.next;
        temp.next.prev = front;
        temp.next = null;
        temp.prev = null;
        size--;
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if( isEmpty()) {
            throw new NoSuchElementException("No element");
        }
        Node<Item>temp = rear.prev;
        rear.prev = temp.prev;
        temp.prev.next =  rear;
        temp.prev = null;
        temp.next = null;
        size--;
        return temp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeItorator();
    }
    private  class DequeItorator implements Iterator<Item>{

        private Node<Item> f;
        private int copySize = size;
        DequeItorator(){
            f = new Node<>();
            f.next  = front.next;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported");
        }

        @Override
        public boolean hasNext() {

            return copySize> 0 ;
        }

        @Override
        public Item next() {
            if( size == 0) {
                throw  new NoSuchElementException("No element");
            }
            Node<Item>temp = f.next;
            f.next = temp.next;
            copySize --;
            return temp.item;
        }
    }
    public static void main(String[] args) {
        Deque<Integer>deque = new Deque<Integer>();
        deque.addFirst( 1);
        deque.addFirst( 0);
        deque.addFirst( -1);
        deque.addLast(4);
        deque.addLast(5);
        deque.addFirst(2);
        for(int i : deque){
            System.out.println("i : "+i);
        }

        for(int i : deque){
            System.out.println("i : "+i);
        }

        System.out.println("item : "+deque.removeLast());
        System.out.println("item :"+deque.removeFirst());
        System.out.println("item :"+deque.removeFirst());
        System.out.println("item :"+deque.removeLast());
        System.out.println("item :"+deque.removeLast());
        System.out.println("item :"+deque.removeLast());
        //System.out.println("item :"+deque.removeFirst());


    }
}
