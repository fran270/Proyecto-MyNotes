package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Nota;
import static com.ieslapuebla.bloc.utils.ConexionBD.conectarBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class ControladorNotas {

    //TABLA NOTAS
    public static ObservableList<Nota> obtenerNota(ObservableList<Nota> nota, int idNota){
        
        String sql = "SELECT * FROM notas WHERE id_nota = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {
                
                consultaSelect.setInt(1, idNota);

                ResultSet resultadoConsulta = consultaSelect.executeQuery();

                while (resultadoConsulta.next()) {

                    idNota = resultadoConsulta.getInt("id_nota");
                    String nombreNota = resultadoConsulta.getString("nombre_nota");
                    String contenido = resultadoConsulta.getString("descripcion");
                    String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                    String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");
                    
                    nota.add(new Nota(idNota, nombreNota, contenido, fechaCreacion, fechaModificacion));
                }

            } catch (SQLException e) {
                System.out.println("Error al obtener los datos de la nota");
                System.out.printf("ERROR: %s\n", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return nota;
    }
    
    
    public static int obtenerIdNota(int idNota) {

        String sql = "SELECT id_nota FROM notas WHERE id_nota = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {
                
                consultaSelect.setInt(1, idNota);

                ResultSet resultadoConsulta = consultaSelect.executeQuery();

                while (resultadoConsulta.next()) {

                    idNota = resultadoConsulta.getInt("id_nota");
                    System.out.println(idNota);
                }

            } catch (SQLException e) {
                System.out.println("Error al obtener el id de la nota");
                System.out.printf("ERROR: %s\n", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return idNota;
    }

    
    /*Operaciones CRUD*/
    
    //Metodo para obtener las notas que ha creado el usuario
    public static boolean verNotas(ObservableList<Nota> notas, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE usuarios_id = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setInt(1, idUsuario);

                ResultSet resultadoConsulta = consultaSelect.executeQuery();

                while (resultadoConsulta.next()) {

                    int idNota = resultadoConsulta.getInt("id_nota");
                    String nombreNota = resultadoConsulta.getString("nombre_nota");
                    String contenidoNota = resultadoConsulta.getString("descripcion");
                    String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                    String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");

                    notas.add(new Nota(idNota, nombreNota, contenidoNota, fechaCreacion, fechaModificacion));
                }

                return true;

            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

    //Metodo para crear una nueva nota
    public static void insertarNota(Nota nota) {

        String sql = "INSERT INTO notas(nombre_nota, descripcion, fecha_creacion, fecha_modificacion, usuarios_id) VALUES(?, ?, ?, ?, ?)";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

                consultaInsert.setString(1, nota.getNombreNota());
                consultaInsert.setString(2, nota.getContenido());
                consultaInsert.setString(3, nota.getFechaCreacion());
                consultaInsert.setString(4, nota.getFechaModificacion());
                consultaInsert.setInt(5, nota.getIdUsuario());

                int resultadoConsulta = consultaInsert.executeUpdate();

                if (resultadoConsulta > 0) {
                    JOptionPane.showMessageDialog(null, "La nota se ha guardado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido guardar la nota", null, JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                System.out.println("Error al guardar la nota");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    //Metodo para realizar cambios en la nota
    public static void modificarNota(Nota notaModificar) {

        String sql = "UPDATE notas SET nombre_nota = ?, descripcion = ?, fecha_modificacion = ? WHERE id_nota = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaUpdate = conexion.prepareStatement(sql)) {

                consultaUpdate.setString(1, notaModificar.getNombreNota());
                consultaUpdate.setString(2, notaModificar.getContenido());
                consultaUpdate.setString(3, notaModificar.getFechaModificacion());
                consultaUpdate.setInt(4, notaModificar.getIdNota());

                int resultadoConsulta = consultaUpdate.executeUpdate();

                if (resultadoConsulta > 0) {
                    JOptionPane.showMessageDialog(null, "Se ha modificado la nota");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido modificar la nota", null, JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                System.out.println("Error al modificar la nota");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    //Metodo que elimina la nota a traves del id
    public static void eliminarNota(int idNota) {

        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estas seguro que quieres borrar esta nota "+idNota+"?",
                "Mensaje de confirmacion", JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirmacion == 0) {

            String sql = "DELETE FROM notas WHERE id_nota = ?";

            try (Connection conexion = conectarBD()) {

                try (PreparedStatement consultaDelete = conexion.prepareStatement(sql)) {

                    consultaDelete.setInt(1, idNota);

                    int resultadoConsulta = consultaDelete.executeUpdate();

                    if (resultadoConsulta > 0) {
                        JOptionPane.showMessageDialog(null, "La nota se ha borrado correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha podido borrar la nota", "Error de borrado", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException e) {
                    System.out.println("Error al borrar la nota");
                    System.out.printf("ERROR: %s", e.getMessage());
                }

            } catch (SQLException e) {
                System.out.println("Error en la conexion con la bd");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        }

    }

    //Metodo que comprueba si existe el nombre de la nota
    public static boolean comprobarNombreNota(String nombreNota) {

        String sql = "SELECT nombre_nota FROM notas WHERE nombre_nota = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setString(1, nombreNota);

                ResultSet resultado = consultaSelect.executeQuery();

                if (resultado.next()) {

                    while (resultado.next()) {

                        nombreNota = resultado.getString("nombre_nota");
                    }

                    return true;
                }

            } catch (SQLException e) {
                System.out.println("No se ha podido ejecutar la consulta");
                System.out.printf("Error: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("Error: %s", e.getMessage());
        }

        return false;
    }

}
