package com.example.ricardosernam.puntodeventa.Productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickCodigo;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickElementosProductos;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickImagen;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter <ProductosAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<Pro_ventas_class> itemsProductos;
    private Context context;
    private interfazUnidades_OnClick Interfaz;
    private interfaz_OnClickCodigo Interfaz2;
    private interfaz_OnClickImagen Interfaz3;
    private interfaz_OnClick Interfaz4;
    private interfaz_OnClickElementosProductos Interfaz5;


    public ProductosAdapter(Context context, ArrayList<Pro_ventas_class> itemsProductos, interfazUnidades_OnClick Interfaz, interfaz_OnClickCodigo Interfaz2, interfaz_OnClickImagen Interfaz3, interfaz_OnClickElementosProductos Interfaz5, interfaz_OnClick Interfaz4) {  ///recibe el arrayProductos como parametro y la interface
        this.context=context;
        this.itemsProductos = itemsProductos;
        this.Interfaz=Interfaz;
        this.Interfaz2=Interfaz2;
        this.Interfaz3=Interfaz3;
        this.Interfaz5=Interfaz5;
        this.Interfaz4=Interfaz4;
    }

    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
        public EditText codigo, nombreP, precio, unidad;
        public ImageView imagen;
        public Button editar, eliminar, aceptarM, cancelarM, traerImagen, escanear;
        public LinearLayout botones;
        public Productos_ventasViewHolder(View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            codigo = v.findViewById(R.id.ETcodigo);  ////Textview donde se coloca el nombre del producto
            nombreP = v.findViewById(R.id.ETnombre);  ////Textview donde se coloca el nombre del producto
            precio=v.findViewById(R.id.ETprecio);
            unidad=v.findViewById(R.id.ETunidad);
            editar=v.findViewById(R.id.BtnEditarProducto);
            eliminar=v.findViewById(R.id.BtnEliminarProducto);
            aceptarM=v.findViewById(R.id.BtnAceptarProducto);
            cancelarM=v.findViewById(R.id.BtnCancelarProducto);
            traerImagen=v.findViewById(R.id.BtnImagen);
            escanear=v.findViewById(R.id.BtnEscanear);
            botones=v.findViewById(R.id.LObotones);
            imagen=v.findViewById(R.id.IVimagenProducto);
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
        holder.codigo.setText(itemsProductos.get(position).getCodigo());
        holder.nombreP.setText(itemsProductos.get(position).getNombre());
        holder.precio.setText(itemsProductos.get(position).getPrecio());
        holder.unidad.setText(itemsProductos.get(position).getUnidad());
        holder.imagen.setImageURI(Uri.parse(itemsProductos.get(position).getFoto()));

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interfaz5.onClick(String.valueOf(holder.nombreP.getText()), holder.codigo, holder.nombreP, holder.imagen, holder.unidad, holder.precio);
                holder.editar.setEnabled(false);
                holder.codigo.setEnabled(true);
                holder.nombreP.setEnabled(true);
                holder.precio.setEnabled(true);
                holder.unidad.setEnabled(true);
                holder.botones.setVisibility(View.VISIBLE);
                holder.traerImagen.setVisibility(View.VISIBLE);
                holder.escanear.setVisibility(View.VISIBLE);
                holder.aceptarM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {////guardamos los cambios realizados
                        Interfaz4.onClick(view);
                        holder.editar.setEnabled(true);
                        holder.nombreP.setEnabled(false);
                        holder.precio.setEnabled(false);
                        holder.unidad.setEnabled(false);
                        holder.codigo.setEnabled(false);
                        holder.botones.setVisibility(View.GONE);
                        holder.escanear.setVisibility(View.GONE);
                        holder.traerImagen.setVisibility(View.GONE);
                        Toast.makeText(context, "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.cancelarM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.editar.setEnabled(true);
                        holder.nombreP.setEnabled(false);
                        holder.precio.setEnabled(false);
                        holder.codigo.setEnabled(false);
                        holder.unidad.setEnabled(false);
                        holder.traerImagen.setVisibility(View.GONE);
                        holder.escanear.setVisibility(View.GONE);
                        holder.botones.setVisibility(View.GONE);
                    }
                });

            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder eliminarProducto = new AlertDialog.Builder(context);
                eliminarProducto .setTitle("Cuidado");
                eliminarProducto .setMessage("¿Seguro que quieres eliminar este producto?");
                eliminarProducto .setCancelable(false);
                eliminarProducto .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface eliminarProducto , int id) {
                        itemsProductos.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,itemsProductos.size());
                        Interfaz.onClick(view, String.valueOf(holder.nombreP.getText()));
                        eliminarProducto.dismiss();
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
        holder.unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Unidades_DialogFragment(new interfazUnidades_OnClick() {
                    @Override
                    public void onClick(View v, String unidadSeleccionada) {
                        holder.unidad.setText(unidadSeleccionada);
                    }
                }).show(manager, "Unidades_DialogFragment");
            }
        });
        holder.escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interfaz2.onClick(view, holder.codigo);
            }
        });
        holder.traerImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Interfaz3.onClick(view, holder.imagen);
            }
        });
    }

}
