package com.example.ricardosernam.puntodeventa.web;

/**
 * Esta clase representa un gasto individual de cada registro de la base de datos
 */
public class Inventario {
    public String idinventario;
    public int idcarrito;
    public String fecha;




    public Inventario(String idinventario, int idcarrito, String fecha) {
        this.idinventario = idinventario;
        this.idcarrito = idcarrito;
        this.fecha=fecha;
    }
}
