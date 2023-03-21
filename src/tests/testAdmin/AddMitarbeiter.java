package tests.testAdmin;

import model.database.DatabaseConnection;
import model.database.UserDao;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddMitarbeiter extends JPanel {

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();


    public AddMitarbeiter() {
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

        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Password:"), c);

        c.gridx = 1;
        c.gridy = 3;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, c);

        c.gridx = 1;
        c.gridy = 4;
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String vorname = vornameField.getText();
            String nachname = nachnameField.getText();
            int id = Integer.parseInt(idField.getText());
            String password = new String(passwordField.getPassword());

            String passwordHash = UserDao.hashPassword(password);

            addMitarbeiter(id, vorname, nachname, passwordHash);

            vornameField.setText("");
            nachnameField.setText("");
            idField.setText("");
            passwordField.setText("");

        });
        panel.add(addButton, c);
        return panel;
    }


    private void addMitarbeiter(int id, String vorname, String nachname, String passwordHash) {

        try {
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO mitarbeiter (MitarbeiterID, vorname, nachname) VALUES (?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, vorname);
            stmt.setString(3, nachname);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO authentication (MitarbeiterID, passwort) VALUES (?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
