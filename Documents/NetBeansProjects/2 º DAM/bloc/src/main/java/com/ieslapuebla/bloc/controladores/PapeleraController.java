package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
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
    private Label usuarioConectado;
    @FXML
    private Label cerrar;
    @FXML
    private TableView<Nota> notasPapelera;
    @FXML
    private TableColumn<Nota, String> nombreNota;
    @FXML
    private TableColumn<Nota, String> fechaModificacion;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonRestaurar;

    private String usuario;
    private Nota notaEliminar;
    private Nota notaRestaurar;
    private ObservableList<Nota> notas;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);

        cargarNotasPapelera();
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
        
        notaRestaurar = notasPapelera.getSelectionModel().getSelectedItem();
        
        // Si no se ha seleccionado ninguna nota para restaurar, mensaje de error
        if(notaRestaurar == null){
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea restaurar", null, JOptionPane.ERROR_MESSAGE);
        } else {
            
            int idNota = notaRestaurar.getIdNota();
            String nomNota = notaRestaurar.getNombreNota();
            String contenido = notaRestaurar.getContenido();
            String fechaCreacion = notaRestaurar.getFechaCreacion();
            String fechaMod = notaRestaurar.getFechaModificacion();
           
            int idUsuario = ControladorUsuarios.obtenerId(usuario);
            
            //Nota nota = new Nota(nomNota, contenido, fechaCreacion, fechaMod, idUsuario);
            
            //ControladorNotas.insertarNota(nota);
            
            ControladorPapelera.eliminarNotaPapelera(idNota);
        }
    }

    private void cargarNotasPapelera() {

        /*if (notasPapelera.getItems().isEmpty()) {
            Label textoPlaceholder = new Label("La papelera esta vacia");
            notasPapelera.setPlaceholder(textoPlaceholder);
            
        } else {*/
        // Mostramos en la tabla las notas que hemos mandado a la papelera
       

        int idUsuario = ControladorUsuarios.obtenerId(usuario);
        
        notas = FXCollections.observableArrayList();
        
        notas = ControladorPapelera.verNotasPapelera(notas, idUsuario);

        notasPapelera.setItems(notas);

        nombreNota.setCellValueFactory(new PropertyValueFactory<Nota, String>("nombreNota"));
        fechaModificacion.setCellValueFactory(new PropertyValueFactory<Nota, String>("fechaModificacion"));
    }

    @FXML
    private void eliminarNota(ActionEvent event) {

        notaEliminar = notasPapelera.getSelectionModel().getSelectedItem();

        //Si no ha seleccionado la nota que desea borrar -> mensaje de error
        if (notaEliminar == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la nota que desea borrar", null, JOptionPane.ERROR_MESSAGE);
        } else {
            //Guardamos en una variable el id de la nota
            int idNota = notaEliminar.getIdNota();
            ControladorNotas.eliminarNota(idNota);
        }
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        
        Platform.exit();
        System.exit(0);
    }

}
