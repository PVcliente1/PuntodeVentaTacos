package com.example.ricardosernam.puntodeventa.web;

/**
 * Esta clase representa un gasto individual de cada registro de la base de datos
 */
public class Inventario_detalle {
    public String idinventario;
    public String idproducto;
    public double existente_inicial;
    public double existente_final;




    public Inventario_detalle(String idinventario, String idproducto, double existente_inicial, double existente_final) {
        this.idinventario = idinventario;
        this.idproducto = idproducto;
        this.existente_inicial = existente_inicial;
        this.existente_inicial = existente_final;
    }
}
