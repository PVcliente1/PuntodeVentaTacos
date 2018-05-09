package com.example.ricardosernam.puntodeventa.web;

/**
 * Esta clase representa un gasto individual de cada registro de la base de datos
 */
public class Producto {
    public String idproducto;
    public String nombre;
    public double precio;
    public double porcion;
    public String guisado;


    public Producto(String idproducto, String nombre, double precio, double porcion, String guisado) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.porcion = porcion;
        this.guisado = guisado;
    }
}
