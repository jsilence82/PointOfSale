package controller;

import model.database.DatabaseConnection;
import model.items.Essen;
import model.items.Getraenk;
import model.items.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LagerDBController {

    private final List<Item> essenList;
    private final List<Item> trinkList;
    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();


    public LagerDBController() {
        this.essenList = new ArrayList<>();
        this.trinkList = new ArrayList<>();
        getEssenFromDatabase();
        getGetraenkFromDatabase();
    }

    public void getEssenFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT EssenID, Essen, Preis, Anzahl FROM Essen where anzahl > 0 ORDER BY EssenID");
            while (resultSet.next()) {
                int idNumber = resultSet.getInt("EssenID");
                String description = resultSet.getString("Essen");
                double price = resultSet.getDouble("Preis");
                int numAvailable = resultSet.getInt("Anzahl");
                essenList.add(new Essen(idNumber, description, numAvailable, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getGetraenkFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TrinkenId, Trinken, Preis, Anzahl FROM Trinken where anzahl > 0 ORDER BY TrinkenId");
            while (resultSet.next()) {
                int idNumber = resultSet.getInt("TrinkenId");
                String description = resultSet.getString("Trinken");
                double price = resultSet.getDouble("Preis");
                int numAvailable = resultSet.getInt("Anzahl");
                trinkList.add(new Getraenk(idNumber, description, numAvailable, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshDatabase() {
        getEssenFromDatabase();
        getGetraenkFromDatabase();
    }

    public static void insertBestellung(String bestellungID, int mitarbeiterId, BigDecimal betrag) {
        String sql = "INSERT INTO bestellung (BestellungID, MitarbeiterID, betrag) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bestellungID);
            statement.setInt(2, mitarbeiterId);
            statement.setBigDecimal(3, betrag);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLagerBestand(Item item, int quantity) {
        String sql;
        if (item instanceof Essen) {
            sql = "UPDATE essen SET Anzahl = Anzahl - ? WHERE EssenID = ?";
        } else {
            sql = "UPDATE trinken SET Anzahl = Anzahl - ? WHERE TrinkenID = ?";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, item.getItemID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertItemSold(String bestellungID, Item item, int quantity) {
        String sql;
        if (item instanceof Essen) {
            sql = "INSERT INTO p_essen (BestellungID, EssenID, MENGE) VALUES (?, ?, ?)";
        } else {
            sql = "INSERT INTO p_trinken (BestellungID, TrinkenID, MENGE) VALUES (?, ?, ?)";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bestellungID);
            statement.setInt(2, item.getItemID());
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getEssenList() {
        return essenList;
    }

    public List<Item> getTrinkList() {
        return trinkList;
    }
}
