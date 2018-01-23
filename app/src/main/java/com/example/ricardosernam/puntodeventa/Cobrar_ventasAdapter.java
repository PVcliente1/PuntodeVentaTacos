package com.example.ricardosernam.puntodeventa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cobrar_ventasAdapter extends RecyclerView.Adapter <Cobrar_ventasAdapter.Productos_ventasViewHolder>{  ///adaptador para la seccion de cobrar
    private ArrayList<Cobrar_ventas_class> itemsCobrar;
    //private interfaz_OnClick interfaz;

    public Cobrar_ventasAdapter(ArrayList<Cobrar_ventas_class> itemsCobrar) {  ///recibe el arrayCobrar como parametro
        this.itemsCobrar=itemsCobrar;
        //this.interfaz=interfaz;
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder {    ////clase donde van los elementos del cardview
        public TextView nombreP;
        public Button eliminar;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = (TextView) v.findViewById(R.id.TVnombreProductoCobrar);  ///cardviews donde va el nombre del producto
            eliminar = v.findViewById(R.id.BtnEliminarArt);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),"Se abrio", Toast.LENGTH_SHORT).show();
                    //interfaz.onClick(view);
                    //b poeritemsCobrar.remove(view);
                    //itemsCobrar.remove(itemsCobrar.get(recycler.getChildAdapterPosition(v))) ;
                    //itemsCobrar.remove(itemsCobrar.get(1));
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return itemsCobrar.size();
    }

    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_cobrar, parent, false);////mencionamos el archivo del layout
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Productos_ventasViewHolder holder, final int position) {  ////mencionamos que se hara con los elementos del cardview
        holder.nombreP.setText(itemsCobrar.get(position).getSeleccionado());
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsCobrar.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsCobrar.size());
            }
        });
    }
}
