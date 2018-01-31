package com.example.ricardosernam.puntodeventa;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Pro_DialogFragment extends DialogFragment {     //clase que me crea el dialogFragment con productos
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Productos_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos

    public Pro_DialogFragment(ArrayList itemsProductos, ArrayList itemsCobrar){
        this.itemsProductos=itemsProductos;
        this.itemsCobrar=itemsCobrar;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.recyclerpro,container);

        ////mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        final FragmentManager fm= getFragmentManager();
        recycler = rootView.findViewById(R.id.RVrecicladorPro); ///declaramos el recycler
        lManager = new GridLayoutManager(this.getActivity(),2);  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new Productos_ventasAdapter(itemsProductos, new interfaz_OnClick() { ///llamamos al adaptador y le enviamos el array y la interface  como parametro (la interface es un onclick)
            @Override
            public void onClick(View v) {  ////cuando se presione un Cardview...
                dismiss(); ////cerramos la ventana
                itemsCobrar.add(new Cobrar_ventas_class(itemsProductos.get(recycler.getChildAdapterPosition(v)).getNombre()));//obtenemos el cardview seleccionado y lo agregamos a items2
                fm.beginTransaction().replace(R.id.LOcobrar, new Cobrar_ventas_Fragment(itemsCobrar)).commit(); ///sustituimos el layout frament por el del recycler de cobrar
            }
        });
        recycler.setAdapter(adapter);
        this.getDialog().setTitle("Productos");///cambiamos titulo del DialogFragment

        return rootView;
    }
}
