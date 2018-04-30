package com.example.ricardosernam.puntodeventa.Inventario;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.R;

/**
 * Adaptador del recycler view
 */
public class AdaptadorInventario extends RecyclerView.Adapter<AdaptadorInventario.ViewHolder> {
    private Cursor cursor;
    private Context context;

    public AdaptadorInventario(Context context) {
        this.context= context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public TextView porcion;


        public ViewHolder(View v) {
            super(v);
            nombre =  v.findViewById(R.id.monto);
            precio =  v.findViewById(R.id.etiqueta);
            porcion = v.findViewById(R.id.fecha);

        }
    }

    @Override
    public int getItemCount() {
        if (cursor!=null)
        return cursor.getCount();
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjeta_productos_inventario, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        cursor.moveToPosition(i);

        String nombre;
        String precio;
        String porcion;

        nombre = cursor.getString(1);
        precio = cursor.getString(2);
        porcion = cursor.getString(3);

        viewHolder.nombre.setText("$"+nombre);
        viewHolder.precio.setText(precio);
        viewHolder.porcion.setText(porcion);
    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }
}