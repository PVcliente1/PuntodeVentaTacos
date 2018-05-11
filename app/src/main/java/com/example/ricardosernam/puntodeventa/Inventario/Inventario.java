package com.example.ricardosernam.puntodeventa.Inventario;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.DatabaseHelper;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

import java.util.ArrayList;

//public class Inventario extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
public class Inventario extends Fragment {
    public static RecyclerView recyclerView;
    public static LinearLayoutManager layoutManager;
    public static AdaptadorInventario adapter;
    public static SQLiteDatabase db;
    public static TextView emptyView;
    public static Cursor nombre;
    public static ArrayList<Inventario_class> itemsInventario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inventario, container, false);

        itemsInventario= new ArrayList <>(); ///Arraylist que contiene los productos
        DatabaseHelper admin=new DatabaseHelper(getContext(), ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        db=admin.getWritableDatabase();

        SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO);
        emptyView = (TextView) view.findViewById(R.id.recyclerview_data_empty);

        recyclerView = view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getContext());

        relleno();
        return view;
    }
    public static void relleno(){
        nombre=db.rawQuery("select nombre, existente from productos inner join inventario_detalles on productos.idRemota=inventario_detalles.idproducto" ,null);

        if(nombre.moveToFirst()) {///si hay un elemento
            itemsInventario.add(new Inventario_class(nombre.getString(0), nombre.getFloat(1)));
            while (nombre.moveToNext()) {
                itemsInventario.add(new Inventario_class(nombre.getString(0), nombre.getFloat(1)));
            }
            emptyView.setVisibility(View.INVISIBLE);
        }
        else{
            emptyView.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorInventario(itemsInventario);
        recyclerView.setAdapter(adapter);
    }
}
