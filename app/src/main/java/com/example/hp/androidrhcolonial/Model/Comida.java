package com.example.hp.androidrhcolonial.Model;

/**
 * Created by HP on 06/02/2018.
 */

public class Comida {
  private String Nombre ;
    private String  Imagen;
    private String Descripcion;
    private String Precio;
    private String Descuento;
    private String MenuId;

    public Comida(String nombre, String imagen, String descripcion, String precio, String descuento, String menuId) {
        Nombre = nombre;
        Imagen = imagen;
        Descripcion = descripcion;
        Precio = precio;
        Descuento = descuento;
        MenuId = menuId;
    }
    public Comida() {}

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDescuento() {
        return Descuento;
    }

    public void setDescuento(String descuento) {
        Descuento = descuento;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
