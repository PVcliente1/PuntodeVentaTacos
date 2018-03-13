package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_descuento;

@SuppressLint("ValidFragment")
public class pagar_DialogFragment extends android.support.v4.app.DialogFragment {
    private Button aceptar,cancelar;
    private String tipo,pagar;
    interfaz_descuento Interface_historial;
     @SuppressLint("ValidFragment")
     public pagar_DialogFragment(String tipo, String pagar){
         this.tipo=tipo;
         this.pagar=pagar;
     }

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.pagar_ventas,container);
        Interface_historial =(interfaz_descuento) getActivity();//ESTO SOLO ES POSIBLE SI MainActivity es una subclase de Comunicador por lo tanto implementa Comunicator: Polimorfismo
        this.getDialog().setTitle("Productos");///cambiamos titulo del DialogFragment
        aceptar=rootView.findViewById(R.id.BtnAceptarPago);
        cancelar=rootView.findViewById(R.id.BtnCancelarPago);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Tu "+ tipo+ " se envió",Toast.LENGTH_LONG).show();
                Interface_historial.descontar(tipo, 0);  ///usamos la interface para "puentear" con el activity y mandar a historial
                //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.LOcobrar)).commit();///cerramos la venta
                dismiss();
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
