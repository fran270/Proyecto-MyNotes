package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class PapeleraController implements Initializable {

    @FXML
    private Label cerrar;
    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private Label usuarioConectado;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonRestaurar;

    private String usuario;
    private Nota notaEliminar;
    @FXML
    private TableView<Nota> notasPapelera;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }

    @FXML
    private void verNotas(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }

    @FXML
    private void verPapelera(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion3.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

        Scene escena = new Scene(root);
        stage.setScene(escena);
        stage.show();
    }
    
    @FXML
    private void restaurarNota(ActionEvent event) {
        
    }

    @FXML
    private void eliminarNota(ActionEvent event) {

        notaEliminar = notasPapelera.getSelectionModel().getSelectedItem();

        //Si no ha seleccionado la nota que desea borrar -> mensaje de error
        if (notaEliminar == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea borrar", null, JOptionPane.ERROR_MESSAGE);
        } else {
            //Guardamos en una variable el id de la nota
            int idNota = notaEliminar.getIdNota();
            ControladorNotas.eliminarNota(idNota);
        }
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
