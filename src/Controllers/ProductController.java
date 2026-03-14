package Controllers;

import Models.Product;
import Utils.DAO.ProductDAO;
import Views.VistaConsola;

import java.util.List;
import java.util.Optional;

public class ProductController {

    private final ProductDAO productDAO;
    private final VistaConsola vista;

    public ProductController(ProductDAO productDAO, VistaConsola vista) {
        this.productDAO = productDAO;
        this.vista = vista;
    }

    /**
     * Crea un nuevo producto y lo persiste en la base de datos.
     */
    public void crearProducto(String name, double price, String category) {
        vista.mostrarMensaje("Creando producto...");
        try {
            Product product = new Product(name, price, category);
            productDAO.create(product);
            vista.mostrarProductoCreado(product);
        } catch (Exception e) {
            vista.mostrarError("No se pudo crear el producto: " + e.getMessage());
        }
    }

    /**
     * Busca un producto por su ID y lo muestra.
     */
    public void buscarProductoPorId(int id) {
        vista.mostrarMensaje("Buscando producto con ID=" + id + "...");
        try {
            Optional<Product> resultado = productDAO.findById(id);
            if (resultado.isPresent()) {
                vista.mostrarProducto(resultado.get());
            } else {
                vista.mostrarMensaje("  No se encontró ningún producto con ID=" + id);
            }
        } catch (Exception e) {
            vista.mostrarError("Error al buscar producto: " + e.getMessage());
        }
    }

    /**
     * Lista todos los productos disponibles.
     */
    public void listarProductos() {
        vista.mostrarMensaje("Listando todos los productos...");
        try {
            List<Product> products = productDAO.findAll();
            vista.mostrarListaProductos(products);
        } catch (Exception e) {
            vista.mostrarError("Error al listar productos: " + e.getMessage());
        }
    }

    /**
     * Actualiza los datos de un producto existente.
     */
    public void actualizarProducto(int id, String name, double price, String category) {
        vista.mostrarMensaje("Actualizando producto con ID=" + id + "...");
        try {
            Optional<Product> resultado = productDAO.findById(id);
            if (resultado.isPresent()) {
                Product product = resultado.get();
                product.setName(name);
                product.setPrice(price);
                product.setCategory(category);
                productDAO.update(product);
                vista.mostrarProductoActualizado(product);
            } else {
                vista.mostrarMensaje("No se encontró ningún producto con ID=" + id);
            }
        } catch (Exception e) {
            vista.mostrarError("Error al actualizar producto: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto por su ID.
     */
    public void eliminarProducto(int id) {
        vista.mostrarMensaje("Eliminando producto con ID=" + id + "...");
        try {
            productDAO.delete(id);
            vista.mostrarProductoEliminado(id);
        } catch (Exception e) {
            vista.mostrarError("Error al eliminar producto: " + e.getMessage());
        }
    }
}

