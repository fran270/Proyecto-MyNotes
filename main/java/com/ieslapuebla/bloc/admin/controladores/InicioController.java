package com.ieslapuebla.bloc.admin.controladores;

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
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InicioController implements Initializable {

    
    @FXML
    private ImageView imagen;
    @FXML
    private Button botonUsuarios;
    @FXML
    private ImageView logo;
    @FXML
    private MenuItem administrador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {

        Stage stage = (Stage) logo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/Inicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void verUsuarios(ActionEvent event) throws IOException {

        Stage stage = (Stage) logo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/Usuarios.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
