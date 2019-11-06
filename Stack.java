/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Stack<Item> {
    private Node<Item>last;
    private class Node<Item>{
        Item item;
        Node<Item>next;
    }
    private int size = 0;
    public Stack(){
        last = new Node<>();
    }
    public boolean isEmpty(){
        return size ==0;
    }
    public void push(Item item){
        Node<Item> itemNode = new Node<>();
        itemNode.item = item;
        itemNode.next = last.next;
        last.next = itemNode;
        size++;
    }
    public Item pop(){
        if( size == 0){
            throw new NoSuchElementException("No element");
        }
        Node<Item>node = last.next;
        last.next = node.next;
        size--;
        return node.item;
    }
    public int size(){
        return size;
    }

    public static boolean isOperator(char ch){
        return ch =='+' || ch =='-' || ch=='*' || ch =='/';
    }
    public static boolean isRightParenthesis (char ch){
        return ch==')';
    }
    public static boolean isNumeric(char ch) {
        return  ch >='0' && ch <='9';
    }
    public static double compute(double left ,double right ,char operator) {
        switch (operator) {
            case  '+':
                    return left+ right;
            case  '-':
                return  left - right;
            case  '*':
                    return left * right;
            case  '/':
                return left/right;
        }
        return 0;
    }

    public static double  checkAndCompute( Stack<Character>operatorStack ,Stack<Double> valueStack){
        double left = valueStack.pop();
        double right = valueStack.pop();
        double value = compute( left, right, operatorStack.pop());
        valueStack.push( value);
        return value;
    }
    public static boolean isHighOperator(char ch){
        return ch =='*' || ch =='/';
    }

    public static void main(String[] args) {
        Stack<Double>valueStack  = new Stack<>();
        Stack<Character>operatorStack  = new Stack<>();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        char[] chars = str.toCharArray();
        StringBuilder strB = new StringBuilder();
        char lastOperator = '\0';
        for(char ch : chars) {
            if( isOperator( ch)) {

                if( strB.length() > 0) {
                    valueStack.push( Double.parseDouble( strB.toString()));
                    strB = new StringBuilder();
                }
                if( isHighOperator( lastOperator)) {
                    checkAndCompute(  operatorStack , valueStack);
                }
                operatorStack.push( ch);
                lastOperator = ch;
            }
            else if ( isRightParenthesis( ch)) {
                if( strB.length() > 0) {
                    valueStack.push( Double.parseDouble( strB.toString()));
                    strB = new StringBuilder();
                }
                while (operatorStack.size() > 0 && valueStack.size() > 1) {
                        checkAndCompute( operatorStack , valueStack);
                }
            }
            else if( isNumeric( ch)) {
                strB.append( ch);
            }
        }

        if( strB.length() > 0) {
            valueStack.push( Double.parseDouble( strB.toString()));
            strB = new StringBuilder();
        }

        while ( operatorStack.size() > 0 && valueStack.size() > 1) {
            checkAndCompute( operatorStack,valueStack);
        }

        if( valueStack.size() > 0) {
            System.out.println(" result : " + valueStack.pop());
        }
    }
}
