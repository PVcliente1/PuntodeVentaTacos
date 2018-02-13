package com.example.ricardosernam.puntodeventa;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ricardo Serna M on 06/02/2018.
 */

public class nuevoProducto_DialogFragment extends DialogFragment {
    private EditText nombreP, precio, codigo;
    private Button aceptarM, cancelarM, imagen, escanear;
    //private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
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

        aceptarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getActivity(), "Se han guardado el producto", Toast.LENGTH_SHORT).show();
            }
        });
        cancelarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,2);//inicializar el activity con RequestCode2
            }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria(view);
            }
        });
        getDialog().setTitle("Nuevo Producto");
        return rootView;
    }
    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"),
                1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ///2 para escanear
        if (requestCode == 2 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }
        if (requestCode == 1) {
            Uri selectedImageUri = null;
            Uri selectedImage;
            if (resultCode == Activity.RESULT_OK) {
                        selectedImage = data.getData();
                        String selectedPath=selectedImage.getPath();
                        if (requestCode == 1) {

                            if (selectedPath != null) {
                                InputStream imageStream = null;
                                try {
                                    imageStream = getActivity().getContentResolver().openInputStream(
                                            selectedImage);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                                Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                                // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                                ImageView mImg = (ImageView) getView().findViewById(R.id.ImgImagen);
                                mImg.setImageBitmap(bmp);

                            }
                        }
                    }

            }
        }

}
