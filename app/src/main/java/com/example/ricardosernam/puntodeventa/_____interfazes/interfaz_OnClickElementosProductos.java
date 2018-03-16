package com.example.ricardosernam.puntodeventa._____interfazes;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface interfaz_OnClickElementosProductos {  ////interfaz_OnClick usada para cerrar FragmentDialog con productos
    void onClick(String producto, EditText codigo, EditText nombre, ImageView imagen, EditText unidad, EditText precio, Button editar, Button escanear, Button traerImagen, LinearLayout botones);
}
