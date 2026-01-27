package com.ieslapuebla.bloc.controladores;

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
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PortadaController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private MenuBar opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private Label usuarioConectado;
  
    @FXML
    private MenuItem op1;
    @FXML
    private MenuItem notasFijadas;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //Guardamos en una variable el nombre del usuario que ha iniciado sesion
        String usu1 = Usuario.getUsuario();

        //Mostramos el nombre de usuario conectado en label que tiene id usuario
        usuarioConectado.setText(usu1);
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.getIcons().add(new Image(PortadaController.class.getResource("/images/favicon.png").toExternalForm()));
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
    private void importarNota(ActionEvent event) {
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
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
