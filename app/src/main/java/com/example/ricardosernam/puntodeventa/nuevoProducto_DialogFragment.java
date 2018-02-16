package com.example.ricardosernam.puntodeventa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class nuevoProducto_DialogFragment extends DialogFragment {
    private EditText nombreP, precio, codigo;
    private Button aceptarM, cancelarM, imagen, escanear;
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
                final AlertDialog.Builder imagenProducto= new AlertDialog.Builder(getActivity());
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
                        startActivityForResult(i, 3);
                        tipoDescuento.dismiss();
                    }
                });
                imagenProducto.show();
            }
        });
        getDialog().setTitle("Nuevo Producto");
        return rootView;
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
        ///2 para escanear
        if (requestCode == 2 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }
        //3 Captura de foto
        if(requestCode == 3) {
            if(data != null) {
                ImageView imagen = getView().findViewById(R.id.ImgImagen);
                imagen.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
            }
            else{
            }
        }
        }

}
