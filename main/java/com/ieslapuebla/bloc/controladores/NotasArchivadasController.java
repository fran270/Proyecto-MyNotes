package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class NotasArchivadasController implements Initializable {

    @FXML
    private Label opcion1;
    @FXML
    private MenuBar opcion2;
    @FXML
    private Label opcion3;
    @FXML
    private MenuItem usuarioConectado;
    @FXML
    private TableView<Nota> notasArchivadas;
    @FXML
    private TableColumn<Nota, String> titulo;
    @FXML
    private TableColumn<Nota, String> contenido;
    @FXML
    private Button botonDesarchivar;
    
    private int idUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuarioConectado.setText(Usuario.getUsuario());

        idUsuario = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), Usuario.getUsuario());

        cargarNotasArchivadas();
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
            System.out.println("ERROR: " + e.getMessage());
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
            System.out.println("ERROR: " + e.getMessage());
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
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void notasImportantes(ActionEvent event) {

        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasImportantes.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @FXML
    private void verNotasArchivadas(ActionEvent event) {

        Stage stage = (Stage) opcion2.getScene().getWindow();

        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/NotasArchivadas.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar el fichero");
            System.out.println("ERROR: " + e.getMessage());
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
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void cargarNotasArchivadas() {

        ObservableList<Nota> listaNotas = FXCollections.observableArrayList();

        listaNotas = ConsultasBDNotas.verNotasArchivadas(ConexionBD.crearConexion(), listaNotas, idUsuario);

        notasArchivadas.setItems(listaNotas);
        titulo.setCellValueFactory(new PropertyValueFactory<Nota, String>("nombreNota"));
        contenido.setCellValueFactory(new PropertyValueFactory<Nota, String>("contenido"));
    }

    @FXML
    private void desarchivarNota(MouseEvent event) {

        Nota notaSeleccionada = notasArchivadas.getSelectionModel().getSelectedItem();

        if (notaSeleccionada != null) {
            // Ejecutar la consulta SQL
            ConsultasBDNotas.desarchivarNota(ConexionBD.crearConexion(), notaSeleccionada.getNombreNota());
            notasArchivadas.getItems().remove(notaSeleccionada);
        } else {
           JOptionPane.showMessageDialog(null, "Selecciona la nota que desea desarchivar", null, JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
