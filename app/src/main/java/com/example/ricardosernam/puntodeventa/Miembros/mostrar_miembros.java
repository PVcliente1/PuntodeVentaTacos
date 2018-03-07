package com.example.ricardosernam.puntodeventa.Miembros;

import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

/**
 * Created by MartinRdz on 06/03/2018.
 */

public class mostrar_miembros extends Fragment{
    LinearLayout LLmiembros, LLspinnerMiembros,LLinformacionMiembros, LLeliminarEditarMiembros;
    Button agregar, editar, eliminar, cancelar, aceptar, imagen;
    EditText nombre, apellidos, telefono, correo;
    Spinner puesto, turno;

    SimpleCursorAdapter puestoSpinnerAdapter;
    SimpleCursorAdapter turnoSpinnerAdapter;




}
