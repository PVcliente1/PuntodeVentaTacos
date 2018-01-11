package com.example.ricardosernam.puntodeventa;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Pro extends DialogFragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
   // private Context context;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.popoutproducts);
        View rootView=inflater.inflate(R.layout.recyclerpro,container);
        ArrayList<Productos_venta> items = new ArrayList <>();
        items.add(new Productos_venta("Tacos"));
        items.add(new Productos_venta("Tortas"));
        items.add(new Productos_venta("Quesadillas"));
        items.add(new Productos_venta("Refrescos"));
        items.add(new Productos_venta("Cervezas"));
        items.add(new Productos_venta("Pizza"));

// Obtener el Recycler
        recycler = rootView.findViewById(R.id.recicladorPro);
        //recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this.getActivity());
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new Productos_ventasAdapter(items, new interfaz() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Putos", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });
        recycler.setAdapter(adapter);

        this.getDialog().setTitle("Productos");

        return rootView;
        //return createPro();
    }
    /*public AlertDialog createPro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Titulo")
                .setMessage("El Mensaje para el usuario")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                            }
                        });

        return builder.create();
    }*/
}
