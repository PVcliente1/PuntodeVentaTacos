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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Vendedores extends Fragment {
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor;
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,SpinnerLayout;
    Boolean visible;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedores, container, false);

        btnNuevoVendedor = view.findViewById(R.id.BtnAgregarVendedor);
        spinner = view.findViewById(R.id.SPvendedores);

        visible = false;
        layout1 = view.findViewById(R.id.Layout1);
        layout2 = view.findViewById(R.id.Layout2);
        layout3 = view.findViewById(R.id.Layout3);
        layout4 = view.findViewById(R.id.Layout4);
        layout5 = view.findViewById(R.id.Layout5);
        layout6 = view.findViewById(R.id.Layout1);
        SpinnerLayout = view.findViewById(R.id.SpinnerLayout);

        items = new String[]{
                "----","1","2","3","4","5"
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
                String item = spinner.getSelectedItem().toString();

                if (!visible && item != "----") {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                    layout4.setVisibility(View.VISIBLE);
                    layout5.setVisibility(View.VISIBLE);
                    layout6.setVisibility(View.VISIBLE);
                    visible = true;
                    Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
