package com.example.ricardosernam.puntodeventa.Productos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickCodigo;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickElementosProductos;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickImagen;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Productos extends Fragment{
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Cursor fila;
    private String rutaImagen;
    private Uri selectedImage;
    private SQLiteDatabase db;
    private ContentValues values;
    private Button nuevoProducto;
    private ImageView ponerImagen;
    private android.app.FragmentManager fm;
    private EditText codigo, nombre, unidad, precio;
    private String producto;

    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///

    @SuppressLint("ValidFragment")
    public Productos(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_productos, container, false);

        nuevoProducto=view.findViewById(R.id.BtnNuevoProducto);
        fm=getActivity().getFragmentManager();

        ponerImagen = new ImageView(getContext());
        codigo= new EditText(getContext());
        nombre= new EditText(getContext());
        unidad= new EditText(getContext());
        precio= new EditText(getContext());
        //comunicacion con DB
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();

        fila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos" ,null);

        if(fila.moveToFirst()) {
            while (fila.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
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
        }, new interfaz_OnClickCodigo() {
            @Override
            public void onClick(View v, EditText codigo2) {
                codigo = codigo2;
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent, 3);//inicializar el activity con RequestCode3
            }
        }, new interfaz_OnClickImagen() {
            @Override
            public void onClick(View v, ImageView imagen) {
                ponerImagen = imagen;
                DialogFragment dialog = new traerImagen(new interfaz_SeleccionarImagen() {
                    @Override
                    public void onClick(Intent intent, int requestCode) {
                        startActivityForResult(intent, requestCode);
                    }
                });
                dialog.show(fm, "NoticeDialogFragment");
            }
        }, new interfaz_OnClickElementosProductos() {
            @Override
            public void onClick(String productos, EditText codigo2, EditText nombre2, ImageView imagen, EditText unidad2, EditText precio2) {///cuando presiona editar
                producto=productos;
                ponerImagen = imagen;
                codigo = codigo2;
                nombre = nombre2;
                unidad = unidad2;
                precio = precio2;
            }
        }, new interfaz_OnClick() {
            @Override
            public void onClick(View v) {
                values.put("codigo_barras", String.valueOf(codigo.getText()));
                values.put("nombre", String.valueOf(nombre.getText()));
                values.put("ruta_imagen", MediaStore.Images.Media.insertImage(getContext().getContentResolver(), ((BitmapDrawable) ponerImagen.getDrawable()).getBitmap(), "Title", null));////obtenemos el uri de la imagen que esta actualmente seleccionada
                values.put("unidad", String.valueOf(unidad.getText()));
                values.put("precio_venta", String.valueOf(precio.getText()));

                db.update("Productos", values, "nombre='" + producto + "'", null);
                db.close();
            }
        });
        recycler.setAdapter(adapter);
        nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new nuevoProducto_DialogFragment().show(getFragmentManager(), "nuevoProducto");
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
                    //ponerImagen.setImageURI(selectedImage);
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                    ponerImagen.setImageBitmap(bmp);

                    //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                   // bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    //String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmp, "Title", null);
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
                    //ponerImagen.setImageURI(selectedImage);
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                    ponerImagen.setImageBitmap(bmp);
                }
            }
        }
        ///3 para escanear
        if (requestCode == 3 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }

    }
}
