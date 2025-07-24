/**
 * GameOfLife
 *
 *
 * @Vinzenz Lanzensberger
 * @9/6/25
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLife
{  
    int cellSize = 10; // Size of each cell in pixels
    int rows = 60, cols = 60; // Number of rows and columns in the grid
    boolean[][] board = new boolean[rows][cols]; // Game state: true = live cell
    BoardPanel panel;
    private Timer gameTimer; // Timer to control animation speed
    private boolean isRunning = false; // Tracks whether simulation is running

    public GameOfLife() {
        // Create main application window
        JFrame frame = new JFrame("Game of Life");
        panel = new BoardPanel(); // Custom panel that draws the board
        panel.setLayout(null); // Use absolute positioning
        panel.setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));

        // Add a circular info icon to the panel
        InfoCircle info = new InfoCircle("Click to toggle cells. Start button runs the game.");
        info.setBounds(10, 10, 40, 40); // Set position and size manually
        panel.add(info); 

        // Start/Stop button setup
        JButton startButton = new JButton("Start");
        startButton.setBounds(60, 10, 80, 30); // Position and size of the button

        // Create the timer that updates the game every 250ms
        gameTimer = new Timer(250, e2 -> updateBoard());

        // Toggle game on or off when the button is clicked
        startButton.addActionListener(e -> {
            if (isRunning) {
                gameTimer.stop(); // Pause the game
                startButton.setText("Start");
                System.out.println("Game paused.");
            } else {
                gameTimer.start(); // Start/resume the game
                startButton.setText("Stop");
                System.out.println("Game running...");
            }
            isRunning = !isRunning; // Flip the state
        });
        panel.add(startButton);

        // Reset button clears the board
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(150, 10, 80, 30);
        resetButton.addActionListener(e -> {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j] = false; // Set all cells to dead
                }
            }
            panel.repaint(); // Refresh the display
            System.out.println("Board reset.");
        });
        panel.add(resetButton);

        // Finalize frame setup
        frame.add(panel);
        frame.pack(); // Resize frame to fit contents
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); // Show the window
    }

    // Inner class responsible for drawing the grid and handling mouse clicks
    class BoardPanel extends JPanel {
        BoardPanel() {
            // Toggle cell state when the user clicks
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int col = e.getX() / cellSize;
                    int row = e.getY() / cellSize;
                    if (row >= 0 && row < rows && col >= 0 && col < cols) {
                        board[row][col] = !board[row][col]; // Toggle the cell
                        repaint(); // Refresh the panel
                    }
                }
            });
        }

        // Draw live cells as black rectangles
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (board[i][j]) {
                        g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
            }
        }
    }

    // Compute the next generation of the board
    private void updateBoard() {
        boolean[][] nextBoard = new boolean[rows][cols]; // New board to store updates

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);

                if (board[row][col]) {
                    // Rule 1 & 3: Stay alive only with 2 or 3 neighbors
                    nextBoard[row][col] = liveNeighbors == 2 || liveNeighbors == 3;
                } else {
                    // Rule 4: A dead cell with exactly 3 live neighbors becomes alive
                    nextBoard[row][col] = liveNeighbors == 3;
                }
            }
        }

        board = nextBoard; // Replace old board with updated one
        panel.repaint(); // Redraw the panel
    }

    // Count how many live neighbors surround a given cell
    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Skip the cell itself
                int r = row + dr;
                int c = col + dc;
                if (r >= 0 && r < rows && c >= 0 && c < cols && board[r][c]) {
                    count++; // Add to count if neighbor is alive
                }
            }
        }
        return count;
    }
}