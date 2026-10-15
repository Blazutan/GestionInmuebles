package co.edu.poli.infraestructure.ui;

import co.edu.poli.aplication.domain.model.Apartamento;
import co.edu.poli.aplication.domain.model.Casa;
import co.edu.poli.aplication.domain.model.Inmueble;
import co.edu.poli.aplication.domain.model.Propietario;
import co.edu.poli.aplication.domain.model.Sotano;
import co.edu.poli.aplication.port.in.ActualizarInmuebleUseCase;
import co.edu.poli.aplication.port.in.ConsultarInmuebleUseCase;
import co.edu.poli.aplication.port.in.CrearInmuebleUseCase;
import co.edu.poli.aplication.port.in.EliminarInmuebleUseCase;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Adaptador de la interfaz gráfica para gestionar inmuebles.
 * <p>
 * Este controlador conecta la vista JavaFX con los casos de uso de la
 * aplicación, permitiendo crear, consultar, actualizar y eliminar propiedades.
 * </p>
 */
public class InmuebleController {
    @FXML
    private TableView<Inmueble> inmuebleTable;
    @FXML
    private TableColumn<Inmueble, String> chipColumn;
    @FXML
    private TableColumn<Inmueble, String> tipoColumn;
    @FXML
    private TableColumn<Inmueble, String> fechaColumn;
    @FXML
    private TableColumn<Inmueble, String> direccionColumn;
    @FXML
    private TableColumn<Inmueble, String> estadoColumn;
    @FXML
    private TableColumn<Inmueble, String> propietarioColumn;
    @FXML
    private TextField buscarChipField;
    @FXML
    private TextField chipField;
    @FXML
    private ComboBox<String> tipoComboBox;
    @FXML
    private DatePicker fechaCompraPicker;
    @FXML
    private TextField direccionField;
    @FXML
    private ComboBox<String> estadoComboBox;
    @FXML
    private TextField propietarioIdField;
    @FXML
    private TextField propietarioNombreField;
    @FXML
    private TextField propietarioCorreoField;
    @FXML
    private TextField numeroPisoField;
    @FXML
    private TextField pisosField;
    @FXML
    private TextField sotanoAreaField;
    @FXML
    private TextField sotanoTipoUsoField;
    @FXML
    private HBox apartamentoOptions;
    @FXML
    private HBox casaOptions;

    private final CrearInmuebleUseCase crearInmuebleUseCase;
    private final ConsultarInmuebleUseCase consultarInmuebleUseCase;
    private final ActualizarInmuebleUseCase actualizarInmuebleUseCase;
    private final EliminarInmuebleUseCase eliminarInmuebleUseCase;

    public InmuebleController(CrearInmuebleUseCase crearInmuebleUseCase,
                              ConsultarInmuebleUseCase consultarInmuebleUseCase,
                              ActualizarInmuebleUseCase actualizarInmuebleUseCase,
                              EliminarInmuebleUseCase eliminarInmuebleUseCase) {
        this.crearInmuebleUseCase = Objects.requireNonNull(crearInmuebleUseCase);
        this.consultarInmuebleUseCase = Objects.requireNonNull(consultarInmuebleUseCase);
        this.actualizarInmuebleUseCase = Objects.requireNonNull(actualizarInmuebleUseCase);
        this.eliminarInmuebleUseCase = Objects.requireNonNull(eliminarInmuebleUseCase);
    }

