package com.example.ricardosernam.puntodeventa.Reportes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.ricardosernam.puntodeventa.R;

public class Reportes extends Fragment {
    public TableLayout reportes;
    public LinearLayout layout;
    public FragmentManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reportes, container, false);
        manager= getFragmentManager();
        //manager2=getChildFragmentManager();
        reportes=view.findViewById(R.id.TLreportes);
            for(int i=0; i<reportes.getChildCount();i++){  ///me dara 4
                final TableRow row = (TableRow) reportes.getChildAt(i); ///me dara 2
                for(int j=0;j<row.getChildCount();j++){
                    LinearLayout Card = (LinearLayout) row.getChildAt(j);
                    Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.SVreportes, new formatoReportes_Fragments()).commit(); ///sustituimos el layout fragment por el del recycler de cobra
                }
                });
                }
            }

        return view;
    }
}
