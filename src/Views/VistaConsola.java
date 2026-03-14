package Views;

import Models.Product;
import java.util.List;

public class VistaConsola {

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String error) {
        System.err.println("[ERROR] " + error);
    }

    public void mostrarTitulo(String titulo) {
        System.out.println("\n========== " + titulo + " ==========");
    }

    public void mostrarProducto(Product product) {
        if (product == null) {
            System.out.println("  Producto no encontrado.");
            return;
        }
        System.out.println("  ID       : " + product.getId());
        System.out.println("  Nombre   : " + product.getName());
        System.out.println("  Precio   : $" + product.getPrice());
        System.out.println("  Categoría: " + product.getCategory());
    }

    public void mostrarListaProductos(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("  No hay productos registrados.");
            return;
        }
        System.out.printf("  %-5s %-25s %-10s %-15s%n", "ID", "Nombre", "Precio", "Categoría");
        System.out.println("  " + "-".repeat(58));
        for (Product p : products) {
            System.out.printf("  %-5d %-25s %-10.2f %-15s%n",
                    p.getId(), p.getName(), p.getPrice(), p.getCategory());
        }
    }

    public void mostrarProductoCreado(Product product) {
        System.out.println("Producto creado con ID=" + product.getId() + ": " + product.getName());
    }

    public void mostrarProductoActualizado(Product product) {
        System.out.println(" Producto actualizado: " + product);
    }

    public void mostrarProductoEliminado(int id) {
        System.out.println("Producto con ID=" + id + " eliminado.");
    }
}