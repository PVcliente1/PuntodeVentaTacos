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
        void actualizar(String unidad, String nombre, int cantidad, String precio, int position, int subTotal);
    }

    public class Productos_ventasViewHolder extends RecyclerView.ViewHolder{    ////clase donde van los elementos del cardview
        public TextView nombreP, tipoD, unidad;
        public EditText cantidad, precio;
        public TextView subtotal;
        public Button eliminarArt, eliminarCompra;
        public CheckBox descuento;
        public actualizado Interfaz;

        public Productos_ventasViewHolder(View v) {
            super(v);
            nombreP = v.findViewById(R.id.TVnombreProductoCobrar);  ///cardviews donde va el nombre del producto
            unidad = v.findViewById(R.id.TVunidadProductoCobrar);
            eliminarArt = v.findViewById(R.id.BtnEliminarArt);
            eliminarCompra = v.findViewById(R.id.BtnEliminarCompra);
            descuento = v.findViewById(R.id.CBDescuento);
            tipoD = v.findViewById(R.id.TVtipoDescuento);
            precio = v.findViewById(R.id.ETprecio);
            cantidad = v.findViewById(R.id.ETcantidad);
            subtotal = v.findViewById(R.id.TVsubtotal);
            Interfaz=(actualizado) fragment;
        }
    }
    public static class MyTextWatcher implements TextWatcher{
        private EditText cantidad;
        private EditText precio;
        private TextView subtotal;
        private actualizado Interfaz;
        private String unidad, nombre;
        private int position;

        public MyTextWatcher(String unidad, String nombre,  EditText cantidad, EditText precio, TextView subtotal, actualizado Interfaz, int position) {
            this.unidad=unidad;
            this.nombre=nombre;
            this.cantidad = cantidad;
            this.precio = precio;
            this.subtotal = subtotal;
            this.Interfaz=Interfaz;
            this.position=position;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!(TextUtils.isEmpty(cantidad.getText()))&!(TextUtils.isEmpty(precio.getText()))) {
                subtotal.setText(String.valueOf(Integer.parseInt(String.valueOf((cantidad.getText()))) * Integer.parseInt(String.valueOf((precio.getText())))));
                Interfaz.actualizar(unidad, nombre, Integer.parseInt(String.valueOf((cantidad.getText()))), String.valueOf((precio.getText())), position, Integer.parseInt(String.valueOf(subtotal.getText())));
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //cantidad.setInputType(InputType.TYPE_NULL);  ///cerramos el teclado
            //precio.setInputType(InputType.TYPE_NULL);  ///cerramos el teclado
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
        holder.unidad.setText(itemsCobrar.get(position).getUnidad());
        holder.nombreP.setText(itemsCobrar.get(position).getNombre());
        holder.precio.setText(itemsCobrar.get(position).getPrecio());
        holder.cantidad.setText(String.valueOf(itemsCobrar.get(position).getCantidad()));
        holder.subtotal.setText(String.valueOf(itemsCobrar.get(position).getSubTotal()));

        MyTextWatcher watcher=new MyTextWatcher( String.valueOf(holder.unidad.getText()), String.valueOf(holder.nombreP.getText()), holder.cantidad, holder.precio, holder.subtotal, holder.Interfaz, position);
        holder.cantidad.addTextChangedListener(watcher);
        holder.precio.addTextChangedListener(watcher);

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
                       final AlertDialog.Builder tipoDescuento= new AlertDialog.Builder(context);
                       tipoDescuento.setTitle("Descuentos");
                       tipoDescuento.setMessage("Seleccion un tipo de descuento");
                       tipoDescuento.setCancelable(false);
                       tipoDescuento.setPositiveButton("Normal  (%)", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface tipoDescuento, int id) {
                               holder.tipoD.setText("Normal (%)");

                               tipoDescuento.dismiss();
                           }
                       });
                       tipoDescuento.setNegativeButton("Especial (%)", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface tipoDescuento, int id) {
                               holder.tipoD.setText("Especial (%)");
                               tipoDescuento.dismiss();
                           }
                       });
                       tipoDescuento.show();
                   }
                   else{
                       holder.tipoD.setText(" ");
                   }
                }
            });
    }
}
