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

import com.example.ricardosernam.puntodeventa.DatabaseHelper;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

import java.util.ArrayList;

//public class Inventario extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
public class Inventario extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorInventario adapter;
    private SQLiteDatabase db;
    private TextView emptyView;
    private LoaderManager lm;
    private Cursor existente, nombre;
    private ArrayList<Inventario_class> itemsInventario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inventario, container, false);

        itemsInventario= new ArrayList <>(); ///Arraylist que contiene los productos
        DatabaseHelper admin=new DatabaseHelper(getContext(), ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        db=admin.getWritableDatabase();

        SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO);

        /*existente=db.rawQuery("select existente from inventario_detalles" ,null);
        nombre=db.rawQuery("select nombre from productos where idproducto=(select idproducto from inventario_detalles where idproducto=productos.idproducto)" ,null);
        //nombre=db.rawQuery("select nombre from productos inner join inventario_detalles on productos.idproducto=inventario_detalles.idproducto" ,null);

        if(existente.moveToFirst()) {///si hay un elemento
            itemsInventario.add(new Inventario_class(nombre.getString(0), existente.getFloat(0)));
            while (existente.moveToNext()) {
                itemsInventario.add(new Inventario_class(nombre.getString(0), existente.getFloat(0)));
            }
        }*/
        recyclerView = view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorInventario(itemsInventario, getContext());
        recyclerView.setAdapter(adapter);
        emptyView = (TextView) view.findViewById(R.id.recyclerview_data_empty);

        //lm=getActivity().getSupportLoaderManager();

        //lm.initLoader(0, null, this);
        return view;
    }
    /*@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Consultar todos los registros
        return new CursorLoader(getContext(), ContractParaProductos.CONTENT_URI_INVENTARIO, null, null, null, null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        if (data.moveToFirst()){//hay algo
            emptyView.setVisibility(View.INVISIBLE);
        }
        else {  //no hay nada
            emptyView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }*/
}
