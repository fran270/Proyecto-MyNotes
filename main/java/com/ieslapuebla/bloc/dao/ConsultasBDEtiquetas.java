package com.ieslapuebla.bloc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ConsultasBDEtiquetas {

    /**
     * Metodo para obtener las etiquetas creadas
     * @param conexion -> realiza la conexion con la bd
     * @param idUsuario -> id del usuario que recibe como parametro la consulta
     * @return etiquetas -> se guarda en una lista todas las etiquetas que haya creado
     */
    public static ArrayList<String> obtenerEtiquetas(Connection conexion, int idUsuario) {

        ArrayList<String> etiquetas = new ArrayList<>();

        String sql = "SELECT etiqueta FROM etiquetas WHERE usuarioId = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setInt(1, idUsuario);

            ResultSet resultado = consultaSelect.executeQuery();

            while (resultado.next()) {

                String etiqueta = resultado.getString("etiqueta");

                etiquetas.add(etiqueta);
            }

        } catch (SQLException e) {
            System.out.println("No se ha podido realizar la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return etiquetas;
    }
    
    /**
     * Metodo para guardar nuevas etiquetas en la bd
     * @param conexion -> conexion con la bd
     * @param etiqueta -> nombre de la etiqueta que se va a guardar
     * @param idUsuario -> el id del usuario que la ha creado
     */
    public static void añadirEtiqueta(Connection conexion, String etiqueta, int idUsuario) {

        String sql = "INSERT INTO etiquetas(etiqueta, usuarioId) VALUES(?, ?)";

        try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

            consultaInsert.setString(1, etiqueta);
            consultaInsert.setInt(2, idUsuario);

            int resultadoConsulta = consultaInsert.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "Se ha guardado la etiqueta");
            }

        } catch (SQLException e) {
            System.out.println("No se ha podido realizar la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }
}
