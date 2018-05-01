package com.example.max00.lain.Class;

import java.io.Serializable;

public class Contacto implements Serializable {

    private String nombre;
    private String apellido;
    private String email;
    private String phone;
    private String date;
    private int Imagen;


    public Contacto(String nombre, String apellido, String email, String phone, String date) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.date = date;
    }

    public Contacto(String nombre, String apellido, String email, String phone, String date, int imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.phone = phone;
        this.date = date;
        Imagen = imagen;
    }

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
