package tests;

import tests.testAdmin.AddMitarbeiter;
import tests.testAdmin.MitarbeiterPanel;

import javax.swing.*;
import java.awt.*;

public class TestMitarbeiterDB extends JFrame {

    public TestMitarbeiterDB() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Mitarbeiter Panel Example");

    MitarbeiterPanel panel = new MitarbeiterPanel();
    AddMitarbeiter add = new AddMitarbeiter();
        JPanel addMitarbeiterPanel = add.generateAddMitarbeiterPanel();

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(panel, BorderLayout.NORTH);
        contentPane.add(addMitarbeiterPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    getContentPane().add(panel);
    pack();
    setLocationRelativeTo(null); // center the frame on the screen
    setVisible(true);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TestMitarbeiterDB();
        });
    }
}

