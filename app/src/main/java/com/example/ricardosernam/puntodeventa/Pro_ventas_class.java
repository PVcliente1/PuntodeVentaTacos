package com.example.ricardosernam.puntodeventa;

class Pro_ventas_class {  ///clase para obtener productos para cobrar
    private String nombre;

    public Pro_ventas_class(String nombre) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
