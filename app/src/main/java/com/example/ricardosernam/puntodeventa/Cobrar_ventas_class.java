package com.example.ricardosernam.puntodeventa;


public class Cobrar_ventas_class {   ///clase para obtener productos para cobrar
    private String seleccionado;

    public Cobrar_ventas_class(String seleccionado){  ///se manda desde el arrayProductos
        this.seleccionado=seleccionado;
    }
    public String getSeleccionado(){
        return seleccionado;
    }
}
