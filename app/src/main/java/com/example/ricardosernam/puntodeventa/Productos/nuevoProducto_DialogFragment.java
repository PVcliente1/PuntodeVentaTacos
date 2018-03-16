package com.example.ricardosernam.puntodeventa.Productos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Benvenida.Registro_inicial;
import com.example.ricardosernam.puntodeventa.Proveedores.Proveedores;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;

@SuppressLint("ValidFragment")
public class nuevoProducto_DialogFragment extends android.support.v4.app.DialogFragment {
    private EditText nombreP, precio, codigo, unidad;
    private Button aceptarM, cancelarM, imagen, escanear;
    private ImageView ponerImagen;
    private String rutaImagen;
    private Uri selectedImage;
    private Cursor nombreR, codigoR;
    private FragmentManager fm;
    private ContentValues values;
    private SQLiteDatabase db;
    private agregado Interfaz;
    private boolean rp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Interfaz = (agregado) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement Callback interface");
        }
    }
    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.dialog_fragment_nuevo_producto,container);
        nombreP = rootView.findViewById(R.id.ETnombre);  ////Textview donde se coloca el nombre del producto
        precio=rootView.findViewById(R.id.ETprecio);
        codigo=rootView.findViewById(R.id.ETcodigoNuevo);
        aceptarM=rootView.findViewById(R.id.BtnAceptarProducto);
        cancelarM=rootView.findViewById(R.id.BtnCancelarProducto);
        escanear=rootView.findViewById(R.id.BtnEscanearNuevo);
        imagen=rootView.findViewById(R.id.BtnImagen);
        unidad=rootView.findViewById(R.id.ETunidad);
        ponerImagen = rootView.findViewById(R.id.ImgImagen);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();
        fm=getActivity().getFragmentManager();

        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,3);//inicializar el activity con RequestCode3
            }
        });
        unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///traemos la unidad seleccionada
                new Unidades_DialogFragment(new interfazUnidades_OnClick() {
                    @Override
                    public void onClick(View v, String unidadSeleccionada) {
                        unidad.setText(unidadSeleccionada);
                    }
                }).show(fm, "Clientes_DialogFragment");
            }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new traerImagen(new interfaz_SeleccionarImagen() {
                    @Override
                    public void onClick(Intent intent, int requestCode) {
                        startActivityForResult(intent, requestCode);
                    }
                });
                dialog.show(fm, "NoticeDialogFragment");
            }
        });
        aceptarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {////aceptamos agregar el producto
                if(validate()){
                    Toast.makeText(getActivity(), "Se han guardado el producto", Toast.LENGTH_SHORT).show();
                    values.put("codigo_barras", String.valueOf(codigo.getText()));
                    values.put("nombre", String.valueOf(nombreP.getText()));
                    values.put("precio_venta", String.valueOf(precio.getText())); //
                    values.put("ruta_imagen", MediaStore.Images.Media.insertImage(getContext().getContentResolver(), ((BitmapDrawable) ponerImagen.getDrawable()).getBitmap(), "Title", null));////obtenemos el uri de la imagen que esta actualmente seleccionada
                    values.put("unidad", String.valueOf(unidad.getText()));
                    db.insertOrThrow("Productos", null, values);
                    db.close();
                    dismiss();
                    if(Interfaz!=null){  ///notificamos al fragment que se agrego para actualizar el recyclerview
                        Interfaz.agregar();
                    }
                }

            }
        });
        cancelarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Nuevo Producto");
        return rootView;
    }
    public boolean nombreRepetido(){
        rp=false;
        nombreR=db.rawQuery("select nombre from Productos where nombre='"+nombreP.getText()+"'" ,null);
        if(nombreR.moveToFirst()) {///si hay un elemento
            rp=true;
        }
        return rp;
    }
    public boolean codigoRepetido() {
        rp = false;
        if (!(TextUtils.isEmpty(codigo.getText()))) {  ///si no esta vacio lo comprobamos
            codigoR = db.rawQuery("select codigo_barras from Productos where codigo_barras='" + codigo.getText() + "'", null);
            if (codigoR.moveToFirst()) {///si hay un elemento
                rp = true;
            }
        }
        return rp;
    }
    public boolean validate() {  ///validamos que los campos cumplan los requisitos
        boolean valid = true;
        String name = nombreP.getText().toString();
        String unity = unidad.getText().toString();
        String price = precio.getText().toString();
        if (name.isEmpty()) {
            nombreP.setError("Campo obligatorio");
            valid = false;
        }
        else if(nombreRepetido()){
            nombreP.setError("Nombre existente, ingresa otro");
            valid = false;
        }
        else  if ((TextUtils.isDigitsOnly(nombreP.getText()))){
            nombreP.setError("Ingresa mínimo una letra");
            valid = false;
        }
        else
        {
            nombreP.setError(null);
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
        ///3 para escanear
        if (requestCode == 3 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }

        }
}
