package com.example.ricardosernam.puntodeventa;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Historial_ventasAdapter extends RecyclerView.Adapter<Historial_ventasAdapter.HistorialVentasViewHolder> {
    private ArrayList<Historial_ventas_class> itemsHistorial;

    public Historial_ventasAdapter(ArrayList<Historial_ventas_class> itemsCobrar) {  ///recibe el arrayCobrar como parametro
        this.itemsHistorial=itemsCobrar;
    }

    public class HistorialVentasViewHolder extends RecyclerView.ViewHolder {
        public TextView tipo;

        public HistorialVentasViewHolder(View itemView) {
            super(itemView);
            tipo=itemView.findViewById(R.id.TVtipoHistorial);
        }

    }

    @Override
    public HistorialVentasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjetas_historial_ventas, parent, false);////mencionamos el archivo del layout
        return new HistorialVentasViewHolder(v);
    }
    @Override
    public int getItemCount() {
        return itemsHistorial.size();
    }
    @Override
    public void onBindViewHolder(HistorialVentasViewHolder holder, int position) {
        holder.tipo.setText(itemsHistorial.get(position).getNombre());
        if(itemsHistorial.get(position).getNombre().equals("Venta")){
            //holder.itemView.setBackgroundColor(4);
            holder.itemView.setBackgroundColor(Color.RED);
        }
        else if(itemsHistorial.get(position).getNombre().equals("Apartado")){
            holder.itemView.setBackgroundColor(Color.BLUE);
        }
    }




}
