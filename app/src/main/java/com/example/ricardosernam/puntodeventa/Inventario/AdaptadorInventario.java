package com.example.ricardosernam.puntodeventa.Inventario;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;

import java.util.ArrayList;

public class AdaptadorInventario extends RecyclerView.Adapter<AdaptadorInventario.ViewHolder> {
    private ArrayList<Inventario_class> itemsInventarios;
    private Cursor cursor;

    public AdaptadorInventario(ArrayList<Inventario_class> itemsInventarios) {
        this.itemsInventarios = itemsInventarios;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView existente;

        public ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.TVproducto);
            existente = v.findViewById(R.id.TVexistente);

        }
    }

    @Override
    public int getItemCount() {
        return itemsInventarios.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjeta_productos_inventario, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(itemsInventarios.get(i).getNombre());
        if(itemsInventarios.get(i).getTipo().equals("Guisado")){
            viewHolder.existente.setText(String.valueOf(itemsInventarios.get(i).getExistente())+" gramo(s)");
        }
        else if(itemsInventarios.get(i).getTipo().equals("Pieza")){
            //int pieza= (int)(itemsInventarios.get(i).getExistente());
            viewHolder.existente.setText(String.valueOf(  (int)(itemsInventarios.get(i).getExistente()) )+" pieza(s)");
        }
    }
}