package com.ieslapuebla.bloc.admin.controladores;

import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class UsuariosController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private Label usuarioConectado;
    @FXML
    private TableColumn<Usuario, Integer> id;
    @FXML
    private TableColumn<Usuario, String> usuario;
    @FXML
    private TableColumn<Usuario, String> contrasena;
    @FXML
    private TableColumn<Usuario, String> email;
    @FXML
    private TableColumn<Usuario, String> nombre;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private Button botonInsertar;
    @FXML
    private Button botonModificar;
    @FXML
    private Button botonEliminar;
    private ObservableList<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
   
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarUsuarios();
    }
    
    @FXML
    private void volverInicio(MouseEvent event) throws IOException {
        
        Stage stage = (Stage) logo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/Inicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    private void cargarUsuarios() {

        usuarios = FXCollections.observableArrayList();
        
        ConsultasBDUsuarios.obtenerUsuarios(ConexionBD.crearConexion(), usuarios);

        tablaUsuarios.setItems(usuarios);

        id.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        usuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
        contrasena.setCellValueFactory(new PropertyValueFactory<Usuario, String>("contrasena"));
        email.setCellValueFactory(new PropertyValueFactory<Usuario, String>("correo"));
        nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
    }

    @FXML
    private void crearUsuario(ActionEvent event) throws IOException {

        Stage stage = (Stage) botonInsertar.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/InsertarUsuario.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modificarUsuario(ActionEvent event) throws IOException {

        usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {

            JOptionPane.showMessageDialog(null, "Selecciona el usuario que desea modificar",
                    "Mensaje de advertencia", JOptionPane.ERROR_MESSAGE);

        } else {

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/vistas/ModificarUsuario.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del FXML cargado
                ModificarUsuarioController controlador = loader.getController();

                // Pasar el ID de la nota seleccionada al nuevo controlador
                controlador.recibirIdUsuario(usuarioSeleccionado.getId());

                Stage stage = (Stage) botonModificar.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {

                System.out.println("Error al cargar el fichero");
                System.out.printf("ERROR: %s", ex.getMessage());
            }
        }
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) {

        usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione el usuario que desea borrar", null, JOptionPane.ERROR_MESSAGE);
        } else {
            // Guardamos en una variable el id del usuario
            int idUsuario = usuarioSeleccionado.getId();
            ConsultasBDUsuarios.eliminarUsuario(ConexionBD.crearConexion(), idUsuario);
            cargarUsuarios();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
