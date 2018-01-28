package com.example.ricardosernam.puntodeventa;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fecha_DialogFragment extends DialogFragment {
    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.fecha,container);
        this.getDialog().setTitle("Fecha de entrega");///cambiamos titulo del DialogFragment
        return rootView;
    }
}