    @FXML
    private void initialize() {
        tipoComboBox.setItems(FXCollections.observableArrayList("Casa", "Apartamento"));
        tipoComboBox.getSelectionModel().selectFirst();
        estadoComboBox.setItems(FXCollections.observableArrayList(
                "Disponible", "Arrendado", "Vendido"));
        estadoComboBox.getSelectionModel().selectFirst();
        tipoComboBox.valueProperty().addListener(
                (observable, anterior, tipo) -> actualizarOpcionesTipo(tipo));
        actualizarOpcionesTipo(tipoComboBox.getValue());

        chipColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getChip()));
        tipoColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(obtenerTipo(cell.getValue())));
        fechaColumn.setCellValueFactory(cell -> {
            LocalDate fecha = cell.getValue().getFechaCompra();
            return new ReadOnlyStringWrapper(fecha == null ? "" : fecha.toString());
        });
        direccionColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getDireccion()));
        estadoColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(cell.getValue().getEstado()));
        propietarioColumn.setCellValueFactory(cell -> {
            Propietario propietario = cell.getValue().getPropietario();
            return new ReadOnlyStringWrapper(propietario == null ? "" : propietario.getNombre());
        });

        inmuebleTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, seleccionado) -> mostrarInmueble(seleccionado));
        cargarInmuebles();
    }

    @FXML
    private void crearInmueble() {
        ejecutarAccion(() -> {
            crearInmuebleUseCase.crear(leerFormulario());
            cargarInmuebles();
            limpiarFormulario();
            mostrarInformacion("Inmueble creado correctamente.");
        });
    }

    @FXML
    private void actualizarInmueble() {
        ejecutarAccion(() -> {
            actualizarInmuebleUseCase.actualizar(leerFormulario());
            cargarInmuebles();
            limpiarFormulario();
            mostrarInformacion("Inmueble actualizado correctamente.");
        });
    }

    @FXML
    private void eliminarInmueble() {
        ejecutarAccion(() -> {
            Inmueble seleccionado = inmuebleTable.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                throw new IllegalArgumentException("Selecciona un inmueble para eliminar.");
            }
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Eliminar el inmueble con CHIP " + seleccionado.getChip() + "?",
                    ButtonType.CANCEL, ButtonType.OK);
            confirmacion.setHeaderText("Confirmar eliminación");
            Optional<ButtonType> respuesta = confirmacion.showAndWait();
            if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
                eliminarInmuebleUseCase.eliminar(seleccionado.getChip());
                cargarInmuebles();
                limpiarFormulario();
                mostrarInformacion("Inmueble eliminado correctamente.");
            }
        });
    }

    @FXML
    private void consultarInmuebles() {
        ejecutarAccion(this::cargarInmuebles);
    }

    @FXML
    private void consultarPorChip() {
        ejecutarAccion(() -> {
            String chip = buscarChipField.getText();
            if (chip == null || chip.isBlank()) {
                cargarInmuebles();
                return;
            }
            Optional<Inmueble> inmueble = consultarInmuebleUseCase.consultarPorChip(chip.trim());
            inmuebleTable.setItems(FXCollections.observableArrayList(
                    inmueble.map(List::of).orElseGet(List::of)));
            if (!inmueble.isPresent()) {
                mostrarInformacion("No se encontró un inmueble con ese CHIP.");
            }
        });
    }

    @FXML
    private void limpiarFormulario() {
        inmuebleTable.getSelectionModel().clearSelection();
        chipField.clear();
        chipField.setEditable(true);
        tipoComboBox.getSelectionModel().selectFirst();
        fechaCompraPicker.setValue(null);
        direccionField.clear();
        estadoComboBox.getSelectionModel().selectFirst();
        propietarioIdField.clear();
        propietarioNombreField.clear();
        propietarioCorreoField.clear();
        numeroPisoField.clear();
        pisosField.clear();
        sotanoAreaField.clear();
        sotanoTipoUsoField.clear();
    }

    private Inmueble leerFormulario() {
        String chip = textoObligatorio(chipField, "El CHIP es obligatorio.");
        String direccion = textoObligatorio(direccionField, "La dirección es obligatoria.");
        String estado = estadoComboBox.getValue();
        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("Selecciona el estado del inmueble.");
        }
        int identificacion = leerEntero(propietarioIdField, "La identificación del propietario");
        String nombre = textoObligatorio(propietarioNombreField,
                "El nombre del propietario es obligatorio.");
        String correo = textoObligatorio(propietarioCorreoField,
                "El correo del propietario es obligatorio.");
        Propietario propietario = new Propietario(identificacion, nombre, correo);
        LocalDate fechaCompra = fechaCompraPicker.getValue();

        switch (tipoComboBox.getValue()) {
            case "Apartamento":
                return new Apartamento(chip, fechaCompra, direccion, estado, propietario,
                        leerEntero(numeroPisoField, "El número de piso"));
            case "Casa":
                Sotano sotano = null;
                if (!sotanoAreaField.getText().isBlank()
                        || !sotanoTipoUsoField.getText().isBlank()) {
                    double area = leerDecimal(sotanoAreaField, "El área del sótano");
                    String tipoUso = textoObligatorio(sotanoTipoUsoField,
                            "El tipo de uso del sótano es obligatorio.");
                    sotano = new Sotano(area, tipoUso);
                }
                return new Casa(chip, fechaCompra, direccion, estado, propietario,
                        leerEntero(pisosField, "La cantidad de pisos"), sotano);
            default:
                throw new IllegalArgumentException("Selecciona Casa o Apartamento.");
        }
    }

    private void cargarInmuebles() {
        inmuebleTable.setItems(FXCollections.observableArrayList(
                consultarInmuebleUseCase.consultarTodos()));
    }

    private void mostrarInmueble(Inmueble inmueble) {
        if (inmueble == null) {
            return;
        }
        chipField.setText(inmueble.getChip());
        chipField.setEditable(false);
        fechaCompraPicker.setValue(inmueble.getFechaCompra());
        direccionField.setText(inmueble.getDireccion());
        estadoComboBox.setValue(inmueble.getEstado());

        Propietario propietario = inmueble.getPropietario();
        if (propietario != null) {
            propietarioIdField.setText(String.valueOf(propietario.getIdentificacion()));
            propietarioNombreField.setText(propietario.getNombre());
            propietarioCorreoField.setText(propietario.getCorreo());
        }

        numeroPisoField.clear();
        pisosField.clear();
        sotanoAreaField.clear();
        sotanoTipoUsoField.clear();
        if (inmueble instanceof Apartamento) {
            Apartamento apartamento = (Apartamento) inmueble;
            tipoComboBox.setValue("Apartamento");
            numeroPisoField.setText(String.valueOf(apartamento.getNumeroPiso()));
        } else if (inmueble instanceof Casa) {
            Casa casa = (Casa) inmueble;
            tipoComboBox.setValue("Casa");
            pisosField.setText(String.valueOf(casa.getPisos()));
            numeroPisoField.clear();
            if (casa.getSotano() != null) {
                sotanoAreaField.setText(String.valueOf(casa.getSotano().getAreaM2()));
                sotanoTipoUsoField.setText(casa.getSotano().getTipoUso());
            } else {
                sotanoAreaField.clear();
                sotanoTipoUsoField.clear();
            }
        }
    }

    private void actualizarOpcionesTipo(String tipo) {
        boolean esApartamento = "Apartamento".equals(tipo);
        apartamentoOptions.setVisible(esApartamento);
        apartamentoOptions.setManaged(esApartamento);
        casaOptions.setVisible("Casa".equals(tipo));
        casaOptions.setManaged("Casa".equals(tipo));
    }

    private String obtenerTipo(Inmueble inmueble) {
        if (inmueble instanceof Apartamento) {
            return "Apartamento";
        }
        if (inmueble instanceof Casa) {
            return "Casa";
        }
        return "Inmueble";
    }

    private String textoObligatorio(TextField campo, String mensaje) {
        String valor = campo.getText();
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    private int leerEntero(TextField campo, String nombreCampo) {
        try {
            return Integer.parseInt(textoObligatorio(campo, nombreCampo + " es obligatorio."));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número entero.");
        }
    }

    private double leerDecimal(TextField campo, String nombreCampo) {
        try {
            double valor = Double.parseDouble(textoObligatorio(
                    campo, nombreCampo + " es obligatorio."));
            if (valor < 0) {
                throw new IllegalArgumentException(nombreCampo + " no puede ser negativo.");
            }
            return valor;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número válido.");
        }
    }

    private void ejecutarAccion(Runnable accion) {
        try {
            accion.run();
        } catch (RuntimeException exception) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("No se pudo completar la operación");
            alerta.setContentText(exception.getMessage() == null
                    ? "Ocurrió un error inesperado." : exception.getMessage());
            alerta.showAndWait();
        }
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Gestión de inmuebles");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
