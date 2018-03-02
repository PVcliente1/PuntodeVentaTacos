package com.example.ricardosernam.puntodeventa.Productos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class nuevoProducto_DialogFragment extends DialogFragment implements traerImagen.requestCode {
    private EditText nombreP, precio, codigo, unidad;
    private Button aceptarM, cancelarM, imagen, escanear;
    private ImageView ponerImagen;
    private String rutaImagen;
    private Uri selectedImage;
    private FragmentActivity fm=(FragmentActivity) getActivity();
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
                }).show(getFragmentManager(), "Unidades_DialogFragment");
            }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new traerImagen();
                dialog.show(getFragmentManager(), "NoticeDialogFragment");

                /*final AlertDialog.Builder imagenProducto= new AlertDialog.Builder(getActivity());
                imagenProducto.setTitle("Imagen");
                imagenProducto.setMessage("Seleccion como quieres traer tu imagen");
                imagenProducto.setCancelable(false);

                imagenProducto.setPositiveButton("Desde galeria...", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface tipoDescuento, int id) {
                       Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        gallery.setType("image/*");
                        startActivityForResult(Intent.createChooser(gallery, "Seleccione una imagen"), 1);
                        tipoDescuento.dismiss();
                    }
                });
                imagenProducto.setNegativeButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface tipoDescuento, int id) {
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 2);
                        tipoDescuento.dismiss();
                    }
                });
                imagenProducto.show();*/

            }
        });
        aceptarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getActivity(), "Se han guardado el producto", Toast.LENGTH_SHORT).show();
                values.put("codigo_barras", String.valueOf(codigo.getText()));
                values.put("nombre", String.valueOf(nombreP.getText()));
                values.put("precio_venta", String.valueOf(precio.getText()));
                values.put("ruta_imagen", String.valueOf(selectedImage));
                values.put("unidad", String.valueOf(unidad.getText()));
                db.insertOrThrow("Productos",null, values);

                db.close();
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
    @Override
    public void onDialogPositiveClick(Intent data, int requestCode) {
        startActivityForResult(data, requestCode);
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
                         //Transformamos la URI de la imagen a inputStream y este a un Bitmap
                        /*Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                        ponerImagen.setImageBitmap(bmp);*/
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

                    //Transformamos la URI de la imagen a inputStream y este a un Bitmap
                    /*Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                    // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                    ponerImagen.setImageBitmap(bmp);*/
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
