import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class AlgorithmVisualizer {

    private JFrame window;
    private SortVisualizerPanel sortPanel;
    private int[] array;
    private static final int ARRAY_SIZE = 100;
    private static final int MAX_VALUE = 500;

    public AlgorithmVisualizer() {
        window = new JFrame("Algorithm Visualizer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLayout(new BorderLayout());

        array = generateRandomArray();
        sortPanel = new SortVisualizerPanel(array);
        window.add(sortPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("Reset Array");
        resetButton.addActionListener(e -> resetArray());

        JButton bubbleSortButton = new JButton("Bubble Sort");
        bubbleSortButton.addActionListener(e -> {
            // Run sorting in a separate thread to not freeze the UI
            new Thread(() -> SortAlgorithm.bubbleSort(array, sortPanel)).start();
        });
        
        JButton quickSortButton = new JButton("Quick Sort");
        quickSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.quickSort(array, 0, array.length - 1, sortPanel)).start();
        });

        JButton mergeSortButton = new JButton("Merge Sort");
        mergeSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.mergeSort(array, sortPanel)).start();
});

        JButton radixSortButton = new JButton("Radix Sort");
        radixSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.radixSort(array, sortPanel)).start();
});

        JButton bogoSortButton = new JButton("Bogo Sort");
        bogoSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.bogoSort(array, sortPanel)).start();
});
        JButton cocktailSortButton = new JButton("Cocktail Shaker Sort");
        cocktailSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.cocktailShakerSort(array, sortPanel)).start();
});

        JButton heapSortButton = new JButton("Heap Sort");
        heapSortButton.addActionListener(e -> {
            new Thread(() -> SortAlgorithm.heapSort(array, sortPanel)).start();
});
        buttonPanel.add(resetButton);
        buttonPanel.add(bubbleSortButton);
        buttonPanel.add(quickSortButton);
        buttonPanel.add(mergeSortButton);
        buttonPanel.add(radixSortButton);
        buttonPanel.add(bogoSortButton);
        buttonPanel.add(cocktailSortButton);
        buttonPanel.add(heapSortButton);
        window.add(buttonPanel, BorderLayout.SOUTH);

        window.setVisible(true);
    }

    private int[] generateRandomArray() {
        int[] newArray = new int[ARRAY_SIZE];
        Random rand = new Random();
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = rand.nextInt(MAX_VALUE) + 1;
        }
        return newArray;
    }

    private void resetArray() {
        this.array = generateRandomArray();
        sortPanel.setArray(this.array);
        sortPanel.resetColors();
        sortPanel.repaint();
    }

    public static void main(String[] args) {
        // Ensure UI updates are on the Event Dispatch Thread
        SwingUtilities.invokeLater(AlgorithmVisualizer::new);
    }
}