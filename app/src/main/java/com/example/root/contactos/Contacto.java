package com.example.root.contactos;

/**
 * Created by root on 2/1/18.
 */

public class Contacto {
    int id;



    String id_contacto;
    String nombre;
    String email;
    String direccion;
    String genero;

    public Contacto(int id, String id_contacto, String nombre, String email, String direccion, String genero) {
        this.id = id;
        this.id_contacto = id_contacto;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.genero = genero;
    }

    public Contacto(String id_contacto, String nombre, String email, String direccion, String genero) {
        this.id_contacto = id_contacto;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.genero = genero;
    }

    public Contacto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(String id_contacto) {
        this.id_contacto = id_contacto;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
