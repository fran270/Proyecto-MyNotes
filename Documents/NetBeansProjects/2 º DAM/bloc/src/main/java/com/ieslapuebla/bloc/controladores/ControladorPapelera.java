package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import static com.ieslapuebla.bloc.utils.ConexionBD.conectarBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class ControladorPapelera {

    public static ObservableList<Nota> verNotasPapelera(ObservableList<Nota> nota, int idUsuario) {

        String sql = "SELECT * FROM papelera WHERE usuarios_id = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setInt(1, idUsuario);

                ResultSet resultadoConsulta = consultaSelect.executeQuery();

                while (resultadoConsulta.next()) {

                    int idNota = resultadoConsulta.getInt("id_nota");
                    String nombreNota = resultadoConsulta.getString("nombre_nota");
                    String contenido = resultadoConsulta.getString("contenido");
                    String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                    String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");

                    nota.add(new Nota(idNota, nombreNota, contenido, fechaCreacion, fechaModificacion));
                }

            } catch (SQLException e) {
                System.out.println("No se ha podido ejecutar la consulta");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return nota;
    }

    public static void moverNotaPapelera(Nota notaEliminar, int idUsuario) {

        String sql = "INSERT INTO papelera VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

                consultaInsert.setInt(1, notaEliminar.getIdNota());
                consultaInsert.setString(2, notaEliminar.getNombreNota());
                consultaInsert.setString(3, notaEliminar.getContenido());
                consultaInsert.setString(4, notaEliminar.getFechaCreacion());
                consultaInsert.setString(5, notaEliminar.getFechaModificacion());
                consultaInsert.setInt(6, idUsuario);

                int resultadoConsulta = consultaInsert.executeUpdate();

                if (resultadoConsulta > 0) {
                    JOptionPane.showMessageDialog(null, "La nota se ha movido a la papelera");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido mover la nota a la papelera");
                }

            } catch (SQLException e) {
                System.out.println("No se ha podido realizar la consulta");
                System.out.println("Error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Se ha producido un error en la conexion con la bd");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void eliminarNotaPapelera(int idNota) {

        String sql = "DELETE FROM papelera WHERE id_nota = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaDelete = conexion.prepareStatement(sql)) {

                consultaDelete.setInt(1, idNota);

                int resultadoConsulta = consultaDelete.executeUpdate();

                if (resultadoConsulta > 0) {
                    JOptionPane.showMessageDialog(null, "La nota ha sido eliminada correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido borrar la nota");
                }
                
            } catch (SQLException e) {
                System.out.println("No se ha podido realizar la consulta");
                System.out.println("ERROR: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

}
