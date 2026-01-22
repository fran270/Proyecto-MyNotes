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

public class FormularioRegistroController implements Initializable {

    @FXML
    private Hyperlink enlace_login;
    @FXML
    private Button boton_registrar;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasena;
    @FXML
    private TextField email;
    @FXML
    private TextField nombre;
    @FXML
    private Label error_usuario;
    @FXML
    private Label error_contrasena;
    @FXML
    private Label error_correo;
    @FXML
    private Label error_nombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void enlace_login(ActionEvent event) throws IOException {

        Stage stage = (Stage) enlace_login.getScene().getWindow();
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

        //Variables donde se almacena el patron de diseño que debe
        //cumplir los datos introducidos en los campos del formulario
        String patronUsuario = "[a-zA-Z0-9]+";
        String patronContrasena = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}";
        String patronCorreo = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        String patronNombre = "[a-zA-Z ]+";//^[A-Za-z ]*$

        Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
        Border border1 = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
        
        //Validaciones campos formulario
        if (usu.isEmpty() || password.isEmpty() || correo.isEmpty() || nombreCompleto.isEmpty()) {

            if (usu.isEmpty()) {
                error_usuario.setText("El campo usuario esta en blanco");
                usuario.setBorder(border);
            } else if (!usu.matches(patronUsuario)) {
                error_usuario.setText("El nombre de usuario solo puede contener "
                        + "letras y numeros");
                usuario.setBorder(border);
            } else {
                error_usuario.setText("");
                usuario.setBorder(border1);
            }

            if (password.isEmpty()) {
                error_contrasena.setText("El campo contraseña esta en blanco");
                contrasena.setBorder(border);
            } else if (!password.matches(patronContrasena) || (password.length() > 8 || password.length() < 8)) {
                error_contrasena.setText("La contraseña tiene que tener 8 caracteres "
                        + " \nentre los cuales 1 letra mayuscula, minuscula, numero "
                        + " \ny 1 caracter especial");
                contrasena.setBorder(border);
            } else {
                error_contrasena.setText("");
                contrasena.setBorder(border1);
            }

            if (correo.isEmpty()) {
                error_correo.setText("El campo email esta en blanco");
                email.setBorder(border);
            } else if (!correo.matches(patronCorreo)) {
                error_correo.setText("El correo introducido no es valido");
                email.setBorder(border);
            } else {
                error_correo.setText("");
                email.setBorder(border1);
            }

            if (nombreCompleto.isEmpty()) {
                error_nombre.setText("El campo nombre esta en blanco");
                nombre.setBorder(border);
            } else if (!nombreCompleto.matches(patronNombre)) {
                error_nombre.setText("El nombre solo puede contener letras");
                nombre.setBorder(border);
            } else {
                error_nombre.setText("");
                nombre.setBorder(border1);
            }

        } else {

            if (ControladorUsuarios.comprobarUsuario(usu)) {

                error_usuario.setText("Este usuario ya existe");
                usuario.setBorder(border);
                
            } else if (ControladorUsuarios.comprobarCorreo(correo)) {

                error_correo.setText("Este correo ya existe");
                email.setBorder(border);

            } else {

                Usuario nuevoUsuario = new Usuario(usu, password, correo, nombreCompleto);
                
                ControladorUsuarios.insertarUsuario(nuevoUsuario);

                //Limpia los campos del formulario
                limpiarCampos();
            }

        }
    }
    
    private void limpiarCampos() {

        usuario.clear();
        contrasena.clear();
        email.clear();
        nombre.clear();
    }

}
