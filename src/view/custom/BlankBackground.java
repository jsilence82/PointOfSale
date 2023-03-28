package view.custom;

import javax.swing.*;

public class BlankBackground extends JFrame {

    public BlankBackground() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1680, 1080);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setLocationRelativeTo(null);
        setBackground(SystemColors.BACKGROUND.getColorCode());
        setVisible(true);
    }
}
