package com.example.ricardosernam.puntodeventa;

import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Cobrar extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2= inflater.inflate(R.layout.recyclercobrar, container, false);
        ArrayList<Cobrar_venta> items2;
        items2 = new ArrayList<>();
        items2.add(new Cobrar_venta("Puto"));

        //Toast.makeText(view2.getContext(), "Ejecutado", Toast.LENGTH_SHORT).show();

        RecyclerView recycler2 = view2.findViewById(R.id.recicladorCobrar);
        RecyclerView.LayoutManager lManager2 = new LinearLayoutManager(this.getActivity());
        recycler2.setLayoutManager(lManager2);
        RecyclerView.Adapter adapter2=new Cobrar_ventasAdapter(items2);
        recycler2.setAdapter(adapter2);
        return view2;
    }
}
