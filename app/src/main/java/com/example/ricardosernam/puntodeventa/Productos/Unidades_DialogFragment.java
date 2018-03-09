package com.example.ricardosernam.puntodeventa.Productos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Cobrar_ventasAdapter;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ricardo Serna M on 27/02/2018.
 */
@SuppressLint("ValidFragment")
public class Unidades_DialogFragment extends android.app.DialogFragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Cursor fila, ultimaFila;
    private Button agregarNuevaUnidad, cancelar;
    private EditText capturarNuevaUnidad;
    private interfazUnidades_OnClick Interfaz;
    private ContentValues values;
    private SQLiteDatabase db;
    private ArrayList<Unidades_class> itemsUnidades;
    private View view2;

    @SuppressLint("ValidFragment")
    public Unidades_DialogFragment(interfazUnidades_OnClick Interfaz){
        this.Interfaz=Interfaz;
    }
    @SuppressLint("ValidFragment")
    public Unidades_DialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.dialog_fragment_unidades, container, false);
        getDialog().setTitle("Unidades");
        ////programamos lo botones del dialog
        agregarNuevaUnidad=view2.findViewById(R.id.BtnNuevaUnidad);
        cancelar=view2.findViewById(R.id.BtnCancelar);
        capturarNuevaUnidad=view2.findViewById(R.id.ETnuevaUnidad);
        ////llenamos el fragment con las unidades disponibles
        itemsUnidades=new ArrayList<>() ;
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();

        fila=db.rawQuery("select nombre_unidad from Unidades" ,null);

        if(fila.moveToFirst()) {///si hay un elemento
            itemsUnidades.add(new Unidades_class(fila.getString(0)));
            while (fila.moveToNext()){
                itemsUnidades.add(new Unidades_class(fila.getString(0)));
            }
        }
        //mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        adapter = new UnidadesAdapter(view2.getContext(), itemsUnidades, new interfazUnidades_OnClick() {
            @Override
            public void onClick(View v, String unidad) {
                dismiss();
                Interfaz.onClick(v, unidad);
            }
        }, new interfazUnidades_OnClick() {
            @Override
            public void onClick(View v, String unidad) { //si se requiere eliminar una unidad.
                db.delete(" Unidades ","nombre_unidad='"+unidad+"'", null);
                Toast.makeText(getActivity(), "Borraste "+ unidad, Toast.LENGTH_LONG).show();
            }
        });///llamamos al adaptador y le enviamos el array como parametro
        recycler = view2.findViewById(R.id.RVrecicladorunidades);///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        agregarNuevaUnidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(capturarNuevaUnidad.getText())){
                    Toast.makeText(getActivity(), "Ingresa la unidad a agregar", Toast.LENGTH_LONG).show();
                }
                else {///agregamos la nueva unidad
                    Toast.makeText(getActivity(), "Se ha agregado la nueva unidad", Toast.LENGTH_LONG).show();
                    values.put("nombre_unidad", String.valueOf(capturarNuevaUnidad.getText()));
                    db.insertOrThrow("Unidades",null, values);
                    capturarNuevaUnidad.setText(" ");
                    ultimaFila=db.rawQuery("select nombre_unidad from Unidades" ,null);//aqui esta el error
                    ultimaFila.moveToLast();
                    itemsUnidades.add(new Unidades_class(ultimaFila.getString(0)));
                    adapter.notifyDataSetChanged();
                    db.close();
                }
            }
        });
        return view2;
    }
}
