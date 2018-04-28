package com.example.ricardosernam.puntodeventa.web;

/**
 * Esta clase representa un gasto individual de cada registro de la base de datos
 */
public class Gasto {
    public String idproducto;
    public String nombre;
    public double precio;
    public double porcion;
    public double existente;


    public Gasto(String idproducto, String nombre, double precio, double porcion, double existente) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.porcion = porcion;
        this.existente= existente;
    }
}
