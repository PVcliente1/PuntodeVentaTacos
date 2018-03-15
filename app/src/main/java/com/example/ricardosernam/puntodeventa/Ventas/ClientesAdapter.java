package com.example.ricardosernam.puntodeventa.Ventas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.Productos.Unidades_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;

import java.util.ArrayList;

public class ClientesAdapter extends RecyclerView.Adapter <ClientesAdapter.Productos_ventasViewHolder>{  ///adaptador para el Fragmet Ventas
    private ArrayList<clientes_ventas_class> itemsClientes;
    private Context context;
    private interfazUnidades_OnClick Interfaz;
    private interfazUnidades_OnClick Interfaz2;

    public ClientesAdapter(Context context, ArrayList<clientes_ventas_class>  itemsClientes, interfazUnidades_OnClick Interfaz, interfazUnidades_OnClick Interfaz2) {  ///recibe el arrayProductos como parametro y la interface
        this.context=context;
        this.itemsClientes = itemsClientes;
        this.Interfaz=Interfaz;
        this.Interfaz2=Interfaz2;
    }

    public  class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        // Campos respectivos de un item
       public TextView nombre;
       public Button eliminar;
        public Productos_ventasViewHolder(View v) {   ////lo que se programe aqui es para cuando se le de clic a un item del recycler
            super(v);
            nombre = v.findViewById(R.id.TVnombreCliente);  ////Textview donde se coloca el nombre del producto
            v.setOnClickListener(new View.OnClickListener() {  ///usamos desde aqui la interface(ya que aqui no podemos cerrar el Fragmentdialog y lo cerraremos en ventas
                @Override
                public void onClick(View view) {
                    Interfaz.onClick(view, (String) nombre.getText());
                }
            });

        }
    }
    @Override
    public int getItemCount() {
        return itemsClientes.size();
    }
    @Override
    public Productos_ventasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarjeta_clientes, viewGroup, false);
        return new Productos_ventasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Productos_ventasViewHolder holder, final int position) {
        holder.nombre.setText(itemsClientes.get(position).getNombre());
    }
}