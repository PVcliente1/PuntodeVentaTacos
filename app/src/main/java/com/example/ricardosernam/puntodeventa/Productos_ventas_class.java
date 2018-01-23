package com.example.ricardosernam.puntodeventa;

class Productos_ventas_class {  ///clase para obtener productos para cobrar
    private String nombre;

    public Productos_ventas_class(String nombre) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
