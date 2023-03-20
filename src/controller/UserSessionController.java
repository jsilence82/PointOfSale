package controller;

import model.User;
import model.database.DatabaseConnection;
import view.SelectScreen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSessionController {

    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private final Connection connection = dbConnection.getConnection();
    private static User mitarbeiter;

    public UserSessionController(int username, SelectScreen startProgram) {
        mitarbeiter = User.getInstance();
        mitarbeiter.setMitarbeiterID(username);
        getMitarbeiterInfo(username);
        startProgram.startScreen();
    }

    private void getMitarbeiterInfo(int username){
        try {
            String query = "SELECT vorname, nachname FROM mitarbeiter WHERE MitarbeiterID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mitarbeiter.setVorname(rs.getString("vorname"));
                mitarbeiter.setNachname(rs.getString("nachname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMitarbeiterID(){
        return mitarbeiter.getMitarbeiterID();
    }

    public static String getMitarbeiterVorname(){
        return mitarbeiter.getVorname();
    }

    public static String getMitarbeiterNachname(){
        return mitarbeiter.getNachname();
    }
}
