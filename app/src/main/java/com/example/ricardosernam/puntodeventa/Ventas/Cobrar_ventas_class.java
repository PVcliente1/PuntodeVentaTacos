package com.example.ricardosernam.puntodeventa.Ventas;


import android.text.Editable;

public class Cobrar_ventas_class {   ///clase para obtener productos para cobrar
    private String unidad;
    private String nombre;
    private int descuento;
    private float subTotal, cantidad, precioVenta;

    Cobrar_ventas_class(String unidad, String nombre, float cantidad, float precioVenta,  float subTotal, int descuento) {  ///se manda desde el arrayProductos
        this.unidad=unidad;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precioVenta=precioVenta;
        this.subTotal=subTotal;
        this.descuento=descuento;
    }
    public String getUnidad(){
        return unidad;
    }
    public String getNombre(){
        return nombre;
    }
    float getCantidad(){
        return cantidad;
    }
    public float getPrecio(){
        return precioVenta;
    }
    float getSubTotal(){
        return subTotal;
    }
    int getDescuento(){
        return descuento;
    }

}
