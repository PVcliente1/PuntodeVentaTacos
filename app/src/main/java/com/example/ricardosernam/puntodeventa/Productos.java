package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Productos extends Fragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Productos_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////productos de ejemplo//////////////
        itemsProductos.add(new Productos_ventas_class("Tacos"));
        itemsProductos.add(new Productos_ventas_class("Tortas"));
        itemsProductos.add(new Productos_ventas_class("Quesadillas"));
        itemsProductos.add(new Productos_ventas_class("Refrescos"));
        itemsProductos.add(new Productos_ventas_class("Cervezas"));
        itemsProductos.add(new Productos_ventas_class("Pizza"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_productos, container, false);
        recycler = view.findViewById(R.id.RVproductos); ///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new Productos_ventasAdapter(itemsProductos);
        recycler.setAdapter(adapter);
       return view;
    }


}
