package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment {     /////Fragment de categoria ventas
    private ArrayList<Productos_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////productos de ejemplo//////////////
        itemsProductos.add(new Productos_ventas_class("Tacos"));
        itemsProductos.add(new Productos_ventas_class("Tortas"));
        itemsProductos.add(new Productos_ventas_class("Quesadillas"));
        itemsProductos.add(new Productos_ventas_class("Refrescos"));
        itemsProductos.add(new Productos_ventas_class("Cervezas"));
        itemsProductos.add(new Productos_ventas_class("Pizza"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);

        final FragmentManager fm= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de productos

        final Pro_DialogFragment pro =new Pro_DialogFragment(itemsProductos);

        Button Productos= (Button) view.findViewById(R.id.BtnProductos);

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///mandamos llamar el DialogFragment
                pro.show(fm, "Pro_DialogFragment");
            }
        });

        return view;

    }
}
