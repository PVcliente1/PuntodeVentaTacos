package com.example.ricardosernam.puntodeventa.Ventas;


import android.text.Editable;

public class Cobrar_ventas_class {   ///clase para obtener productos para cobrar
    private String unidad;
    private String nombre;
    private String cantidad;
    private String precioVenta;

    public Cobrar_ventas_class(String unidad, String nombre, String precioVenta) {  ///se manda desde el arrayProductos
        this.unidad=unidad;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precioVenta=precioVenta;
    }
    public Cobrar_ventas_class(String unidad) {  ///se manda desde el arrayProductos
        this.unidad=unidad;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precioVenta=precioVenta;
    }
    public String getUnidad(){
        return unidad;
    }
    public String getNombre(){
        return nombre;
    }
    public String getPrecio(){
        return precioVenta;
    }

}
