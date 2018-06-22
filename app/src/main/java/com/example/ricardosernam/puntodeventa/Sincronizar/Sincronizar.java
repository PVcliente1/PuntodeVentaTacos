package com.example.ricardosernam.puntodeventa.Sincronizar;

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
    public String carritoSeleccionado;
    public  EditText ip;
    public Cursor carrito;
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
            view=inflater.inflate(R.layout.fragment_sincronizar, container, false);
        //onRestoreInstanceState(savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if (state == null) {
            Toast.makeText(getContext(), "Nuevo Fragment Created" , LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), state.getString("NUMERO") , LENGTH_LONG).show();
             //myFragment = getFragmentManager().getFragment(state,"Sincronizar");
            //getFragmentManager().beginTransaction().replace(R.id.LOprincipal, myFragment).commit(); ///cambio de fragment*/
            //state.get
        }
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

            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.sincronizarAhora(getContext(), true, Constantes.INSERT_URL_VENTA);
                }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {   ///solo entra cuando gira
        super.onSaveInstanceState(outState);
        //getFragmentManager().putFragment(outState,"Sincronizar", this);
        Toast.makeText(getContext(), "Entra a onsavedintance" , LENGTH_LONG).show();
        outState.putString("NUMERO", String.valueOf(ip.getText()));
    }

    /*@Override
    public void onPause() {   ///cambio de fragment
        super.onPause();
        //Bundle out=new Bundle();
       // onSaveInstanceState(out);
        //
        // Toast.makeText(getContext(), "Pausado" , LENGTH_LONG).show();
        getArguments().putString("NUMERO", String.valueOf(ip.getText()));
        //out.putString("NUMERO", String.valueOf(ip.getText()));
    }

   /* @Override
    public void onResume() {   ///regresa al fragment pausado
        super.onResume();
        Toast.makeText(getContext(), "Resumido" , LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(), "On destroy View" , LENGTH_LONG).show();
    }

    @Override
    public void onStart() {  //////
        super.onStart();
        Toast.makeText(getContext(), "On Start" , LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(), "On Stop" , LENGTH_LONG).show();
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(), "On Destroy" , LENGTH_LONG).show();
        }

    @Override
    public void onAttach(Context context) { ////eeewe
        super.onAttach(context);
        Toast.makeText(getContext(), "On Atach" , LENGTH_LONG).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Toast.makeText(getContext(), "On Detach" , LENGTH_LONG).show();
        }*/
    //on

    public static void adapterSpinner(){
        DatabaseHelper admin = new DatabaseHelper(context, ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idRemota AS _id, descripcion from carritos", null);


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
