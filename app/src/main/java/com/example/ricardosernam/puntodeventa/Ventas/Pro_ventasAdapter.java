package com.example.ricardosernam.puntodeventa.Ventas;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;

import java.util.ArrayList;

public class Pro_ventasAdapter extends RecyclerView.Adapter <Pro_ventasAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<Pro_ventas_class> itemsProductos;
    private interfaz_OnClick Interfaz;

    public Pro_ventasAdapter(ArrayList<Pro_ventas_class> itemsProductos, interfaz_OnClick Interfaz) {  ///recibe el arrayProductos como parametro y la interface
        this.itemsProductos = itemsProductos;
        this.Interfaz=Interfaz;
    }
    /*public Pro_ventasAdapter(ArrayList<Pro_ventas_class> itemsProductos, interfaz_OnClick interfaz_onClick) {  ///recibe el arrayProductos como parametro y la interface
        this.itemsProductos = itemsProductos;
    }*/

    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public TextView nombreP;
        public ImageView imagen;
        public Productos_ventasViewHolder(View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductos);  ////Textview donde se coloca el nombre del producto
            imagen = v.findViewById(R.id.IVimagenProducto);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Interfaz.onClick(view);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return itemsProductos.size();
    }


    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjetas_productos_ventas, viewGroup, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, int position) {
        holder.nombreP.setText(itemsProductos.get(position).getNombre());
        holder.imagen.setImageURI(Uri.parse(itemsProductos.get(position).getFoto()));
    }
}
