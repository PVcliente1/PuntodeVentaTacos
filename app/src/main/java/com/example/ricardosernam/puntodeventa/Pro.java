package com.example.ricardosernam.puntodeventa;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Pro extends DialogFragment {
    private RecyclerView recycler,recycler2;
    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView.LayoutManager lManager,lManager2;
    private ArrayList<Productos_venta> items;


    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView=inflater.inflate(R.layout.recyclerpro,container);
        items = new ArrayList <>();
        items.add(new Productos_venta("Tacos"));
        items.add(new Productos_venta("Tortas"));
        items.add(new Productos_venta("Quesadillas"));
        items.add(new Productos_venta("Refrescos"));
        items.add(new Productos_venta("Cervezas"));
        items.add(new Productos_venta("Pizza"));

        final FragmentManager fm= getFragmentManager();
        //android.support.v4.app.FragmentManager fm = getSuppFragmentManager();  //manejador que permite hacer el cambio de ventanas

        // Commit a la transacción
        recycler = rootView.findViewById(R.id.recicladorPro);
        lManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(lManager);
        adapter = new Productos_ventasAdapter(items, new interfaz() {
            @Override
            public void onClick(View v) {
                dismiss();

                fm.beginTransaction().replace(R.id.Cobrar, new Cobrar()).commit(); ///cambio de fragment

                /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.Ventas, new Compras());
                transaction.addToBackStack(null);
                transaction.commit();*/

                //new Cobrar().show(fm,"Cobrar");
            }
        });

        recycler.setAdapter(adapter);

        this.getDialog().setTitle("Productos");


        return rootView;
    }
    public String getSeleccionado() {
        return items.get(recycler.getChildAdapterPosition(getView())).getNombre();
    }
}
