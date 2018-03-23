package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.example.ricardosernam.puntodeventa.Productos.UnidadesAdapter;
import com.example.ricardosernam.puntodeventa.Productos.Unidades_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa.____herramientas_app.dialog_fragment_agregar_cliente;

import java.util.ArrayList;

/**
 * Created by Ricardo Serna M on 27/02/2018.
 */
@SuppressLint("ValidFragment")
public class Clientes_DialogFragment extends android.support.v4.app.DialogFragment implements agregado {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Cursor fila, ultimaFila;
    private Button agregarNuevoCliente, cancelar;
    private interfazUnidades_OnClick Interfaz;
    private SQLiteDatabase db;
    private ArrayList<clientes_ventas_class> itemsClientes;
    private View view2;

    @SuppressLint("ValidFragment")
    public Clientes_DialogFragment(interfazUnidades_OnClick Interfaz){
        this.Interfaz=Interfaz;
    }
    @SuppressLint("ValidFragment")
    public Clientes_DialogFragment(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view2 = inflater.inflate(R.layout.dialog_fragment_clientes, container, false);
        getDialog().setTitle("Clientes");
        ////programamos lo botones del dialog
        agregarNuevoCliente=view2.findViewById(R.id.BtnNuevaUnidad);
        cancelar=view2.findViewById(R.id.BtnCancelar);
        //fm=view2.getParentFragment();
        //fm=
        ////llenamos el fragment con las unidades disponibles
        itemsClientes=new ArrayList<>() ;
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();

        fila=db.rawQuery("select nombre from Clientes where idcliente!=1" ,null);

        if(fila.moveToFirst()) {///si hay un elemento
            itemsClientes.add(new clientes_ventas_class(fila.getString(0)));
            while (fila.moveToNext()){
                itemsClientes.add(new clientes_ventas_class(fila.getString(0)));
            }
        }
        //mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        adapter = new ClientesAdapter(view2.getContext(), itemsClientes, new interfazUnidades_OnClick() {
            @Override
            public void onClick(View v, String clienteSeleccionado) {
                dismiss();
                Interfaz.onClick(v, clienteSeleccionado);  //mandamos la unidad seleccionada al dialogFragment de nuevoProducto
            }
        }, new interfazUnidades_OnClick() {   ///si se presiona eliminar en la tarjeta
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
        agregarNuevoCliente.setOnClickListener(new View.OnClickListener() {   ////si queremos agregar un nuevo cliente
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_cliente().show(getChildFragmentManager(), "nuevo_cliente");
            }
        });
        return view2;
    }

    @Override
    public void agregar() {
        ultimaFila=db.rawQuery("select nombre from Clientes" ,null);//aqui esta el error
        ultimaFila.moveToLast();
        itemsClientes.add(new clientes_ventas_class(ultimaFila.getString(0)));
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();
    }
}
