
/**
 * Write a description of class InfoCircle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InfoCircle extends JComponent {
    private String infoMessage;
    private boolean showInfo = false;

    public InfoCircle(String message) {
        this.infoMessage = message;
        setPreferredSize(new Dimension(40, 40));
        setOpaque(false);

        // Toggle info display on click
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showInfo = !showInfo;
                if (showInfo) {
                    JOptionPane.showMessageDialog(InfoCircle.this, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(0, 0, size, size);

        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(0, 0, size - 1, size - 1);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        FontMetrics fm = g2.getFontMetrics();
        String text = "?";
        int textX = (size - fm.stringWidth(text)) / 2;
        int textY = (size + fm.getAscent() - fm.getDescent()) / 2 - 1;
        g2.drawString(text, textX, textY);
    }
}
