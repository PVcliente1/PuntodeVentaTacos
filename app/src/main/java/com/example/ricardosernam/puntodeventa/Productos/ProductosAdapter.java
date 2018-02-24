package com.example.ricardosernam.puntodeventa.Productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter <ProductosAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<Pro_ventas_class> itemsProductos;
    private interfaz_OnClick Interfaz;
    private Context context;

    public ProductosAdapter(ArrayList<Pro_ventas_class> itemsProductos, interfaz_OnClick Interfaz) {  ///recibe el arrayProductos como parametro y la interface
        this.itemsProductos = itemsProductos;
        this.Interfaz=Interfaz;
    }
    public ProductosAdapter(Context context, ArrayList<Pro_ventas_class> itemsProductos) {  ///recibe el arrayProductos como parametro y la interface
        this.context=context;
        this.itemsProductos = itemsProductos;
    }

    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public EditText nombreP, precio;
        public Button editar, eliminar, aceptarM, cancelarM, imagen;
        public LinearLayout botones;
        public Productos_ventasViewHolder(View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            nombreP = v.findViewById(R.id.ETnombre);  ////Textview donde se coloca el nombre del producto
            precio=v.findViewById(R.id.ETprecio);
            editar=v.findViewById(R.id.BtnEditarProducto);
            eliminar=v.findViewById(R.id.BtnEliminarProducto);
            aceptarM=v.findViewById(R.id.BtnAceptarProducto);
            cancelarM=v.findViewById(R.id.BtnCancelarProducto);
            imagen=v.findViewById(R.id.BtnImagen);
            botones=v.findViewById(R.id.LObotones);
            /*v.setOnClickListener(new View.OnClickListener() {  ///usamos desde aqui la interface(ya que aqui no podemos cerrar el Fragmentdialog y lo cerraremos en ventas
                @Override
                public void onClick(View view) {
                    Interfaz.onClick(view);
                }
            });*/
        }
    }
    @Override
    public int getItemCount() {
        return itemsProductos.size();
    }


    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjeta_productos, viewGroup, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Productos_ventasViewHolder holder, final int position) {
        final FragmentManager manager = ((Activity) context).getFragmentManager();
        holder.nombreP.setText(itemsProductos.get(position).getNombre());
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.nombreP.setEnabled(true);
                holder.precio.setEnabled(true);
                holder.botones.setVisibility(View.VISIBLE);
                holder.imagen.setVisibility(View.VISIBLE);
                holder.aceptarM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.nombreP.setEnabled(false);
                        holder.precio.setEnabled(false);
                        holder.botones.setVisibility(View.INVISIBLE);
                        holder.imagen.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.cancelarM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.nombreP.setEnabled(false);
                        holder.precio.setEnabled(false);
                        holder.imagen.setVisibility(View.INVISIBLE);
                        holder.botones.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder eliminarProducto = new AlertDialog.Builder(context);
                eliminarProducto .setTitle("Cuidado");
                eliminarProducto .setMessage("¿Seguro que quieres eliminar este producto?");
                eliminarProducto .setCancelable(false);
                eliminarProducto .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface eliminarProducto , int id) {
                        itemsProductos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,itemsProductos.size());
                        eliminarProducto .dismiss();
                    }
                });
                eliminarProducto .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface eliminarProducto , int id) {
                        eliminarProducto .dismiss();
                    }
                });
                eliminarProducto .show();
            }
        });
    }
}