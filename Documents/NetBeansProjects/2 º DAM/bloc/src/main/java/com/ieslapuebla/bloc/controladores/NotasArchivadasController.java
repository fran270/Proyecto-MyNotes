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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NotasArchivadasController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private MenuBar opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private Label usuarioConectado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuarioConectado.setText(Usuario.getUsuario());
    }

    @FXML
    private void volverInicio(MouseEvent event) {

        Stage stage = (Stage) opcion1.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }

    }

    @FXML
    private void verNotas(ActionEvent event) {
        
        Stage stage = (Stage) opcion1.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    @FXML
    private void importarNota(ActionEvent event) {
        
        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/ImportarNota.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    @FXML
    private void notasImportantes(ActionEvent event) {
        
        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasImportantes.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }
    }
    
    @FXML
    private void notasArchivadas(ActionEvent event) {
        
        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasArchivadas.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }
    }


    @FXML
    private void verPapelera(MouseEvent event) {
        
        Stage stage = (Stage) opcion3.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

 
}
