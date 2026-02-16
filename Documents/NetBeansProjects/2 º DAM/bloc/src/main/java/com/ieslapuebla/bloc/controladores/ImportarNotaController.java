package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
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
import org.json.JSONArray;
import org.json.JSONObject;

public class ImportarNotaController implements Initializable {

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
    private int idUsuario;
    private String formatoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);

        idUsuario = ControladorUsuarios.obtenerId(usuario);

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

        String nombreArchivo = "";
        String rutaArchivo = "";

        FileChooser archivo = new FileChooser();
        archivo.setInitialDirectory(new File("C:"));

        File archivoSeleccionado = archivo.showOpenDialog(new Stage());

        if (archivoSeleccionado != null) {

            nombreArchivo = archivoSeleccionado.getName();

            rutaArchivo = archivoSeleccionado.getPath();

            botonArchivo.setText(nombreArchivo);
            
            importarNota(nombreArchivo, rutaArchivo);

        } else {

            JOptionPane.showMessageDialog(null, "No has seleccionado ningun archivo", "Error de seleccion de fichero", JOptionPane.ERROR_MESSAGE);
        }

    }

    private String seleccionarFormato() {

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
    }

    private void importarNota(String nombreArchivo, String rutaArchivo) {

        botonImportar.setOnAction(e -> {

            if (nombreArchivo.equals("")) {
                JOptionPane.showMessageDialog(null, "No has seleccionado ningun fichero", "Fichero no seleccionado", JOptionPane.ERROR_MESSAGE);
            } else {
                // Extraer la extension del fichero
                String[] array = nombreArchivo.split("\\.");

                // Guardamos la extension en esta variable
                String extension = array[1];
                
                // Creamos un array para guardar los formatos del archivo validos
                String[] formatos = {"json", "csv"};

                if (extension.equalsIgnoreCase(formatos[0])) {

                    importarFicheroJson(rutaArchivo);

                } else if (extension.equalsIgnoreCase(formatos[1])) {

                    importarFicheroCSV(rutaArchivo);

                } else {

                    JOptionPane.showMessageDialog(null, "La extensión del fichero tiene que ser json o csv", "Formato no valido", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    private void importarFicheroJson(String rutaFichero) {

        //Recorrer el fichero json con BufferedReader
        try (BufferedReader leerFichero = new BufferedReader(new FileReader(rutaFichero))) {

            String linea;

            StringBuilder datos = new StringBuilder();

            while ((linea = leerFichero.readLine()) != null) {
                datos.append(linea);
            }

            JSONArray jsonArray = new JSONArray(datos.toString());
            JSONObject jsonObject = null;

            if (jsonArray.length() > 1) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                }
            } else {
                jsonObject = jsonArray.getJSONObject(0);
            }

            Iterator<String> claves = jsonObject.keys();

            Object valor = "";
            String cadena = "";

            //Extraer los datos de la nota
            for (int i = 0; i < jsonObject.length(); i++) {
                String clave = claves.next();
                valor = jsonObject.get(clave);

                cadena = cadena + valor + ",";
            }

            String[] datosNota = cadena.split(",");
            String nombreNota = datosNota[0];
            String contenidoNota = datosNota[1];
            String fechaCreacion = datosNota[2];
            String fechaModificacion = datosNota[3];

            //Crear un objeto Nota y que reciba los datos como parametros
            /*Nota notaImportar = new Nota(nombreNota, contenidoNota, fechaCreacion, fechaModificacion, idUsuario);

            //invocar al metodo que inserta la nota en la bd
            ControladorNotas.insertarNota(notaImportar);*/

            JOptionPane.showMessageDialog(null, "La nota se ha importado correctamente");

        } catch (IOException ex) {
            System.out.println("Error al leer el fichero");
            System.out.printf("ERROR: %s", ex.getMessage());
        }
    }

    private void importarFicheroCSV(String rutaFichero) {

        //Leer el fichero CSV con BufferedReader
        try (BufferedReader buffReader = new BufferedReader(new FileReader(rutaFichero))) {

            String linea;
            String cadena = "";

            while ((linea = buffReader.readLine()) != null) {

                cadena = cadena + linea;
            }

            String[] datosNota = cadena.split(",");
            String nombreNota = datosNota[0];
            String contenido = datosNota[1];
            String fechaCreacion = datosNota[2];
            String fechaModificacion = datosNota[3];

            /*Nota notaImportar = new Nota(nombreNota, contenido, fechaCreacion, fechaModificacion, idUsuario);

            ControladorNotas.insertarNota(notaImportar);*/

            JOptionPane.showMessageDialog(null, "La nota se ha importado correctamente");

        } catch (IOException e) {
            System.out.println("Error al leer el fichero");
            System.out.printf("Error: %s", e.getMessage());
        }

    }

    @FXML
    private void cerrarSesion(MouseEvent event) {

        Platform.exit();
        System.exit(0);
    }

}
