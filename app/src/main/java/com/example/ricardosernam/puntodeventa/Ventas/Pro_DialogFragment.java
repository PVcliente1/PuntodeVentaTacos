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
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Pro_DialogFragment extends android.support.v4.app.DialogFragment {     //clase que me crea el dialogFragment con productos
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private SQLiteDatabase db;
    private Cursor fila;
    private ArrayList<Pro_ventas_class> itemsProductos;
    private agregado Interfaz;

    public interface agregado {
        void agregar(String seleccionado);
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
        adapter = new Pro_ventasAdapter(itemsProductos, new interfaz_OnClick() { ///llamamos al adaptador y le enviamos el array y la interface  como parametro (la interface es un onclick)
            @Override
            public void onClick(View v) {  ////cuando se presione un Cardview...
                dismiss(); ////cerramos la ventana
                if(Interfaz!=null){  ///notificamos al fragment que se agrego para actualizar el recyclerview
                        Interfaz.agregar(itemsProductos.get(recycler.getChildAdapterPosition(v)).getNombre());
                    }
            }
        });
        recycler.setAdapter(adapter);
        this.getDialog().setTitle("Productos");///cambiamos titulo del DialogFragment

        return rootView;
    }
}
