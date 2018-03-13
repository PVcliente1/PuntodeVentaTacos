package com.example.ricardosernam.puntodeventa.Ventas;



public class Historial_ventas_class {
    private String tipo;
    private int pagar;

    public Historial_ventas_class(String tipo, int pagar) {   ///se manda desde el arrayProductos
        this.tipo= tipo;
        this.pagar=pagar;
    }

    public String getTipo() {
        return tipo;
    }
    public int getPagar() {
        return pagar;
    }
}
