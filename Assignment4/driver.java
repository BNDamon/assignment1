import java.util.Scanner;

public class driver {

    static int[][] array = new int[5][4];
    static int[][] unsortedArray = {
        {5, 3, 2, 16},
        {9, 8, 10, 17},
        {4, 7, 11, 18},
        {2, 5, 9, 12},
        {7, 9, 4, 10}
    };

    public static void main(String[] args) {
        Scanner scannerInstance = new Scanner(System.in);

        reset();
        System.out.println("\nThis is my array: ");
        display();

        reset();
        //1st column in ascending order
        System.out.println("Sorted using Bubble Sort");
        BubbleSort.bubbleSort(array);
        display();

        reset();
        //2nd column in descending order 
        System.out.println("\nSorted using Selection Sort");
        SelectionSort.selectionSort(array);
        display();

        reset();
        //3rd column in ascending order 
        System.out.println("\nSorted using Shell Sort");
        ShellSort.shellSort(array);
        display();

        reset();
        //5th row in ascending order
        System.out.println("\nSorted using Insertion Sort");
        InsertionSort.insertionSort(array);
        display();

        try {
            System.out.println("\nSearch for a number in the 5th row:");
            int y;
            y = scannerInstance.nextInt();
            
            int x = BinarySearch.binarySearch(array, y);
            if (x != -1) {
                for(int i = 0; i < 5; i++) {
                    System.out.println(array[i][x]);
                }
            } else {
                System.out.println("Not in that row");
            }
        } catch (Exception e) {
            System.out.println("Error");
        } 
        scannerInstance.close();
    }

    public static void reset() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++)
                array[i][j] = unsortedArray[i][j];
    }

    public static void display() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%4d ", array[i][j]);
            }
            System.out.println();
        }
    }
}

class BubbleSort {
    public static void bubbleSort(int[][] x) {
        int limit = 4; 
        for (; limit > 0; limit--) {
            for (int i = 0; i < limit; i++) {
                if (x[i][0] > x[i + 1][0]) {
                    int[] temp;
                    temp = x[i];
                    x[i] = x[i + 1];
                    x[i + 1] = temp;
                }
            }
        }
    }
}

class SelectionSort {
    public static void selectionSort(int[][] x) {
        int limit = 4; 
        for (; limit > 0; limit--) {
            int smallestI = 0; 
            for (int i = 1; i <= limit; i++) {
                if (x[i][1] < x[smallestI][1]) {
                    smallestI = i;
                }
            }
            if (limit != smallestI) {
                int[] temp = x[limit];
                x[limit] = x[smallestI];
                x[smallestI] = temp;
            }
        }
    }
}

class ShellSort {
    public static void shellSort(int[][] x) {
        int n = 5; 
        for (int gap = n / 2; gap >= 1; gap = gap / 2) {
            for (int i = gap; i < n; i++) {
                for (int j = i; j >= gap && x[j - gap][2] > x[j][2]; j = j - gap) {
                    int[] temp = x[j - gap];
                    x[j - gap] = x[j];
                    x[j] = temp;
                }
            }
        }
    }
}

class BinarySearch {
    public static int binarySearch(int[][] x, int key) {
        int high = 3;
        int low = 0; 
        int mid = 0;
        boolean condition = false;
        int indexFound = -1;

        while (high >= low && !condition) {
            mid = (high + low) / 2;
            if (key > x[4][mid]) {
                low = mid + 1;
            } else if (key < x[4][mid]) {
                high = mid - 1;
            } else {
                condition = true;
                indexFound = mid; 
            }
        }
        return indexFound; 
    }
}

class InsertionSort {
    public static void insertionSort(int[][] x) {
        for (int i = 1; i < 4; i++) {
            for (int j = i; j > 0 && x[4][j] < x[4][j - 1]; j--) {
                for (int k = 0; k < 5; k++) {
                    int tmp = x[k][j];
                    x[k][j] = x[k][j - 1];
                    x[k][j - 1] = tmp;
                }
            }
        }
    }
}