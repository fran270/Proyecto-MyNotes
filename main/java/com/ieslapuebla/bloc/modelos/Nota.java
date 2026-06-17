package com.ieslapuebla.bloc.modelos;

public class Nota {

    //Atributos
    private int idNota;
    private String nombreNota;
    private String contenido;
    private String etiqueta;
    private String fechaCreacion;
    private String fechaModificacion;
    private int idUsuario;

    private boolean notaArchivada;
    private boolean notaImportante;

    // Constructor de la clase
    public Nota(int idNota, String nombreNota, String contenido, String etiqueta,
            String fechaCreacion, String fechaModificacion) {

        this.idNota = idNota;
        this.nombreNota = nombreNota;
        this.contenido = contenido;
        this.etiqueta = etiqueta;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Nota(String nombreNota, String contenido, String etiqueta, String fechaCreacion,
            String fechaModificacion, int idUsuario) {

        this.nombreNota = nombreNota;
        this.contenido = contenido;
        this.etiqueta = etiqueta;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.idUsuario = idUsuario;
    }

    //Metodos getters y setters
    public int getIdNota() {

        return idNota;
    }

    public void setIdNota(int idNota) {

        this.idNota = idNota;
    }

    public String getNombreNota() {

        return nombreNota;
    }

    public void setNombreNota(String nombreNota) {

        this.nombreNota = nombreNota;
    }

    public String getContenido() {

        return contenido;
    }

    public void setContenido(String contenido) {

        this.contenido = contenido;
    }

    public String getEtiqueta() {

        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {

        this.etiqueta = etiqueta;
    }

    public String getFechaCreacion() {

        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {

        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaModificacion() {

        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {

        this.fechaModificacion = fechaModificacion;
    }

    public boolean getFijada() {
        return notaImportante;
    }

    public void setFijada(boolean notaImportante) {
        this.notaImportante = notaImportante;
    }

    public boolean getArchivada() {
        return notaArchivada;
    }

    public void setArchivada(boolean notaArchivada) {
        this.notaArchivada = notaArchivada;
    }

    public int getIdUsuario() {

        return idUsuario;
    }
}
