package com.example.ricardosernam.puntodeventa.____herramientas_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_descuento;

/**
 * Created by Ricardo Serna M on 01/03/2018.
 */

@SuppressLint("ValidFragment")
public class Descuentos extends DialogFragment {
    private Cursor descuentoNormal, descuentoEspecial;
    private SQLiteDatabase db;
    private interfaz_descuento Interfaz;
    private String normal, especial;

    public Descuentos(interfaz_descuento Interfaz) {
        this.Interfaz=Interfaz;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        descuentoNormal=db.rawQuery("select porcentaje from Descuentos where tipo_descuento='Normal'" ,null);
        descuentoEspecial=db.rawQuery("select porcentaje from Descuentos where tipo_descuento='Especial'" ,null);

        if(descuentoNormal.moveToFirst()){
            normal=descuentoNormal.getString(0);
        }
        if(descuentoEspecial.moveToFirst()){
            especial=descuentoEspecial.getString(0);
        }
        final TextView tipoD=getActivity().findViewById(R.id.TVtipoDescuento);
        final AlertDialog.Builder tipoDescuento= new AlertDialog.Builder(getActivity());
        tipoDescuento.setTitle("Descuentos");
        tipoDescuento.setMessage("Seleccion un tipo de descuento");
        tipoDescuento.setCancelable(false);
        tipoDescuento.setPositiveButton("Normal: "+ normal +"%", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface tipoDescuento, int id) {
                Interfaz.descontar("Normal %: ", descuentoNormal.getInt(0));
                tipoDescuento.dismiss();
            }
        });
        tipoDescuento.setNegativeButton("Especial: "+ especial +"%", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface tipoDescuento, int id) {
                Interfaz.descontar("Especial %: ", descuentoEspecial.getInt(0));
                tipoDescuento.dismiss();
            }
        });
        return tipoDescuento.create();
    }
}
