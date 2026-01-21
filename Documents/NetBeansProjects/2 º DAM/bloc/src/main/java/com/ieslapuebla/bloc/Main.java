
package com.ieslapuebla.bloc;

import com.ieslapuebla.bloc.utils.AbrirVentanas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Login.fxml"));
           
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bloc de Notas");
        primaryStage.getIcons().add(new Image(AbrirVentanas.class.getResource("/images/iniciar-sesion.png").toExternalForm()));
        primaryStage.show();
        
        //AbrirVentanas.cambiarVentana(AbrirVentanas.INICIO_SESION);
    }
    
    public static void main(String[] args) {
        launch();
    }
   
}
