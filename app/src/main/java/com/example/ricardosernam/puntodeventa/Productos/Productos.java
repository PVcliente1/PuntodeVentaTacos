package com.example.ricardosernam.puntodeventa.Productos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa.R;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Productos extends Fragment{
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Button nuevoProducto;
    private android.app.FragmentManager fm;

    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///

    @SuppressLint("ValidFragment")
    public Productos(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////productos de ejemplo//////////////
        itemsProductos.add(new Pro_ventas_class("Tacos"));
        itemsProductos.add(new Pro_ventas_class("Tortas"));
        itemsProductos.add(new Pro_ventas_class("Quesadillas"));
        itemsProductos.add(new Pro_ventas_class("Refrescos"));
        itemsProductos.add(new Pro_ventas_class("Cervezas"));
        itemsProductos.add(new Pro_ventas_class("Pizza"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_productos, container, false);
       fm=getActivity().getFragmentManager();
        nuevoProducto=view.findViewById(R.id.BtnNuevoProducto);
        recycler = view.findViewById(R.id.RVproductos); ///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new ProductosAdapter(getActivity(), itemsProductos);
        recycler.setAdapter(adapter);
        nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new nuevoProducto_DialogFragment().show(fm, "nuevoProducto");
            }
        });
       return view;
    }


}
