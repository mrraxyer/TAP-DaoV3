package Views;

import Models.Product;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VistaConsola {

    private final Scanner scanner = new Scanner(System.in);

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

    public void mostrarMenuPrincipal() {
        mostrarTitulo("MENU PRINCIPAL - BASE DE DATOS");
        System.out.println("1) MySQL");
        System.out.println("2) Supabase (Cloud PostgreSQL)");
        System.out.println("3) Postgres Local (opcional)");
        System.out.println("0) Salir");
    }

    public void mostrarMenuCrud(String dbName, boolean incluirProductoAlumno) {
        mostrarTitulo("MENU CRUD - " + dbName);
        System.out.println("1) Crear producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        if (incluirProductoAlumno) {
            System.out.println("6) Agregar producto alumno");
        }
        System.out.println("0) Cambiar de base de datos");
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = scanner.nextInt();
                scanner.nextLine(); // consume salto de linea pendiente
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                mostrarError("Entrada invalida. Debe ser un numero entero.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = scanner.nextDouble();
                scanner.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                mostrarError("Entrada invalida. Debe ser un numero decimal.");
            }
        }
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}