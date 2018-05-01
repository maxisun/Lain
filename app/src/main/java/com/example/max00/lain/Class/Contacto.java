package com.example.max00.lain.Class;

import java.io.Serializable;

public class Contacto implements Serializable {

    private String nombre;
    private int Imagen;


    public Contacto(String nombre, int imagen) {
        this.nombre = nombre;
        this.Imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
