package com.example.ricardosernam.puntodeventa;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

        View view=inflater.inflate(R.layout.fragment_ventas, container, false);

        final Button Cancelar= (Button) view.findViewById(R.id.Cancelar);

        final FragmentManager fm= getActivity().getFragmentManager();
        final Pro pro =new Pro ();

        Button sw= (Button) view.findViewById (R.id.Escanear);
        Button Productos= (Button) view.findViewById(R.id.Productos);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Activado", Toast.LENGTH_SHORT).show();
            }
        });

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pro.dismiss();
                    }
                });
                //pro.show();
                pro.show(fm, "Pro");
            }
        });
        return view;
    }
}
