package com.example.ricardosernam.puntodeventa;



public class Historial_ventas_class {
    private String nombre;

    public Historial_ventas_class(String nombre) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
