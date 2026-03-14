import Controllers.ProductController;
import Views.VistaConsola;
import Utils.connection.DatabaseConnection;
import Utils.connection.LocalPostgresConnection;
import Utils.connection.SupabaseConnection;
import Utils.dao.MySQLProductDAOImpl;
import Utils.dao.PostgresProductDAOImpl;
import Utils.dao.ProductDAO;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {

        VistaConsola vista = new VistaConsola();

        // MySQL
        vista.mostrarTitulo("MySQL");
        try {
            DatabaseConnection.getConnection().close();
            ProductDAO mysqlDAO = new MySQLProductDAOImpl();
            ProductController mysqlController = new ProductController(mysqlDAO, vista);
            demostrarCRUD(mysqlController, vista, "MySQL");
        } catch (SQLException e) {
            vista.mostrarError("MySQL no disponible: " + e.getMessage());
            vista.mostrarMensaje("  >> Saltando MySQL.");
        }

        // Supabase
        vista.mostrarTitulo("Supabase - Cloud");
        ProductDAO supabaseDAO = new PostgresProductDAOImpl(() -> {
            try {
                return SupabaseConnection.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException("Error en conexión Supabase", e);
            }
        }, "Supabase");
        ProductController supabaseController = new ProductController(supabaseDAO, vista);

        // CRUD de demostración general
        demostrarCRUD(supabaseController, vista, "Supabase");

        // Nombre y número de control
        vista.mostrarTitulo("Supabase - Alumno");
        supabaseController.crearProducto("Rafael Abonce Garcia", 2026.00, "c23030448");

        // Listar para confirmar que se insertó
        vista.mostrarTitulo("Supabase - Lista Final de Productos");
        supabaseController.listarProductos();

        // Postgres Local
        vista.mostrarTitulo("Postgres Local");
        try {
            LocalPostgresConnection.getConnection().close(); // prueba de conexión
            ProductDAO localDAO = new PostgresProductDAOImpl(() -> {
                try {
                    return LocalPostgresConnection.getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException("Error en conexión Postgres Local", e);
                }
            }, "Postgres Local");
            ProductController localController = new ProductController(localDAO, vista);
            demostrarCRUD(localController, vista, "Postgres Local");
        } catch (SQLException e) {
            vista.mostrarError("Postgres Local no disponible: " + e.getMessage());
            vista.mostrarMensaje("  >> Saltando Postgres Local (es opcional).");
        }
    }

    private static void demostrarCRUD(ProductController controller, VistaConsola vista, String dbName) {

        // CREATE
        vista.mostrarTitulo(dbName + " - CREATE");
        controller.crearProducto("Pixel 8 Pro Max", 20050.00, "telefonos");

        // READ ALL
        vista.mostrarTitulo(dbName + " - READ ALL");
        controller.listarProductos();

        // READ BY ID
        vista.mostrarTitulo(dbName + " - READ BY ID (1)");
        controller.buscarProductoPorId(1);

        // UPDATE
        // vista.mostrarTitulo(dbName + " - UPDATE");
        // controller.actualizarProducto(1, "Pixel 8 Pro Max XL", 18500.00, "telefonos");

        // DELETE
        // vista.mostrarTitulo(dbName + " - DELETE");
        // controller.eliminarProducto(1);
    }
}
