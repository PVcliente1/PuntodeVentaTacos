package com.example.ricardosernam.puntodeventa.Sincronizar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.DatabaseHelper;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

import static android.widget.Toast.LENGTH_LONG;

public class Sincronizar extends Fragment {
    public static Spinner carritos;
    public static Context context;
    public  EditText ip;
    public Button establecer, importar, exportar, buscar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sincronizar, container, false);
        context=view.getContext();
        carritos=(Spinner) view.findViewById(R.id.SpnCarritos);
        ip=view.findViewById(R.id.ETip);
        importar=view.findViewById(R.id.BtnImportar);
        exportar=view.findViewById(R.id.BtnExportar);
        buscar=view.findViewById(R.id.BtnBuscarCarritos);

        //SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO);
        establecer=view.findViewById(R.id.BtnEstablecer);
        establecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establecer.getText().equals("Establecer")){
                    establecer.setText("Modificar");
                    ip.setEnabled(false);
                    //new Constantes("http://"+String.valueOf(ip.getText()));
                    new Constantes("http://192.168.0.9");

                    //SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_CARRITO);

                    //SyncAdapter.sincronizarAhora(getContext(), false);
                }
                else{
                    establecer.setText("Establecer");
                    ip.setEnabled(true);
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //while (importar.isEnabled()){
                    SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_CARRITO);
                //Toast.makeText(getContext(), Constantes.GET_URL_CARRITO, LENGTH_LONG).show();
                SyncAdapter.sincronizarAhora(getContext(), false);
                //}
            }
        });
        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //importar.setEnabled(false);

                //Toast.makeText(getContext(), String.valueOf(carritos.getSelectedItemId()) ,Toast.LENGTH_SHORT).show();


                //SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO+carritos.getSelectedItemId());
                SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO);

                //Toast.makeText(getContext(), Constantes.GET_URL_INVENTARIO, LENGTH_LONG).show();

                SyncAdapter.sincronizarAhora(getContext(), false);
            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.sincronizarAhora(getContext(), true);
            }
        });
        return view;
    }
    public static void adapterSpinner(){
        DatabaseHelper admin = new DatabaseHelper(context, ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idRemota AS _id, descripcion, ubicacion from carritos", null);


        if (c.moveToFirst()){
            //adapter
            String[] desde = new String[] {"descripcion"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            carritos.setAdapter(adapter);
        }
    }
}
