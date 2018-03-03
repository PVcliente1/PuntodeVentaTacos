package com.example.ricardosernam.puntodeventa.____herramientas_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;

/**
 * Created by Ricardo Serna M on 01/03/2018.
 */

@SuppressLint("ValidFragment")
public class traerImagen extends DialogFragment {
    private interfaz_SeleccionarImagen Interfaz;
    private int galeria=1, camara=2;

    public traerImagen (interfaz_SeleccionarImagen Interfaz) {
        this.Interfaz=Interfaz;
    }
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
                        Interfaz.onClick(Intent.createChooser(gallery, "Seleccione una imagen"), galeria);
                        tipoDescuento.dismiss();

                    }
                });
                imagenProducto.setNegativeButton("Tomar foto", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface tipoDescuento, int id) {
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        Interfaz.onClick(i, camara);
                        tipoDescuento.dismiss();

                    }
                });
        return imagenProducto.create();
    }
}
