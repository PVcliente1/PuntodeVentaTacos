package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Cobrar_ventas_Fragment extends Fragment {   ////Fragment para seccion cobrar con recyclerview

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();   ///array para productos seleccionados

    @SuppressLint("ValidFragment")
    public Cobrar_ventas_Fragment(ArrayList itemsCobrar){
        this.itemsCobrar=itemsCobrar; ///recibios como parametro el array
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view2= inflater.inflate(R.layout.recyclercobrar, container, false);

        ////mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        adapter=new Cobrar_ventasAdapter(itemsCobrar);///llamamos al adaptador y le enviamos el array como parametro
        recycler = view2.findViewById(R.id.RVrecicladorCobrar);///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);

        return view2;
    }
}
