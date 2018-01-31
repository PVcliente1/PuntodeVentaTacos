package com.example.ricardosernam.puntodeventa;


import android.text.Editable;

public class Cobrar_ventas_class {   ///clase para obtener productos para cobrar
    private String seleccionado;
    private Editable escaneado;

    public Cobrar_ventas_class(String seleccionado) {  ///se manda desde el arrayProductos
        this.seleccionado=seleccionado;
    }
    public String getSeleccionado(){
        return seleccionado;
    }

}
