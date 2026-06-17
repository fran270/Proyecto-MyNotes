package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.utils.AbrirVentanas;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FormularioRegistroController implements Initializable {

    @FXML
    private TextField inputUsuario;
    @FXML
    private PasswordField inputContrasena;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputNombre;
    @FXML
    private Label errorCorreo;
    @FXML
    private Label errorUsuario;
    @FXML
    private Label errorContrasena;
    @FXML
    private Label errorNombre;
    @FXML
    private Button botonRegistrar;
    @FXML
    private Hyperlink enlaceLogin;
    
    private static final String PATRON_USUARIO = "[a-zA-Z0-9]+";
    private static final String PATRON_CONTRASENA = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])"
            + "[A-Za-z\\d@#$%^&*!]{8,}";
    private static final String PATRON_EMAIL = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
    private static final String PATRON_NOMBRE = "[a-zA-Z ]+";
    
    public static final Border BORDE_ROJO = new Border(new BorderStroke(Color.RED, 
            BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
    public static final Border BORDE_VERDE = new Border(new BorderStroke(Color.GREEN, 
            BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void enlaceLogin(ActionEvent event) throws IOException {

        Stage stage = (Stage) enlaceLogin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Iniciar Sesion");
        stage.getIcons().add(new Image(AbrirVentanas.class.getResource("/images/iniciar-sesion.png").toExternalForm()));
        stage.show();
    }

    @FXML
    private void insertarUsuario(ActionEvent event) {

        //Variables donde se almacena el contenido de los campos del formulario
        String usuario = inputUsuario.getText();
        String contrasena = inputContrasena.getText();
        String email = inputEmail.getText();
        String nombre = inputNombre.getText();

        // Validaciones campos formulario
        if (usuario.isEmpty() || contrasena.isEmpty() || email.isEmpty() || nombre.isEmpty()) {

            validarUsuario(usuario);

            validarContrasena(contrasena);

            validarEmail(email);
            
            validarNombre(nombre);

        } else {

            Usuario nuevoUsuario = new Usuario(usuario, contrasena, email, nombre);

            ConsultasBDUsuarios.insertarUsuario(ConexionBD.crearConexion(), nuevoUsuario);

            // Limpia los campos del formulario
            limpiarCampos();
        }
    }

    private void validarUsuario(String nombreUsuario) {

        if (nombreUsuario.isEmpty()) {
            errorUsuario.setText("El campo usuario esta en blanco");
            inputUsuario.setBorder(BORDE_ROJO);
        } else if (!nombreUsuario.matches(PATRON_USUARIO)) {
            errorUsuario.setText("El nombre de usuario solo puede contener "
                    + "letras y numeros");
            inputUsuario.setBorder(BORDE_ROJO);
        } else if (ConsultasBDUsuarios.comprobarUsuario(ConexionBD.crearConexion(), nombreUsuario)) {
            errorUsuario.setText("Este usuario ya existe");
            inputUsuario.setBorder(BORDE_ROJO);
        } else {
            errorUsuario.setText("");
            inputUsuario.setBorder(BORDE_VERDE);
        }
    }

    private void validarContrasena(String password) {

        if (password.isEmpty()) {
            errorContrasena.setText("El campo contraseña esta en blanco");
            inputContrasena.setBorder(BORDE_ROJO);
        } else if (!password.matches(PATRON_CONTRASENA) || (password.length() > 8 || password.length() < 8)) {
            errorContrasena.setText("La contraseña tiene que tener 8 caracteres "
                    + " \nentre los cuales 1 letra mayuscula, minuscula, numero "
                    + " \ny 1 caracter especial");
            inputContrasena.setBorder(BORDE_ROJO);
        } else {
            errorContrasena.setText("");
            inputContrasena.setBorder(BORDE_VERDE);
        }
    }

    private void validarEmail(String correo) {

        if (correo.isEmpty()) {
            errorCorreo.setText("El campo email esta en blanco");
            inputEmail.setBorder(BORDE_ROJO);
        } else if (!correo.matches(PATRON_EMAIL)) {
            errorCorreo.setText("El correo introducido no es valido");
            inputEmail.setBorder(BORDE_ROJO);
        } else if (ConsultasBDUsuarios.comprobarCorreo(ConexionBD.crearConexion(), correo)) {
            errorCorreo.setText("Este correo ya existe");
            inputEmail.setBorder(BORDE_ROJO);
        } else {
            errorCorreo.setText("");
            inputEmail.setBorder(BORDE_VERDE);
        }
    }

    private void validarNombre(String nombre) {
        
        if (nombre.isEmpty()) {
            errorNombre.setText("El campo nombre esta en blanco");
            inputNombre.setBorder(BORDE_ROJO);
        } else if (!nombre.matches(PATRON_NOMBRE)) {
            errorNombre.setText("El nombre solo puede contener letras");
            inputNombre.setBorder(BORDE_ROJO);
        } else {
            errorNombre.setText("");
            inputNombre.setBorder(BORDE_VERDE);
        }
    }

    private void limpiarCampos() {

        inputUsuario.clear();
        inputContrasena.clear();
        inputEmail.clear();
        inputNombre.clear();
    }
}
