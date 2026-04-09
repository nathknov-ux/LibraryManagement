package view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private int radius;
    private Color bgColor;

    public RoundedPanel(int radius, Color bgColor) {
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(bgColor);

    int w = getWidth();
    int h = getHeight();
    int r = radius;

    Path2D path = new Path2D.Float();
    path.moveTo(r, 0);                          
    path.lineTo(w, 0);                          
    path.lineTo(w, h);                          
    path.lineTo(r, h);                         
    path.quadTo(0, h, 0, h - r);               
    path.lineTo(0, r);                          
    path.quadTo(0, 0, r, 0);                   
    path.closePath();

    g2.fill(path);
    super.paintComponent(g2);
    g2.dispose();
}
}