package com.example.ricardosernam.puntodeventa;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ricardo Serna M on 08/02/2018.
 */

public class formatoReportes_Fragments extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.plantilla_reportes, container, false);
        return view;
    }
}
