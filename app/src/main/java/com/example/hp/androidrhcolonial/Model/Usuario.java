package com.example.hp.androidrhcolonial.Model;

/**
 * Created by HP on 02/02/2018.
 */

public class Usuario {
    private String Nombre;
    private String Password;
    private String Telefono;

    public Usuario(){}

    public Usuario(String nombre, String password) {
        Nombre = nombre;
        Password = password;

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
