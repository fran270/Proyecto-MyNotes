package com.ieslapuebla.bloc.funcionalidad;

import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.dao.ConsultasBDUsuarios;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.dao.ConexionBD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class OperacionesPapelera {

    private static final String CARPETA = "Papelera";

    // Metodo para crear la carpeta de la papelera
    public static boolean crearPapelera() {

        File carpeta = new File(CARPETA);

        if (carpeta.exists()) {
            return true;
        } else {
            boolean carpetaCreada = carpeta.mkdir();
            if (carpetaCreada) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    // Metodo que devuelve el nombre de la carpeta de la papelera
    public static String obtenerNombreCarpeta(){
        return CARPETA;
    }

    public static void restaurarNota(File fichero, String usuario) {

        String[] datosNota = null;
     
        // Abrimos y leemos el fichero para obtener los datos de la nota
        try (BufferedReader buffReader = new BufferedReader(new FileReader(fichero))) {

            String linea;

            while ((linea = buffReader.readLine()) != null) {
               
                datosNota = linea.split(",");
            }
            
            int idUsuario = ConsultasBDUsuarios.obtenerId(ConexionBD.crearConexion(), usuario);

            // Creamos un objeto Nota y le pasamos al constructor los datos de la nota
            Nota notaRestaurar = new Nota(datosNota[0], datosNota[1], datosNota[2], "", "", idUsuario);

            // Insertamos la nota en la tabla Notas
            if (ConsultasBDNotas.insertarNota(ConexionBD.crearConexion(), notaRestaurar)) {
                JOptionPane.showMessageDialog(null, "La nota ha sido restaurada");
            } else {
                JOptionPane.showMessageDialog(null, "Ha habido un problema al restaurar la nota", 
                        null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado");
            System.out.printf("ERROR: %s", e.getMessage());

        } catch (IOException e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }

    public static void borrarNota(String nota) {

        File notaBorrar = new File(nota);

        if (notaBorrar.delete()) {
            JOptionPane.showMessageDialog(null, "La nota se ha borrado");
        } else {
            JOptionPane.showMessageDialog(null, "No se ha podido borrar la nota", 
                    null, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static boolean verNotas(Connection conexion, ObservableList<Nota> notas, int idUsuario) {

        String sql = "SELECT * FROM notas WHERE usuario_id = ?";

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
}
