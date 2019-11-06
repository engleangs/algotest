/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        int size = 8;
        if( args.length > 0) {
            size = Integer.parseInt(  args[0] );
        }
        RandomizedQueue<String>randomizedQueue = new RandomizedQueue<>();
        /*
        String[] arr = line.split(" ");
            int n = size;
            if( size > arr.length) {
                n  = arr.length;
            }
            for(int i = 0;i< arr.length;i++){
                randomizedQueue.enqueue( arr[ i]);
            }
            for ( int i =0;i<n;i++) {
                System.out.println( randomizedQueue.dequeue());
            }
         */
        while ( !StdIn.isEmpty()) {
            String line = StdIn.readString();
             randomizedQueue.enqueue( line);

        }
        int k = size;
        for(int i=0;i<k;i++){
            StdOut.println( randomizedQueue.dequeue());
        }


    }
}
