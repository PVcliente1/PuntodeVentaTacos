package com.example.ricardosernam.puntodeventa.Productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class nuevoProducto_DialogFragment extends android.support.v4.app.DialogFragment {
    private EditText nombreP, precio, codigo, unidad;
    private Button aceptarM, cancelarM, imagen, escanear;
    private ImageView ponerImagen;
    private String rutaImagen;
    private Cursor fila;
    private Uri selectedImage;
    private FragmentManager fm;
    private ContentValues values;
    private SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fila=db.rawQuery("select nombre from Productos" ,null);

        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,3);//inicializar el activity con RequestCode3
            }
        });
        unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new Unidades_DialogFragment().show(getFragmentManager(), "Unidades_DialogFragment");
                new Unidades_DialogFragment(new interfazUnidades_OnClick() {
                    @Override
                    public void onClick(View v, String unidadSeleccionada) {
                        unidad.setText(unidadSeleccionada);
                    }
                }).show(fm, "Unidades_DialogFragment");
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
                    values.put("precio_venta", String.valueOf(precio.getText()));
                     values.put("ruta_imagen", MediaStore.Images.Media.insertImage(getContext().getContentResolver(), ((BitmapDrawable) ponerImagen.getDrawable()).getBitmap(), "Title", null));////obtenemos el uri de la imagen que esta actualmente seleccionada
                    values.put("unidad", String.valueOf(unidad.getText()));
                    db.insertOrThrow("Productos", null, values);
                    db.close();
                    ////refrescamos nuestro recylcer
                    refrescar();
                     dismiss();
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
    void refrescar(){   ///se cierra en automatico
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.LOprincipal, new Productos());
        ft.addToBackStack(null);
        ft.commit();
    }
    public boolean validate() {  ///validamos que los campos cumplan los requisitos
        boolean valid = true;
        String name = nombreP.getText().toString();
        String unity = unidad.getText().toString();
        String price = precio.getText().toString();
        if (name.isEmpty()) {
            nombreP.setError("Campo obligatorio");
            valid = false;
        } else {
            nombreP.setError(null);
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
