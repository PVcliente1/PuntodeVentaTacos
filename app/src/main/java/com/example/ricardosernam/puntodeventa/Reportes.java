package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

public class Reportes extends Fragment {
    public GridLayout reportes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ejemplo, container, false);

        /*reportes=view.findViewById(R.id.GLgrid);
        int childCount=reportes.getChildCount();

        for (int i= 0; i < childCount; i++) {
            //CardView conta = (CardView) reportes.getChildAt(childCount);
            LinearLayout conta = (LinearLayout) reportes.getChildAt(childCount);
            conta.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Entre", Toast.LENGTH_SHORT).show();
                }
            });
        }
        /*conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Entre", Toast.LENGTH_SHORT).show();
            }
        });*/
        /*reportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), reportes.getChildCount(), Toast.LENGTH_SHORT).show();
            }
        });*/
        /*reportes.getChildAt(reportes.getChildCount()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Entre", Toast.LENGTH_SHORT).show();
            }
        });*/
        //getFragmentManager().beginTransaction().replace(R.id.LOcobrar, new formatoReportes_Fragments()).commit(); ///sustituimos el layout fragment por el del recycler de cobrar
        return view;
    }
}
