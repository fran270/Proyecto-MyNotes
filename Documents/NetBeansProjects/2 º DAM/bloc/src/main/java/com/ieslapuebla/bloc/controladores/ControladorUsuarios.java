package com.ieslapuebla.bloc.controladores;

import com.ieslapuebla.bloc.modelos.Usuario;
import static com.ieslapuebla.bloc.utils.ConexionBD.conectarBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ControladorUsuarios {

    //TABLA USUARIOS
    //Metodo para comprobar si el usuario que inicia sesion esta en la bd
    public static boolean iniciarSesion(String usuario, String contrasena) {

        String sql = "SELECT * FROM usuarios WHERE usuario = ? and contrasena = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setString(1, usuario);
                consultaSelect.setString(2, contrasena);

                ResultSet resultadoConsulta = consultaSelect.executeQuery();

                if (resultadoConsulta.next()) {

                    while (resultadoConsulta.next()) {

                        int id = resultadoConsulta.getInt("id");
                        String usu = resultadoConsulta.getString("usuario");
                        String password = resultadoConsulta.getString("contrasena");
                       
                    }

                    return true;
                }

            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta");
                System.out.printf("ERROR: %s\n", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return false;
    }

    //Metodo para obtener el id del usuario que ha iniciado sesion
    public static int obtenerId(String usuario) {

        int id = 0;

        String sql = "SELECT id FROM usuarios WHERE usuario = ?";

        try (Connection conexion = conectarBD()) {

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

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s\n", e.getMessage());
        }

        return id;
    }

    //Metodo para insertar nuevo usuario
    public static void insertarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios(usuario, contrasena, email, nombre) VALUES(?, ?, ?, ?)";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaInsert = conexion.prepareStatement(sql)) {

                consultaInsert.setString(1, usuario.getUsuario());
                consultaInsert.setString(2, usuario.getContrasena());
                consultaInsert.setString(3, usuario.getCorreo());
                consultaInsert.setString(4, usuario.getNombre());

                int resultado = consultaInsert.executeUpdate();

                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "El usuario " + usuario.getUsuario() + " se ha insertado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha podido insertar el usuario", null, JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                System.out.println("Error al insertar el usuario");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la bd");
            System.out.printf("ERROR: %s", e.getMessage());
        }

    }

    //Metodo para comprobar si el usuario existe en la bd
    public static boolean comprobarUsuario(String usuario) {

        String sql = "SELECT usuario FROM usuarios WHERE usuario = ?";

        try (Connection conexion = conectarBD();) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setString(1, usuario);

                ResultSet resultado = consultaSelect.executeQuery();

                if (resultado.next()) {

                    while (resultado.next()) {

                        String nombreUsuario = resultado.getString("usuario");
                    }
                    return true;
                }

            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

    //Metodo para comprobar si el correo existe en la bd
    public static boolean comprobarCorreo(String email) {

        String sql = "SELECT email FROM usuarios WHERE email = ?";

        try (Connection conexion = conectarBD()) {

            try (PreparedStatement consultaSelect = conexion.prepareStatement(sql)) {

                consultaSelect.setString(1, email);

                ResultSet resultado = consultaSelect.executeQuery();

                if (resultado.next()) {

                    while (resultado.next()) {

                        String correo = resultado.getString("email");
                    }

                    return true;
                }

            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta");
                System.out.printf("ERROR: %s", e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error en la conexion con la base de datos");
            System.out.printf("ERROR: %s", e.getMessage());
        }

        return false;
    }

}
