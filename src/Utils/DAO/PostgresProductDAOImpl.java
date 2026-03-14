package Utils.dao;

import Models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Implementación reutilizable de ProductDAO para cualquier base de datos PostgreSQL.
 * Utiliza un Supplier de Connection para obtener la conexión de diferentes
 * fuentes (Local, Supabase, etc).
 */
public class PostgresProductDAOImpl implements ProductDAO {
    private final Supplier<Connection> connectionSupplier;
    private final String dbLabel;

    public PostgresProductDAOImpl(Supplier<Connection> connectionSupplier, String dbLabel) {
        this.connectionSupplier = connectionSupplier;
        this.dbLabel = dbLabel;
    }

    @Override
    public void create(Product product) {
        String query = "INSERT INTO products (name, price, category) VALUES (?, ?, ?)";
        try (Connection conn = connectionSupplier.get();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getCategory());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error [" + dbLabel + "] al crear producto: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = connectionSupplier.get();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error [" + dbLabel + "] al buscar por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY id ASC";
        try (Connection conn = connectionSupplier.get();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error [" + dbLabel + "] al listar todos: " + e.getMessage());
        }
        return products;
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE products SET name = ?, price = ?, category = ? WHERE id = ?";
        try (Connection conn = connectionSupplier.get();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getCategory());
            stmt.setInt(4, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error [" + dbLabel + "] al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = connectionSupplier.get();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error [" + dbLabel + "] al eliminar: " + e.getMessage());
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setCategory(rs.getString("category"));
        return product;
    }
}

