package com.example.ricardosernam.puntodeventa._____interfazes;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public interface interfaz_OnClickElementosProductos {  ////interfaz_OnClick usada para cerrar FragmentDialog con productos
    void onClick(String producto, EditText codigo, EditText nombre, ImageView imagen, EditText unidad, EditText precio);
}
