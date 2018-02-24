package com.example.ricardosernam.puntodeventa.Miembros;

import android.content.Context;
import android.net.Uri;
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
public class Miembros extends Fragment {
    LinearLayout contenedor, infoMiembro, miembroL;
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor, editarMiembro, imagen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_miembros, container, false);
        btnNuevoVendedor = view.findViewById(R.id.BtnAgregarVendedor);
        editarMiembro = view.findViewById(R.id.BtnEditarMiembro);
        imagen = view.findViewById(R.id.BtnImagen);

        spinner = view.findViewById(R.id.SPvendedores);
        infoMiembro=view.findViewById(R.id.LLinformación);
        //miembroL=view.findViewById(R.id.LLmiembros);
        getFragmentManager().beginTransaction().replace(R.id.LLmiembros, new MiPerfil()).commit();

        items = new String[]{
                "Miembros...","1","2","3","4","5"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items); //selected item will look like a spinner set from XML
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnNuevoVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_miembros().show(getFragmentManager(),"Agregar Vendedor");
            }
        });

        editarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagen.setVisibility(View.VISIBLE);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    infoMiembro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

}
