package gui;

import javax.swing.*;
import java.awt.*;

public class NeonButton extends JButton {

    private Color color1;
    private Color color2;

    public NeonButton(String text, Color color1, Color color2) {
        super(text);
        this.color1 = color1;
        this.color2 = color2;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        GradientPaint gradient = new GradientPaint(
                0, 0, color1,
                getWidth(), getHeight(), color2);

        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }
}
