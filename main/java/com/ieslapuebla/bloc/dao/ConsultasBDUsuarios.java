package com.ieslapuebla.bloc.dao;

import com.ieslapuebla.bloc.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class ConsultasBDUsuarios {

    public static ObservableList<Usuario> obtenerUsuario(Connection conexion,
            ObservableList<Usuario> usuario, int idUsuario) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setInt(1, idUsuario);

            ResultSet resultadoConsulta = consultaSelect.executeQuery();

            while (resultadoConsulta.next()) {

                String usu = resultadoConsulta.getString("usuario");
                String contrasena = resultadoConsulta.getString("contrasena");
                String email = resultadoConsulta.getString("email");
                String nombre = resultadoConsulta.getString("nombre");

                usuario.add(new Usuario(usu, contrasena, email, nombre));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la nota");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return usuario;
    }

    /**
     * Metodo para comprobar si el usuario que inicia sesion esta en la bd
     *
     * @param conexion
     * @param usuario
     * @param contrasena
     * @return usuario -> si el usuario esta registrado o "" -> si no esta registrado
     */
    public static String iniciarSesion(Connection conexion, String usuario, String contrasena) {
        
        String sql = "SELECT * FROM usuarios WHERE usuario = ? and contrasena = ?";
       
        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, usuario);

            String contrasenaEncriptada = ConsultasBDUsuarios.obtenerContrasena(conexion, usuario);

            // Comprobar que la contraseña existe
            if (!contrasenaEncriptada.equals("")) {
                
                // Comprobar si la contraseña introducida coincide con la encriptada
                if (BCrypt.checkpw(contrasena, contrasenaEncriptada)) {

                    consultaSelect.setString(2, contrasena);
                    
                    ResultSet resultadoConsulta = consultaSelect.executeQuery();

                    while (resultadoConsulta.next()) {
                        
                        resultadoConsulta.getString("usuario");
                        resultadoConsulta.getString("contrasena");
                    }
                    
                    return usuario;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }
        // Devuelve cadena vacia si no existe el usuario
        return "";
    }

    /**
     * Metodo para obtener el id del usuario que ha iniciado sesion
     *
     * @param conexion
     * @param usuario
     * @return
     */
    public static int obtenerId(Connection conexion, String usuario) {

        int id = 0;

        String sql = "SELECT id FROM usuarios WHERE usuario = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, usuario);

            ResultSet resultadoConsulta = consultaSelect.executeQuery();

            while (resultadoConsulta.next()) {

                id = resultadoConsulta.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return id;
    }

    public static String obtenerContrasena(Connection conexion, String usuario) {

        String contrasena = "";

        String sql = "SELECT contrasena FROM usuarios WHERE usuario = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, usuario);

            ResultSet resultadoConsulta = consultaSelect.executeQuery();

            while (resultadoConsulta.next()) {

                contrasena = resultadoConsulta.getString("contrasena");
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return contrasena;
    }

    /**
     * Metodo para comprobar si un usuario existe en la bd
     *
     * @param conexion -> realiza la conexion con la bd
     * @param usuario -> nombre de usuario que recibe como parametro la consulta
     * para comprobar si existe
     * @return true -> si existe o false -> si no existe
     */
    public static boolean comprobarUsuario(Connection conexion, String usuario) {

        String sql = "SELECT usuario FROM usuarios WHERE usuario = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, usuario);

            ResultSet resultado = consultaSelect.executeQuery();

            if (resultado.next()) {

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

    /**
     * Metodo para comprobar si una direccion de correo existe en la bd
     *
     * @param conexion -> realiza la conexion con la bd
     * @param email -> email que recibe como parametro la consulta para
     * comprobar si existe
     * @return true -> si el email ya existe o false -> si el email no existe
     */
    public static boolean comprobarCorreo(Connection conexion, String email) {

        String sql = "SELECT email FROM usuarios WHERE email = ?";

        try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

            consultaSelect.setString(1, email);

            ResultSet resultado = consultaSelect.executeQuery();

            if (resultado.next()) {

                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

    /**
     * Metodo para obtener todos los usuarios registrados
     *
     * @param conexion -> realiza la conexion con la bd
     * @param usuarios -> lista vacia que va a contener objetos tipo Usuario
     * @return usuarios -> devuelve una lista con todos los usuarios que estan
     * registrados
     */
    public static ObservableList<Usuario> obtenerUsuarios(Connection conexion, ObservableList<Usuario> usuarios) {

        String sql = "SELECT * FROM usuarios";

        try (Statement consultaSelect = conexion.createStatement()) {

            ResultSet resultadoConsulta = consultaSelect.executeQuery(sql);

            while (resultadoConsulta.next()) {

                int id = resultadoConsulta.getInt("id");
                String usuario = resultadoConsulta.getString("usuario");
                String contrasena = resultadoConsulta.getString("contrasena");
                String correo = resultadoConsulta.getString("email");
                String nombre = resultadoConsulta.getString("nombre");

                usuarios.add(new Usuario(id, usuario, contrasena, correo, nombre));
            }

        } catch (SQLException e) {
            System.out.println("Se ha producido un error en la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return usuarios;
    }

    /**
     * Metodo para insertar un nuevo usuario en la bd
     *
     * @param conexion -> realiza la conexion con la bd
     * @param usuario -> Objeto de clase Usuario que contiene los datos del
     * usuario
     */
    public static void insertarUsuario(Connection conexion, Usuario usuario) {

        String sql = "INSERT INTO usuarios(usuario, contrasena, email, nombre) VALUES(?, ?, ?, ?)";

        try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

            consultaInsert.setString(1, usuario.getUsuario());
            consultaInsert.setString(2, BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
            consultaInsert.setString(3, usuario.getCorreo());
            consultaInsert.setString(4, usuario.getNombre());

            int resultado = consultaInsert.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "El usuario "
                        + usuario.getUsuario() + " se ha guardado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido guardar el "
                        + "usuario", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

    }

    /**
     * Metodo para modificar usuario
     *
     * @param conexion -> realiza la conexion con la bd
     * @param usuarioModificar -> Objeto de la clase Usuario que contiene los
     * datos actualizados del usuario
     */
    public static void modificarUsuario(Connection conexion, Usuario usuarioModificar) {

        String sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, email = ?, nombre = ? WHERE id = ?";

        try (PreparedStatement consultaUpdate = conexion.prepareStatement(sql)) {

            consultaUpdate.setString(1, usuarioModificar.getUsuario());
            consultaUpdate.setString(2, usuarioModificar.getContrasena());
            consultaUpdate.setString(3, usuarioModificar.getCorreo());
            consultaUpdate.setString(4, usuarioModificar.getNombre());
            consultaUpdate.setInt(5, usuarioModificar.getId());

            int resultadoConsulta = consultaUpdate.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "Los cambios se han guardado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido guardar los cambios", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al realizar la consulta");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    /**
     * Metodo para eliminar un usuario
     *
     * @param conexion -> realiza la conexion con la bd
     * @param id -> id del usuario que recibe como parametro la consulta
     */
    public static void eliminarUsuario(Connection conexion, int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement consultaDelete = conexion.prepareStatement(sql)) {

            consultaDelete.setInt(1, id);

            int resultadoConsulta = consultaDelete.executeUpdate();

            if (resultadoConsulta > 0) {
                JOptionPane.showMessageDialog(null, "El usuario se ha eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido borrar el usuario", "Error de borrado de usuario", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error en la consulta");
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}
