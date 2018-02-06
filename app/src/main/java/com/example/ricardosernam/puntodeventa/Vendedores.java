package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Vendedores extends Fragment {
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedores, container, false);

        btnNuevoVendedor = view.findViewById(R.id.BtnAgregarVendedor);
        spinner = view.findViewById(R.id.SPvendedores);

        items = new String[]{
                "1","2","3","4","5"
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
        // Inflate the layout for this fragment
        return view;
    }
}
