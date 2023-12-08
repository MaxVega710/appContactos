package com.example.myapplication.Objects;

import java.io.Serializable;

public class Contactos implements Serializable {
    private String ID;
    private String nombre;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String notas;
    private int favorito;

    public Contactos(String ID, String nombre, String direccion, String telefono1, String telefono2, String notas, int favorito) {
        this.ID = ID;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.notas = notas;
        this.favorito = favorito;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }


}
