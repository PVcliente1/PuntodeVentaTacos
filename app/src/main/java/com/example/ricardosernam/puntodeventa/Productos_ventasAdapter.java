package com.example.ricardosernam.puntodeventa;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Productos_ventasAdapter extends RecyclerView.Adapter <Productos_ventasAdapter.Productos_ventasViewHolder> {
    private ArrayList<Productos_venta> items;

    public static class Productos_ventasViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public CardView tarjeta;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre);
            tarjeta=(CardView) v.findViewById(R.id.tarjeta);
        }
    }

    public Productos_ventasAdapter(ArrayList<Productos_venta> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjetas_productos, viewGroup, false);
        notifyDataSetChanged();
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, int position) {
        holder.nombre.setText(items.get(position).getNombre());
    }
}
