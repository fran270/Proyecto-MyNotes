package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.funcionalidad.ExportarNota;
import com.ieslapuebla.bloc.dao.ConsultasBDEtiquetas;
import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
import javafx.scene.paint.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class NotasController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private MenuBar opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuario;
    @FXML
    private TextField buscador;
    @FXML
    private TableView<Nota> tabla;
    @FXML
    private TableColumn<Nota, String> nota;
    @FXML
    private TableColumn<Nota, String> contenido;
    @FXML
    private TableColumn<Nota, String> etiqueta;
    @FXML
    private TableColumn<Nota, String> fechaCreacion;
    @FXML
    private TableColumn<Nota, String> fechaModificacion;
    @FXML
    private MenuButton selectorEtiquetas;
    @FXML
    private MenuItem etiqueta1;
    @FXML
    private MenuItem crearEtiqueta;
    @FXML
    private MenuItem opcionFecha;
    @FXML
    private MenuItem opcionEtiqueta;
    @FXML
    private Button botonInsertar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonExportar;
    @FXML
    private Button botonFijar;
    @FXML
    private MenuItem notasArchivadas;
    @FXML
    private Button botonArchivar;
    @FXML
    private MenuItem notasImportantes;

    private Nota notaSeleccionada;
    private ObservableList<Nota> notas;
    private int idUsuario;

    public static final Border BORDE_ROJO = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
    public static final Border BORDE_VERDE = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Muestra el nombre de usuario que ha iniciado sesion
        usuario.setText(Usuario.getUsuario());

        // En esta variable se guarda el id del usuario 
        idUsuario = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), Usuario.getUsuario());

        actualizarTablaNotas();

        cargarEtiquetas();
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void verNotas(ActionEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void importarNota() throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/ImportarNota.fxml"));

        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }

    @FXML
    private void verNotasArchivadas(ActionEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasArchivadas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void verNotasImportantes(ActionEvent event) {

        try {

            Stage stage = (Stage) opcion2.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasImportantes.fxml"));

            Scene escena = new Scene(root);
            stage.setScene(escena);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Error al cargar el fichero");
            System.out.printf("ERROR: %s", ex.getMessage());
        }
    }

    @FXML
    private void verPapelera(MouseEvent ev) {

        try {

            Stage stage = (Stage) opcion3.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

            Scene escena = new Scene(root);
            stage.setScene(escena);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Error al cargar el fichero");
            System.out.printf("ERROR: %s", ex.getMessage());
        }
    }

    @FXML
    private void seleccionarEtiqueta(MouseEvent event) {

        etiqueta1.setOnAction(e -> {

            selectorEtiquetas.setText(etiqueta1.getText());
        });

        crearEtiqueta.setOnAction(e -> {

            String etiquetaNueva = JOptionPane.showInputDialog(null, "Introduce el nombre de la etiqueta");

            // Insertamos la nueva etiqueta en la tabla Etiquetas de la bd 
            ConsultasBDEtiquetas.añadirEtiqueta(ConexionBD.crearConexion(), etiquetaNueva, idUsuario);

            cargarEtiquetas();
        });

        //etiquetas.getItems().addLast(crearEtiqueta);
    }

    @FXML
    private void seleccionarFiltro(MouseEvent event) {

        String textoInputBuscador = buscador.getText();

        opcionFecha.setOnAction(e -> {
            filtrarNotaFecha(textoInputBuscador);
        });

        opcionEtiqueta.setOnAction(e -> {
            filtrarNotaEtiqueta(textoInputBuscador);
        });
    }

    @FXML
    private void crearNota(ActionEvent event) throws IOException {

        Stage stage = (Stage) botonInsertar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/InsertarNota.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modificarNota(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {

            JOptionPane.showMessageDialog(null, "Selecciona la nota que desea modificar",
                    "Mensaje de advertencia", JOptionPane.ERROR_MESSAGE);

        } else {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/ModificarNota.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del FXML cargado
                ModificarNotaController controlador = loader.getController();

                // Pasar el ID de la nota seleccionada al nuevo controlador
                controlador.recibirNota(notaSeleccionada);

                Stage stage = (Stage) botonModificar.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {

                System.out.println("Error al cargar el fichero");
                System.out.printf("ERROR: %s", ex.getMessage());
            }
        }
    }

    @FXML
    private void moverNotaPapelera(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Selecciona la nota que desea borrar",
                    "Mensaje de advertencia", JOptionPane.ERROR_MESSAGE);
        } else {

            String rutaFicheroNota = "Papelera/".concat(notaSeleccionada.getNombreNota()).concat(".csv");

            // Crear un fichero
            File fichero = new File(rutaFicheroNota);

            // Abrir el fichero y guardar los datos de la nota con BufferedWriter
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {

                bw.write(notaSeleccionada.getNombreNota() + ",");

                String contenidoNota = notaSeleccionada.getContenido().replace("\n", " ");
                bw.write(contenidoNota + ",");

                bw.write(notaSeleccionada.getContenido() + ",");
                bw.write(notaSeleccionada.getEtiqueta() + ",");
                bw.write(notaSeleccionada.getFechaCreacion() + ",");

                if (notaSeleccionada.getFechaModificacion() != null) {
                    bw.write(notaSeleccionada.getFechaModificacion());
                }

            } catch (IOException e) {
                System.out.println("Se ha producido un error");
                System.out.printf("ERROR: %s", e.getMessage());
            }

            ConsultasBDNotas.eliminarNota(ConexionBD.crearConexion(), notaSeleccionada.getIdNota());

            actualizarTablaNotas();
        }
    }

    @FXML
    private void exportarNota(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {

            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea exportar", null, JOptionPane.ERROR_MESSAGE);

        } else {

            FileChooser.ExtensionFilter formatoJSON = new FileChooser.ExtensionFilter("JSON", "*.json");
            FileChooser.ExtensionFilter formatoCSV = new FileChooser.ExtensionFilter("CSV", "*.csv");

            FileChooser ficheroExportar = new FileChooser();
            ficheroExportar.setTitle("Exportar fichero");

            ficheroExportar.getExtensionFilters().addAll(formatoJSON, formatoCSV);

            File file = ficheroExportar.showSaveDialog(new Stage());

            // Obtenemos la ruta del fichero que hemos guardado
            String archivo = file.getPath();

            // Comprobamos si la formato escogido es JSON o CSV
            if (ficheroExportar.getSelectedExtensionFilter() == formatoJSON) {
                ExportarNota.exportarJSON(archivo, notaSeleccionada);
            } else {
                ExportarNota.exportarCSV(archivo, notaSeleccionada);
            }
        }
    }

    @FXML
    private void fijarNota(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea fijar como importante", null, JOptionPane.ERROR_MESSAGE);
        } else {
            ConsultasBDNotas.fijarNota(ConexionBD.crearConexion(), notaSeleccionada.getIdNota());
            tabla.getItems().remove(notaSeleccionada);
        }
    }

    @FXML
    private void archivarNota(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea archivar", null, JOptionPane.ERROR_MESSAGE);
        } else {
            ConsultasBDNotas.archivarNota(ConexionBD.crearConexion(), notaSeleccionada.getIdNota());
            tabla.getItems().remove(notaSeleccionada);
        }
    }

    private void actualizarTablaNotas() {

        notas = FXCollections.observableArrayList();

        if (ConsultasBDNotas.verNotas(ConexionBD.crearConexion(), notas, idUsuario)) {

            tabla.setItems(notas);
           
            nota.setCellValueFactory(new PropertyValueFactory<>("nombreNota"));
            contenido.setCellValueFactory(new PropertyValueFactory<Nota, String>("contenido"));
            etiqueta.setCellValueFactory(new PropertyValueFactory<Nota, String>("etiqueta"));
            fechaCreacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaCreacion"));
            fechaModificacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaModificacion"));

        } else {

            Label texto = new Label("No has creado ninguna nota");

            // Cambiamos el texto del centro de la tabla
            tabla.setPlaceholder(texto);
        }
    }

    private void filtrarNotaFecha(String textoBuscador) {

        notas = FXCollections.observableArrayList();

        // Si no he seleccionado ningun opcion y el input del buscador esta vacio mensaje de error
        String[] datosNota = textoBuscador.split("\\s");

        if (textoBuscador.isEmpty()) {
            buscador.setBorder(BORDE_ROJO);
            JOptionPane.showMessageDialog(null, "Introduzca el nombre o "
                    + "contenido de la nota que desea buscar", "", JOptionPane.ERROR_MESSAGE);
        } else {
            switch (datosNota.length) {

                case 1:
                    buscador.setBorder(BORDE_ROJO);
                    JOptionPane.showMessageDialog(null, "Introduzca el contenido de la nota", null, JOptionPane.ERROR_MESSAGE);
                    break;

                case 2:
                    // Se ejecuta el metodo que filtra la nota mediante su nombre y contenido 
                    buscador.setBorder(BORDE_VERDE);
                    Nota notaFiltrar = new Nota(datosNota[0], datosNota[1], "", "", "", idUsuario);
                    notas = ConsultasBDNotas.filtrarNotaFecha(ConexionBD.crearConexion(), notas, notaFiltrar);
                    cargarNotaTabla(notas);
                    break;
            }
        }
    }

    private void filtrarNotaEtiqueta(String textoBuscador) {

        notas = FXCollections.observableArrayList();

        String[] datosNota = textoBuscador.split("\\s");

        // Si no he seleccionado ninguna opcion y el input del buscador esta vacio mensaje de error
        if (textoBuscador.isEmpty()) {

            buscador.setBorder(BORDE_ROJO);

            JOptionPane.showMessageDialog(null, "Introduzca el nombre y "
                    + "contenido de la nota que desea buscar", "", JOptionPane.ERROR_MESSAGE);

        } else {

            switch (datosNota.length) {
                case 1:
                    buscador.setBorder(BORDE_ROJO);
                    JOptionPane.showMessageDialog(null, "Introduzca el contenido de la nota", null, JOptionPane.ERROR_MESSAGE);
                    break;
                case 2:
                    buscador.setBorder(BORDE_VERDE);
                    Nota notaFiltrar = new Nota(datosNota[0], datosNota[1], selectorEtiquetas.getItems().get(0).getText(), "", "",
                            idUsuario);
                    // Se ejecuta el metodo que se encarga de filtrar la nota mediante la etiqueta
                    notas = ConsultasBDNotas.filtrarNotaEtiqueta(ConexionBD.crearConexion(), notas, notaFiltrar);
                    cargarNotaTabla(notas);
                    break;
            }
        }
    }

    private void cargarNotaTabla(ObservableList<Nota> notas) {

        // Comprobamos si existe o no la nota a buscar
        if (notas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nota no encontrada", null, JOptionPane.ERROR_MESSAGE);
        } else {

            tabla.setItems(notas);

            nota.setCellValueFactory(new PropertyValueFactory<Nota, String>("nombreNota"));
            contenido.setCellValueFactory(new PropertyValueFactory<Nota, String>("contenido"));
            etiqueta.setCellValueFactory(new PropertyValueFactory<Nota, String>("etiqueta"));
            fechaCreacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaCreacion"));
            fechaModificacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaModificacion"));
        }
    }

    private void cargarEtiquetas() {

        ArrayList<String> listaEtiquetas = ConsultasBDEtiquetas.obtenerEtiquetas(ConexionBD.crearConexion(), idUsuario);

        for (String etiquetas : listaEtiquetas) {

            MenuItem etiquetaNueva = new MenuItem(etiquetas);

            selectorEtiquetas.getItems().add(etiquetaNueva);
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
