package com.ieslapuebla.bloc.admin.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModificarUsuarioController implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private MenuItem usuarioConectado;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputUsuario;
    @FXML
    private TextField inputContrasena;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputNombre;
    @FXML
    private Button botonModificar;
  
    private ObservableList<Usuario> usuarios;
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuarioConectado.setText(Usuario.getUsuario());
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {
        
        Stage stage = (Stage) logo.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/Inicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void recibirIdUsuario(int idUsuario) {

        usuarios = FXCollections.observableArrayList();

        usuarios = ConsultasBDUsuarios.obtenerUsuario(ConexionBD.crearConexion(), usuarios, idUsuario);

        for (Usuario usuario : usuarios) {

            inputId.setText(String.valueOf(idUsuario));
            inputUsuario.setText(usuario.getUsuario());
            inputContrasena.setText(usuario.getContrasena());
            inputEmail.setText(usuario.getCorreo());
            inputNombre.setText(usuario.getNombre());
        }
    }

    @FXML
    private void guardarCambios(ActionEvent event) {

        int id = Integer.parseInt(inputId.getText());
        String usuario = inputUsuario.getText();
        String contrasena = inputContrasena.getText();
        String correo = inputEmail.getText();
        String nombre = inputNombre.getText();

        Usuario usuarioModificar = new Usuario(id, usuario, contrasena, correo, nombre);

        ConsultasBDUsuarios.modificarUsuario(ConexionBD.crearConexion(), usuarioModificar);
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        Platform.exit();
        System.exit(0);
    }
}
