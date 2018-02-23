package com.example.ricardosernam.puntodeventa.Miembros;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;


public class dialog_fragment_agregar_miembros extends DialogFragment {
    Button btnGuardar, btnCancelar;
    EditText EtNombre, EtApellido, EtTelefono, EtDireccion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_agregar_miembro, container, false);

        btnGuardar = view.findViewById(R.id.btnGuardarNuevo);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        EtNombre = view.findViewById(R.id.EtNombreNew);
        EtApellido = view.findViewById(R.id.EtApellidoNew);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Agregar Miembro");


        // Inflate the layout for this fragment
        return view;
    }

}
