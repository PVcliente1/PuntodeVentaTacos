package com.example.ricardosernam.puntodeventa.Benvenida;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.pagar_DialogFragment;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_historial;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Fecha_DialogFragment;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Hora_DialogFragment;

public class Personalizacion1 extends Fragment {
    EditText entradaHorario, salidaHorario, entradaTurno1,salidaTurno1, entradaTurno2,salidaTurno2;
    Button aceptar, imagen;
    AppBarLayout bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_personalizacion1, container, false);
      bar=getActivity().findViewById(R.id.APLappBar);
      ///editTexts/////
      entradaHorario=view.findViewById(R.id.ETentradaHorario);
      salidaHorario=view.findViewById(R.id.ETsalidaHorario);
      entradaTurno1=view.findViewById(R.id.ETentradaTurno1);
      salidaTurno1=view.findViewById(R.id.ETsalidaTurno1);
      entradaTurno2=view.findViewById(R.id.ETentradaTurno2);
      salidaTurno2=view.findViewById(R.id.ETsalidaTurno2);
      ///Botones///
      aceptar=view.findViewById(R.id.BtnAceptarPersonalización);

      entradaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        entradaHorario.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        salidaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        salidaHorario.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });

        entradaTurno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        entradaTurno1.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        salidaTurno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        salidaTurno1.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });


      aceptar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.CLcontenedorTotal)).commit();
              bar.setVisibility(View.VISIBLE);
          }
      });
        return view;
    }
}
