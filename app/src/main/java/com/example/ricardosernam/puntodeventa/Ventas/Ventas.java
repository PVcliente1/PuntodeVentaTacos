package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment implements Pro_DialogFragment.agregado {     /////Fragment de categoria ventas
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private android.support.v4.app.FragmentManager fm;
    private ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados
    private Button productos, escanear, historial;
    private EditText codigo;
    private SQLiteDatabase db;
    private ContentValues values;
    private Cursor fila, fila2, datosSeleccionado;
    private Pro_DialogFragment pro;
    private Historial_DialogFragment DFhistorial;
    private TextView total;
    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos

    /*@SuppressLint("ValidFragment")
    public Ventas(ArrayList tipo) {
        this.itemsHistorial=tipo;   /////viene del mainActivity (Mediante Interface)
    }*/
    @SuppressLint("ValidFragment")
    public Ventas() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();
        fm= getActivity().getSupportFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        total=view.findViewById(R.id.TVtotal);
        //DFhistorial=new Historial_DialogFragment(itemsHistorial);  ////enviamos el array con las compras al fragment de historial

        productos= view.findViewById(R.id.BtnProductos);
        historial= view.findViewById(R.id.BtnHistorial);
        escanear= view.findViewById(R.id.BtnEscanear);
        codigo=view.findViewById(R.id.ETcodigo);

        adapter = new Cobrar_ventasAdapter(getActivity(), itemsCobrar, total);///llamamos al adaptador y le enviamos el array como parametro
        recycler = view.findViewById(R.id.RVproductosSeleccionados);///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);


        //fila=db.rawQuery("select nombre, ruta_imagen from Productos" ,null);

        /*if(fila.moveToFirst()) {///si hay un elemento
            itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1)));
                while (fila.moveToNext()) {
                    itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1)));
                }
        }*/
        //pro =new Pro_DialogFragment(itemsProductos, itemsCobrar);
        pro =new Pro_DialogFragment();

        codigo.setInputType(InputType.TYPE_NULL);

        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,2);//inicializar el activity con RequestCode2
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///mandamos llamar el DialogFragment
                pro.show(getChildFragmentManager(), "Pro_DialogFragment");
            }
        });
        historial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DFhistorial.show(fm, "Historial_DialogFragment");
            }
        });

        return view;
    }

    @Override
    public void agregar(String seleccionado) {
        datosSeleccionado=db.rawQuery("select unidad, nombre, precio_venta from Productos where nombre='"+seleccionado+"'" ,null);
        if(datosSeleccionado.moveToFirst()) {
            //Toast.makeText(getContext(), datosSeleccionado.getString(1), Toast.LENGTH_SHORT).show();
            itemsCobrar.add(new Cobrar_ventas_class(datosSeleccionado.getString(0), datosSeleccionado.getString(1), datosSeleccionado.getString(2)));//obtenemos el cardview seleccionado y lo agregamos a items2
            adapter.notifyDataSetChanged();
        }
    }
    ///agregar nuevo producto
    /*@Override
    public void agregar(String unidad, String nombre, String precio) {
        itemsCobrar.add(new Cobrar_ventas_class(unidad, nombre, precio));
        adapter.notifyDataSetChanged();
    }*/
    //metodo para obtener resultados DEL ESCANER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && data != null) {
            //obtener resultados
            fila2=db.rawQuery("select unidad, nombre, precio_venta from Productos where codigo_barras='"+data.getStringExtra("BARCODE")+"'" ,null);

            if(fila2.moveToFirst()) {
                itemsCobrar.add(new Cobrar_ventas_class(fila2.getString(0), fila2.getString(1), fila2.getString(2)));//obtenemos el cardview seleccionado y lo agregamos a items2
                fm.beginTransaction().replace(R.id.LOcobrar, new Cobrar_ventas_Fragment(itemsCobrar)).commit(); ///sustituimos el layout frament por el del recycler de cobrar
            }
        }
    }
}
