package com.example.ricardosernam.puntodeventa.____herramientas_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Ricardo Serna M on 01/03/2018.
 */

@SuppressLint("ValidFragment")
public class traerImagen extends DialogFragment {
    private String rutaImagen;
    private Uri selectedImage;
    private ImageView ponerImagen;
    private int galeria=1, camara=2;

    public interface requestCode{
        public void onDialogPositiveClick(Intent data, int requestCode);
        //public void onDialogNegativeClick(Intent data, int requestCode);
    }
    requestCode mListener=(requestCode) getActivity();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder imagenProducto= new AlertDialog.Builder(getActivity());
                imagenProducto.setTitle("Imagen");
                imagenProducto.setMessage("Seleccion como quieres traer tu imagen");
                imagenProducto.setCancelable(false);

                imagenProducto.setPositiveButton("Desde galeria...", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface tipoDescuento, int id) {
                       Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        gallery.setType("image/*");
                        //start(Intent.createChooser(gallery, "Seleccione una imagen"), galeria);
                        //mListener.onDialogPositiveClick(Intent.createChooser(gallery, "Seleccione una imagen"), galeria);
                        //startActivityForResult(Intent.createChooser(gallery, "Seleccione una imagen"), galeria);
                        tipoDescuento.dismiss();
                    }
                });
                imagenProducto.setNegativeButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface tipoDescuento, int id) {
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        //start(i, camara);
                        //mListener.onDialogPositiveClick(i, camara);
                        //startActivityForResult(i, camara);
                        tipoDescuento.dismiss();
                    }
                });
        return imagenProducto.create();
    }
    public void start(Intent data, int requestCode){
        startActivityForResult(data, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////1 para entrar a galeria y tomar una foto
        if (requestCode == 1) {
            Toast.makeText(getActivity(), "Entre a 1", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(), "Entre a 2", Toast.LENGTH_LONG).show();
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
       /* if (requestCode == 3 && data != null) {
            //obtener resultados
            codigo.setText(data.getStringExtra("BARCODE"));
        }*/

    }

}
