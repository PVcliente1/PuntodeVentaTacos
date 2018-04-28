package com.example.ricardosernam.puntodeventa.Inventario;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.provider.ContractParaGastos;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.ui.AdaptadorDeGastos;

public class Inventario extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AdaptadorDeGastos adapter;
    private TextView emptyView;
    private LoaderManager lm;
    private MenuItem importar, exportar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_inventario, container, false);

        recyclerView = view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdaptadorDeGastos(getContext());
        recyclerView.setAdapter(adapter);
        emptyView = (TextView) view.findViewById(R.id.recyclerview_data_empty);

        lm=getActivity().getSupportLoaderManager();

        lm.initLoader(0, null, this);

        SyncAdapter.inicializarSyncAdapter(getContext());
        return view;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        emptyView.setText("Cargando datos...");
        // Consultar todos los registros
        return new CursorLoader(getContext(), ContractParaGastos.CONTENT_URI, null, null, null, null);
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        emptyView.setText("");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}
