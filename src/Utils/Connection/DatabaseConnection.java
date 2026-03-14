package Utils.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilidad para gestionar la conexión a MySQL.
 * Sigue el principio SRP.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3308/tienda_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver no encontrado", e);
        }
    }
}
