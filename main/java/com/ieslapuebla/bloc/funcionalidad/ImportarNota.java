package com.ieslapuebla.bloc.funcionalidad;

import com.ieslapuebla.bloc.dao.ConsultasBDNotas;
import com.ieslapuebla.bloc.modelos.Nota;
import com.ieslapuebla.bloc.dao.ConexionBD;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class ImportarNota {

    public static void importarFicheroJson(String rutaFichero, int idUsuario) {

        //Recorrer el fichero json con BufferedReader
        try (BufferedReader leerFichero = new BufferedReader(new FileReader(rutaFichero))) {

            String linea;

            StringBuilder datos = new StringBuilder();

            // Leemos las lineas del fichero
            while ((linea = leerFichero.readLine()) != null) {
                datos.append(linea);
            }

            JSONObject notas = recorrerNotasArray(datos);

            String cadena = obtenerDatosNota(notas);

            Nota notaImportar = obtenerNota(cadena, idUsuario);

            // Invocar al metodo que inserta la nota en la bd
            boolean notaImportada = ConsultasBDNotas.insertarNota(ConexionBD.crearConexion(), notaImportar);

            if (notaImportada) {
                JOptionPane.showMessageDialog(null, "La nota se ha importado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido importar la nota", null, JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException ex) {
            System.out.println("Error al leer el fichero");
            System.out.printf("ERROR: %s", ex.getMessage());
        }
    }

    public static void importarFicheroCSV(String rutaFichero, int idUsuario) {

        // Leer el fichero CSV con BufferedReader
        try (BufferedReader buffReader = new BufferedReader(new FileReader(rutaFichero))) {

            String linea;
            String cadena = "";

            while ((linea = buffReader.readLine()) != null) {

                cadena = cadena + linea;
            }

            String[] datosNota = cadena.split(",");
            String nombreNota = datosNota[0];
            String contenido = datosNota[1];
            String etiqueta = datosNota[2];
            String fechaCreacion = datosNota[3];
            String fechaModificacion = datosNota[4];

            Nota notaImportar = new Nota(nombreNota, contenido, etiqueta, fechaCreacion, fechaModificacion, idUsuario);

            ConsultasBDNotas.insertarNota(ConexionBD.crearConexion(), notaImportar);

            JOptionPane.showMessageDialog(null, "La nota se ha importado correctamente");

        } catch (IOException e) {

            System.out.println("Error al leer el fichero");
            System.out.printf("Error: %s", e.getMessage());
        }
    }

    private static Nota obtenerNota(String cadena, int idUsuario) {

        String[] datosNota = cadena.split(",");

        // Guardamos en variables los datos de la nota
        String etiqueta = datosNota[0];
        String contenidoNota = datosNota[1];
        String fechaModificacion = datosNota[2];
        String fechaCreacion = datosNota[3];
        String nombreNota = datosNota[4];

        // Cremos un objeto Nota y le pasamos al constructor los datos 
        Nota notaImportar = new Nota(nombreNota, contenidoNota, etiqueta, fechaCreacion, fechaModificacion, idUsuario);

        return notaImportar;
    }

    private static JSONObject recorrerNotasArray(StringBuilder datos) {

        JSONArray jsonArray = new JSONArray(datos.toString());
        JSONObject jsonObject = new JSONObject();

        if (jsonArray.length() > 1) {

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
            }

        } else {
            jsonObject = jsonArray.getJSONObject(0);
        }

        return jsonObject;
    }

    private static String obtenerDatosNota(JSONObject notas) {

        String cadena = "";

        Iterator<String> claves = notas.keys();

        // Extraer los datos de la nota
        for (int i = 0; i < notas.length(); i++) {

            String clave = claves.next();
            Object valor = notas.get(clave);

            cadena = cadena + valor + ",";
        }

        return cadena;
    }

}
