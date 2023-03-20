package model.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    private final Connection connection = dbConnection.getConnection();

    public boolean authenticateUser(int username, String password) {
        String sql = "SELECT COUNT(*) AS count FROM authentication WHERE MitarbeiterID = ? AND passwort = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count == 1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}