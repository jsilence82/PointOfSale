package model.database;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private static final Connection connection = dbConnection.getConnection();

    public boolean authorizeUser(int username, String password) {
        String sql = "SELECT MitarbeiterID, passwort from authentication where MitarbeiterID = ?";
        boolean match = false;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPass = resultSet.getString("passwort");
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    match = argon2.verify(storedPass, password.toCharArray());
                    Arrays.fill(password.toCharArray(), '0');
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return match;
    }



    public static String hashPassword(String password){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(4, 1024, 1, password.getBytes());
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}