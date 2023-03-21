package controller;

import model.database.DatabaseConnection;
import model.items.Food;
import model.items.Drink;
import model.items.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsDBController {

    private final List<Item> foodList;
    private final List<Item> drinksList;
    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();

    public ItemsDBController() {
        this.foodList = new ArrayList<>();
        this.drinksList = new ArrayList<>();
        getFoodItemsFromDB();
        getDrinkItemsFromDB();
    }

    public void getFoodItemsFromDB() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT EssenID, Essen, Preis, Anzahl FROM Essen where anzahl > 0 ORDER BY EssenID");
            while (resultSet.next()) {
                int idNumber = resultSet.getInt("EssenID");
                String description = resultSet.getString("Essen");
                double price = resultSet.getDouble("Preis");
                int numAvailable = resultSet.getInt("Anzahl");
                foodList.add(new Food(idNumber, description, numAvailable, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDrinkItemsFromDB() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TrinkenId, Trinken, Preis, Anzahl FROM Trinken where anzahl > 0 ORDER BY TrinkenId");
            while (resultSet.next()) {
                int idNumber = resultSet.getInt("TrinkenId");
                String description = resultSet.getString("Trinken");
                double price = resultSet.getDouble("Preis");
                int numAvailable = resultSet.getInt("Anzahl");
                drinksList.add(new Drink(idNumber, description, numAvailable, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshDatabase() {
        getFoodItemsFromDB();
        getDrinkItemsFromDB();
    }

    public static void insertOrder(String orderId, int userID, BigDecimal amount) {
        String sql = "INSERT INTO bestellung (BestellungID, MitarbeiterID, betrag) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderId);
            statement.setInt(2, userID);
            statement.setBigDecimal(3, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDBItemQuantity(Item item, int quantity) {
        String sql;
        if (item instanceof Food) {
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

    public static void insertItemSold(String orderID, Item item, int quantity) {
        String sql;
        if (item instanceof Food) {
            sql = "INSERT INTO p_essen (BestellungID, EssenID, MENGE) VALUES (?, ?, ?)";
        } else {
            sql = "INSERT INTO p_trinken (BestellungID, TrinkenID, MENGE) VALUES (?, ?, ?)";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderID);
            statement.setInt(2, item.getItemID());
            statement.setInt(3, quantity);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Item> getFoodList() {
        return foodList;
    }

    public List<Item> getDrinksList() {
        return drinksList;
    }
}
