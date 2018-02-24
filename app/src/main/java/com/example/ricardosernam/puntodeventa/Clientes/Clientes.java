package com.example.ricardosernam.puntodeventa.Clientes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.ricardosernam.puntodeventa.R;

public class Clientes extends Fragment {
    private Button agregarCliente;
    private Spinner agregados;
    private LinearLayout datosCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        agregarCliente = view.findViewById(R.id.BtnAgregarCliente);
        datosCliente = view.findViewById(R.id.LLdatosCliente);
        agregados = view.findViewById(R.id.SPagreagados);
        String[] clientes= {"Selecciona un cliente", "Juan Perez (Juanito)","Pedro Martínez(Peter)","Manuel Gómez(Meño)"};
        agregados.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,clientes));

        agregados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    datosCliente.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        agregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_cliente().show(getFragmentManager(),"ddddddd");
            }
        });
        return view;
    }
}
