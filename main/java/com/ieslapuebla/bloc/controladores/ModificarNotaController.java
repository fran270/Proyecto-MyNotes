package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.dao.ConsultasBDEtiquetas;
import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModificarNotaController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuario;
    @FXML
    private ImageView iconoModificar;
    @FXML
    private Button botonDeshacer;
    @FXML
    private Label mensaje;
    @FXML
    private TextArea inputContenido;
    @FXML
    private MenuButton inputEtiqueta;
    @FXML
    private TextField inputNota;
    @FXML
    private TextField inputFechaModificacion;
    @FXML
    private TextField etiqueta;
    @FXML
    private MenuItem etiqueta1;

    private int idNota;
    private int idUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        usuario.setText(Usuario.getUsuario());

        idUsuario = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), Usuario.getUsuario());

        mostrarBotones();
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
    private void verNotas(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error al cargar el fichero");
            System.out.printf("ERROR: %s\n", ex.getMessage());
        }
    }

    @FXML
    private void verPapelera(MouseEvent ev) {

        Stage stage = (Stage) opcion3.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error al cargar el fichero");
            System.out.printf("ERROR: %s\n", ex.getMessage());
        }
    }

    public void recibirNota(Nota notaRecibida) {

        this.idNota = notaRecibida.getIdNota();
        inputNota.setText(notaRecibida.getNombreNota());
        inputContenido.setText(notaRecibida.getContenido());
        inputFechaModificacion.setText(String.valueOf(LocalDate.now()));
        inputEtiqueta.setText(notaRecibida.getEtiqueta());
    }

    private void mostrarBotones() {

        botonDeshacer.setVisible(false);

        inputNota.setOnMouseClicked(e -> {
            botonDeshacer.setVisible(true);
        });

        inputContenido.setOnMouseClicked(e -> {
            botonDeshacer.setVisible(true);
        });

        etiqueta.setOnMouseClicked(e -> {
            botonDeshacer.setVisible(true);
        });
    }

    @FXML
    private void nota(KeyEvent event) {

        Nota notaModificar = new Nota(idNota,
                inputNota.getText(),
                inputContenido.getText(),
                inputEtiqueta.getText(),
                null,
                inputFechaModificacion.getText());

        inputNota.textProperty().addListener((observable, old, nombreNuevo) -> {

            notaModificar.setNombreNota(nombreNuevo);

            botonDeshacer.setOnMouseClicked(e -> {

                notaModificar.setNombreNota(old);
            });

            ConsultasBDNotas.modificarNota(ConexionBD.crearConexion(), notaModificar, mensaje);
        });

        mensaje.setStyle("-fx-background-color:  rgb(255, 199, 32)");
    }

    @FXML
    private void contenido(KeyEvent event) {

        Nota notaModificar = new Nota(idNota,
                inputNota.getText(),
                inputContenido.getText(),
                inputEtiqueta.getText(),
                null,
                inputFechaModificacion.getText());

        inputContenido.textProperty().addListener((observable, old, contenidoNuevo) -> {

            notaModificar.setContenido(contenidoNuevo);

            botonDeshacer.setOnMouseClicked(e -> {

                notaModificar.setContenido(old);
            });

            ConsultasBDNotas.modificarNota(ConexionBD.crearConexion(), notaModificar, mensaje);
        });

        mensaje.setStyle("-fx-background-color:  rgb(255, 199, 32)");
    }

    @FXML
    private void seleccionarEtiqueta(MouseEvent event) {

        seleccionarEtiqueta();

        Nota notaModificar = new Nota(idNota,
                inputNota.getText(),
                inputContenido.getText(),
                inputEtiqueta.getText(),
                null,
                inputFechaModificacion.getText());

        inputEtiqueta.textProperty().addListener((observable, old, etiquetaNueva) -> {

            notaModificar.setEtiqueta(etiquetaNueva);

            botonDeshacer.setOnMouseClicked(e -> {

                notaModificar.setEtiqueta(old);
            });

            ConsultasBDNotas.modificarNota(ConexionBD.crearConexion(), notaModificar, mensaje);
        });

        mensaje.setStyle("-fx-background-color:  rgb(255, 199, 32)");
    }

    private void seleccionarEtiqueta() {

        etiqueta1.setOnAction(e -> {

            inputEtiqueta.setText(etiqueta1.getText());
        });

        cargarEtiquetas();
    }

    private void cargarEtiquetas() {

        ArrayList<String> listaEtiquetas = ConsultasBDEtiquetas.obtenerEtiquetas(ConexionBD.crearConexion(), idUsuario);

        for (String etiquetas : listaEtiquetas) {
            MenuItem elemento = new MenuItem();
            elemento.setText(etiquetas);
            inputEtiqueta.getItems().addAll(elemento);
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
