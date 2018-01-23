package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



@SuppressLint("ValidFragment")
public class Ventas extends Fragment {     /////Fragment de categoria ventas
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        final FragmentManager fm= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de productos

        final Pro_DialogFragment pro =new Pro_DialogFragment();

        Button Productos= (Button) view.findViewById(R.id.BtnProductos);

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///mandamos llamar el DialogFragment
                //new Pro_DialogFragment().show(fm, "Pro_DialogFragment");
                pro.show(fm, "Pro_DialogFragment");
            }
        });

        return view;

    }
}
