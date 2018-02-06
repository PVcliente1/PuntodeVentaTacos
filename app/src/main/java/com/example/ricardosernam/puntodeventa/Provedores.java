package com.example.ricardosernam.puntodeventa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Provedores extends Fragment {
    Button btnNuevoProvedor;
    Spinner spinner;
    String[] items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provedores, container, false);

        btnNuevoProvedor = view.findViewById(R.id.BtnAgregarProvedor);
        spinner = view.findViewById(R.id.SPprovedores);

        items = new String[]{
                "1","2","3","4","5","6","7"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnNuevoProvedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AgregarProvedor().show(getFragmentManager(),"Agregar Provedor");
            }
        });
        return view;

    }
}
