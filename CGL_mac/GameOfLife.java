/**
 * GameOfLife
 *
 * @Vinzenz Lanzensberger
 * @9/6/25
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GameOfLife
{  
    int cellSize = 10;
    int rows = 60, cols = 60;
    boolean[][] board = new boolean[rows][cols];

    public GameOfLife() {
        JFrame frame = new JFrame("Game of Life");
        BoardPanel panel = new BoardPanel();
        panel.setLayout(null);//So that I can manually change layout values
        panel.setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
        
        InfoCircle info = new InfoCircle("Click to toggle cells.Hover over circle for help. Start button runs the game.");
        info.setBounds(10, 10, 40, 40); // Set position and size manually
        panel.add(info); 

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class BoardPanel extends JPanel {
        BoardPanel() {
            addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        int col = e.getX() / cellSize;
                        int row = e.getY() / cellSize;
                        board[row][col] = !board[row][col];
                        repaint();
                    }
                });
        }

        protected void paintComponent(Graphics g) { //got the "protected" access modifier from stack overflow, restricts to different package subclass
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

}