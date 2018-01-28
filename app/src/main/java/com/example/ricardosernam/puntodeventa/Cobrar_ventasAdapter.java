package com.example.ricardosernam.puntodeventa;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
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
    private Context context;

    public Cobrar_ventasAdapter(Context context, Activity activity, ArrayList<Cobrar_ventas_class> itemsCobrar) {  ///recibe el arrayCobrar como parametro
        this.itemsCobrar=itemsCobrar;
        this.context=context;
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder {    ////clase donde van los elementos del cardview
        public TextView nombreP;
        public Button eliminarArt,eliminarCompra ;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = (TextView) v.findViewById(R.id.TVnombreProductoCobrar);  ///cardviews donde va el nombre del producto
            eliminarArt = v.findViewById(R.id.BtnEliminarArt);
            eliminarCompra = v.findViewById(R.id.BtnEliminarCompra);
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
        final FragmentManager manager = ((Activity) context).getFragmentManager();
        holder.eliminarArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsCobrar.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsCobrar.size());
                if(itemsCobrar.isEmpty()){
                    manager.beginTransaction().remove(manager.findFragmentById(R.id.LOcobrar)).commit();
                }
            }
        });
    }
}
