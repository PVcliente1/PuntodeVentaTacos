package com.example.ricardosernam.puntodeventa.Clientes;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ricardosernam.puntodeventa.R;


public class dialog_fragment_agregar_cliente extends DialogFragment {
    private Button guardar_cliente, cancelar_cliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_agregar_cliente, container, false);

        guardar_cliente=view.findViewById(R.id.BtnGuardarCliente);
        cancelar_cliente=view.findViewById(R.id.BtnCancelarAgregarCliente);

        guardar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        cancelar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Agregar Cliente");
        return view;

    }

}

