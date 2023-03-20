package view.custom;

import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {

    private int cornerRadius = 10;

    public RoundedPanel() {
        super();
    }

    public RoundedPanel(LayoutManager layout) {
        super(layout);
    }

    public RoundedPanel(LayoutManager layout, int cornerRadius) {
        super(layout);
        this.cornerRadius = cornerRadius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        g2d.setColor(getForeground());
        g2d.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }
}
