package com.ieslapuebla.bloc.dao;

import java.sql.*;

public class ConexionBD {

    public static Connection crearConexion() {

        Connection conexion = null;

        try {

            String url = "jdbc:sqlite:bloc_notas.db";
            conexion = DriverManager.getConnection(url);

            crearTablas(conexion);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return conexion;
    }

    private static void crearTablas(Connection conexion) {

        String tablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios("
                + "id integer primary key AUTOINCREMENT,"
                + "usuario text,"
                + "contrasena text,"
                + "email text,"
                + "nombre text,"
                + "rol text"
                + ");";

        String tablaNotas = "CREATE TABLE IF NOT EXISTS notas("
                + "id_nota integer primary key AUTOINCREMENT,"
                + "nombre_nota text,"
                + "contenido text,"
                + "etiqueta text,"
                + "fecha_creacion text,"
                + "fecha_modificacion text,"
                + "archivada boolean default 0,"
                + "fijada boolean default 0,"
                + "usuario_id integer,"
                + "FOREIGN KEY(usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE"
                + ");";

        String tablaEtiquetas = "CREATE TABLE IF NOT EXISTS etiquetas("
                + "id integer primary key AUTOINCREMENT,"
                + "etiqueta text,"
                + "usuarioId integer,"
                + "FOREIGN KEY(usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE"
                + ");";

        String datosPrueba = ""
                + "INSERT INTO usuarios(id, usuario, contrasena, email, nombre, rol) VALUES(1, 'prueba', 'prueba', 'prueba@gmail.com', 'Prueba', 'usuario')"
                + "INSERT INTO usuarios(id, usuario, contrasena, email, nombre, rol) VALUES(2, 'admin', 'admin123', 'admin@gmail.com', 'Administrador', 'admin')";

        try {

            Statement crearTablaUsuarios = conexion.createStatement();
            crearTablaUsuarios.execute(tablaUsuarios);

            Statement crearTablaNotas = conexion.createStatement();
            crearTablaNotas.execute(tablaNotas);

            Statement crearTablaEtiquetas = conexion.createStatement();
            crearTablaEtiquetas.execute(tablaEtiquetas);

            /*PreparedStatement insertarDatosPrueba = conexion.prepareStatement(datosPrueba);
            insertarDatosPrueba.executeUpdate();*/

        } catch (SQLException e) {
            System.out.println("Error al crear las tablas");
            System.out.printf("ERROR: ", e.getMessage());
        }
    }
}
