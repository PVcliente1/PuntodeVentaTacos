package com.example.ricardosernam.puntodeventa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ricardosernam.puntodeventa.R.id.Layout1;

public class Provedores extends Fragment {
    Button btnNuevoProvedor;
    Spinner spinner;
    String[] items;
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,SpinnerLayout;
    Boolean visible;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provedores, container, false);

        visible = false;
        layout1 = view.findViewById(R.id.Layout1);
        layout2 = view.findViewById(R.id.Layout2);
        layout3 = view.findViewById(R.id.Layout3);
        layout4 = view.findViewById(R.id.Layout4);
        layout5 = view.findViewById(R.id.Layout5);
        layout6 = view.findViewById(R.id.Layout1);
        SpinnerLayout = view.findViewById(R.id.SpinnerLayout);

        btnNuevoProvedor = view.findViewById(R.id.BtnAgregarProvedor);
        spinner = view.findViewById(R.id.SPprovedores);

        items = new String[]{
                "----","1","2","3","4","5","6","7"
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

        return view;

    }
}
