
package com.ieslapuebla.bloc;

import com.ieslapuebla.bloc.utils.AbrirVentanas;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AbrirVentanas.cambiarVentana(AbrirVentanas.INICIO_SESION);
    }
    
    public static void main(String[] args) {
        launch();
    }
}
