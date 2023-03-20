package testAdmin;

import model.database.DatabaseConnection;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MitarbeiterPanel extends JPanel {

    private JComboBox<String> comboBox;
    private JTextArea vornameTextArea;
    private JTextArea nachnameTextArea;
    private JTextArea mitarbeiterIdTextArea;
    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();

    public MitarbeiterPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;

        // Create the components
        JLabel mitarbeiterLabel = new JLabel("Mitarbeiter:");
        comboBox = new JComboBox<>();
        vornameTextArea = new JTextArea(2, 20);
        nachnameTextArea = new JTextArea(2, 20);
        mitarbeiterIdTextArea = new JTextArea(1, 10);
        mitarbeiterIdTextArea.setEditable(false);

        // Add the components to the panel
        add(mitarbeiterLabel, c);
        c.gridx++;
        add(comboBox, c);
        c.gridx = 0;
        c.gridy++;
        add(new JLabel("Vorname:"), c);
        c.gridx++;
        add(vornameTextArea, c);
        c.gridx = 0;
        c.gridy++;
        add(new JLabel("Nachname:"), c);
        c.gridx++;
        add(nachnameTextArea, c);
        c.gridx = 0;
        c.gridy++;
        add(new JLabel("Mitarbeiter ID:"), c);
        c.gridx++;
        add(mitarbeiterIdTextArea, c);

        // Load the data from the database
        ArrayList<Mitarbeiter> mitarbeiterList = loadMitarbeiter();

        // Add the data to the combo box
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            comboBox.addItem(mitarbeiter.getFullName());
        }

        // Add a listener to the combo box
        comboBox.addActionListener(e -> {
            // Get the selected item from the combo box
            String selectedFullName = (String) comboBox.getSelectedItem();

            // Find the corresponding Mitarbeiter object
            Mitarbeiter selectedMitarbeiter = null;
            for (Mitarbeiter mitarbeiter : mitarbeiterList) {
                if (mitarbeiter.getFullName().equals(selectedFullName)) {
                    selectedMitarbeiter = mitarbeiter;
                    break;
                }
            }

            // Update the text areas with the selected Mitarbeiter's data
            vornameTextArea.setText(selectedMitarbeiter.getVorname());
            nachnameTextArea.setText(selectedMitarbeiter.getNachname());
            mitarbeiterIdTextArea.setText(String.valueOf(selectedMitarbeiter.getMitarbeiterId()));
        });

    }

    private ArrayList<Mitarbeiter> loadMitarbeiter() {
        ArrayList<Mitarbeiter> mitarbeiterList = new ArrayList<>();

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT mitarbeiterid, vorname, nachname FROM mitarbeiter");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int mitarbeiterId = resultSet.getInt("mitarbeiterid");
                String vorname = resultSet.getString("vorname");
                String nachname = resultSet.getString("nachname");

                Mitarbeiter mitarbeiter = new Mitarbeiter(mitarbeiterId, vorname, nachname);
                mitarbeiterList.add(mitarbeiter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mitarbeiterList;
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