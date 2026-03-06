import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//node class
//variables to hold name, next, and previous pointers
class Node {
    private String nameVarString;
    private Node nextPtr;
    private Node prevPtr;

    public Node(String name) {
        this.nameVarString = name.toLowerCase();
        this.nextPtr = null;
        this.prevPtr = null;
    }
    public String getName() {
        return nameVarString; 
    }   
    public Node getNext() { 
        return nextPtr; 
    }
    public void setNext(Node next) { 
        this.nextPtr = next; 
    }
    public Node getPrev() { 
        return prevPtr; 
    }
    public void setPrev(Node prev) { 
        this.prevPtr = prev; 
    }
}

class DoublyLinkedList {
    private Node head;
    private Node tail;

    public void insert(String x) {
        Node nodeInstance = new Node(x);
        String lowerCaseString = x.toLowerCase();
        
        if (head == null) {
            head = nodeInstance;
            tail = nodeInstance;
        } else {
            String nameAtHeadString = head.getName();
            if (lowerCaseString.compareTo(nameAtHeadString) < 0) {
                nodeInstance.setNext(head);
                head.setPrev(nodeInstance);
                head = nodeInstance;
            } else {
                String nameAtTailString = tail.getName();
                if (lowerCaseString.compareTo(nameAtTailString) > 0) {
                    tail.setNext(nodeInstance);
                    nodeInstance.setPrev(tail);
                    tail = nodeInstance;
                } else {
                    Node currentNode = head;
                    while (currentNode != null && lowerCaseString.compareTo(currentNode.getName()) > 0) {
                        currentNode = currentNode.getNext();
                    }
                    nodeInstance.setNext(currentNode);
                    nodeInstance.setPrev(currentNode.getPrev());    
                    currentNode.getPrev().setNext(nodeInstance);
                    currentNode.setPrev(nodeInstance);
                }
            }
        }
    }

    public void delete(String x) {
        Node currentPosition = head;
        String target = x.toLowerCase();
        
        while (currentPosition != null && !currentPosition.getName().equals(target)) {
            currentPosition = currentPosition.getNext();
        }
        if (currentPosition == null) {
            return;
        }
        if (currentPosition == head && currentPosition == tail) {
            head = null;
            tail = null;
        } else if (currentPosition == head) {
            head = head.getNext();
            head.setPrev(null);
        } else if (currentPosition == tail) {
            tail = tail.getPrev();
            tail.setNext(null);
        } else {
            Node previousNode = currentPosition.getPrev();
            Node nextNode = currentPosition.getNext();
            
            previousNode.setNext(nextNode);
            nextNode.setPrev(previousNode);
        }
    }

    public void traverseAscending(PrintWriter x) {
        Node currentNode = head;
        while (currentNode != null) {
            x.println(currentNode.getName());
            currentNode = currentNode.getNext();
        }
    }

    public void traverseDescending(PrintWriter x) {
        Node currentNode = tail;
        while (currentNode != null) {
            x.println(currentNode.getName());
            currentNode = currentNode.getPrev();
        }
    }
}

//this class is the main class 
//main method creates a doubly linked list
//grabs the input file and output file
//reads the input file and performs insert or delete operations on the linked list
public class driver {
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        try {
            File iFile = new File("input.txt");
            Scanner scannerInstance = new Scanner(iFile);
            while (scannerInstance.hasNext()) {
                String lineToCheck = scannerInstance.next();
                String lowerCaseLineString = lineToCheck.toLowerCase();
                if (lowerCaseLineString.equals("delete")) {
                    if (scannerInstance.hasNext()) {
                        String nameToDelete = scannerInstance.next();
                        System.out.println("Delete - " + nameToDelete);
                        list.delete(nameToDelete);
                    }
                } else {
                    System.out.println("Insert - " + lineToCheck);
                    list.insert(lineToCheck);
                }
            }
            scannerInstance.close();
            PrintWriter writingToOutputInstance = new PrintWriter(new File("output.txt"));
            list.traverseAscending(writingToOutputInstance);
            writingToOutputInstance.println("=============");
            list.traverseDescending(writingToOutputInstance);
            writingToOutputInstance.close();
            System.out.println("\n===============================================");
            System.out.println("Wrote list to output.txt");
            System.out.println("===============================================\n");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } 
    }
}