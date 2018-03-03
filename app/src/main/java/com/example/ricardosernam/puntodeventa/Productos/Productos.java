package com.example.ricardosernam.puntodeventa.Productos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClickCodigo;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClickImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Productos extends Fragment{
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Cursor fila;
    private SQLiteDatabase db;
    private Button nuevoProducto;
    private android.app.FragmentManager fm;
    private EditText codigo;

    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///

    @SuppressLint("ValidFragment")
    public Productos(){
    }
   /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////productos de ejemplo//////////////
       itemsProductos.add(new Pro_ventas_class("09879241412", "Coca", "20"));
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_productos, container, false);

        nuevoProducto=view.findViewById(R.id.BtnNuevoProducto);
        fm=getActivity().getFragmentManager();
        codigo= new EditText(getContext()) ;
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        fila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen from Productos" ,null);



        if(fila.moveToFirst()) {
            while (fila.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(3)));
            }
        }

        recycler = view.findViewById(R.id.RVproductos); ///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new ProductosAdapter(getActivity(), itemsProductos, new interfazUnidades_OnClick() {///adaptador del recycler
            @Override
            public void onClick(View v, String nombre) {
                db.delete(" Productos ", "nombre='" + nombre + "'", null);
            }
        }, new interfazUnidades_OnClickCodigo() {
            @Override
            public void onClick(View v, EditText codigo2) {
                codigo = (EditText) codigo2;
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent, 3);//inicializar el activity con RequestCode3
            }
        }, new interfazUnidades_OnClickImagen() {
            @Override
            public void onClick(View v, ImageView imagen) {

            }
        });
        recycler.setAdapter(adapter);

        nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new nuevoProducto_DialogFragment().show(fm, "nuevoProducto");
            }
        });
       return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////1 para entrar a galeria y tomar una foto
        if (requestCode == 1) {
            Uri selectedImage;
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();
                String selectedPath=selectedImage.getPath();
                if (requestCode == 1) {

                    if (selectedPath != null) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                        ImageView mImg = (ImageView) getView().findViewById(R.id.ImgImagen);
                        mImg.setImageBitmap(bmp);
                        Toast.makeText(getActivity(), selectedPath, Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        //2 Captura de foto
        if(requestCode == 2) {
            if(data != null) {
                ImageView imagen = getView().findViewById(R.id.ImgImagen);
                imagen.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }
            else{
            }
        }
        ///3 para escanear
        if (requestCode == 3 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }

    }

}
