import java.util.Scanner;

//simple node class that uses a constructor to create a node with data and next pointer
class Node{
    int data;
    Node next;
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

//simple stack that has push, pop, peek, and isEmpty functions
//push takes the node and makes it the new head
//pop takes the head and makes the next node the new head
//peek looks at the head and returns data
//isEmpty checks if head is null
class Stack{
    Node head;

    public Stack() {
        this.head = null;
    }

    //push takes the data and makes a new node and makes it the new head
    public void push(int data) {
        Node instance = new Node(data);
        instance.next = head;
        head = instance;
    }

    //if head is null, then we return -1 to signify stack is empty, otherwise we return the data of the head and make the next node the new head
    public int pop() {
        if (head == null) {
            return -1; 
        }
        int data = head.data;
        head = head.next;
        return data;
    }

    //peek returns -1 if stack is empty, otherwise it returns the data of the head
    public int peek() {
        if (head == null) {
            return -1;
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
}

class Queue{
    Node head;
    Node tail;

    public Queue() {
        this.head = null;
        this.tail = null;
    }

    //enqueue takes the data and makes a new node and adds it to the end of the queue
    //if head is null, then we set head to the new node as well
    public void enqueue(int data) {
        Node instance = new Node(data);
        if (tail != null) {
            tail.next = instance;
        }
        tail = instance;
        if (head == null) {
            head = tail;
        }
    }

    //returning -1 if the queue is empty, otherwise returning the data of the head and making the next node the new head
    //if the new head is null, then we also set tail to null
    public int dequeue() {
        if (head == null) {
            return -1;
        }
        int data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    //curr is a pointer that starts at the head and loops through the queue until it reaches null, adding the data of each node to the result string
    public String toString() {
        String result = "";
        Node curr = head;
        while (curr != null) {
            result += (char)curr.data;
            curr = curr.next;
        }
        return result;
    } 
}

//postfix process
//start with creating instances of stack and queue and loop through the expression and looking at every char
//the for loop enqueues if it's a number and pushes if it's an operator
//if its a ) then we pop until we find the ( and enqueue those operators
//lastly pop leftover operators, enqueue, then return queue
class Postfix{

    public static String PostFixFunc(String input) {
        Stack stackInstance = new Stack();
        Queue queueInstance = new Queue();

        for (int i = 0; i < input.length(); i++) {
            char charBeingLookedAt = input.charAt(i);
            
            if (charBeingLookedAt >= '0' && charBeingLookedAt <= '9') {
                queueInstance.enqueue(charBeingLookedAt); 
            } else if (charBeingLookedAt == '(') {
                stackInstance.push(charBeingLookedAt);
            } else if (charBeingLookedAt == ')') {
                while (!stackInstance.isEmpty() && stackInstance.peek() != '(') {
                    queueInstance.enqueue(stackInstance.pop());
                }
                stackInstance.pop();
            } else {
                while (!stackInstance.isEmpty() && stackInstance.peek() != '(' && WhatHasPrecedence((char)stackInstance.peek()) >= WhatHasPrecedence(charBeingLookedAt)) {
                    queueInstance.enqueue(stackInstance.pop());
                }
                stackInstance.push(charBeingLookedAt);
            }
        }

        //if the stack has stuff in it, pop and enqueue until empty
        while (!stackInstance.isEmpty()) {
            queueInstance.enqueue(stackInstance.pop());
        }
        return queueInstance.toString(); 
    }

    //precedence function to determine what operator goes first and what goes second and so on
    //x is the operator and we return a number that represents its precedence, higher number means higher precedence
    public static int WhatHasPrecedence(int x) {
        if (x == '+' || x == '-') {
            return 1;
        } else if (x == '*' || x == '/') {
            return 2;
        }
        return 0;
    }

    //takes the postfix expression and determines result
    //create stack and for every char in the expression look at if its a number or operator
    //if number, push to stack, if operator pop twice and do the operation and push result back to stack
    public static int WhatDoesThisMean(String postFixExpression){
        Stack s = new Stack();

        for (int i = 0; i < postFixExpression.length(); i++) {
         char c = postFixExpression.charAt(i);
            if (c >= '0' && c <= '9') { 
                s.push(c - '0');
            } else {
                int val2 = s.pop();
                int val1 = s.pop();
                
                if (c == '+') s.push(val1 + val2);
                else if (c == '-') s.push(val1 - val2);
                else if (c == '*') s.push(val1 * val2);
                else if (c == '/') s.push(val1 / val2);
            }
        }
        return s.pop();
    }
}

//the main class that is called first and calls everything else
//grabs input from the user and runs it through the postfix process
//returns result
public class Driver {
    public static void main(String[] args){
        System.out.println("\n\nHello World. My name is Damon Bun and this is my postfix evaluation program.");

        Scanner inputScannerInstance = new Scanner(System.in);
        System.out.println("Enter input expression:\n========================================");
        String inputExpression = inputScannerInstance.nextLine();

        System.out.println("========================================\nYour infix expression:" + inputExpression);

        String postfix = Postfix.PostFixFunc(inputExpression);
        System.out.println("========================================\nAfter PostFixFunc: " + postfix);

        int result = Postfix.WhatDoesThisMean(postfix);
        System.out.println("Result: " + result);
        System.out.println("========================================\nThank you for using my program. Goodbye!");
        inputScannerInstance.close();
    } 
}

