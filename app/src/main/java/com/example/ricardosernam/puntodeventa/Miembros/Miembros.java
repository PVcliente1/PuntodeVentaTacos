package com.example.ricardosernam.puntodeventa.Miembros;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Productos.Unidades_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Miembros extends Fragment {
    LinearLayout contenedor, infoMiembro, miembroL;
    String[] items;
    Spinner spinner;
    Button btnNuevoVendedor, editarMiembro, imagen, btneliminarSeleccionado;
    private ArrayList<Unidades_class> itemsUnidades;
    FragmentManager fm;
    String rutaImagen;
    Uri selectedImage;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    private ImageView ponerImagen;



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
        ponerImagen = view.findViewById(R.id.ImgImagen);
        spinner = view.findViewById(R.id.SPvendedores);
        infoMiembro=view.findViewById(R.id.LLinformación);
        fm=getActivity().getFragmentManager();
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
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.DialogFragment dialog = new traerImagen(new interfaz_SeleccionarImagen() {
                    @Override
                    public void onClick(Intent intent, int requestCode) {
                        startActivityForResult(intent, requestCode);
                    }
                });
                dialog.show(fm, "NoticeDialogFragment");
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////1 para entrar a galeria y tomar una foto
        if (requestCode == 1) {
            //Uri selectedImage;///uri es la ruta
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen=selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ponerImagen.setImageURI(selectedImage);
                }
            }
        }
        //2 Captura de foto
        if(requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen=selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ponerImagen.setImageURI(selectedImage);
                }
            }
        }

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
