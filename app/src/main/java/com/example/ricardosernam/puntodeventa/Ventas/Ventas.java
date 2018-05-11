package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.DatabaseHelper;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
//public class Ventas extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {     /////Fragment de categoria ventas
public class Ventas extends Fragment  {     /////Fragment de categoria ventas

private RecyclerView recycler;
    private Pro_ventasAdapter adapter;
    private LoaderManager lm;
    private RecyclerView.LayoutManager lManager;
    private android.support.v4.app.FragmentManager fm;
    private FragmentManager fm2;
    private Button eliminarCompra,aceptarCompra;
    private ContentValues values;
    private CardView cobro;
    private TextView total;
    private SQLiteDatabase db;
    private Cursor datosSeleccionado, productos, existente;
    private TextView emptyView;
    private ArrayList<Pro_ventas_class> itemsProductos;
    private ArrayList<Cobrar_ventas_class> itemsCobrar= new ArrayList <>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);

        DatabaseHelper admin=new DatabaseHelper(getContext(), ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        db=admin.getWritableDatabase();

        fm= getActivity().getSupportFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        fm2= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto

        values = new ContentValues();
        emptyView = (TextView) view.findViewById(R.id.recyclerview_data_empty);
        itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos

        total=view.findViewById(R.id.TVtotal);
        cobro=view.findViewById(R.id.CVcobrar);

        eliminarCompra = view.findViewById(R.id.BtnEliminarCompra);
        aceptarCompra = view.findViewById(R.id.BtnAceptarCompra);


        recycler = view.findViewById(R.id.RVrecicladorPro); ///declaramos el recycler
        relleno();
        return view;
    }
    public void calcularTotal(){
        float suma=0;
        for(int i=0; i<itemsCobrar.size(); i++){
            suma=suma+itemsCobrar.get(i).getSubTotal();
            total.setText(String.valueOf(suma));
        }
    }
    public void relleno(){    ///llamamos el adapter del recycler
        productos=db.rawQuery("select nombre, precio, porcion, guisado from productos where precio!=0" ,null);
        if(productos.moveToFirst()) {///si hay un elemento
            itemsProductos.add(new Pro_ventas_class(productos.getString(0), productos.getFloat(1), productos.getFloat(2), productos.getString(3)));
            while (productos.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(productos.getString(0), productos.getFloat(1), productos.getFloat(2), productos.getString(3)));
            }
            cobro.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
        else{
            cobro.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        lManager = new GridLayoutManager(this.getActivity(),2);  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new Pro_ventasAdapter(itemsProductos, new actualizado() {  ///obtenemos los datos capturados para cada producto
            @Override
            public void actualizar(int cantidad, String seleccionado) {
                datosSeleccionado=db.rawQuery("select precio, porcion from productos where nombre='"+seleccionado+"'" ,null);
                if(datosSeleccionado.moveToFirst()) {
                    itemsCobrar.add(new Cobrar_ventas_class(seleccionado, cantidad, datosSeleccionado.getFloat(0), cantidad*datosSeleccionado.getFloat(0), datosSeleccionado.getFloat(1)));//obtenemos el cardview seleccionado y lo agregamos a items2
                }
                ///comprobamos si se repite
                for(int i=0; i<itemsCobrar.size(); i++) {
                    String dato=itemsCobrar.get(i).getNombre();
                    for(int j=i+1; j<itemsCobrar.size(); j++) {
                        if(dato.equals(itemsCobrar.get(j).getNombre())){  ///si se repite
                            if(itemsCobrar.get(j).getCantidad()==0){  ///si es cero, lo eliminamos de la lista
                                itemsCobrar.remove(j);   ////eliminamos el previamente agregado
                                itemsCobrar.remove(i);   ////eliminamos el previamente agregado
                            }
                            else {
                                itemsCobrar.remove(i);   ////eliminamos el previamente agregado
                            }
                        }
                    }
                }
                calcularTotal();
                if(itemsCobrar.isEmpty()){
                    total.setText("0.0");
                    eliminarCompra.setVisibility(View.INVISIBLE);
                    aceptarCompra.setVisibility(View.INVISIBLE);
                }
                else{
                    eliminarCompra.setVisibility(View.VISIBLE);
                    aceptarCompra.setVisibility(View.VISIBLE);
                }
            }
        }, getContext());
        recycler.setAdapter(adapter);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eliminarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  ///al eliminar compra mostramos un AlertDialog
                AlertDialog.Builder cancelarVenta = new AlertDialog.Builder(getActivity());
                cancelarVenta.setTitle("Cuidado");
                cancelarVenta.setMessage("¿Cancelar venta?");
                cancelarVenta.setCancelable(false);
                cancelarVenta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        itemsCobrar.removeAll(itemsCobrar);
                        relleno();
                        eliminarCompra.setVisibility(View.INVISIBLE);
                        aceptarCompra.setVisibility(View.INVISIBLE);
                        total.setText("0.0");
                    }
                });
                cancelarVenta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        cancelarCompra.dismiss();
                    }
                });
                cancelarVenta.show();
            }
        });
        aceptarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder aceptarVenta = new AlertDialog.Builder(getActivity());
                aceptarVenta.setTitle("Cuidado");
                aceptarVenta.setMessage("¿Aceptar venta?");
                aceptarVenta.setCancelable(false);
                aceptarVenta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        for(int i=0; i<itemsCobrar.size(); i++) {
                            existente=db.rawQuery("select idproducto, existente from inventario_detalles WHERE idproducto=(select idproducto from productos where nombre=(select guisado from productos where nombre='"+itemsCobrar.get(i).getNombre()+"'))" ,null);
                            if(existente.moveToFirst()){
                                float porcion = existente.getFloat(1) - (itemsCobrar.get(i).getCantidad() * itemsCobrar.get(i).getPorcion());
                                values.put("existente", porcion);
                                db.update("inventario_detalles", values, "idproducto='" + existente.getString(0) + "'", null);
                            }
                        }
                            Toast.makeText(getContext(), "Venta exitosa", Toast.LENGTH_LONG ).show();
                        itemsCobrar.removeAll(itemsCobrar);
                        relleno();
                        eliminarCompra.setVisibility(View.INVISIBLE);
                        aceptarCompra.setVisibility(View.INVISIBLE);
                        total.setText("0.0");
                    }
                });
                aceptarVenta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        cancelarCompra.dismiss();
                    }
                });
                aceptarVenta.show();
            }
        });
    }
}
