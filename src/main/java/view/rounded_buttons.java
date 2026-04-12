package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class rounded_buttons extends JButton {

    private int radius;
    private Color bgColor;

    public rounded_buttons(int radius, Color bgColor) {
        this.radius = radius;
        this.bgColor = bgColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(bgColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, radius, radius));

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor.darker()); // or any border color you want
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
        g2.dispose();
    }
}