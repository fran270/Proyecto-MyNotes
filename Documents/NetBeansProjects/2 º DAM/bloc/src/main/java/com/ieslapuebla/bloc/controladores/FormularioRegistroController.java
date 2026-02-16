package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Usuario;
import com.ieslapuebla.bloc.utils.AbrirVentanas;
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
import org.mindrot.jbcrypt.BCrypt;

public class FormularioRegistroController implements Initializable {

    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasena;
    @FXML
    private TextField email;
    @FXML
    private TextField nombre;
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
        String usu = usuario.getText();
        String password = contrasena.getText();
        String correo = email.getText();
        String nombreCompleto = nombre.getText();

        /*Variables donde se almacena el patron de diseño que debe
        cumplir los datos introducidos en los campos del formulario*/
        String patronUsuario = "[a-zA-Z0-9]+";
        String patronContrasena = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}";
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        String patronNombre = "[a-zA-Z ]+";//^[A-Za-z ]*$

        Border bordeRojo = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
        Border bordeVerde = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));

        //Validaciones campos formulario
        if (usu.isEmpty() || password.isEmpty() || correo.isEmpty() || nombreCompleto.isEmpty()) {

            if (usu.isEmpty()) {
                errorUsuario.setText("El campo usuario esta en blanco");
                usuario.setBorder(bordeRojo);
            } else if (!usu.matches(patronUsuario)) {
                errorUsuario.setText("El nombre de usuario solo puede contener "
                        + "letras y numeros");
                usuario.setBorder(bordeRojo);
            } else if (ControladorUsuarios.comprobarUsuario(usu)){
                errorUsuario.setText("Este usuario ya existe");
                usuario.setBorder(bordeRojo);
            } else {
                errorUsuario.setText("");
                usuario.setBorder(bordeVerde);
            }

            if (password.isEmpty()) {
                errorContrasena.setText("El campo contraseña esta en blanco");
                contrasena.setBorder(bordeRojo);
            } else if (!password.matches(patronContrasena) || (password.length() > 8 || password.length() < 8)) {
                errorContrasena.setText("La contraseña tiene que tener 8 caracteres "
                        + " \nentre los cuales 1 letra mayuscula, minuscula, numero "
                        + " \ny 1 caracter especial");
                contrasena.setBorder(bordeRojo);
            } else {
                errorContrasena.setText("");
                contrasena.setBorder(bordeVerde);
            }

            if (correo.isEmpty()) {
                errorCorreo.setText("El campo email esta en blanco");
                email.setBorder(bordeRojo);
            } else if (!correo.matches(patronCorreo)) {
                errorCorreo.setText("El correo introducido no es valido");
                email.setBorder(bordeRojo);
            } else if (ControladorUsuarios.comprobarCorreo(correo)){
                errorCorreo.setText("Este correo ya existe");
                email.setBorder(bordeRojo);
            } else {
                errorCorreo.setText("");
                email.setBorder(bordeVerde);
            }

            if (nombreCompleto.isEmpty()) {
                errorNombre.setText("El campo nombre esta en blanco");
                nombre.setBorder(bordeRojo);
            } else if (!nombreCompleto.matches(patronNombre)) {
                errorNombre.setText("El nombre solo puede contener letras");
                nombre.setBorder(bordeRojo);
            } else {
                errorNombre.setText("");
                nombre.setBorder(bordeVerde);
            }

        } else {

            /*if (ControladorUsuarios.comprobarUsuario(usu)) {

                errorUsuario.setText("Este usuario ya existe");
                usuario.setBorder(bordeRojo);

            } else if (ControladorUsuarios.comprobarCorreo(correo)) {

                errorCorreo.setText("Este correo ya existe");
                email.setBorder(bordeRojo);

            } else {*/

                String contrasenaEncriptada = BCrypt.hashpw(password, BCrypt.gensalt());
                
                Usuario nuevoUsuario = new Usuario(usu, contrasenaEncriptada, correo, nombreCompleto);

                ControladorUsuarios.insertarUsuario(nuevoUsuario);

                //Limpia los campos del formulario
                limpiarCampos();
        }
    }

    private void limpiarCampos() {

        usuario.clear();
        contrasena.clear();
        email.clear();
        nombre.clear();
    }

}
