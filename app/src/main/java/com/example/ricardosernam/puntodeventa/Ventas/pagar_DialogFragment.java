package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_historial;

@SuppressLint("ValidFragment")
public class pagar_DialogFragment extends DialogFragment {
    private Button aceptar,cancelar;
    private String tipo,pagar;
    interfaz_historial Interface_historial;
     @SuppressLint("ValidFragment")
     public pagar_DialogFragment(String tipo, String pagar){
         this.tipo=tipo;
         this.pagar=pagar;
     }

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.pagar_ventas,container);
        Interface_historial =(interfaz_historial) getActivity();//ESTO SOLO ES POSIBLE SI MainActivity es una subclase de Comunicador por lo tanto implementa Comunicator: Polimorfismo
        this.getDialog().setTitle("Productos");///cambiamos titulo del DialogFragment
        aceptar=rootView.findViewById(R.id.BtnAceptarPago);
        cancelar=rootView.findViewById(R.id.BtnCancelarPago);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Tu "+ tipo+ " se envió",Toast.LENGTH_LONG).show();
                Interface_historial.mandarHistorial(tipo, pagar);  ///usamos la interface para "puentear" con el activity y mandar a historial
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
