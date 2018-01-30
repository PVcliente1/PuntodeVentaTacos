package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class Hora_DialogFragment extends DialogFragment {
    private TimePicker hora;
    private Button aceptar, cancelar;
    private Calendar calendar;
    private interfaz_OnClickHora Interface;

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

        hora.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, final int i, final int i1) {
                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Interface.onClick(view, i, i1);
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