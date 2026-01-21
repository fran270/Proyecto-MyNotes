
package com.ieslapuebla.bloc.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AbrirVentanas {
    
    public static final String INICIO_SESION = "/fxmls/Login.fxml";
    public static final String USUARIO = "/fxmls/";
    
    public static void cambiarVentana(String rutaFXML){
        
        try {
            
            Parent root = FXMLLoader.load(AbrirVentanas.class.getResource(rutaFXML));
            Scene scene = new Scene(root);
           
            Stage stage = new Stage(); 
            stage.setScene(scene);
            stage.setTitle("Iniciar Sesion");//Nombre de la interfaz
            stage.getIcons().add(new Image(AbrirVentanas.class.getResource("/images/iniciar-sesion.png").toExternalForm()));//Añadir un icono al borde de la interfaz
            stage.centerOnScreen();//Centra la interfaz en el centro de la pantalla
            stage.show();//Muestra la interfaz
           
        } catch(IOException e){
            
            System.out.println("Error al cambiar la ventana "+rutaFXML);
        }
    }
    
}
