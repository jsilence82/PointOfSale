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
    private static User user;

    public UserSessionController(int userID, SelectScreen startProgram) {
        user = User.getInstance();
        user.setUserID(userID);
        getUserInfo(userID);
        startProgram.startScreen();
    }

    private void getUserInfo(int userID) {
        try {
            String query = "SELECT vorname, nachname FROM mitarbeiter WHERE MitarbeiterID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user.setFirstName(resultSet.getString("vorname"));
                user.setLastName(resultSet.getString("nachname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getUsersDailyOrders() {
        String sql = "SELECT BestellungID, betrag FROM bestellung WHERE MitarbeiterID=? AND Datum= CURRENT_DATE()";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user.getUserID());
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getOrderInfo(String orderID) {
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
            statement.setString(1, orderID);
            statement.setString(2, orderID);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getUserId() {
        return user.getUserID();
    }

    public static String getUserFirstName() {
        return user.getFirstName();
    }

    public static String getUserLastName() {
        return user.getLastName();
    }
}
