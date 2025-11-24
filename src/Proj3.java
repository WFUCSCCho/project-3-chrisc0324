/**
 * @file: Proj3.java
 * @description: Runs performance tests for different sorting algorithms
 * @author: Chris Cha {@literal <chah22@wfu.edu>}
 * @date: November 12, 2025
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    /**
     * Performs merge sort on an array list
     * @param a ArrayList to sort
     * @param left Starting index
     * @param right Ending index
     */
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;           // Finds the middle value
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays
     * @param a ArrayList to merge
     * @param left Start of left subarray
     * @param mid End of left subarray
     * @param right End of right subarray
     */
    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;
        /* merge two halves by comparing the smallest elements */
        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) <= 0) {
                temp.add(a.get(i));
                i++;
            }
            else {
                temp.add(a.get(j));
                j++;
            }
        }
        /* add remaining elements from left and right */
        while (i <= mid) {
            temp.add(a.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(a.get(j));
            j++;
        }
        /* copy merged results back into the original array */
        for (int k = 0; k < temp.size(); k++) {
            a.set(left + k, temp.get(k));
        }
    }

    /**
     * Performs Quick Sort on an ArrayList using median-of-three pivot
     * @param a ArrayList to sort
     * @param left Starting index
     * @param right Ending index
     */
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(a, left, right);
            quickSort(a, left, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, right);
        }
    }

    /**
     * Partitions the array using median-of-three pivot
     * @param a ArrayList used in sort
     * @param left Starting index
     * @param right Ending index
     * @return final index of the pivot
     */
    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        /* Base case */
        if (right - left <= 1) {
            if (a.get(left).compareTo(a.get(right)) > 0) {
                swap(a, left, right);
            }
            return right;
        }
        int center = (left + right) / 2;

        /* Arrange for median-of-three */
        if (a.get(center).compareTo(a.get(left)) < 0)
            swap(a, left, center);
        if (a.get(right).compareTo(a.get(left)) < 0)
            swap(a, left, right);
        if (a.get(right).compareTo(a.get(center)) < 0)
            swap(a, center, right);

        /* Move pivot for partitioning */
        swap(a, center, right - 1);
        T pivot = a.get(right - 1);

        int i = left;
        int j = right - 2;
        while (true) {
            while (i < right - 1 && a.get(i).compareTo(pivot) < 0) i++;
            while (j > left && a.get(j).compareTo(pivot) > 0) j--;
            if (i < j) swap(a, i, j);
            else break;
        }

        /* Place pivot in its final position */
        swap(a, i, right - 1);
        return i;
    }

    /* Swaps two elements in an ArrayList */
    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    /**
     * Performs Heap Sort on an ArrayList
     * @param a ArrayList used in sort
     * @param left Starting index
     * @param right Ending index
     */
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        int n = right - left + 1;
        /* Build the max heap */
        for (int i = left + n / 2 - 1; i >= left; i--) {
            heapify(a, i, right);
        }
        /* Remove max elements */
        for (int i = right; i > left; i--) {
            swap(a, left, i);
            heapify(a, left, i - 1);
        }
    }

    /**
     * Heapify to satisfy max-heap property
     * @param a ArrayList used in sort
     * @param left Starting index
     * @param right Ending index
     */
    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        int root = left;
        while (true) {
            int leftChild = 2 * (root - left) + 1 + left;
            int rightChild = 2 * (root - left) + 2 + left;
            int largest = root;
            /* Compare children with root */
            if (leftChild <= right && a.get(leftChild).compareTo(a.get(largest)) > 0)
                largest = leftChild;
            if (rightChild <= right && a.get(rightChild).compareTo(a.get(largest)) > 0)
                largest = rightChild;
            /* Swap if root is not the largest */
            if (largest != root) {
                swap(a, root, largest);
                root = largest;
            } else {
                break;
            }
        }
    }

    /**
     * Performs Bubble Sort on ArrayList
     * @param a ArrayList used in sort
     * @param size Size of the array
     * @return Number of comparisons
     */
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        int numCompare = 0;
        boolean swapped;
        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                numCompare++;
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            /* Break early if sorted */
            if (!swapped) {
                break;
            }
        }
        return numCompare;
    }

    /**
     * Perform Odd-Even Transposition Sort in ArrayList
     * @param a ArrayList to sort
     * @param size Size of the array
     * @return Number of comparisons
     */
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int comparisons = 0;
        while (!isSorted) {
            isSorted = true;
            /* Odd comparisons */
            for (int i = 1; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            /* Even comparisons */
            for (int i = 0; i < size - 1; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java Proj3 {dataset-file} {algorithm-type} {number-of-lines}");
            return;
        }
        String filename = args[0];
        String algorithm = args[1].toLowerCase();
        int numLines = Integer.parseInt(args[2]);

        FileInputStream inputFileNameStream = new FileInputStream(filename);
        Scanner inputFileNameScanner = new Scanner(inputFileNameStream);

        inputFileNameScanner.nextLine();

        ArrayList<F1> data = new ArrayList<>(numLines);
        while (inputFileNameScanner.hasNextLine() && data.size() < numLines) {
            String line = inputFileNameScanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] stats = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String driver = stats[0];
            String nationality = stats[1];
            String seasonsRaw = stats[2].replace("[", "").replace("]", "").replace("\"", "").trim();
            String[] seasons;
            if (seasonsRaw.isEmpty()) {
                seasons = new String[0];
            } else {
                seasons = seasonsRaw.split("\\s*,\\s*");
            }
            int championships = Integer.parseInt(stats[3]);
            int raceWins = Integer.parseInt(stats[4]);
            int podiums = Integer.parseInt(stats[5]);
            double points = Double.parseDouble(stats[6]);
            data.add(new F1(driver, nationality, seasons, championships, raceWins, podiums, points));
        }
        inputFileNameScanner.close();
        inputFileNameStream.close();

        ArrayList<F1> sorted = new ArrayList<>(data);
        Collections.sort(sorted);
        ArrayList<F1> shuffled = new ArrayList<>(sorted);
        Collections.shuffle(shuffled);
        ArrayList<F1> reversed = new ArrayList<>(sorted);
        reversed.sort(Collections.reverseOrder());

        FileWriter analysisWriter = new FileWriter("analysis.txt", true); // append
        FileWriter sortedWriter = new FileWriter("sorted.txt", false);

        System.out.println("-----------Algorithm: " + algorithm + "-----------");
        System.out.println("Number of lines: " + numLines);
        if (algorithm.equals("bubblesort")) {
            long start;
            long end;
            double sortedTime;
            double shuffledTime;
            double reversedTime;

            start = System.nanoTime();
            int sortedComparison = bubbleSort(new ArrayList<>(sorted), sorted.size());
            end = System.nanoTime();
            sortedTime = (end - start) / 1_000_000_000.0;
            start = System.nanoTime();
            int shuffledComparison = bubbleSort(new ArrayList<>(shuffled), shuffled.size());
            end = System.nanoTime();
            shuffledTime = (end - start) / 1_000_000_000.0;
            start = System.nanoTime();
            int reversedComparison = bubbleSort(new ArrayList<>(reversed), reversed.size());
            end = System.nanoTime();
            reversedTime = (end - start) / 1_000_000_000.0;

            System.out.printf("Already sorted: %.6f seconds%n", sortedTime);
            System.out.printf("Shuffled: %.6f seconds%n", shuffledTime);
            System.out.printf("Reversed: %.6f seconds%n", reversedTime);
            System.out.printf("Already sorted: %d comparisons%n", sortedComparison);
            System.out.printf("Shuffled: %d comparisons%n", shuffledComparison);
            System.out.printf("Reversed: %d comparisons%n", reversedComparison);

            analysisWriter.write(algorithm + "," + numLines + "," + sortedComparison + "," + shuffledComparison + "," + reversedComparison + "\n");
            analysisWriter.write(algorithm + "," + numLines + "," + sortedTime + "," + shuffledTime + "," + reversedTime + "\n");
            for (F1 val : new ArrayList<>(sorted)) {
                sortedWriter.write(val + "\n");
            }
            sortedWriter.close();
        }

        else if (algorithm.equals("transpositionsort")) {
            int sortedComparison = transpositionSort(new ArrayList<>(sorted), sorted.size());
            int shuffledComparison = transpositionSort(new ArrayList<>(shuffled), shuffled.size());
            int reversedComparison = transpositionSort(new ArrayList<>(reversed), reversed.size());

            System.out.printf("Already sorted: %d comparisons%n", sortedComparison);
            System.out.printf("Shuffled: %d comparisons%n", shuffledComparison);
            System.out.printf("Reversed: %d comparisons%n", reversedComparison);

            analysisWriter.write(algorithm + "," + numLines + "," + sortedComparison + "," + shuffledComparison + "," + reversedComparison + "\n");
            for (F1 val : new ArrayList<>(sorted)) {
                sortedWriter.write(val + "\n");
            }
            sortedWriter.close();
        }

        else if (algorithm.equals("mergesort") || algorithm.equals("quicksort") || algorithm.equals("heapsort")) {
            long start;
            long end;
            double sortedTime;
            double shuffledTime;
            double reversedTime;

            // Already sorted
            ArrayList<F1> copy = new ArrayList<>(sorted);
            start = System.nanoTime();
            if (algorithm.equals("mergesort")){
                mergeSort(copy, 0, copy.size() - 1);
            }
            else if (algorithm.equals("quicksort")){
                quickSort(copy, 0, copy.size() - 1);
            }
            else {
                heapSort(copy, 0, copy.size() - 1);
            }
            end = System.nanoTime();
            sortedTime = (end - start) / 1_000_000_000.0;

            // Shuffled
            copy = new ArrayList<>(shuffled);
            start = System.nanoTime();
            if (algorithm.equals("mergesort")){
                mergeSort(copy, 0, copy.size() - 1);
            }
            else if (algorithm.equals("quicksort")){
                quickSort(copy, 0, copy.size() - 1);
            }
            else {
                heapSort(copy, 0, copy.size() - 1);
            }
            end = System.nanoTime();
            shuffledTime = (end - start) / 1_000_000_000.0;

            // Reversed
            copy = new ArrayList<>(reversed);
            start = System.nanoTime();
            if (algorithm.equals("mergesort")){
                mergeSort(copy, 0, copy.size() - 1);
            }
            else if (algorithm.equals("quicksort")){
                quickSort(copy, 0, copy.size() - 1);
            }
            else {
                heapSort(copy, 0, copy.size() - 1);
            }
            end = System.nanoTime();
            reversedTime = (end - start) / 1_000_000_000.0;

            System.out.printf("Already sorted: %.6f seconds%n", sortedTime);
            System.out.printf("Shuffled: %.6f seconds%n", shuffledTime);
            System.out.printf("Reversed: %.6f seconds%n", reversedTime);

            analysisWriter.write(algorithm + "," + numLines + "," + sortedTime + "," + shuffledTime + "," + reversedTime + "\n");
            for (F1 val : copy) {
                sortedWriter.write(val + "\n");
            }
            sortedWriter.close();
        }
        analysisWriter.close();
    }
}