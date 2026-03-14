package Utils.DAO;

import Models.Product;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz ProductDAO que define las operaciones CRUD.
 * Sigue el Principio de Segregación de Interfaces (ISP).
 */
public interface ProductDAO {
    void create(Product product);

    Optional<Product> findById(int id);

    List<Product> findAll();

    void update(Product product);

    void delete(int id);
}

