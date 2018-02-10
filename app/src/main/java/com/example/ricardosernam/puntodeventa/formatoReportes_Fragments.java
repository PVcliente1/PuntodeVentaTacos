package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Ricardo Serna M on 08/02/2018.
 */

@SuppressLint("ValidFragment")
public class formatoReportes_Fragments extends android.support.v4.app.Fragment{
    public String tipo;
    /*@SuppressLint("ValidFragment")
    public formatoReportes_Fragments(String tipo){
        this.tipo=tipo;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.plantilla_reportes, container, false);

        return view;
    }
}
