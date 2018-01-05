package com.example.ricardosernam.puntodeventa;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ventas extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Dialog popProducts = new Dialog(getContext());
        final Dialog reciclador=new Dialog (getContext());

        View view=inflater.inflate(R.layout.fragment_ventas, container, false);

        popProducts.setContentView(R.layout.popoutproducts);
        reciclador.setContentView(R.layout.prueba);

        //RecyclerView reciclador= (RecyclerView) view.findViewById(R.id.reciclador);

        final Button Cancelar= (Button) popProducts.findViewById(R.id.Cancelar);
        Button sw= (Button) view.findViewById (R.id.Escanear);
        Button Productos= (Button) view.findViewById(R.id.Productos);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Activado", Toast.LENGTH_SHORT).show();
                //manejador.beginTransaction().replace(R.id.Ventas, new Products()).commit(); ///cambio de fragment
            }
        });

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popProducts.dismiss();
                    }
                });
                reciclador.show();
            }
        });
        return view;
    }

}
