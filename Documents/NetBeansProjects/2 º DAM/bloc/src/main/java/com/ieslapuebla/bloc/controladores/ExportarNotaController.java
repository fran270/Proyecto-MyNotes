
package com.ieslapuebla.bloc.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class ExportarNotaController implements Initializable {

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

   
    @FXML
    private void verInicio(MouseEvent event) {
    }

    @FXML
    private void verNotas(MouseEvent event) {
    }

    @FXML
    private void verPapelera(MouseEvent event) {
    }
    
    @FXML
    private void cerrarSesion(MouseEvent event) {
    }

    private void exportarNotaJSON(){
        
    }
    
    private void exportarNotaCSV(){
        
    }
}
