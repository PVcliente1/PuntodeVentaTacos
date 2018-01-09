package com.example.ricardosernam.puntodeventa;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Pro extends DialogFragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.popoutproducts);

        View rootView=inflater.inflate(R.layout.prueba,container);

        ArrayList<Productos_venta> items = new ArrayList <>();
        items.add(new Productos_venta("Tacos"));
        items.add(new Productos_venta("Tortas"));
        items.add(new Productos_venta("Quesadillas"));
        items.add(new Productos_venta("Refrescos"));
        items.add(new Productos_venta("Cervezas"));

// Obtener el Recycler
        recycler = rootView.findViewById(R.id.reciclador);
        //recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new Productos_ventasAdapter(items);

        recycler.setAdapter(adapter);
        this.getDialog().setTitle("Productos");

        return rootView;
    }
}
