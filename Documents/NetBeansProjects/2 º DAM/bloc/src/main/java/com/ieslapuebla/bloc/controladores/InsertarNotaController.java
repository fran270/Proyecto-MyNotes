package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.modelos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InsertarNotaController implements Initializable {

   
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
    private TextField nombreNota;
    @FXML
    private Label errorNombreNota;
    @FXML
    private TextArea descripcionNota;
    @FXML
    private Label errorDescripcionNota;
    @FXML
    private TextField fecha;
    @FXML
    private Button botonGuardar;
    @FXML
    private ImageView iconoInsertar;
    @FXML
    private MenuButton etiqueta;
    @FXML
    private MenuItem etiqueta1;
    @FXML
    private MenuItem etiqueta2;
    @FXML
    private MenuItem etiqueta3;
    @FXML
    private MenuItem etiqueta4;
    
    private ObservableList<Nota> notas;
    private String usuario;
    private String tituloNota;
    private String contenidoNota;
    private String fechaCreacion;
    private int usuarioId;
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario = Usuario.getUsuario();
        usuarioConectado.setText(usuario);
        
        LocalDate fechaNota = LocalDate.now();
        fecha.setText(String.valueOf(fechaNota));
    }

    @FXML
    private void volverInicio(MouseEvent event) throws IOException {
        
        Stage stage = (Stage) opcion1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Portada.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void verNotas(MouseEvent event) throws IOException {

        Stage stage = (Stage) opcion2.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Notas.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void verPapelera(MouseEvent event) throws IOException {
        
        Stage stage = (Stage) opcion3.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Papelera.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void guardarNota(ActionEvent event) {

        tituloNota = nombreNota.getText();
        contenidoNota = descripcionNota.getText();
        fechaCreacion = fecha.getText();
        String etiquetaSeleccionada = etiqueta.getText();
        usuarioId = ControladorUsuarios.obtenerId(usuario);
        
        

        Border bordeRojo = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1)));
        Border bordeVerde = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))); 
        
        boolean notaExiste = ControladorNotas.comprobarNombreNota(tituloNota);

        if (tituloNota.isEmpty()) {
            
            errorNombreNota.setText("Introduzca el nombre de la nota");
            nombreNota.setBorder(bordeRojo);
       
        } else if (notaExiste) {
            
            errorNombreNota.setText("El nombre de la nota ya existe");
            nombreNota.setBorder(bordeRojo);

        } else if (contenidoNota.isEmpty()) {
            
            errorNombreNota.setText("");
            nombreNota.setBorder(bordeVerde);
            
            errorDescripcionNota.setText("Introduzca el contenido de la nota");
            descripcionNota.setBorder(bordeRojo);
            
        } 
        else {
            
            System.out.println(etiquetaSeleccionada);
            Nota notaInsertar = new Nota(tituloNota, contenidoNota, fechaCreacion, null, etiquetaSeleccionada, usuarioId);
           
            ControladorNotas.insertarNota(notaInsertar);
            
            limpiarCampos();
        }
    }
    
    @FXML
    private void seleccionarEtiqueta(MouseEvent event) {
        
        etiqueta1.setOnAction(e -> {
            etiqueta.setText(etiqueta1.getText());
        });
        
        etiqueta2.setOnAction(e -> {
            etiqueta.setText(etiqueta2.getText());
        });
        
        etiqueta3.setOnAction(e -> {
            etiqueta.setText(etiqueta3.getText());
        });
        
        etiqueta4.setOnAction(e -> {
            etiqueta.setText(etiqueta4.getText());
        });
    }
    
    public void limpiarCampos(){
        
        nombreNota.clear();
        descripcionNota.clear();
    }

    @FXML
    private void cerrarSesion(MouseEvent event) {
        
        Platform.exit();
        System.exit(0);
    }   
}
