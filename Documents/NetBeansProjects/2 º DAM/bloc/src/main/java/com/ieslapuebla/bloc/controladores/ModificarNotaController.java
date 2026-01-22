package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModificarNotaController implements Initializable {

    @FXML
    private Label cerrar;
    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label usuarioConectado;
    @FXML
    private TextField nombreNota;
    @FXML
    private TextArea descripcionNota;
    @FXML
    private Button boton_modificar;
    @FXML
    private TextField fechaModificacion;
    @FXML
    private ImageView iconoModificar;
    @FXML
    private Label opcion3;
    @FXML
    private Button botonAutoguardar;
    @FXML
    private Button botonDeshacer;
    private String usuario;
    private int idUsuario;
    private int idNota;
    private ObservableList<Nota> notas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);
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

        verNotas();
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
            System.out.printf("ERROR: %s\n", ex.getMessage());
        }

    }

    public void recibirIdNota(int id) {

        this.idNota = id;

        cargarDatos(idNota);
    }

    private void cargarDatos(int id) {

        notas = FXCollections.observableArrayList();

        notas = ControladorNotas.obtenerNota(notas, id);

        for (Nota nota : notas) {

            String titulo = nota.getNombreNota();
            String contenido = nota.getContenido();
            LocalDate fecha = LocalDate.now();

            nombreNota.setText(titulo);
            descripcionNota.setText(contenido);
            fechaModificacion.setText(String.valueOf(fecha));
        }
    }

    /*private void habilitar() {

        botonAutoguardar.setDisable(true);
        botonDeshacer.setDisable(true);

        nombreNota.setOnMouseClicked(e -> {
            botonAutoguardar.setDisable(false);
            botonDeshacer.setDisable(false);
        });

        descripcionNota.setOnMouseClicked(e -> {
            botonAutoguardar.setDisable(false);
            botonDeshacer.setDisable(false);
        });
    }*/
    @FXML
    private void actualizarNota(ActionEvent event) {

        String tituloNota = nombreNota.getText();
        String descripcion = descripcionNota.getText();
        String fechaEdicion = fechaModificacion.getText();

        Nota notaModificar = new Nota(tituloNota, descripcion, null, fechaEdicion, idNota);
        ControladorNotas.modificarNota(notaModificar);
        verNotas();
    }

    private void verNotas() {

        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {
            
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            
            System.out.printf("ERROR: %s\n", ex.getMessage());
        }

    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
