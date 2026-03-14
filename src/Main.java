import Controllers.AppController;
import Views.VistaConsola;

public class Main {

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();

        AppController appController = new AppController(vista);
        appController.iniciar();
    }
}
