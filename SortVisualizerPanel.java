import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SortVisualizerPanel extends JPanel {

    private int[] array;
    private int[] colorMarkers; // 0: default, 1: comparing, 2: swapping

    public SortVisualizerPanel(int[] array) {
        this.array = array;
        this.colorMarkers = new int[array.length];
        setBackground(Color.DARK_GRAY);
    }

    public void setArray(int[] array) {
        this.array = array;
        this.colorMarkers = new int[array.length];
        repaint();
    }
    
    public void resetColors() {
        Arrays.fill(colorMarkers, 0);
    }

    public void markAsComparing(int index) {
        colorMarkers[index] = 1;
    }

    public void markAsSwapping(int index) {
        colorMarkers[index] = 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (array == null) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int barWidth = panelWidth / array.length;
        int maxVal = Arrays.stream(array).max().orElse(1);

        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) (((double) array[i] / maxVal) * (panelHeight * 0.9));
            int x = i * barWidth;
            int y = panelHeight - barHeight;

            if (colorMarkers[i] == 1) {
                g.setColor(Color.YELLOW); // Comparing
            } else if (colorMarkers[i] == 2) {
                g.setColor(Color.RED); // Swapping
            } else {
                g.setColor(Color.CYAN); // Default
            }

            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);
        }
    }
}