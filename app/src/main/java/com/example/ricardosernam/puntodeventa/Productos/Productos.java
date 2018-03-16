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
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickCodigo;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickElementosProductos;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickImagen;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Productos extends Fragment implements agregado {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private Cursor fila, filaBusqueda, ultimaFila, filaActualizar, nombreR, codigoR, id;
    private String rutaImagen;
    private Uri selectedImage;
    private SQLiteDatabase db;
    private ContentValues values;
    private Button nuevoProducto, escanear2, editar, escanear, traerImagen;
    private ImageView ponerImagen;
    private android.app.FragmentManager fm;
    private EditText codigo, nombre, unidad, precio;
    private SearchView nombreCodigo;
    private String producto;
    private LinearLayout botones;
    private boolean rp;
    private Integer idseleccionado;


    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_productos, container, false);
        nuevoProducto=view.findViewById(R.id.BtnNuevoProducto);
        escanear=view.findViewById(R.id.BtnEscanearProducto);
        nombreCodigo=view.findViewById(R.id.ETnombreProducto);
        fm=getActivity().getFragmentManager();



        ponerImagen = new ImageView(getContext());
        codigo= new EditText(getContext());
        nombre= new EditText(getContext());
        unidad= new EditText(getContext());
        precio= new EditText(getContext());
        editar= new Button(getContext());
        escanear2=new Button(getContext());
        traerImagen=new Button(getContext());
        botones=new LinearLayout(getContext());
        //comunicacion con DB
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();

        fila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos" ,null);
        if(fila.moveToFirst()) {///si hay un elemento
            itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
            while (fila.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
            }
        }
        //consulta_total();

        recycler = view.findViewById(R.id.RVproductos); ///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new ProductosAdapter(getActivity(), itemsProductos, new interfazUnidades_OnClick() {///adaptador del recycler
            @Override
            public void onClick(View v, String nombre) {////eliminamos el producto deseado
                db.delete(" Productos ", "nombre='" + nombre + "'", null);
            }
        }, new interfaz_OnClickCodigo() {  ///modificar código
            @Override
            public void onClick(View v, EditText codigo2) {
                codigo = codigo2;
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent, 4);//inicializar el activity con RequestCode3
            }
        }, new interfaz_OnClickImagen() { ////modificar imagen
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
            public void onClick(String productos, EditText codigo2, EditText nombre2, ImageView imagen, EditText unidad2, EditText precio2, Button editar2, Button escanear3, Button traerImagen2, LinearLayout botones2) {///cuando presiona editar
                producto = productos;  ///nombre del producto antes de modificarlo
                ponerImagen = imagen;
                codigo = codigo2;
                nombre = nombre2;
                unidad = unidad2;
                precio = precio2;
                editar=editar2;
                escanear2=escanear3;
                traerImagen=traerImagen2;
                botones=botones2;
                ///obtenemos el id para poder actualizar mediante el
                id=db.rawQuery("select idproducto from Productos where nombre='"+producto+"'" ,null);
                if(id.moveToFirst()){
                    //Toast.makeText(getContext(), "id "+id.getInt(0), Toast.LENGTH_LONG).show();
                    idseleccionado=id.getInt(0);
                }
            }
        }, new actualizado () { ////cuando  presionamos aceptar cambios
            @Override
            public void actualizar(int position, String productos) {
                if (validate()) {  ///validamos los campos
                    values.put("codigo_barras", String.valueOf(codigo.getText()));
                    values.put("nombre", String.valueOf(nombre.getText()));
                    values.put("ruta_imagen", MediaStore.Images.Media.insertImage(getContext().getContentResolver(), ((BitmapDrawable) ponerImagen.getDrawable()).getBitmap(), "Title", null));////obtenemos el uri de la imagen que esta actualmente seleccionada
                    values.put("unidad", String.valueOf(unidad.getText()));
                    values.put("precio_venta", String.valueOf(precio.getText()));
                    Toast.makeText(getContext(), "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
                    db.update("Productos", values, "nombre='" + producto + "'", null);
                    ///actualizamos el recycler
                    filaActualizar = db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos where nombre='" + productos + "'", null);
                    if (filaActualizar.moveToFirst()) {
                        itemsProductos.set(position, new Pro_ventas_class(filaActualizar.getString(0), filaActualizar.getString(1), filaActualizar.getString(2), filaActualizar.getString(3), filaActualizar.getString(4)));
                        adapter.notifyDataSetChanged();
                    }
                    ///ocultamos lo inecesario
                    editar.setEnabled(true);
                    nombre.setEnabled(false);
                    precio.setEnabled(false);
                    unidad.setEnabled(false);
                    codigo.setEnabled(false);
                    botones.setVisibility(View.GONE);
                    escanear2.setVisibility(View.GONE);
                    traerImagen.setVisibility(View.GONE);
                }
            }
        }, new interfaz_OnClick() {////cancelamos cambios
            @Override
            public void onClick(View v) {   ////vaciamos el array y lo volvemos a llenar porque se escribe
                rellenado_total();
            }
        });
        recycler.setAdapter(adapter);
        ////si buscamos el producto escribiendo su nombre
        nombreCodigo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!(TextUtils.isEmpty(newText))) {   ///el campo tiene algo
                    if ((TextUtils.isDigitsOnly(newText))) {  ///si el campo tiene tan solo numeros es un codigo
                        filaBusqueda = db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos where codigo_barras='" + newText + "'", null);
                    } else {  ///sino es un nombre
                        filaBusqueda = db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos where nombre='" + newText + "'", null);
                    }
                    if (filaBusqueda.moveToFirst()) { ///si hay un elemento
                        itemsProductos.removeAll(itemsProductos);
                        itemsProductos.add(new Pro_ventas_class(filaBusqueda.getString(0), filaBusqueda.getString(1), filaBusqueda.getString(2), filaBusqueda.getString(3), filaBusqueda.getString(4)));
                        while (filaBusqueda.moveToNext()) {
                            itemsProductos.add(new Pro_ventas_class(filaBusqueda.getString(0), filaBusqueda.getString(1), filaBusqueda.getString(2), filaBusqueda.getString(3), filaBusqueda.getString(4)));
                        }
                    }
                    else{ ///El producto no existe
                        Toast.makeText(getContext(), "Producto inexistente", Toast.LENGTH_SHORT).show();
                    }
                }  ////esta vacio
                else {
                    rellenado_total();
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        escanear.setOnClickListener(new View.OnClickListener() {  ///buscamos un producto mediante el escaner
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,3);//inicializar el activity con RequestCode2
            }
        });
        nuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {////para agregar un nuevo producto
                new nuevoProducto_DialogFragment().show(getChildFragmentManager(), "nuevoProducto");
            }
        });
       return view;
    }
  //////////////////////////validaciones/////////////////////////////777
    public boolean nombreRepetido(){
        rp=false;
        nombreR=db.rawQuery("select nombre from Productos where idproducto!='"+idseleccionado+"' and nombre='"+nombre.getText()+"'" ,null);
        if(nombreR.moveToFirst()) {///si hay un elemento
            rp=true;
        }
        return rp;
    }
    public boolean codigoRepetido(){
        rp=false;
        if (!(TextUtils.isEmpty(codigo.getText()))) {  ///si no esta vacio lo comprobamos
            codigoR = db.rawQuery("select codigo_barras from Productos where idproducto!='" + idseleccionado + "' and codigo_barras='" + codigo.getText() + "'", null);
            if (codigoR.moveToFirst()) {///si hay un elemento
                rp = true;
            }
        }
        return rp;
    }
    public boolean validate() {  ///validamos que los campos cumplan los requisitos
        boolean valid = true;
        String name = nombre.getText().toString();
        String unity = unidad.getText().toString();
        String price = precio.getText().toString();
        if (name.isEmpty()) {
            nombre.setError("Campo obligatorio");
            valid = false;
        }
        else if(nombreRepetido()){
            nombre.setError("Nombre existente, ingresa otro");
            valid = false;
        }
        else
        {
            nombre.setError(null);
        }
        if(codigoRepetido()){
            codigo.setError("Código existente, ingresa otro");
            valid = false;
        }
        if (unity.isEmpty()) {
            unidad.setError("Campo obligatorio");
            valid = false;
        } else {
            unidad.setError(null);
        }

        if (price.isEmpty()) {
            precio.setError("Campo obligatorio");
            valid = false;
        }
        else if(price.length()>7){
            precio.setError("No puedes exceder 7 dígitos");
            valid = false;
        } else{
            unidad.setError(null);
        }
        return valid;
    }
    public void rellenado_total(){  ////volvemos a llenar el racycler despues de actualizar, o de una busqueda
        fila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos" ,null);
        if(fila.moveToFirst()) {///si hay un elemento
            itemsProductos.removeAll(itemsProductos);
            itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
            while (fila.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override  ////interfaz  para actualizar mi recyclerview
    public void agregar() {
        ultimaFila=db.rawQuery("select codigo_barras, nombre, precio_venta, ruta_imagen, unidad from Productos",null);
        ultimaFila.moveToLast();
        itemsProductos.add(new Pro_ventas_class(ultimaFila.getString(0), ultimaFila.getString(1), ultimaFila.getString(2), ultimaFila.getString(3), ultimaFila.getString(4)));;
        adapter.notifyDataSetChanged();
    }
    @Override  ///acciones de camara
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////1 para entrar a galeria
        if (requestCode == 1) {
            //Uri selectedImage;///uri es la ruta
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen = selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //ponerImagen.setImageURI(selectedImage);
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                    //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                    //bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    ponerImagen.setImageBitmap(bmp);
                    //String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmp, "Title", null);
                }
            }
        }
        //2 Captura de foto
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen = selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //ponerImagen.setImageURI(selectedImage);
                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);////obtenemos el bitmap

                    //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                    //bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                    ponerImagen.setImageBitmap(bmp);
                }
            }
        }
        ///3 para escanear
        if (requestCode == 3 && data != null) {   ///escaner de busqueda
            nombreCodigo.setQuery(data.getStringExtra("BARCODE"), false);
        }
        if (requestCode == 4 && data != null) {  ///modificar código de producto
            codigo.setText(data.getStringExtra("BARCODE"));
        }
    }
}
