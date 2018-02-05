package com.example.ricardosernam.puntodeventa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Productos_ventasAdapter extends RecyclerView.Adapter <Productos_ventasAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<Productos_ventas_class> itemsProductos;
    private interfaz_OnClick Interfaz;

    public Productos_ventasAdapter(ArrayList<Productos_ventas_class> itemsProductos, interfaz_OnClick Interfaz) {  ///recibe el arrayProductos como parametro y la interface
        this.itemsProductos = itemsProductos;
        this.Interfaz=Interfaz;
    }

    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public TextView nombreP;
        public Productos_ventasViewHolder(View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductos);  ////Textview donde se coloca el nombre del producto
            v.setOnClickListener(new View.OnClickListener() {  ///usamos desde aqui la interface(ya que aqui no podemos cerrar el Fragmentdialog y lo cerraremos en ventas
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjetas_productos, viewGroup, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, int position) {
        holder.nombreP.setText(itemsProductos.get(position).getNombre());
    }
}
