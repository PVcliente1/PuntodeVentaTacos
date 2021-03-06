package com.example.ricardosernam.puntodeventa.Ventas;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;

import java.util.ArrayList;

public class Pro_ventasAdapter extends RecyclerView.Adapter <Pro_ventasAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<Pro_ventas_class> itemsProductos;
    private actualizado Interfaz;
    //private Cursor cursor;
    private Context context;

    public Pro_ventasAdapter(ArrayList<Pro_ventas_class> itemsProductos, actualizado Interfaz, Context context) {  ///recibe el arrayProductos como parametro y la interface
        this.itemsProductos=itemsProductos;
        this.Interfaz=Interfaz;
        this.context= context;
    }
    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public TextView nombreP;
        public Button restar;
        public TextView cuenta, precio;
        public Productos_ventasViewHolder(final View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductos);  ////Textview donde se coloca el nombre del producto
            restar=v.findViewById(R.id.BtnRestar);
            cuenta=v.findViewById(R.id.TVcuenta);
            precio = v.findViewById(R.id.TVprecio);  ////Textview donde se coloca el nombre del producto

        }
    }



    public static class watcherCalculo1 implements TextWatcher {   ///detecta cambios en los editText
        private TextView cantidad;
        private actualizado Interfaz;
        private String  nombre;

        watcherCalculo1(String nombre, TextView cantidad, actualizado Interfaz) {
            this.nombre=nombre;
            this.cantidad = cantidad;
            this.Interfaz=Interfaz;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!(TextUtils.isEmpty(cantidad.getText()))){
                Interfaz.actualizar(Integer.parseInt(String.valueOf((cantidad.getText()))), nombre);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
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
        return itemsProductos.size();
    }


    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjetas_productos_ventas, viewGroup, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Productos_ventasViewHolder holder, final int position) {
        /*cursor.moveToPosition(position);

        String nombre;
        String precio;

        nombre = cursor.getString(1);
        precio = cursor.getString(2);*/

        holder.nombreP.setText(itemsProductos.get(position).getNombre());
        holder.precio.setText(String.valueOf(itemsProductos.get(position).getPrecio()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.cuenta.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.cuenta.getText()))+1));
            }
        });
        holder.restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(String.valueOf(holder.cuenta.getText()))>0) {
                    holder.cuenta.setText(String.valueOf(Integer.parseInt(String.valueOf(holder.cuenta.getText())) - 1));
                }
            }
        });
        holder.cuenta.addTextChangedListener(new watcherCalculo1(String.valueOf(holder.nombreP.getText()), holder.cuenta, Interfaz));
    }
    /*public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }*/

    /*public Cursor getCursor() {
        return cursor;
    }*/
}
