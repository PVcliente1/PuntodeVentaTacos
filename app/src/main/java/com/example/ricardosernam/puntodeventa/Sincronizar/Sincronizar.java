package com.example.ricardosernam.puntodeventa.Sincronizar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
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
import com.example.ricardosernam.puntodeventa.Inventario.AdaptadorInventario;
import com.example.ricardosernam.puntodeventa.Inventario.Inventario_class;
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
    public static  EditText ip;
    public static Cursor estado;
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
        DatabaseHelper admin=new DatabaseHelper(getContext(), ProviderDeProductos.DATABASE_NAME, null, ProviderDeProductos.DATABASE_VERSION);
        db=admin.getWritableDatabase();
        if(view==null){  //si la vista es nueva
                view=inflater.inflate(R.layout.fragment_sincronizar, container, false);
                context=getContext();
                ip=view.findViewById(R.id.ETip);
                carritos=view.findViewById(R.id.SpnCarritos);
                importar=view.findViewById(R.id.BtnImportar);
                exportar=view.findViewById(R.id.BtnExportar);
                buscar=view.findViewById(R.id.BtnBuscarCarritos);
                establecer=view.findViewById(R.id.BtnEstablecer);
                relleno();
            }
        adapterSpinner();
        return view;
    }

    public static void relleno(){
        estado=db.rawQuery("select ip, importado from estados" ,null);
        if(estado.moveToFirst()) {///si hay un elemento
            ip.setText(estado.getString(0));

            if(estado.getInt(1)==0) { //importar esta deshabilitado
                carritos.setEnabled(false);   //spinner
                //rgb(230,228,228)

                importar.setEnabled(false);
                exportar.setEnabled(true);    //boton
                buscar.setEnabled(false);     //boton
                importar.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); ///deshabilitado
                exportar.getBackground().setColorFilter(null);  //habilitado
                buscar.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); ///deshabilitado*/
            }
            else {///si hay un elemento     //importar esta habilitado
                carritos.setEnabled(true);

                importar.setEnabled(true);
                exportar.setEnabled(false);
                buscar.setEnabled(true);
                importar.getBackground().setColorFilter(null);  //habilitado
                exportar.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP); ///deshabilitado
                buscar.getBackground().setColorFilter(null);  //habilitado*/
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
             establecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establecer.getText().equals(" Establecer IP ")){  //validamos que no este vacio
                    if(TextUtils.isEmpty(ip.getText())){
                        Toast.makeText(getContext(), "Ingresa un valor", LENGTH_LONG).show();
                    }
                    else{
                        establecer.setText(" Modificar IP ");
                        ip.setEnabled(false);
                        ///guardamo el estado de la pantalla
                        values.put(ContractParaProductos.Columnas.IP,  String.valueOf(ip.getText()));
                        db.update("estados", values, null, null);

                        new Constantes("http://"+String.valueOf(ip.getText()));
                        //new Constantes("http://192.168.0.10");
                    }
                    }
                else{
                    establecer.setText(" Establecer IP ");
                    ip.setEnabled(true);
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.isEnabled()){
                    Toast.makeText(getContext(), "Establece la IP", LENGTH_LONG).show();
                }
                else{
                    db.execSQL("delete from carritos");  ///vaciamos la tabla
                    SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_CARRITO, null);
                    SyncAdapter.sincronizarAhora(getContext(), false, null);
                }
            }
        });
        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.isEnabled()){
                    Toast.makeText(getContext(), "Establece la IP", LENGTH_LONG).show();
                }
                else {
                    String idcarrito = String.valueOf(carritos.getSelectedItemId());    ////obtenemos el carrito sincronizado
                    SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO, idcarrito);
                    SyncAdapter.sincronizarAhora(getContext(), false, null);

                    progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Importando datos..." +
                            "Si tardamos checa tus servicios de XAMPP o tu conexión a Internet y espera que termine el proceso");
                    progressDialog.show();
                }
                }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.isEnabled()){
                    Toast.makeText(getContext(), "Establece la IP", LENGTH_LONG).show();
                }
                else {
                    SyncAdapter.sincronizarAhora(getContext(), true, Constantes.UPDATE_URL_INVENTARIO_DETALLE);

                    progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Exportando datos..." +
                            "Si tardamos checa tus servicios de XAMPP o tu conexión a Internet y espera que termine el proceso");
                    progressDialog.show();
                }
                }
        });
    }

    public static void adapterSpinner(){
        //cursor
        @SuppressLint("Recycle") Cursor c = db.rawQuery("select idRemota AS _id, descripcion from carritos", null);


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
