package com.example.ricardosernam.puntodeventa.Ventas;

public class Pro_ventas_class {  ///clase para obtener productos para cobrar
    public String nombre;

    public Pro_ventas_class(String nombre) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
