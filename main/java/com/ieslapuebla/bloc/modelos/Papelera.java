package com.ieslapuebla.bloc.modelos;

import java.time.LocalDate;

public class Papelera {

    // Atributos
    private String fichero;
    private LocalDate fechaEliminacion;
    

    // Constructor de la clase
    public Papelera(String fichero) {
        
        this.fichero = fichero;
    }

    // Metodos getters and setters
    public String getFichero() {

        return fichero;
    }

    public LocalDate getFechaEliminacion() {

        return LocalDate.now();
    }
}
