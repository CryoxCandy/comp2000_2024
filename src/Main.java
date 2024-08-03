import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Red vs. Blue");
        
        // Create the frame
        JFrame frame = new JFrame("20x20 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 720);
        
        // Create the custom panel
        JPanel panel = new JPanel() {
            Grid grid = new Grid(20, 35, 10);
            Point mousePosition = null;

            {
                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        mousePosition = e.getPoint();
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                grid.paint(g, mousePosition);
            }
        };
        
        frame.add(panel);
        frame.setVisible(true);
    }
}

class Grid {
    private int gridSize;
    private int cellSize;
    private int offset;
    private Cell[][] cells;

    public Grid(int gridSize, int cellSize, int offset) {
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.offset = offset;
        this.cells = new Cell[gridSize][gridSize];
        initializeCells();
    }

    private void initializeCells() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col] = new Cell(offset + col * cellSize, offset + row * cellSize, cellSize);
            }
        }
    }

    public void paint(Graphics g, Point mousePosition) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col].paint(g, mousePosition);
            }
        }
    }
}

class Cell {
    private int x;
    private int y;
    private int size;

    public Cell(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void paint(Graphics g, Point mousePosition) {
        if (mousePosition != null && contains(mousePosition)) {
            g.setColor(java.awt.Color.BLUE);
        } else {
            g.setColor(java.awt.Color.BLACK);
        }
        g.drawRect(x, y, size, size);
    }

    private boolean contains(Point p) {
        return p.x >= x && p.x < x + size && p.y >= y && p.y < y + size;
    }
}