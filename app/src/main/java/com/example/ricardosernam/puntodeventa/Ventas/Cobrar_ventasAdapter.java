package com.example.ricardosernam.puntodeventa.Ventas;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.R;

import java.util.ArrayList;

public class Cobrar_ventasAdapter extends RecyclerView.Adapter <Cobrar_ventasAdapter.Productos_ventasViewHolder> {  ///adaptador para la seccion de cobrar
    private ArrayList<Cobrar_ventas_class> itemsCobrar;
    private Context context;
    private android.support.v4.app.Fragment fragment;

    public Cobrar_ventasAdapter(android.support.v4.app.Fragment fragment, Context context, ArrayList<Cobrar_ventas_class> itemsCobrar) {  ///recibe el arrayCobrar como parametro
        this.fragment=fragment;
        this.itemsCobrar = itemsCobrar;
        this.context = context;
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        public TextView nombreP, unidad, subtotal, precio, cantidad;
        public Button eliminarArt;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductoCobrar);  ///cardviews donde va el nombre del producto
            eliminarArt = v.findViewById(R.id.BtnEliminarArt);
            precio = v.findViewById(R.id.ETprecio);
            cantidad = v.findViewById(R.id.ETcantidad);
            subtotal = v.findViewById(R.id.TVsubtotal);
        }
    }
    public static class watcherCalculo1 implements TextWatcher{   ///detecta cambios en los editText
        private TextView cantidad,precio, subtotal;
        private String nombre;
        private int position;

       watcherCalculo1(String nombre,  TextView cantidad, TextView precio, TextView subtotal) {
            this.nombre=nombre;
            this.cantidad = cantidad;
            this.precio = precio;
            this.subtotal = subtotal;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!(TextUtils.isEmpty(cantidad.getText()))&!(TextUtils.isEmpty(precio.getText()))) {
                float totalParcial=Float.parseFloat(String.valueOf((cantidad.getText()))) * Float.parseFloat(String.valueOf((precio.getText())));
                subtotal.setText(String.valueOf(totalParcial));  ////hacemos el descuento
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
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
    public void onBindViewHolder(final Productos_ventasViewHolder holder, final int position) {  ////mencionamos que se hara con los elementos del cardview
        final FragmentManager manager = ((Activity) context).getFragmentManager();

        holder.nombreP.setText(itemsCobrar.get(position).getNombre());
        holder.precio.setText(String.valueOf(itemsCobrar.get(position).getPrecio()));
        holder.cantidad.setText(String.valueOf(itemsCobrar.get(position).getCantidad()));
        holder.subtotal.setText(String.valueOf(itemsCobrar.get(position).getSubTotal()));
        ///llamamos al escuchador de cambios en los editText
        watcherCalculo1 watcher1 = new watcherCalculo1(String.valueOf(holder.nombreP.getText()), holder.cantidad, holder.precio, holder.subtotal);
        holder.cantidad.addTextChangedListener(watcher1);
        holder.precio.addTextChangedListener(watcher1);

        holder.eliminarArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  ///eliminamos un articulo comprado
                itemsCobrar.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsCobrar.size());
                notifyDataSetChanged();
                if(itemsCobrar.isEmpty()){////ocultamos por completo el fragment donde se agregan   ///aqui esta el error
                    CardView cobro=((Activity) context).findViewById(R.id.CVcobrar);
                    cobro.setVisibility(View.GONE);
                }
            }
        });
    }
}
