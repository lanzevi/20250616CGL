/**
 * InfoCircle
 *
 * @Vinzenz Lanzensberger
 * @9/6/25
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoCircle extends JComponent {
    private String infoMessage; // The message to show in the popup
    private boolean showInfo = false; // Tracks if info was clicked (helps for toggling)

    public InfoCircle(String message) {
        this.infoMessage = message;
        setPreferredSize(new Dimension(40, 40)); // Set the default size
        setOpaque(false); // Component does not have a background to paint

        // Mouse listener to respond to clicks
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showInfo = !showInfo; // Toggle the state (optional behavior)
                if (showInfo) {
                    // Show a pop-up info box when the circle is clicked
                    JOptionPane.showMessageDialog(InfoCircle.this, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smooth edges (got this off of stack overflow)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()); // Ensure the circle fits in the square area

        // Draw the filled circle background
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(0, 0, size, size);

        // Draw the circle outline
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, size - 1, size - 1);

        // Draw the "?" symbol centered inside the circle
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, size / 2)); // Font size scales with circle size
        FontMetrics fm = g2.getFontMetrics();
        String text = "?";
        int textX = (size - fm.stringWidth(text)) / 2; // Center horizontally
        int textY = (size + fm.getAscent() - fm.getDescent()) / 2 - 1; // Center vertically
        g2.drawString(text, textX, textY);
    }
}