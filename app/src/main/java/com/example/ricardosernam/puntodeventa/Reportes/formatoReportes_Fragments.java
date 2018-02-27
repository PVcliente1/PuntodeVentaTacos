package com.example.ricardosernam.puntodeventa.Reportes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Fecha_DialogFragment;

/**
 * Created by Ricardo Serna M on 08/02/2018.
 */

@SuppressLint("ValidFragment")
public class formatoReportes_Fragments extends android.support.v4.app.Fragment{
    public String tipo;
    public Button fechaInicial, fechaFinal;
    public EditText fechaI, fechaF;
    public RadioGroup opcionesFecha;
    public LinearLayout opcionesInicio, opcionesFinal;

    /*@SuppressLint("ValidFragment")
    public formatoReportes_Fragments(String tipo){
        this.tipo=tipo;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.plantilla_reportes, container, false);
        opcionesFecha=view.findViewById(R.id.RGopcionesFecha);
        fechaInicial=view.findViewById(R.id.BtnFechaInicial);
        fechaFinal=view.findViewById(R.id.BtnFechaFinal);
        fechaI=view.findViewById(R.id.ETfechaInicial);
        fechaF=view.findViewById(R.id.ETfechaFinal);
        opcionesInicio=view.findViewById(R.id.LLopcionesInicio);
        opcionesFinal=view.findViewById(R.id.LLopcionesFinal);
        opcionesFecha.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RBdia:
                        opcionesFinal.setVisibility(View.GONE);
                        fechaI.setHint("día:");
                        break;
                    case R.id.RBperiodo:
                        opcionesFinal.setVisibility(View.VISIBLE);
                        fechaI.setHint("de: ");
                        break;
                }
            }
        });
        fechaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de DatePicker
                new Fecha_DialogFragment(new interfaz_OnClickFecha() {
                    @Override
                    public void onClick(View v, int i, int i1, int i2) {
                        fechaI.setText(i2 + "/" + i1+1 + "/" + i);
                    }
                }).show(getFragmentManager(),"Fecha_apartado");
            }
        });
        fechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de DatePicker
                new Fecha_DialogFragment(new interfaz_OnClickFecha() {
                    @Override
                    public void onClick(View v, int i, int i1, int i2) {
                        fechaF.setText(i2 + "/" + i1+1 + "/" + i);
                    }
                }).show(getFragmentManager(),"Fecha_apartado");
            }
        });



        return view;
    }
}
