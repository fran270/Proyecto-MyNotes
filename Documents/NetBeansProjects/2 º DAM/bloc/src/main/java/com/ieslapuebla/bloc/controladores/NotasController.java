package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.paint.Color;
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
    private Label usuarioConectado;
    @FXML
    private TextField buscador;
    @FXML
    private Button botonBuscar;
    @FXML
    private TableView<Nota> tabla;
    @FXML
    private TableColumn<Nota, String> nota;
    @FXML
    private TableColumn<Nota, String> contenido;
    @FXML
    private TableColumn<Nota, String> fechaCreacion;
    @FXML
    private TableColumn<Nota, String> fechaModificacion;
    @FXML
    private MenuButton etiquetas;
    @FXML
    private MenuItem etiqueta1;
    @FXML
    private MenuItem etiqueta2;
    @FXML
    private MenuItem etiqueta3;
    @FXML
    private MenuItem notasFijadas;
    @FXML
    private MenuItem op1;
    @FXML
    private Button botonInsertar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonEliminar;

    private Nota notaSeleccionada;
    private String usuario;
    private ObservableList<Nota> notas;
    private int idUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();

        //Muestra el nombre de usuario que ha iniciado sesion
        usuarioConectado.setText(usuario);

        //En esta variable se guarda el id del usuario 
        idUsuario = ControladorUsuarios.obtenerId(usuario);

        actualizarTablaNotas();
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void verNotas(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    private void verNotas(ActionEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

        botonModificar.setOnMouseClicked(e -> {

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
                    controlador.recibirIdNota(notaSeleccionada.getIdNota());

                    Stage stage = (Stage) botonModificar.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {

                    System.out.println("Error al cargar el fichero");
                    System.out.printf("ERROR: %s", ex.getMessage());
                }
            }

        });
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
    private void eliminarNota(ActionEvent event) {

        notaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Selecciona la nota que desea borrar",
                    "Mensaje de advertencia", JOptionPane.ERROR_MESSAGE);
        } else {
            ControladorNotas.eliminarNota(notaSeleccionada.getIdNota());
            actualizarTablaNotas();
        }

    }

    private void actualizarTablaNotas() {

        notas = FXCollections.observableArrayList();

        if (ControladorNotas.verNotas(notas, idUsuario)) {

            tabla.setItems(notas);

            nota.setCellValueFactory(new PropertyValueFactory<Nota, String>("nombreNota"));
            contenido.setCellValueFactory(new PropertyValueFactory<Nota, String>("contenido"));
            fechaCreacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaCreacion"));
            fechaModificacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaModificacion"));
        }
    }

    @FXML
    private void seleccionarEtiqueta(MouseEvent event) {

        etiqueta1.setOnAction(e -> {

            etiquetas.setText(etiqueta1.getText());
        });

        etiqueta2.setOnAction(e -> {

            etiquetas.setText(etiqueta2.getText());
        });

        etiqueta3.setOnAction(e -> {

            etiquetas.setText(etiqueta3.getText());
        });
    }

    @FXML
    private void filtrarNotas(ActionEvent event) {

        String inputBuscador = buscador.getText();
        Border bordeRojo = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
        Border bordeVerde = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));

        // Si el campo del buscador esta vacio, mensaje de error
        if (inputBuscador.isEmpty()) {
           
            buscador.setBorder(bordeRojo);
            JOptionPane.showMessageDialog(null, "No ha introducido el nombre o contenido de la nota que desea buscar", null, JOptionPane.ERROR_MESSAGE);
        
        } else {
            
            buscador.setBorder(bordeVerde);
            
            notas = FXCollections.observableArrayList();
            
            /*String[] datosNota = inputBuscador.split("\\s");
            
            String nombreNota = datosNota[0];
            String contenidoNota = datosNota[1];*/
            
            // Sino, filtramos la nota a buscar mediante su fecha
            if (ControladorNotas.filtrarNota(notas, inputBuscador, idUsuario)) {

                //Se actualiza la tabla y muestra las notas que tengan el nombre obtenido del buscador
                tabla.setItems(notas);
                
                nota.setCellValueFactory(new PropertyValueFactory<Nota, String>("nombreNota"));
                contenido.setCellValueFactory(new PropertyValueFactory<Nota, String>("contenido"));
                fechaCreacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaCreacion"));
                fechaModificacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaModificacion"));
                
            } else {
                
                JOptionPane.showMessageDialog(null, "Nota no encontrada", null, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
