package com.ieslapuebla.bloc.dao;

import com.ieslapuebla.bloc.modelos.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;

public class ConsultasBDNotas {

    // TABLA NOTAS
    public static int obtenerIdNota(Connection conexion, int idNota) {

        String sql = "SELECT id_nota FROM notas WHERE id_nota = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setInt(1, idNota);

            ResultSet resultadoConsulta = consultaSelect.executeQuery();

            while (resultadoConsulta.next()) {

                idNota = resultadoConsulta.getInt("id_nota");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el id de la nota");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return idNota;
    }

    /**
     * Metodo que comprueba si existe el nombre de una nota en la bd
     *
     * @param conexion
     * @param nombreNota
     * @param idUsuario
     * @return
     */
    public static String comprobarNombreNota(Connection conexion, String nombreNota, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE nombre_nota = ? and usuario_id = ?";

        String nota = "";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, nombreNota);
            consultaSelect.setInt(2, idUsuario);

            ResultSet resultado = consultaSelect.executeQuery();

            while (resultado.next()) {

                nota = resultado.getString("nombre_nota");
            }

        } catch (SQLException e) {
            System.out.println("No se ha podido ejecutar la consulta");
            System.out.printf("Error: %s", e.getMessage());
        }

        return nota;
    }

    /**
     * Metodo para obtener las notas que ha creado el usuario
     *
     * @param conexion -> realiza la conexion con la bd
     * @param notas -> lista donde se guarda las notas que se van a cargar en la
     * tabla
     * @param idUsuario -> parametro de la consulta para obtener las notas de
     * ese usuario
     * @return true o false
     */
    public static boolean verNotas(Connection conexion, ObservableList<Nota> notas, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE usuario_id = ? and archivada = false";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setInt(1, idUsuario);

            ResultSet resultadoConsulta = consultaSelect.executeQuery();

            while (resultadoConsulta.next()) {

                int idNota = resultadoConsulta.getInt("id_nota");
                String nombreNota = resultadoConsulta.getString("nombre_nota");
                String contenidoNota = resultadoConsulta.getString("contenido");
                String etiqueta = resultadoConsulta.getString("etiqueta");
                String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");

                notas.add(new Nota(idNota, nombreNota, contenidoNota, etiqueta, fechaCreacion, fechaModificacion));
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

    /**
     * Metodo que guarda una nueva nota en la bd
     *
     * @param conexion -> realiza la conexion con la bd
     * @param nota -> Objeto de la clase Nota que contiene los datos de la nota
     * @return
     */
    public static boolean insertarNota(Connection conexion, Nota nota) {

        String sql = "INSERT INTO notas(nombre_nota, contenido, fecha_creacion,"
                + "fecha_modificacion, etiqueta, archivada, usuario_id)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

            consultaInsert.setString(1, nota.getNombreNota());
            consultaInsert.setString(2, nota.getContenido());
            consultaInsert.setString(3, nota.getFechaCreacion());
            consultaInsert.setString(4, nota.getFechaModificacion());
            consultaInsert.setString(5, nota.getEtiqueta());
            consultaInsert.setBoolean(6, false);
            consultaInsert.setInt(7, nota.getIdUsuario());

            int resultadoConsulta = consultaInsert.executeUpdate();

            if (resultadoConsulta > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Se ha producido un error en la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return false;
    }

    /**
     * Metodo para guardar cambios de la nota
     *
     * @param conexion -> realiza la conexion con la bd
     * @param notaModificar -> objeto de la clase Nota que contiene los datos
     * actualizados de la nota
     * @param mensaje -> muestra un mensaje de confirmacion
     */
    public static void modificarNota(Connection conexion, Nota notaModificar, Label mensaje) {

        String sql = "UPDATE notas SET nombre_nota = ?, contenido = ?, "
                + "fecha_modificacion = ?, etiqueta = ? WHERE id_nota = ?";

        try (PreparedStatement consultaUpdate = conexion.prepareStatement(sql)) {

            consultaUpdate.setString(1, notaModificar.getNombreNota());
            consultaUpdate.setString(2, notaModificar.getContenido());
            consultaUpdate.setString(3, notaModificar.getFechaModificacion());
            consultaUpdate.setString(4, notaModificar.getEtiqueta());
            consultaUpdate.setInt(5, notaModificar.getIdNota());

            System.out.println(notaModificar.getIdNota());

            int resultadoConsulta = consultaUpdate.executeUpdate();

            if (resultadoConsulta > 0) {
                mensaje.setText("Se han guardado los cambios");
            } else {
                mensaje.setText("No se han podido guardar los cambios");
            }

        } catch (SQLException e) {
            System.out.println("Se ha producido un error en la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
    }

    /**
     * Metodo que elimina una nota mediante su id
     *
     * @param conexion -> realiza la conexion con la bd
     * @param idNota -> id de la nota que recibe como parametro la consulta
     */
    public static void eliminarNota(Connection conexion, int idNota) {

        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estas seguro que quieres borrar esta nota?",
                "Mensaje de confirmacion", JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirmacion == 0) {

            String sql = "DELETE FROM notas WHERE id_nota = ?";

            try (PreparedStatement consultaDelete = conexion.prepareStatement(sql)) {

                consultaDelete.setInt(1, idNota);

                int resultadoConsulta = consultaDelete.executeUpdate();

                if (resultadoConsulta > 0) {
                    JOptionPane.showMessageDialog(null, "La nota se ha borrado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido borrar la nota",
                            "Error de borrado", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                System.out.println("Se ha producido un error en la consulta");
                System.out.printf("ERROR: %s\n", e.getMessage());
            }
        }

    }

    /* FILTROS NOTAS MEDIANTE FECHA */
    /**
     * Metodo que filtra notas mediante su fecha de creacion
     *
     * @param conexion
     * @param notas
     * @param notaFiltrar
     * @return notas
     */
    public static ObservableList<Nota> filtrarNotaFecha(Connection conexion, ObservableList<Nota> notas,
            Nota notaFiltrar) {

        String sql = "SELECT * FROM notas WHERE nombre_nota LIKE ? and contenido LIKE ? and fecha_creacion <= DATE('now')";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, "%" + notaFiltrar.getNombreNota() + "%");
            consulta.setString(2, "%" + notaFiltrar.getContenido() + "%");

            ResultSet resultadoConsulta = consulta.executeQuery();

            while (resultadoConsulta.next()) {

                int idNota = resultadoConsulta.getInt("id_nota");
                String nota = resultadoConsulta.getString("nombre_nota");
                String contenido = resultadoConsulta.getString("contenido");
                String etiqueta = resultadoConsulta.getString("etiqueta");
                String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");

                notas.add(new Nota(idNota, nota, contenido, etiqueta, fechaCreacion, fechaModificacion));
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return notas;
    }

    /* FILTROS NOTAS MEDIANTE ETIQUETA */
    /**
     * Metodo que filtra notas mediante etiqueta
     *
     * @param conexion
     * @param notas
     * @param notaFiltrar
     * @return
     */
    public static ObservableList<Nota> filtrarNotaEtiqueta(Connection conexion, ObservableList<Nota> notas,
            Nota notaFiltrar) {

        String sql = "SELECT * FROM notas WHERE nombre_nota LIKE ? and contenido LIKE ? and etiqueta = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, "%" + notaFiltrar.getNombreNota() + "%");
            consulta.setString(2, "%" + notaFiltrar.getContenido() + "%");
            consulta.setString(3, notaFiltrar.getEtiqueta());

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                int idNota = resultado.getInt("id_nota");
                String nota = resultado.getString("nombre_nota");
                String contenido = resultado.getString("contenido");
                String etiquetaNota = resultado.getString("etiqueta");
                String fechaCreacion = resultado.getString("fecha_creacion");
                String fechaModificacion = resultado.getString("fecha_modificacion");

                notas.add(new Nota(idNota, nota, contenido, etiquetaNota, fechaCreacion, fechaModificacion));
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return notas;
    }

    public static ObservableList<Nota> verNotasImportantes(Connection conexion, ObservableList<Nota> notas, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE fijada = true and usuario_id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuario);

            ResultSet resultadoConsulta = consulta.executeQuery();

            while (resultadoConsulta.next()) {

                String nombreNota = resultadoConsulta.getString("nombre_nota");
                String contenidoNota = resultadoConsulta.getString("contenido");
                String etiqueta = resultadoConsulta.getString("etiqueta");
                String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");
                int usuarioId = resultadoConsulta.getInt("usuario_id");

                notas.add(new Nota(nombreNota, contenidoNota, etiqueta, fechaCreacion, fechaModificacion, usuarioId));
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return notas;
    }

    /**
     * Metodo que fija las notas importantes
     *
     * @param conexion
     * @param idNota
     */
    public static void fijarNota(Connection conexion, int idNota) {

        String sql = "UPDATE notas SET fijada = true WHERE id_nota = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idNota);

            int resultadoConsulta = consulta.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "Nota fijada");
            } else {
                System.out.println("No se ha podido fijar la nota");
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
    }

    /**
     * Metodo que obtiene las notas archivadas
     *
     * @param conexion
     * @param notas
     * @param idUsuario
     * @return notas
     */
    public static ObservableList<Nota> verNotasArchivadas(Connection conexion, ObservableList<Nota> notas, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE archivada = true and usuario_id = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuario);

            ResultSet resultadoConsulta = consulta.executeQuery();

            while (resultadoConsulta.next()) {

                String nombreNota = resultadoConsulta.getString("nombre_nota");
                String contenidoNota = resultadoConsulta.getString("contenido");
                String etiqueta = resultadoConsulta.getString("etiqueta");
                String fechaCreacion = resultadoConsulta.getString("fecha_creacion");
                String fechaModificacion = resultadoConsulta.getString("fecha_modificacion");
                int usuarioId = resultadoConsulta.getInt("usuario_id");

                notas.add(new Nota(nombreNota, contenidoNota, etiqueta, fechaCreacion, fechaModificacion, usuarioId));
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return notas;
    }

    /**
     * Metodo que archiva las notas no usadas
     *
     * @param conexion -> realiza la conexion con la bd
     * @param idNota -> parametro de la consulta
     */
    public static void archivarNota(Connection conexion, int idNota) {

        String sql = "UPDATE notas SET archivada = true WHERE id_nota = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idNota);

            int resultadoConsulta = consulta.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "Nota archivada");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido archivar la nota", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
    }

    /**
     * Metodo para desarchivar una nota
     *
     * @param conexion -> realiza la conexion con la bd
     * @param nombreNota -> parametro de la consulta
     */
    public static void desarchivarNota(Connection conexion, String nombreNota) {

        String sql = "UPDATE notas SET archivada = false WHERE nombre_nota = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nombreNota);

            int resultadoConsulta = consulta.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "Nota desarchivada");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido desarchivar la nota", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al preparar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
    }
}
