package com.example.ricardosernam.puntodeventa;



public class Historial_ventas_class {
    private String tipo;
    private String pagar;

    public Historial_ventas_class(String tipo, String pagar) {   ///se manda desde el arrayProductos
        this.tipo= tipo;
        this.pagar=pagar;
    }

    public String getTipo() {
        return tipo;
    }
    public String getPagar() {
        return pagar;
    }
}
