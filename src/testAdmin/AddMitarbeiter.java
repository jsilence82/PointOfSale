package testAdmin;

import model.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddMitarbeiter extends JPanel {

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();

    private JComboBox<Mitarbeiter> mitarbeiterComboBox;

    public AddMitarbeiter() {
    }

    public JPanel generateMitarbeiterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Mitarbeiter"));

        mitarbeiterComboBox = new JComboBox<>();
        panel.add(mitarbeiterComboBox);

        JButton showButton = new JButton("Show");
        showButton.addActionListener(e -> {
            Mitarbeiter mitarbeiter = (Mitarbeiter) mitarbeiterComboBox.getSelectedItem();
            if (mitarbeiter != null) {
                System.out.println("Selected Mitarbeiter: " + mitarbeiter.getFullName());
            }
        });
        panel.add(showButton);

        loadMitarbeiter();

        return panel;
    }

    public JPanel generateAddMitarbeiterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Mitarbeiter"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Vorname:"), c);

        c.gridx = 1;
        c.gridy = 0;
        JTextField vornameField = new JTextField(20);
        panel.add(vornameField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Nachname:"), c);

        c.gridx = 1;
        c.gridy = 1;
        JTextField nachnameField = new JTextField(20);
        panel.add(nachnameField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Mitarbeiter ID:"), c);

        c.gridx = 1;
        c.gridy = 2;
        JTextField idField = new JTextField(10);
        panel.add(idField, c);

        c.gridx = 1;
        c.gridy = 3;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String vorname = vornameField.getText();
            String nachname = nachnameField.getText();
            int id = Integer.parseInt(idField.getText());

            addMitarbeiter(id, vorname, nachname);

            loadMitarbeiter();
            vornameField.setText("");
            nachnameField.setText("");
            idField.setText("");
        });
        panel.add(addButton, c);

        return panel;
    }

    private void loadMitarbeiter() {
        mitarbeiterComboBox.removeAllItems();
        try (
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM mitarbeiter")) {

            while (rs.next()) {
                int id = rs.getInt("mitarbeiterid");
                String vorname = rs.getString("vorname");
                String nachname = rs.getString("nachname");

                Mitarbeiter mitarbeiter = new Mitarbeiter(id, vorname, nachname);
                mitarbeiterComboBox.addItem(mitarbeiter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMitarbeiter(int id, String vorname, String nachname) {

        try {
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO mitarbeiter (MitarbeiterID, vorname, nachname) VALUES (?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, vorname);
            stmt.setString(3, nachname);
            stmt.executeUpdate();
            loadMitarbeiter();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class Mitarbeiter {
        private int mitarbeiterId;
        private String vorname;
        private String nachname;

        public Mitarbeiter(int mitarbeiterId, String vorname, String nachname) {
            this.mitarbeiterId = mitarbeiterId;
            this.vorname = vorname;
            this.nachname = nachname;
        }

        public int getMitarbeiterId() {
            return mitarbeiterId;
        }

        public String getVorname() {
            return vorname;
        }

        public String getNachname() {
            return nachname;
        }

        public String getFullName() {
            return vorname + " " + nachname;
        }
    }
}
