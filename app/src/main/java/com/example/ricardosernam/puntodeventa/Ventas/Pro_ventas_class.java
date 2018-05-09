package com.example.ricardosernam.puntodeventa.Ventas;

public class Pro_ventas_class {  ///clase para obtener productos para cobrar
    public String nombre;
    public float precio;
    public float porcion;
    public String guisado;

    public Pro_ventas_class(String nombre, Float precio, Float porcion, String guisado) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
        this.precio = precio;
        this.porcion = porcion;
        this.guisado = guisado;
    }
    public String getNombre() {
        return nombre;
    }
    public Float getPrecio() {
        return precio;
    }
    public Float getPorcion() {
        return porcion;
    }
    public String getGuisado() {
        return guisado;
    }
}
