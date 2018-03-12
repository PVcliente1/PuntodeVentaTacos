package com.example.ricardosernam.puntodeventa.Ventas;


import android.text.Editable;

public class Cobrar_ventas_class {   ///clase para obtener productos para cobrar
    private String unidad;
    private String nombre;
    private String precioVenta;
    private int subTotal, cantidad;

    Cobrar_ventas_class(String unidad, String nombre, int cantidad, String precioVenta,  int subTotal) {  ///se manda desde el arrayProductos
        this.unidad=unidad;
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.precioVenta=precioVenta;
        this.subTotal=subTotal;
    }
    public String getUnidad(){
        return unidad;
    }
    public String getNombre(){
        return nombre;
    }
    int getCantidad(){
        return cantidad;
    }
    public String getPrecio(){
        return precioVenta;
    }
    int getSubTotal(){
        return subTotal;
    }

}
