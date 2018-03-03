package com.example.ricardosernam.puntodeventa.Miembros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Productos.Unidades_class;
import com.example.ricardosernam.puntodeventa.R;

import java.util.ArrayList;

public class Miembros extends Fragment {
    LinearLayout contenedor, infoMiembro, miembroL;
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor, editarMiembro, imagen, btneliminarSeleccionado;
    private ArrayList<Unidades_class> itemsUnidades;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_miembros, container, false);
        btnNuevoVendedor = view.findViewById(R.id.BtnAgregarVendedor);
        editarMiembro = view.findViewById(R.id.BtnEditarMiembro);
        btneliminarSeleccionado=view.findViewById(R.id.BtnEliminarMiembro);
        imagen = view.findViewById(R.id.BtnImagen);
        spinner = view.findViewById(R.id.SPvendedores);
        infoMiembro=view.findViewById(R.id.LLinformación);
        //miembroL=view.findViewById(R.id.LLmiembros);
        getFragmentManager().beginTransaction().replace(R.id.LLmiembros, new MiPerfil()).commit();

        adapterSpinner();


        btnNuevoVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_miembros().show(getFragmentManager(),"Agregar Miembro");
            }
        });

        editarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagen.setVisibility(View.VISIBLE);
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    infoMiembro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


    //procedimiento para agregar datos al spinner
    public void adapterSpinner(){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idmiembro AS _id, nombre from Miembros", null);

        if (c.getCount() > 0){
            //adapter
            String[] desde = new String[] {"nombre"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            spinner.setAdapter(adapter);
        }
    }

}
