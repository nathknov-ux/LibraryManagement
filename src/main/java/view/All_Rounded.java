package view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class All_Rounded extends JPanel {

    private int radius;
    private Color bgColor;

    public All_Rounded(int radius, Color bgColor) {
        this.radius = radius;
        this.bgColor = bgColor;
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
}