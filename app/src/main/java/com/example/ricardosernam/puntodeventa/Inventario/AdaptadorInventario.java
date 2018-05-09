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
    private Context context;

    public AdaptadorInventario(ArrayList<Inventario_class> itemsInventarios, Context context) {
        this.itemsInventarios = itemsInventarios;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public TextView porcion;


        public ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.monto);
            precio = v.findViewById(R.id.etiqueta);
            porcion = v.findViewById(R.id.fecha);

        }
    }

    @Override
    public int getItemCount() {
        /*if (cursor!=null){  ///hay algo
        return cursor.getCount();
        }
        else{  ///no hay nada
            return 0;
        }*/
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
        viewHolder.precio.setText(String.valueOf(itemsInventarios.get(i).getExistente()));
        /*cursor.moveToPosition(i);

        String nombre;
        String precio;
        String porcion;

        nombre = cursor.getString(4);
        precio = cursor.getString(1);
        porcion = cursor.getString(2);

        viewHolder.nombre.setText("idInventario "+nombre);
        viewHolder.precio.setText("IdCarrito "+precio);
        viewHolder.porcion.setText("Disponible "+porcion);
    }

    void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }*/
    }
}