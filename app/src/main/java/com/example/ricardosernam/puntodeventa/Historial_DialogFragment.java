package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Historial_DialogFragment extends DialogFragment {
    private View view2;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados

    @SuppressLint("ValidFragment")
    public Historial_DialogFragment(ArrayList<Historial_ventas_class> itemsHistorial){
        this.itemsHistorial=itemsHistorial;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.recyclerhistorial, container, false);
              adapter = new Historial_ventasAdapter(itemsHistorial);///llamamos al adaptador y le enviamos el array como parametro
                recycler = view2.findViewById(R.id.RVrecicladorHistorial);///declaramos el recycler
                lManager = new LinearLayoutManager(getActivity());  //declaramos el layoutmanager
                recycler.setLayoutManager(lManager);
                recycler.setAdapter(adapter);
        return view2;
    }
}
