package co.edu.poli.vista;

import co.edu.poli.aplication.port.in.ActualizarInmuebleUseCase;
import co.edu.poli.aplication.port.in.ConsultarInmuebleUseCase;
import co.edu.poli.aplication.port.in.CrearInmuebleUseCase;
import co.edu.poli.aplication.port.in.EliminarInmuebleUseCase;
import co.edu.poli.aplication.service.ActualizarInmuebleService;
import co.edu.poli.aplication.service.ConsultarInmuebleService;
import co.edu.poli.aplication.service.CrearInmuebleService;
import co.edu.poli.aplication.service.EliminarInmuebleService;
import co.edu.poli.infraestructure.persistence.JdbcConnectionFactory;
import co.edu.poli.infraestructure.persistence.JdbcInmuebleRepository;
import co.edu.poli.infraestructure.ui.InmuebleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public final class InmuebleApplication {
    private InmuebleApplication() {
    }

    public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }

    public static class FxApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            JdbcInmuebleRepository repository = new JdbcInmuebleRepository(
                    JdbcConnectionFactory.fromDotEnv());
            CrearInmuebleUseCase crear = new CrearInmuebleService(repository);
            ConsultarInmuebleUseCase consultar = new ConsultarInmuebleService(repository);
            ActualizarInmuebleUseCase actualizar = new ActualizarInmuebleService(repository);
            EliminarInmuebleUseCase eliminar = new EliminarInmuebleService(repository);

            URL vistaUrl = InmuebleApplication.class.getResource(
                    "/co/edu/poli/infraestructure/ui/InmuebleView.fxml");
            if (vistaUrl == null) {
                throw new IOException("No se encontró la vista InmuebleView.fxml");
            }
            FXMLLoader loader = new FXMLLoader(vistaUrl);
            loader.setControllerFactory(tipo -> {
                if (tipo == InmuebleController.class) {
                    return new InmuebleController(crear, consultar, actualizar, eliminar);
                }
                throw new IllegalArgumentException("Controlador no soportado: " + tipo.getName());
            });

            Parent root = loader.load();
            stage.setTitle("Gestión de inmuebles");
            stage.setScene(new Scene(root));
            stage.setMinWidth(900);
            stage.setMinHeight(650);
            stage.show();
        }
    }
}
