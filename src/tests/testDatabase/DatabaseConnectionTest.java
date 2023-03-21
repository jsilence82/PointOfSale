package tests.testDatabase;

import model.database.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseConnectionTest {
    @Test
    void testConnection() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try {
            assertNotNull(connection);
            connection.close();
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }
}

