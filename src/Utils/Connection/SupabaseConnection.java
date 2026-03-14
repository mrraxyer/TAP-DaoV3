package Utils.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SupabaseConnection {
    private static final String URL = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require";
    private static final String USER = "postgres.fcwejmmwppqsdacdjujz";
    private static final String PASSWORD = "NZQ8WDWbMuoQobXB";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver no encontrado.", e);
        }
    }
}

