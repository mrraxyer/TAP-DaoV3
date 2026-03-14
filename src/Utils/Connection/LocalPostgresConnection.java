package Utils.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilidad para gestionar la conexión a un PostgreSQL local.
 */
public class LocalPostgresConnection {
    private static final String URL = "jdbc:postgresql://localhost:5438/tienda_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver no encontrado", e);
        }
    }
}

