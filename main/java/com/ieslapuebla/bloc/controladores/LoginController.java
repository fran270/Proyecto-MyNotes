package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.dao.ConexionBD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    @FXML
    private TextField inputUsuario;
    @FXML
    private PasswordField inputContrasena;
    @FXML
    private ImageView iconoMostrar;
    @FXML
    private ImageView iconoOcultar;
    @FXML
    private TextField contrasenaVisible;
    @FXML
    private Label errorContrasena;
    @FXML
    private Button botonAcceder;
    @FXML
    private Hyperlink enlaceRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {

        String usuario = inputUsuario.getText();
        String contrasena = inputContrasena.getText();

        validarLogin(usuario, contrasena);
    }

    @FXML
    private void enlaceRegistro(MouseEvent event) {

        try {

            Stage stage = (Stage) enlaceRegistro.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/FormularioRegistro.fxml"));
           
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    /**
     * Metodo que se encarga de validar las credenciales del usuario que inicia
     * sesion
     *
     * @param usuario
     * @param contrasena
     */
    private void validarLogin(String usuario, String contrasena) {
        
        if (!ConsultasBDUsuarios.iniciarSesion(ConexionBD.crearConexion(), usuario, contrasena).equals("") && 
                !ConsultasBDUsuarios.iniciarSesion(ConexionBD.crearConexion(), usuario, contrasena).equals("admin")) {

            Usuario usuario1 = new Usuario();
            usuario1.setUsuario(usuario);
            
            accesoInicioUsuario();

        } else if (ConsultasBDUsuarios.iniciarSesion(ConexionBD.crearConexion(), usuario, contrasena).equals("admin")) {

            Usuario administrador = new Usuario();
            administrador.setUsuario(usuario);
            
            accesoInicioAdministrador();

        } else {
            errorContrasena.setText("Usuario o contraseña no validos");
        }
    }

    /**
     * Este metodo carga la pagina de inicio de la aplicacion
     */
    private void accesoInicioUsuario() {
        try {

            Stage stage = (Stage) botonAcceder.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    private void accesoInicioAdministrador() {
        try {

            Stage stage = (Stage) botonAcceder.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/admin/vistas/Inicio.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("Error al cargar la vista");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    @FXML
    private void mostrar(MouseEvent event) {

        // Mostrar la contraseña y se muestra el icono ocultar
        contrasenaVisible.setVisible(true);
        inputContrasena.setVisible(false);

        contrasenaVisible.setText(inputContrasena.getText());

        iconoOcultar.setVisible(true);
        iconoMostrar.setVisible(false);
    }

    @FXML
    private void ocultar(MouseEvent event) {

        // Ocultamos la contraseña y se muestra el icono mostrar
        contrasenaVisible.setVisible(false);
        inputContrasena.setVisible(true);

        contrasenaVisible.setText(inputContrasena.getText());

        iconoOcultar.setVisible(false);
        iconoMostrar.setVisible(true);
    }
}
