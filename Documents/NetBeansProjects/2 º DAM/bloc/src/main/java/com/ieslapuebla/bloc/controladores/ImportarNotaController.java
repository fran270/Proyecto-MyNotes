package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.File;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ImportarNotaController implements Initializable {

    @FXML
    private Label cerrar;
    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label usuarioConectado;
    @FXML
    private Button botonArchivo;
    @FXML
    private MenuButton formatoArchivo;
    @FXML
    private Button botonImportar;
    @FXML
    private MenuItem formato1;
    @FXML
    private MenuItem formato2;
    private String usuario;
    @FXML
    private Label opcion3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);
    }

    @FXML
    private void verInicio(MouseEvent event) throws IOException {

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
    private void seleccionarArchivo(ActionEvent event) {
        
        String nombreArchivo = "";
        
        FileChooser archivo = new FileChooser();
        archivo.setInitialDirectory(new File("C:/Users/Fran Crespo Crespo/Documents/ficheros sql"));

        File archivoSeleccionado = archivo.showOpenDialog(new Stage());

        if (archivoSeleccionado != null) {
            nombreArchivo = archivoSeleccionado.getName();
            botonArchivo.setText(nombreArchivo);
            importarArchivo(archivoSeleccionado, nombreArchivo);
        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun archivo", "Error de seleccion", JOptionPane.ERROR_MESSAGE);
        }

    }

    @FXML
    private void seleccionarFormato(MouseEvent event) {

        formato1.setOnAction(e -> {
            formatoArchivo.setText(formato1.getText());
        });

        formato2.setOnAction(e -> {
            formatoArchivo.setText(formato2.getText());
        });
    }

    private void importarArchivo(File archivo, String nombreArchivo) {

        botonImportar.setOnAction(e -> {

            // Extraer la extension del fichero
            String[] array = nombreArchivo.split("\\.");

            // Guardamos la extension en esta variable
            String extension = array[1];

            if (extension.equals("json") || extension.equals("csv")) {
                System.out.println("El formato es valido");
                //ControladorNotas.insertarNota();
            } else {
                JOptionPane.showMessageDialog(null, "El formato del fichero tiene que ser json o csv", "Formato no valido", JOptionPane.ERROR_MESSAGE);
            }

        });
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        
        Platform.exit();
        System.exit(0);
    }

   
    

}
