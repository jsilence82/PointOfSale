package view.custom;

import view.SystemColors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelFadeTransition extends JPanel implements ActionListener {
    private static final int DELAY = 50; // delay between each timer tick
    private static final int TOTAL_TIME = 4000; // total time for the fade effect
    private float alpha = 0.0f; // starting alpha value
    private Timer timer; // timer for the fade effect

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
        else if (alpha < 0.01f) { // if alpha is close to 0, set visibility to false to prevent flickering
            setVisible(false);
        }
        repaint();
    }

    public void fade(boolean fadeIn) {
        alpha = fadeIn ? 0.0f : 1.0f; // set the alpha value to the start or end of the fade
        setVisible(true); // show the panel
        if (!fadeIn) { // if fading out, set the visibility to false to prevent mouse clicks
            setVisible(false);
        }
        timer.start(); // start the timer
    }

}

