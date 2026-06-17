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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class InsertarNotaController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuario; 
    @FXML
    private TextField inputNombreNota;
    @FXML
    private Label errorNombreNota;
    @FXML
    private TextArea inputContenido;
    @FXML
    private Label errorContenidoNota;
    @FXML
    private TextField inputFecha;
    @FXML
    private MenuButton inputEtiqueta;
    @FXML
    private Button botonGuardar;
    @FXML
    private ImageView iconoInsertar;
    @FXML
    private MenuItem etiqueta1;
   
    private int usuarioId;
    public static final Border BORDE_ROJO = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
    public static final Border BORDE_VERDE = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
    

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario.setText(Usuario.getUsuario());

        inputFecha.setText(String.valueOf(LocalDate.now()));
        
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
    private void verNotas(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void verPapelera(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion3.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void guardarNota(ActionEvent event) {

        String nombreNota = inputNombreNota.getText();
        String contenidoNota = inputContenido.getText();
        String fechaCreacion = inputFecha.getText();
        String etiquetaSeleccionada = inputEtiqueta.getText();
        usuarioId = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), usuario.getText());

        
        if (nombreNota.isEmpty()) {

            errorNombreNota.setText("Introduzca el nombre de la nota");
            inputNombreNota.setBorder(BORDE_ROJO);

        } else if (!ConsultasBDNotas.comprobarNombreNota(ConexionBD.crearConexion(), nombreNota, usuarioId).equals("")) {

            errorNombreNota.setText("El nombre de la nota ya existe");
            inputNombreNota.setBorder(BORDE_ROJO);

        } else if (contenidoNota.isEmpty()) {

            errorNombreNota.setText("");
            inputContenido.setBorder(BORDE_VERDE);

            errorContenidoNota.setText("Introduzca el contenido de la nota");
            inputContenido.setBorder(BORDE_ROJO);

        } else {

            errorContenidoNota.setText("");
            inputContenido.setBorder(NotasController.BORDE_VERDE);

            Nota notaInsertar = new Nota(nombreNota, contenidoNota, etiquetaSeleccionada, fechaCreacion, null, usuarioId);

            boolean notaInsertada = ConsultasBDNotas.insertarNota(ConexionBD.crearConexion(), notaInsertar);

            if (notaInsertada) {
                JOptionPane.showMessageDialog(null, "La nota se ha guardado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido guardar la nota", null, JOptionPane.ERROR_MESSAGE);
            }

            limpiarCampos();
        }
    }

    @FXML
    private void seleccionarEtiqueta(MouseEvent event) {

        etiqueta1.setOnAction(e -> {
            inputEtiqueta.setText(etiqueta1.getText());
        });
        
        cargarEtiquetas();
    }

    private void cargarEtiquetas() {

        ArrayList<String> listaEtiquetas = ConsultasBDEtiquetas.obtenerEtiquetas(ConexionBD.crearConexion(), usuarioId);

        for (String etiquetas : listaEtiquetas) {
            MenuItem etiqueta = new MenuItem();
            etiqueta.setText(etiquetas);
            inputEtiqueta.getItems().add(etiqueta);
        }
    }

    private void limpiarCampos() {

        inputNombreNota.clear();
        inputContenido.clear();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
