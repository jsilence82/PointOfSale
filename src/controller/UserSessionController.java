package controller;

import model.User;
import model.database.DatabaseConnection;
import view.SelectScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSessionController {

    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();
    private static User mitarbeiter;

    public UserSessionController(int username, SelectScreen startProgram) {
        mitarbeiter = User.getInstance();
        mitarbeiter.setMitarbeiterID(username);
        getMitarbeiterInfo(username);
        startProgram.startScreen();
    }

    private void getMitarbeiterInfo(int username) {
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

    public static ResultSet getTagesBestellungen() {
        String sql = "SELECT BestellungID, betrag FROM bestellung WHERE MitarbeiterID=? AND Datum= CURRENT_DATE()";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, mitarbeiter.getMitarbeiterID());
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getBestellungInfo(String bestellungId) {
        String sql = """
                SELECT e.Essen as item , pe.menge, e.Preis from Essen e left join p_essen pe
                using(EssenID)
                where pe.BestellungID = ?
                UNION
                SELECT t.Trinken as item, te.Menge, t.Preis from trinken t left join p_trinken te
                using(TrinkenID)
                WHERE te.BestellungID =?""";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bestellungId);
            statement.setString(2, bestellungId);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getMitarbeiterID() {
        return mitarbeiter.getMitarbeiterID();
    }

    public static String getMitarbeiterVorname() {
        return mitarbeiter.getVorname();
    }

    public static String getMitarbeiterNachname() {
        return mitarbeiter.getNachname();
    }
}
