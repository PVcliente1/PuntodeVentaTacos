package com.example.ricardosernam.puntodeventa.____herramientas_app;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class Hora_DialogFragment extends android.support.v4.app.DialogFragment {   ////Dialog Fragment que muestra mi timePicker
    private TimePicker hora;
    private Button aceptar, cancelar;
    private Calendar calendar;
    private interfaz_OnClickHora Interface;  ////interfaz para comunicarlo con ventas

    @SuppressLint("ValidFragment")
    public Hora_DialogFragment(interfaz_OnClickHora Interface){
        this.Interface=Interface;
    }
    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.hora,container);

        this.getDialog().setTitle("Hora de entrega");///cambiamos titulo del DialogFragment
        calendar=Calendar.getInstance();
        aceptar=rootView.findViewById(R.id.BtnaceptarHora);
        cancelar=rootView.findViewById(R.id.BtncancelarHora);
        hora=rootView.findViewById(R.id.TPhora);

        hora.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {  ////escuchador de cambios del timePicker
            @Override
            public void onTimeChanged(TimePicker timePicker, final int i, final int i1) {
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Interface.onClick(i, i1);   ///enviamos los datos a ventas
                        dismiss();
                    }
                });
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
    }
}