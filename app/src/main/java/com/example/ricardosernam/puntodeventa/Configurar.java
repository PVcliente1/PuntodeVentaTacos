package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Configurar extends Fragment {
    private CheckBox costo,cantidad, visitas, descuentos, turnos;
    private EditText costoL, cantidadL, visitasL;
    private LinearLayout descuentosL, turnosL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_configurar, container, false);
        descuentos=view.findViewById(R.id.CBdescuentosConfiguracion);
        turnos=view.findViewById(R.id.CBturnosConfiguracion);
        costo=view.findViewById(R.id.CBcosto);
        cantidad=view.findViewById(R.id.CBcantidad);
        visitas=view.findViewById(R.id.CBvisitas);

        descuentosL=view.findViewById(R.id.LLdescuentos);
        turnosL=view.findViewById(R.id.LLturnos);

        costoL=view.findViewById(R.id.ETcosto);
        cantidadL=view.findViewById(R.id.ETcantidad);
        visitasL=view.findViewById(R.id.ETvisitas);

        descuentos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    descuentosL.setVisibility(View.VISIBLE);
                }
                else{
                    descuentosL.setVisibility(View.GONE);
                }
            }
        });
        turnos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    turnosL.setVisibility(View.VISIBLE);
                }
                else{
                    turnosL.setVisibility(View.GONE);
                }
            }
        });

        costo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    costoL.setEnabled(true);
                }
                else{
                    costoL.setEnabled(false);
                }
            }
        });
        cantidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cantidadL.setEnabled(true);
                }
                else{
                    cantidadL.setEnabled(false);
                }
            }
        });
        visitas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    visitasL.setEnabled(true);
                }
                else{
                    visitasL.setEnabled(false);
                }
            }
        });
        return view;
    }
}
