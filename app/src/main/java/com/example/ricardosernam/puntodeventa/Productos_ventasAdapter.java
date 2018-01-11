package com.example.ricardosernam.puntodeventa;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Productos_ventasAdapter extends RecyclerView.Adapter <Productos_ventasAdapter.Productos_ventasViewHolder>{
    private ArrayList<Productos_venta> items;
    private View.OnClickListener listener;
    private interfaz Interfaz;

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public TextView nombreP;
        //public Context context;
        //public DialogFragment df;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = (TextView) v.findViewById(R.id.nombreProductos);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Interfaz.onClick(view);
                    //Toast.makeText(view.getContext(), "Producto", Toast.LENGTH_SHORT).show();
                    //DialogFragment df = (DialogFragment) ((DialogFragment) context).getSupportFragmentManager().findFragmentByTag("Pro");
                    //df.dismiss();
                    //new Pro().dismiss();//;
                }
            });
        }
    }

    public Productos_ventasAdapter(ArrayList<Productos_venta> items, interfaz Interfaz) {
        this.items = items;
        this.Interfaz=Interfaz;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjetas_productos, viewGroup, false);
        //v.setOnClickListener(this);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, int position) {
        holder.nombreP.setText(items.get(position).getNombre());
    }

    /*public View.OnClickListener getListener() {
        return listener;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }*/
}
