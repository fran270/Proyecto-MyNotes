package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.dao.ConexionBD;
import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.funcionalidad.OperacionesPapelera;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Papelera;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class PapeleraController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private Label opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuario;
    @FXML
    private TableView<Papelera> notasPapelera;
    @FXML
    private TableColumn<Papelera, String> fichero;
    @FXML
    private TableColumn<Papelera, String> fechaEliminacion;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonRestaurar;

    private Papelera notaSeleccionada;
    private ObservableList<Papelera> notas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario.setText(Usuario.getUsuario());

        cargarPapelera();
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

        notaSeleccionada = notasPapelera.getSelectionModel().getSelectedItem();

        // Si no se ha seleccionado ninguna nota para restaurar, mensaje de error
        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea restaurar", null, JOptionPane.ERROR_MESSAGE);
        } else {

            String rutaFichero = "Papelera/".concat(notaSeleccionada.getFichero());

            File ficheroNota = new File(rutaFichero);

            OperacionesPapelera.restaurarNota(ficheroNota, Usuario.getUsuario());

            ficheroNota.delete();

            cargarPapelera();
        }
    }

    @FXML
    private void eliminarNota(ActionEvent event) {

        notaSeleccionada = notasPapelera.getSelectionModel().getSelectedItem();

        // Si no ha seleccionado la nota que desea borrar -> mensaje de error
        if (notaSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea borrar", null, JOptionPane.ERROR_MESSAGE);
        } else {

            String notaBorrar = "Papelera/".concat(notaSeleccionada.getFichero());

            OperacionesPapelera.borrarNota(notaBorrar);

            cargarPapelera();
        }
    }

    private void cargarPapelera() {

        // Crear la carpeta
        if (OperacionesPapelera.crearPapelera()) {

            // Recorremos la carpeta de las notas de la papelera
            File carpeta = new File(OperacionesPapelera.obtenerNombreCarpeta());

            String[] ficherosCarpeta = carpeta.list();

            notas = FXCollections.observableArrayList();

            for (int i = 0; i < ficherosCarpeta.length; i++) {

                Papelera archivosPapelera = new Papelera(ficherosCarpeta[i]);
                archivosPapelera.getFechaEliminacion();

                notas.add(archivosPapelera);
            }

            // Mostramos en la tabla las notas que hemos mandado a la papelera
            notasPapelera.setItems(notas);

            fichero.setCellValueFactory(new PropertyValueFactory<Papelera, String>("fichero"));
            fechaEliminacion.setCellValueFactory(new PropertyValueFactory<Papelera, String>("fechaEliminacion"));
            

            boolean papeleraVacia = notasPapelera.getItems().isEmpty();

            if (papeleraVacia) {

                Label textoPlaceholder = new Label("La papelera esta vacia");
                notasPapelera.setPlaceholder(textoPlaceholder);

                botonRestaurar.setDisable(true);
                botonEliminar.setDisable(true);
            }
        }
    }
    
    
   

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
