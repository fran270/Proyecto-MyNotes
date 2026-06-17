package com.ieslapuebla.bloc.funcionalidad;

import com.ieslapuebla.bloc.modelos.Nota;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class ExportarNota {

    // Metodo para exportar nota en formato json
    public static void exportarJSON(String archivo, Nota notaExportar) {
        // Creamos el fichero con el nombre que recibe el constructor como parametro
        File fichero = new File(archivo);

        // Abrimos el fichero y escribimos su contenido
        try (PrintWriter pw = new PrintWriter(new FileWriter(fichero))) {

            JSONObject jsonObject = new JSONObject();

            // Escribimos el contenido que se va a almacenar en el fichero
            jsonObject.put("Nota", notaExportar.getNombreNota());
            jsonObject.put("Contenido", notaExportar.getContenido());
            jsonObject.put("Etiqueta", notaExportar.getEtiqueta());
            jsonObject.put("Fecha Creacion", notaExportar.getFechaCreacion());
            jsonObject.put("Fecha Modificacion", notaExportar.getFechaModificacion());

            String jsonString = jsonObject.toString();

            pw.write(jsonString);

            JOptionPane.showMessageDialog(null, "El fichero se ha exportado correctamente");

        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el fichero");
            System.out.printf("ERROR: %s", e.getMessage());

        } catch (IOException e) {
            System.out.println("Ha ocurrido un error");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }

    // Metodo para exportat nota en formato csv
    public static void exportarCSV(String archivo, Nota notaExportar) {
        
        String fechaActualizacion = notaExportar.getFechaModificacion();

        // Creamos el fichero
        File fichero = new File(archivo);

        // Abrimos el fichero y escribimos su contenido
        try (PrintWriter escribirFichero = new PrintWriter(new FileWriter(fichero))) {

            escribirFichero.write("Nota, Contenido, Etiqueta, FechaCreacion, FechaModificacion");

            escribirFichero.println();

            escribirFichero.write(notaExportar.getNombreNota() + ",");

            String contenidoNota = notaExportar.getContenido().replace("\n", " ");

            escribirFichero.write(contenidoNota + ",");
            escribirFichero.write(notaExportar.getEtiqueta() + ",");
            escribirFichero.write(notaExportar.getFechaCreacion());

            if (fechaActualizacion != null) {

                escribirFichero.write("," + fechaActualizacion + "\n");
            }

            JOptionPane.showMessageDialog(null, "El fichero se ha exportado correctamente");

        } catch (IOException e) {
            System.out.println("Error al abrir el fichero");
            System.out.printf("ERROR: %s", e.getMessage());
        }
    }
}
