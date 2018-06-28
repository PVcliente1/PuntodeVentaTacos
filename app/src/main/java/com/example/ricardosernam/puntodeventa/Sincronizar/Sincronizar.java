package com.example.ricardosernam.puntodeventa.Sincronizar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.ricardosernam.puntodeventa.Ventas.Ventas;
import com.example.ricardosernam.puntodeventa.provider.ContractParaProductos;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

import java.text.SimpleDateFormat;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.ricardosernam.puntodeventa.Inventario.Inventario.db;

public class Sincronizar extends Fragment {
    public Fragment myFragment;
    public static Spinner carritos;
    public static Context context;
    public static ProgressDialog progressDialog;
    public static SQLiteDatabase db;
    public  EditText ip;
    public View view;
    public ContentValues values=new ContentValues();
    public static Button establecer, importar, exportar, buscar;


    @Override
   public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if(view==null){
                view=inflater.inflate(R.layout.fragment_sincronizar, container, false);
            }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        /*if (state != null) {
            Toast.makeText(getContext(), state.getString("NUMERO") , LENGTH_LONG).show();
             //myFragment = getFragmentManager().getFragment(state,"Sincronizar");
            //getFragmentManager().beginTransaction().replace(R.id.LOprincipal, myFragment).commit(); ///cambio de fragment*
        }*/
        DatabaseHelper admin=new DatabaseHelper(getContext(), ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        db=admin.getWritableDatabase();
        context=getContext();
        carritos=(Spinner) getActivity().findViewById(R.id.SpnCarritos);
        ip=getActivity().findViewById(R.id.ETip);
        importar=getActivity().findViewById(R.id.BtnImportar);
        exportar=getActivity().findViewById(R.id.BtnExportar);
        buscar=getActivity().findViewById(R.id.BtnBuscarCarritos);
        establecer=getActivity().findViewById(R.id.BtnEstablecer);
        establecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establecer.getText().equals("Establecer")){
                    establecer.setText("Modificar");
                    ip.setEnabled(false);
                    //new Constantes("http://"+String.valueOf(ip.getText()));
                    new Constantes("http://192.168.0.8");
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
                db.execSQL("delete from carritos");  ///vaciamos la tabla
                SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_CARRITO, null);
                SyncAdapter.sincronizarAhora(getContext(), false, null);

            }
        });
        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idcarrito= String.valueOf(carritos.getSelectedItemId());    ////obtenemos el carrito sincronizado
                SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO, idcarrito);
                SyncAdapter.sincronizarAhora(getContext(), false, null);

                progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Importando datos...");
                progressDialog.show();
                }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.UPDATE_URL_INVENTARIO_DETALLE);

                progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Exportando datos...");
                progressDialog.show();
                }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {   ///solo entra cuando gira
        super.onSaveInstanceState(outState);
        //getFragmentManager().putFragment(outState,"Sincronizar", this);
        //Toast.makeText(getContext(), "Entra a onsavedintance" , LENGTH_LONG).show();
        outState.putString("NUMERO", String.valueOf(ip.getText()));
    }

    public static void adapterSpinner(){
        //cursor
        Cursor c = db.rawQuery("select idRemota AS _id, descripcion from carritos", null);


        if (c.moveToFirst()){
            //adapter
            String[] desde = new String[] {"descripcion"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(context, android.R.layout.simple_spinner_item, c, desde, para);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            carritos.setAdapter(adapter);
        }
    }
}
