package com.example.ricardosernam.puntodeventa.web;

/**
 * Esta clase representa un gasto individual de cada registro de la base de datos
 */
public class Inventario_detalle {
    public String idinventario;
    public String idproducto;
    public double existente;



    public Inventario_detalle(String idinventario, String idproducto, double existente) {
        this.idinventario = idinventario;
        this.idproducto = idproducto;
        this.existente = existente;
    }
}
