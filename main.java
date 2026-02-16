import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* creating links to the linked list
string items represents the values passed into the link
next serves as a pointer */
class DamonBun {
    String items;
    DamonBun next;

    DamonBun(String name) {
        items = name;
        next = null;
    }
}

/* holds the different methods for using the linkedlist */
class LinkedList {
    DamonBun head = null;
    DamonBun tail = null;

    /* if there isnt a head then create a list with a head and tail 
    else if there is a head just point the tail of the last node to the new one 
    then set the tail */
    void add(String name) {
        DamonBun newList = new DamonBun(name);
        if (head == null) {
            head = newList;
            tail = newList;
        } else {
            tail.next = newList;
            tail = newList;
        }
    }

    /* set the point to be at the very front of the list 
    while the point is still valid go through all the names of the list and display it */
    void traverse() {
        DamonBun current = head;
        while (current != null) {
            System.out.print(current.items + ", ");
            current = current.next;
        }
        System.out.print("\n");
    }

    /* if the list is valid then count all the differenet nodes to find the total sum.
    iterate to the exact middle of the list, then divides it and
    return brand new list to be returned. */
    LinkedList split() {
        if (head == null) return null;

        // count values in the list
        int sum = 0;
        DamonBun current = head;
        while (current != null) {
            sum++;
            current = current.next;
        }

        // find middle of list
        int mid = sum / 2;
        DamonBun middle = head;
        for (int i = 1; i < mid; i++) {
            middle = middle.next;
        }

        // now establish the second half of list
        LinkedList last = new LinkedList();
        last.head = middle.next;
        last.tail = tail; 

        middle.next = null;
        tail = middle;

        return last;
    }

    /* If the first list is empty then set head to be start of second half of the list */
    void merge(LinkedList listTwo) {
        if (head == null) {
            head = listTwo.head;
        } else {
            tail.next = listTwo.head;
            tail = listTwo.tail;
        }
    }
}

class Project1 {
    public static void main(String[] args) {
        File file = new File("input.txt");
        LinkedList list = new LinkedList();

        System.out.println("\n\nDamon Bun - Assignment 1 Split Merge");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String item = scanner.nextLine();
                list.add(item);
            }
            scanner.close();

            System.out.println("\nInput.txt Names:");
            list.traverse();
            LinkedList second = list.split();
            System.out.println("\nFirst Half of list:");
            list.traverse();

            System.out.println("\nSecond half list:");
            second.traverse();
            list.merge(second);
            System.out.println("\nMerged list");
            list.traverse();
            System.out.println("\n");

        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file");
        }
    }
}