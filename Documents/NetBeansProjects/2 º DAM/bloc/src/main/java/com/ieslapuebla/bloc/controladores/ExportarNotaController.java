package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ExportarNotaController {

    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private Label usuarioConectado;
    @FXML
    private Label cerrar;
    @FXML
    private Button botonExportar;
    @FXML
    private TextField nombreFichero;
    @FXML
    private MenuButton formatoArchivo;
    @FXML
    private MenuItem formato1;
    @FXML
    private MenuItem formato2;

    private Nota nota;

    public void initialize(Nota notaRecibida) {
        
        usuarioConectado.setText(Usuario.getUsuario());

        nota = notaRecibida;

        recibirNota(nota);
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
    public void exportarNota(ActionEvent event) {

        recibirNota(nota);
    }

    public void recibirNota(Nota notaRecibida) {

        //Guardamos el nombre del fichero
        String nombreArchivo = nombreFichero.getText();

        /*Comprobamos que el campo donde se introduce el nombre
        del fichero a exportar no este vacio*/
        if (nombreArchivo.equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor, introduzca un nombre para el fichero", null, JOptionPane.ERROR_MESSAGE);
        } else {
            //Obtenemos la extension del fichero
            String[] datos = nombreArchivo.split("\\.");
            String extension = "";

            //Comprobamos si se ha introducido la extension del fichero
            if (datos.length == 1) {
                JOptionPane.showMessageDialog(null, "No has introducido el formato del fichero", null, JOptionPane.ERROR_MESSAGE);
            } else {
                extension = datos[1];
                
                //Comprobamos que la extension sea valida
                switch (extension) {

                    case "json":
                        exportarNotaJSON(notaRecibida, nombreArchivo);
                        break;

                    case "csv":
                        exportarNotaCSV(notaRecibida, nombreArchivo);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "El formato del fichero a exportar tiene que ser json o csv", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void exportarNotaJSON(Nota notaExportar, String archivo) {

        //Obtenemos los datos de la nota 
        String nombreNota = notaExportar.getNombreNota();
        String contenido = notaExportar.getContenido();
        String fechaCreacion = notaExportar.getFechaCreacion();
        String fechaModificacion = notaExportar.getFechaModificacion();

        // Creamos el fichero con el nombre que recibe el constructor como parametro
        File fichero = new File(archivo);

        try {

            boolean ficheroCreado = fichero.createNewFile();

            // Comprobamos si se ha creado el fichero
            if (ficheroCreado) {
                /*Con la clase PrintWriter y FileWriter escribimos el contenido del fichero
                que son los datos de la nota a exportar*/
                try (PrintWriter escribirFichero = new PrintWriter(new FileWriter(fichero))) {

                    escribirFichero.write(nombreNota);
                    escribirFichero.write(contenido);
                    escribirFichero.write(fechaCreacion);

                    if (fechaModificacion != null) {

                        escribirFichero.write(fechaModificacion);
                    }

                } catch (IOException e) {
                    System.out.println("Error al abrir el fichero");
                    System.out.printf("ERROR: %s", e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("No se ha podido crear el archivo");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    private void exportarNotaCSV(Nota notaExportar, String nombreArchivo) {

        //Obtenemos los datos de la nota 
        String nombreNota = notaExportar.getNombreNota();
        String contenido = notaExportar.getContenido();
        String fechaCreacion = notaExportar.getFechaCreacion();
        String fechaModificacion = notaExportar.getFechaModificacion();

        //Creamos el fichero con ese nombre
        File fichero = new File(nombreArchivo);

        //try {
        //boolean ficheroCreado = fichero.createNewFile();
        //if (ficheroCreado) {
        //Con la clase PrintWriter y FileWriter escribimos el contenido del fichero
        //que son los datos de la nota a exportar
        try (PrintWriter escribirFichero = new PrintWriter(new FileWriter(fichero))) {

            escribirFichero.write(nombreNota + ",");
            escribirFichero.write(contenido + ",");
            escribirFichero.write(fechaCreacion);

            if (fechaModificacion != null) {

                escribirFichero.write("," + fechaModificacion + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error al abrir el fichero");
            System.out.printf("ERROR: %s", e.getMessage());
        }
        //}

        /*} catch (IOException ex) {
            System.out.println("No se ha podido crear el archivo");
            System.out.printf("ERROR: %s", ex.getMessage());
        }*/
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
