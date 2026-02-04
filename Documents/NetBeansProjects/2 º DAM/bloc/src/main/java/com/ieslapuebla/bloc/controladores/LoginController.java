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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {

    @FXML
    private TextField usuario;
    @FXML
    private TextField contrasena;
    @FXML
    private Label errorUsuario;
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
    private void iniciarSesion(ActionEvent event) throws IOException {

        String nombreUsuario = usuario.getText();
        String password = contrasena.getText();

        Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));

        if (nombreUsuario.isEmpty() && password.isEmpty()) {
            errorUsuario.setText("El nombre de usuario no es valido");
            usuario.setBorder(border);

            errorContrasena.setText("La contraseña no es valida");
            contrasena.setBorder(border);

        } else if (nombreUsuario.isEmpty()) {
            errorUsuario.setText("El nombre de usuario no es valido");
            usuario.setBorder(border);

        } else if (password.isEmpty()) {
            errorContrasena.setText("La contraseña no es valida");
            contrasena.setBorder(border);

        } else if (ControladorUsuarios.iniciarSesion(nombreUsuario, password)) {

            Usuario usuario1 = new Usuario();
            usuario1.setUsuario(nombreUsuario);
            
            //if(rol == "admin"){
                // me dirige a la pantalla de inicio del administrador
            //}

            Stage stage = (Stage) botonAcceder.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bloc de Notas");
            stage.getIcons().add(new Image(PortadaController.class.getResource("/images/favicon.png").toExternalForm()));
            stage.show();

        } else {
            JOptionPane.showMessageDialog(null, "No estas registrado", "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    private void enlaceRegistro(MouseEvent event) throws IOException {

        Stage stage = (Stage) enlaceRegistro.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/FormularioRegistro.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registro");
        stage.getIcons().add(new Image(AbrirVentanas.class.getResource("/images/registro.png").toExternalForm()));
        stage.show();
    }
}
