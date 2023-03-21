package view.custom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelFadeTransition extends JPanel implements ActionListener {
    private static final int DELAY = 50;
    private static final int TOTAL_TIME = 4000;
    private float alpha = 0.0f;
    private final Timer timer;

    public PanelFadeTransition() {
        setBackground(SystemColors.BACKGROUND.getColorCode());
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(getBackground());
        g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        alpha += DELAY / (float) TOTAL_TIME;
        if (alpha > 1.0f) {
            alpha = 1.0f;
            setVisible(true);
            timer.stop();
        }
        else if (alpha < 0.01f) {
            setVisible(false);
        }
        repaint();
    }

    public void fade(boolean fadeIn) {
        alpha = fadeIn ? 0.0f : 1.0f;
        setVisible(true);
        if (!fadeIn) {
            setVisible(false);
        }
        timer.start();
    }
}

