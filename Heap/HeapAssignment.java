import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//node for each tree element
class Node {
    int data;
    Node left;
    Node right;
    Node parent;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null; 
    }
}

//queue nodes 
class QueueNode {
    Node treeNode;
    QueueNode next;

    public QueueNode(Node x) {
        this.treeNode = x;
        this.next = null;
    }
}

//queue methods
class Queue {
    private QueueNode head = null;
    private QueueNode tail = null;

    //enqueue which adds to the end of the queue
    public void enqueue(Node x) {
        QueueNode newNode = new QueueNode(x);

        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        tail.next = newNode;
        tail = newNode;
    }

    //dequeue which removes from front
    public Node dequeue() {
        if (head == null) {
            return null;
        }
        
        Node temp = head.treeNode;
        head = head.next;

        if (head == null) {
            tail = null;
        }
        return temp;
    }

    public boolean isEmpty() {
        return (head == null);
    }
}

//max heap with methods
class MaxHeap {
    private Node root = null;

    //insert value into heap
    public void insert(int x) {
        Node newNode = new Node(x);

        if (root == null) {
            root = newNode;
            return;
        }

        Queue queue = new Queue();
        queue.enqueue(root);

        while (true) {
            Node current = queue.dequeue();

            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current;
                break;
            }
            queue.enqueue(current.left);

            if (current.right == null) {
                current.right = newNode;
                newNode.parent = current;
                break;
            }
            queue.enqueue(current.right);
        }

        siftUp(newNode);
    }

    //sift up
    private void siftUp(Node node) {
        if (node == null) {
            return;
        }

        if (node.parent == null) {
            return;
        }

        if (node.data > node.parent.data) {
            int temp = node.data;
            node.data = node.parent.data;
            node.parent.data = temp;

            siftUp(node.parent);
        }
    }

    //delete root and replace with last node
    public void deleteRoot() {
        if (root == null) {
            return;
        }

        Node lastNode = null;
        Queue queue = new Queue();
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            lastNode = queue.dequeue();
            if (lastNode.left != null) {
                queue.enqueue(lastNode.left);
            }
            if (lastNode.right != null) {
                queue.enqueue(lastNode.right);
            }
        }

        if (lastNode == root) {
            root = null;
            return;
        }

        root.data = lastNode.data;

        Node parent = lastNode.parent;
        if (parent.left == lastNode) {
            parent.left = null;
        } else {
            parent.right = null;
        }

        siftDown(root);
    }

    //sift down
    private void siftDown(Node node) {
        if (node == null) {
            return;
        }

        Node largest = node;

        if (node.left != null && node.left.data > largest.data) {
            largest = node.left;
        }
        if (node.right != null && node.right.data > largest.data) {
            largest = node.right;
        }

        if (largest != node) {
            int temp = node.data;
            node.data = largest.data;
            largest.data = temp;

            siftDown(largest);
        }
    }

    //display tree
    public void display() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        Queue queue = new Queue();
        queue.enqueue(root);
        queue.enqueue(null); 
        
        String levelText = "";

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();

            if (current == null) {
                System.out.println(levelText.trim());
                levelText = "";
                
                if (!queue.isEmpty()) {
                    queue.enqueue(null);
                }
            } else {
                levelText += current.data + " ";
                if (current.left != null) {
                    queue.enqueue(current.left);
                }
                if (current.right != null) {
                    queue.enqueue(current.right);
                }
            }
        }
    }
}

//main to read file and run methods
public class HeapAssignment {
    public static void main(String[] args) {
        MaxHeap heap = new MaxHeap();

        try {
            File file = new File("test.txt");
            Scanner scanner = new Scanner(file);

            System.out.println("Beginning: ");
            while (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                System.out.println("Number insert: " + num);
                heap.insert(num);
                heap.display();
            }
            scanner.close();

            System.out.println("\nDeleting: ");
            for (int i = 1; i <= 3; i++) {
                System.out.println("Deleting");
                heap.deleteRoot();
                heap.display();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}