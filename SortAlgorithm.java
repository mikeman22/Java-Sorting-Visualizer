public class SortAlgorithm {

    private static final int DELAY_MS = 10; // Speed of visualization

    public static void bubbleSort(int[] array, SortVisualizerPanel panel) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                panel.resetColors();
                panel.markAsComparing(j);
                panel.markAsComparing(j + 1);
                panel.repaint();
                sleep();

                if (array[j] > array[j + 1]) {
                    panel.markAsSwapping(j);
                    panel.markAsSwapping(j + 1);
                    panel.repaint();
                    sleep();

                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    
                    panel.repaint();
                    sleep();
                }
            }
        }
        panel.resetColors();
        panel.repaint();
    }
    
    public static void quickSort(int[] array, int low, int high, SortVisualizerPanel panel) {
        if (low < high) {
            int pi = partition(array, low, high, panel);
            quickSort(array, low, pi - 1, panel);
            quickSort(array, pi + 1, high, panel);
        }
        panel.resetColors();
        panel.repaint();
    }

    private static int partition(int[] array, int low, int high, SortVisualizerPanel panel) {
        int pivot = array[high];
        int i = (low - 1);
        
        panel.markAsComparing(high); // Mark pivot

        for (int j = low; j < high; j++) {
            panel.markAsComparing(j);
            panel.repaint();
            sleep();

            if (array[j] < pivot) {
                i++;
                panel.markAsSwapping(i);
                panel.markAsSwapping(j);
                panel.repaint();
                sleep();

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
            panel.resetColors();
            panel.markAsComparing(high); // Keep pivot marked
            panel.repaint();
        }

        panel.markAsSwapping(i + 1);
        panel.markAsSwapping(high);
        panel.repaint();
        sleep();

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
    public static void mergeSort(int[] array, SortVisualizerPanel panel) {
    if (array == null || array.length <= 1) {
        return; // Already sorted
    }
    int[] helper = new int[array.length];
    mergeSortRecursive(array, helper, 0, array.length - 1, panel);
    panel.resetColors(); // Clean up colors when done
    panel.repaint();
}

private static void mergeSortRecursive(int[] array, int[] helper, int low, int high, SortVisualizerPanel panel) {
    if (low < high) {
        int middle = low + (high - low) / 2;
        mergeSortRecursive(array, helper, low, middle, panel);       // 1. Sort left half
        mergeSortRecursive(array, helper, middle + 1, high, panel); // 2. Sort right half
        merge(array, helper, low, middle, high, panel);             // 3. Merge them
    }
}

private static void merge(int[] array, int[] helper, int low, int middle, int high, SortVisualizerPanel panel) {
    // Copy both halves into the helper array
    for (int i = low; i <= high; i++) {
        helper[i] = array[i];
    }

    int helperLeft = low;
    int helperRight = middle + 1;
    int current = low;

    // Iterate through helper array and copy smaller element back to original array
    while (helperLeft <= middle && helperRight <= high) {
        panel.resetColors();
        panel.markAsComparing(helperLeft);
        panel.markAsComparing(helperRight);
        panel.repaint();
        sleep();

        if (helper[helperLeft] <= helper[helperRight]) {
            array[current] = helper[helperLeft];
            helperLeft++;
        } else {
            array[current] = helper[helperRight];
            helperRight++;
        }
        panel.markAsSwapping(current);
        panel.repaint();
        sleep();
        current++;
    }

    // Copy the rest of the left side of the array into the target array
    int remaining = middle - helperLeft;
    for (int i = 0; i <= remaining; i++) {
        array[current + i] = helper[helperLeft + i];
        panel.markAsSwapping(current + i);
        panel.repaint();
        sleep();
    }
}
// A helper method to get the maximum value in the array
private static int getMaxValue(int[] array) {
    int max = array[0];
    for (int i = 1; i < array.length; i++) {
        if (array[i] > max) {
            max = array[i];
        }
    }
    return max;
}

// The main Radix Sort method
public static void radixSort(int[] array, SortVisualizerPanel panel) {
    // Find the maximum number to know the number of digits
    int max = getMaxValue(array);

    // Do counting sort for every digit. The 'exp' is 10^i
    // where i is the current digit number (1's, 10's, 100's place)
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countingSortForRadix(array, exp, panel);
    }
    panel.resetColors();
    panel.repaint();
}

// A helper method that uses Counting Sort to sort the array by a specific digit
private static void countingSortForRadix(int[] array, int exp, SortVisualizerPanel panel) {
    int n = array.length;
    int[] output = new int[n];
    int[] count = new int[10]; // For digits 0 through 9

    // Store count of occurrences of each digit
    for (int i = 0; i < n; i++) {
        count[(array[i] / exp) % 10]++;
    }

    // Change count[i] so that count[i] now contains the
    // actual position of this digit in the output array
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    // Build the output array
    for (int i = n - 1; i >= 0; i--) {
        int digitIndex = (array[i] / exp) % 10;
        output[count[digitIndex] - 1] = array[i];
        count[digitIndex]--;
    }

    // Copy the output array back to the main array. This is the part we visualize.
    for (int i = 0; i < n; i++) {
        array[i] = output[i];
        panel.markAsSwapping(i); // Re-use the swapping color to show the update
        panel.repaint();
        // A short delay to see the update happening
        try { Thread.sleep(20); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    
    // A longer pause between passes so you can see the result of sorting by each digit place
    panel.resetColors();
    panel.repaint();
    try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}
// The main Bogo Sort method
public static void bogoSort(int[] array, SortVisualizerPanel panel) {
    while (!isSorted(array)) {
        shuffle(array, panel);
        panel.repaint();
        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

// Helper to check if the array is sorted
private static boolean isSorted(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
        if (array[i] > array[i + 1]) {
            return false;
        }
    }
    return true;
}

// Helper to shuffle the array using the Fisher-Yates algorithm
private static void shuffle(int[] array, SortVisualizerPanel panel) {
    java.util.Random rand = new java.util.Random();
    for (int i = array.length - 1; i > 0; i--) {
        int index = rand.nextInt(i + 1);
        // Simple swap
        int temp = array[index];
        array[index] = array[i];
        array[i] = temp;
        panel.markAsSwapping(i);
        panel.markAsSwapping(index);
    }
}
public static void cocktailShakerSort(int[] array, SortVisualizerPanel panel) {
    boolean swapped = true;
    int start = 0;
    int end = array.length;

    while (swapped) {
        swapped = false;
        // Forward pass (like bubble sort)
        for (int i = start; i < end - 1; ++i) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                panel.markAsSwapping(i);
                panel.markAsSwapping(i + 1);
                panel.repaint();
                sleep();
                swapped = true;
            }
        }
        if (!swapped) break;
        swapped = false;
        end = end - 1;

        // Backward pass
        for (int i = end - 1; i >= start; i--) {
            if (array[i] > array[i + 1]) {
                int temp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = temp;
                panel.markAsSwapping(i);
                panel.markAsSwapping(i + 1);
                panel.repaint();
                sleep();
                swapped = true;
            }
        }
        start = start + 1;
    }
    panel.resetColors();
    panel.repaint();
}

public static void heapSort(int[] array, SortVisualizerPanel panel) {
    int n = array.length;

    // 1. Build a max-heap from the array
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(array, n, i, panel);
    }

    // 2. One by one, extract elements from the heap
    for (int i = n - 1; i > 0; i--) {
        // Move current root (the largest element) to the end
        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;
        panel.markAsSwapping(0);
        panel.markAsSwapping(i);
        panel.repaint();
        sleep();

        // Call heapify on the reduced heap
        heapify(array, i, 0, panel);
    }
    panel.resetColors();
    panel.repaint();
}

// Helper method to heapify a subtree rooted at index i
private static void heapify(int[] array, int n, int i, SortVisualizerPanel panel) {
    int largest = i;       // Initialize largest as root
    int leftChild = 2 * i + 1;
    int rightChild = 2 * i + 2;

    panel.markAsComparing(largest);

    // If left child is larger than root
    if (leftChild < n && array[leftChild] > array[largest]) {
        largest = leftChild;
    }

    // If right child is larger than the largest so far
    if (rightChild < n && array[rightChild] > array[largest]) {
        largest = rightChild;
    }
    
    panel.repaint();
    sleep();

    // If the largest element is not the root
    if (largest != i) {
        int swap = array[i];
        array[i] = array[largest];
        array[largest] = swap;
        panel.markAsSwapping(i);
        panel.markAsSwapping(largest);
        panel.repaint();
        sleep();

        // Recursively heapify the affected sub-tree
        heapify(array, n, largest, panel);
    }
    panel.resetColors();
}
    private static void sleep() {
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}