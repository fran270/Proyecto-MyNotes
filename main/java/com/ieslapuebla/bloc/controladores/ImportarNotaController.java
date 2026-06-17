package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.funcionalidad.ImportarNota;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
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
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuario;
    @FXML
    private Button botonArchivo;
    @FXML
    private Button botonImportar;

    private int idUsuario;
    private MenuButton formatoArchivo;
    private MenuItem formato1;
    private MenuItem formato2;
    private String formatoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario.setText(Usuario.getUsuario());

        idUsuario = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), Usuario.getUsuario());

        importarNota("", "");
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

        FileChooser archivo = new FileChooser();

        // Comprobar si el SO es Windows, Linux o MacOs
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

        if (isWindows) {
            archivo.setInitialDirectory(new File("C:"));
        } else {
            archivo.setInitialDirectory(new File("/home"));
        }

        File archivoSeleccionado = archivo.showOpenDialog(new Stage());

        if (archivoSeleccionado != null) {

            String nombreArchivo = archivoSeleccionado.getName();

            String rutaArchivo = archivoSeleccionado.getPath();

            botonArchivo.setText(nombreArchivo);

            importarNota(nombreArchivo, rutaArchivo);

        } else {

            JOptionPane.showMessageDialog(null, "No has seleccionado ningun archivo", 
                    "Error de seleccion de fichero", JOptionPane.ERROR_MESSAGE);
        }

    }

    /*private String seleccionarFormato() {

        formatoArchivo.setOnMouseClicked(e -> {

            formato1.setOnAction(e1 -> {
                formatoArchivo.setText(formato1.getText());
                formatoSeleccionado = formatoArchivo.getText();
            });

            formato2.setOnAction(e2 -> {
                formatoArchivo.setText(formato2.getText());
                formatoSeleccionado = formatoArchivo.getText();
            });
        });

        return formatoSeleccionado;
    }*/

    private void importarNota(String nombreArchivo, String rutaArchivo) {

        botonImportar.setOnMouseClicked(e -> {
            
            if (nombreArchivo.equals("")) {
                JOptionPane.showMessageDialog(null, "No has seleccionado ningun "
                        + "fichero", "Fichero no seleccionado", JOptionPane.ERROR_MESSAGE);
            } else {
                // Extraer la extension del fichero
                String[] array = nombreArchivo.split("\\.");

                // Guardamos la extension en esta variable
                String extension = array[1];

                /* Llamamos al metodo que se encarga de comprobar
                si el formato del fichero es valido */
                validarFormatoFichero(extension, rutaArchivo);
            }
        });
    }

    private void validarFormatoFichero(String extension, String rutaArchivo) {

        // Creamos un array para guardar los formatos de archivo validos
        String[] formatos = {"json", "csv"};

        if (extension.equalsIgnoreCase(formatos[0])) {

            ImportarNota.importarFicheroJson(rutaArchivo, idUsuario);

        } else if (extension.equalsIgnoreCase(formatos[1])) {

            ImportarNota.importarFicheroCSV(rutaArchivo, idUsuario);

        } else {

            JOptionPane.showMessageDialog(null, "La extensión del fichero tiene "
                    + "que ser json o csv", "Formato no valido", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
