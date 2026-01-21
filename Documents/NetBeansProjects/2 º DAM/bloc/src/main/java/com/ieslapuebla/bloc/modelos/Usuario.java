
package com.ieslapuebla.bloc.modelos;


public class Usuario {
    
    //Atributos
    private int id;
    private static String usuario;
    private String contrasena;
    private String correo;
    private String nombre;
    
    //Constructor de la clase
    public Usuario(String usuario, String contrasena, String correo, String nombre){
        
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.nombre = nombre;
    }
    
    public Usuario(){
        
    }
    
    //Metodos getters and setters
    public int getId(){
        
        return id;
    }
    
    public void setId(int id){
        
        this.id = id;
    }
    
    
    public static String getUsuario(){
        
        return usuario;
    }
    
    public void setUsuario(String usuario){
        
        this.usuario = usuario;
    }
    
    public String getContrasena(){
        
        return contrasena;
    }
    
    public void setContrasena(String contrasena){
        
        this.contrasena = contrasena;
    }
    
    public String getCorreo(){
        
        return correo;
    }
    
    public void setCorreo(String correo){
        
        this.correo = correo;
    }
    
    public String getNombre(){
        
        return nombre;
    }
    
    public void setNombre(String nombre){
        
        this.nombre = nombre;
    }
}
