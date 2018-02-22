package com.example.ricardosernam.puntodeventa;

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
import android.widget.Toast;


public class Miembros extends Fragment {
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor;
    LinearLayout datosMiembro;
    Boolean visible;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_miembros, container, false);

        btnNuevoVendedor = view.findViewById(R.id.BtnAgregarVendedor);
        spinner = view.findViewById(R.id.SPvendedores);
        datosMiembro=view.findViewById(R.id.LLinformación);

        items = new String[]{
                "Miembros","1","2","3","4","5"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnNuevoVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AgregarVendedor().show(getFragmentManager(),"Agregar Vendedor");
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    datosMiembro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


}
