package com.example.ricardosernam.puntodeventa.Inventario;

public class Inventario_class {  ///clase para obtener productos para cobrar
    public String nombre;
    public float existente;

    public Inventario_class(String nombre, Float existente) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
        this.existente = existente;
    }
    public String getNombre() {
        return nombre;
    }
    public Float getExistente() {
        return existente;
    }
}
