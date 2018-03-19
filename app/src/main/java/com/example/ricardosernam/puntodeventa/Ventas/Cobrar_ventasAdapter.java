package com.example.ricardosernam.puntodeventa.Ventas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_descuento;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Descuentos;

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
    public interface actualizado {
        void actualizar(String unidad, String nombre, float cantidad, float precio, int position, float subTotal, int descuento);
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        public TextView nombreP, tipoD, porcentajeD, unidad;
        public EditText cantidad, precio, subtotal;
        public Button eliminarArt, eliminarCompra;
        public CheckBox descuento;
        public actualizado actualizar;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductoCobrar);  ///cardviews donde va el nombre del producto
            unidad = v.findViewById(R.id.TVunidadProductoCobrar);
            eliminarArt = v.findViewById(R.id.BtnEliminarArt);
            eliminarCompra = v.findViewById(R.id.BtnEliminarCompra);
            descuento = v.findViewById(R.id.CBDescuento);
            tipoD = v.findViewById(R.id.TVtipoDescuento);
            porcentajeD = v.findViewById(R.id.TVporcentajeDescuento);
            precio = v.findViewById(R.id.ETprecio);
            cantidad = v.findViewById(R.id.ETcantidad);
            subtotal = v.findViewById(R.id.TVsubtotal);
            actualizar=(actualizado) fragment;
        }
    }
    public static class MyTextWatcher implements TextWatcher{   ///detecta cambios en los editText
        private EditText cantidad,precio, subtotal;
        private TextView porcentajeD;
        private actualizado Interfaz;
        private String unidad, nombre;
        private int position;

        MyTextWatcher(String unidad, String nombre,  EditText cantidad, EditText precio, EditText subtotal, actualizado Interfaz, TextView porcentajeD, int position) {
            this.unidad=unidad;
            this.nombre=nombre;
            this.cantidad = cantidad;
            this.precio = precio;
            this.subtotal = subtotal;
            this.Interfaz=Interfaz;
            this.position=position;
            this.porcentajeD=porcentajeD;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!(TextUtils.isEmpty(cantidad.getText()))&!(TextUtils.isEmpty(precio.getText()))) {
                float totalParcial=Float.parseFloat(String.valueOf((cantidad.getText()))) * Float.parseFloat(String.valueOf((precio.getText())));
                subtotal.setText(String.valueOf(totalParcial-(Float.parseFloat(String.valueOf(porcentajeD.getText()))*totalParcial)/100));  ////hacemos el descuento
                Interfaz.actualizar(unidad, nombre, Float.parseFloat(String.valueOf((cantidad.getText()))), Float.parseFloat(String.valueOf((precio.getText()))), position, Float.parseFloat(String.valueOf(subtotal.getText())), Integer.parseInt(String.valueOf(porcentajeD.getText())));
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

        holder.unidad.setText(itemsCobrar.get(position).getUnidad());
        holder.nombreP.setText(itemsCobrar.get(position).getNombre());
        holder.precio.setText(String.valueOf(itemsCobrar.get(position).getPrecio()));
        holder.cantidad.setText(String.valueOf(itemsCobrar.get(position).getCantidad()));
        holder.subtotal.setText(String.valueOf(itemsCobrar.get(position).getSubTotal()));
        holder.porcentajeD.setText(String.valueOf(itemsCobrar.get(position).getDescuento()));
        ///llamamos al escuchador de cambios en los editText
        MyTextWatcher watcher = new MyTextWatcher(String.valueOf(holder.unidad.getText()), String.valueOf(holder.nombreP.getText()), holder.cantidad, holder.precio, holder.subtotal, holder.actualizar, holder.porcentajeD, position);
        holder.cantidad.addTextChangedListener(watcher);
        holder.precio.addTextChangedListener(watcher);
        holder.porcentajeD.addTextChangedListener(watcher);

        holder.eliminarArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  ///eliminamos un articulo comprado
                itemsCobrar.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,itemsCobrar.size());
                notifyDataSetChanged();
                if(itemsCobrar.isEmpty()){////ocultamos por completo el fragment donde se agregan   ///aqui esta el error
                    CardView cobro=((Activity) context).findViewById(R.id.CVcobrar);
                    LinearLayout opcionDeVenta=((Activity) context).findViewById(R.id.LLopcionDeVenta);
                    cobro.setVisibility(View.GONE);
                    opcionDeVenta.setVisibility(View.GONE);
                }
            }
        });
        holder.descuento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if(b) {
                       new Descuentos(new interfaz_descuento() {
                           @Override
                           public void descontar(String tipo, int porcentaje) {
                               holder.tipoD.setText(tipo);
                               holder.tipoD.setVisibility(View.VISIBLE);
                               holder.porcentajeD.setText(String.valueOf(porcentaje));
                               holder.porcentajeD.setVisibility(View.VISIBLE);
                           }
                       }).show(manager, "Descuentos");
                   }
                   else{
                       holder.tipoD.setText(" ");
                       holder.porcentajeD.setText("0");
                       holder.porcentajeD.setVisibility(View.INVISIBLE);
                   }
                }
            });
    }
}
