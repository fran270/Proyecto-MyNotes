package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PapeleraController implements Initializable {

    @FXML
    private Label cerrar;
    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label usuarioConectado;
    private String usuario;
    
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
    private void cerrarSesion(MouseEvent event) {
        
        Platform.exit();
        System.exit(0);
    }

}
