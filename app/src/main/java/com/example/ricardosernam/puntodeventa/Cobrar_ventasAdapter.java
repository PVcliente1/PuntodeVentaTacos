package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Cobrar_ventasAdapter extends RecyclerView.Adapter <Cobrar_ventasAdapter.Productos_ventasViewHolder>{
    private String Seleccionado;
    private int Cantidad;
    private ArrayList<Cobrar_venta> items2;

    public Cobrar_ventasAdapter(ArrayList<Cobrar_venta> items2) {
        this.items2=items2;
        //this.Seleccionado = Seleccionado;
        //this.context=context;
        //this.Cantidad=cantidad;
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview

        // Campos respectivos de un item
        public TextView nombreP2;
        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP2 = (TextView) v.findViewById(R.id.nombreP);
        }
    }
    @Override
    public int getItemCount() {
        return items2.size();
    }

    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_cobrar, parent, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, int position) {
        holder.nombreP2.setText(items2.get(position).getSeleccionado());
    }
}
