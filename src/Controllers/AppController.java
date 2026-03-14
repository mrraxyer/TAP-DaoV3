package Controllers;

import Utils.DAO.MySQLProductDAOImpl;
import Utils.DAO.PostgresProductDAOImpl;
import Utils.DAO.ProductDAO;
import Utils.connection.DatabaseConnection;
import Utils.connection.LocalPostgresConnection;
import Utils.connection.SupabaseConnection;
import Views.VistaConsola;

import java.sql.SQLException;

public class AppController {

    private final VistaConsola vista;

    public AppController(VistaConsola vista) {
        this.vista = vista;
    }

    public void iniciar() {
        ejecutarMenuPrincipal();
    }

    private void ejecutarMenuPrincipal() {
        boolean continuar = true;

        while (continuar) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.leerEntero("Selecciona una opcion: ");

            switch (opcion) {
                case 1:
                    iniciarSesionCrud("MySQL", false);
                    break;
                case 2:
                    iniciarSesionCrud("Supabase", true);
                    break;
                case 3:
                    iniciarSesionCrud("Postgres Local", false);
                    break;
                case 0:
                    continuar = false;
                    vista.mostrarMensaje("Saliendo del sistema...");
                    break;
                default:
                    vista.mostrarError("Opcion invalida.");
            }
        }
    }

    private void iniciarSesionCrud(String dbName, boolean incluirProductoAlumno) {
        ProductController controller = crearControllerPorBase(dbName);
        if (controller == null) {
            return;
        }

        boolean enSesion = true;
        while (enSesion) {
            vista.mostrarMenuCrud(dbName, incluirProductoAlumno);
            int opcion = vista.leerEntero("Selecciona una opcion CRUD: ");

            switch (opcion) {
                case 1:
                    crearProductoDesdeMenu(controller);
                    break;
                case 2:
                    controller.listarProductos();
                    break;
                case 3:
                    int idBusqueda = vista.leerEntero("ID a buscar: ");
                    controller.buscarProductoPorId(idBusqueda);
                    break;
                case 4:
                    actualizarProductoDesdeMenu(controller);
                    break;
                case 5:
                    int idEliminar = vista.leerEntero("ID a eliminar: ");
                    controller.eliminarProducto(idEliminar);
                    break;
                case 6:
                    if (incluirProductoAlumno) {
                        controller.crearProducto("Rafael Abonce Garcia", 1050.50, "c23030448");
                    } else {
                        vista.mostrarError("Opcion invalida.");
                    }
                    break;
                case 0:
                    enSesion = false;
                    break;
                default:
                    vista.mostrarError("Opcion invalida.");
            }
        }
    }

    private ProductController crearControllerPorBase(String dbName) {
        try {
            ProductDAO dao;
            switch (dbName) {
                case "MySQL":
                    DatabaseConnection.getConnection().close();
                    dao = new MySQLProductDAOImpl();
                    break;
                case "Supabase":
                    SupabaseConnection.getConnection().close();
                    dao = new PostgresProductDAOImpl(() -> {
                        try {
                            return SupabaseConnection.getConnection();
                        } catch (SQLException e) {
                            throw new RuntimeException("Error en conexion Supabase", e);
                        }
                    }, "Supabase");
                    break;
                case "Postgres Local":
                    LocalPostgresConnection.getConnection().close();
                    dao = new PostgresProductDAOImpl(() -> {
                        try {
                            return LocalPostgresConnection.getConnection();
                        } catch (SQLException e) {
                            throw new RuntimeException("Error en conexion Postgres Local", e);
                        }
                    }, "Postgres Local");
                    break;
                default:
                    vista.mostrarError("Base de datos no soportada.");
                    return null;
            }

            vista.mostrarMensaje("Conexion establecida con " + dbName + ".");
            return new ProductController(dao, vista);
        } catch (SQLException e) {
            vista.mostrarError(dbName + " no disponible: " + e.getMessage());
            return null;
        }
    }

    private void crearProductoDesdeMenu(ProductController controller) {
        String nombre = vista.leerTexto("Nombre: ");
        double precio = vista.leerDecimal("Precio: ");
        String categoria = vista.leerTexto("Categoria: ");
        controller.crearProducto(nombre, precio, categoria);
    }

    private void actualizarProductoDesdeMenu(ProductController controller) {
        int id = vista.leerEntero("ID del producto a actualizar: ");
        String nombre = vista.leerTexto("Nuevo nombre: ");
        double precio = vista.leerDecimal("Nuevo precio: ");
        String categoria = vista.leerTexto("Nueva categoria: ");
        controller.actualizarProducto(id, nombre, precio, categoria);
    }
}

