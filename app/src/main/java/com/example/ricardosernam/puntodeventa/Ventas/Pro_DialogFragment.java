package com.example.ricardosernam.puntodeventa.Ventas;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import java.util.ArrayList;
import java.util.HashSet;


@SuppressLint("ValidFragment")
public class Pro_DialogFragment extends android.support.v4.app.DialogFragment {     //clase que me crea el dialogFragment con productos
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private SQLiteDatabase db;
    private Cursor fila;
    private Button aceptarOrden;
    private Cursor datosSeleccionado;
    private ArrayList<Pro_ventas_class> itemsProductos;
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos
    private agregado Interfaz;

    public interface agregado {
        void agregar(ArrayList<Cobrar_ventas_class> itemsCobrar);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Interfaz = (agregado) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement Callback interface");
        }
    }

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.recyclerpro,container);
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        aceptarOrden=rootView.findViewById(R.id.BtnAceptarOrden);
        db=admin.getWritableDatabase();
        itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos

        fila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos" ,null);

        if(fila.moveToFirst()) {///si hay un elemento
            itemsProductos.add(new Pro_ventas_class(fila.getString(1), fila.getString(3)));
                while (fila.moveToNext()) {
                    itemsProductos.add(new Pro_ventas_class(fila.getString(1), fila.getString(3)));
                }
        }

        ////mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        final android.support.v4.app.FragmentManager fm= getFragmentManager();
        recycler = rootView.findViewById(R.id.RVrecicladorPro); ///declaramos el recycler
        lManager = new GridLayoutManager(this.getActivity(),2);  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new Pro_ventasAdapter(itemsProductos, new actualizado() {  ///obtenemos los datos capturados para cada producto
            @Override
            public void actualizar(int cantidad, String seleccionado) {
                datosSeleccionado=db.rawQuery("select precio_venta from Productos where nombre='"+seleccionado+"'" ,null);
                if(datosSeleccionado.moveToFirst()) {
                    itemsCobrar.add(new Cobrar_ventas_class(seleccionado, cantidad, datosSeleccionado.getFloat(0), cantidad*datosSeleccionado.getFloat(0)));//obtenemos el cardview seleccionado y lo agregamos a items2
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
                if(itemsCobrar.isEmpty()){
                    aceptarOrden.setEnabled(false);
                }
                else{
                    aceptarOrden.setEnabled(true);
                }
            }
        });
        ////cuando se acepta la ordn previamnete definida
        aceptarOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Interfaz.agregar(itemsCobrar);
            }
        });
        recycler.setAdapter(adapter);
        this.getDialog().setTitle("Productos");///cambiamos titulo del DialogFragment

        return rootView;
    }
}
