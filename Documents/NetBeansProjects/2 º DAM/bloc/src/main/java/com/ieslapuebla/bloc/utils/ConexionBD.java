package com.ieslapuebla.bloc.utils;

import java.sql.*;

public class ConexionBD {

    //Parametros de la conexion con la bd
    //Nombre de la base de datos
    private static final String BD_NAME = "bloc_notas";

    //Url de la conexion
    private static final String URL_CONEXION = "jdbc:mysql://localhost:3306/" + BD_NAME;

    //Driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //Usuario bd
    private static final String USUARIO = "root";

    //Contraseña bd
    private static final String CONTRASENA = "root";

    //Metodo para realizar con la bd
    public static Connection conectarBD() {

        Connection conexion = null;

        try {

            Class.forName(DRIVER);

            try {

                conexion = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENA);

            } catch (SQLException e) {

                System.out.println("Error en la conexion con la base de datos");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (ClassNotFoundException e) {

            System.out.println("No se ha encontrado el driver de la base de datos");
        }

        return conexion;
    }

}
