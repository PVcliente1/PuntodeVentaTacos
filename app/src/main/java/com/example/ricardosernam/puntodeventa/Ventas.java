package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment {
    //private android.support.v4.app.FragmentManager manejador = getFragmentManager();  //manejador que permite hacer el cambio de ventana

    public Activity ob() {
        return this.getActivity();
        //LayoutInflater inflater, ViewGroup container
    }
    //Cobrar_ventasAdapter co=new Cobrar_ventasAdapter(getContext(),);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        //Toast.makeText(view.getContext(), "Somos "+pra.getCantidad(), Toast.LENGTH_SHORT).show();

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
                pro.show(fm, "Pro");
                //fm.beginTransaction().replace(R.id.Ventas, pro).commit(); ///cambio de fragment
                //new Cobrar().show(fm,"Cobrar");
            }
        });

           /* items2 = new ArrayList<>();
        //if(new Pro().getSeleccionado()==null){
            items2.add(new Cobrar_venta("No hay productos"));
            //items2.add(new Cobrar_venta(new Pro().getSeleccionado()));
        //}
        //else{
            items2.add(new Cobrar_venta(new Pro().getSeleccionado()));
        //}

            //View view2= inflater.inflate(R.layout.fragment_ventas, container, false);
            RecyclerView recycler2 = view.findViewById(R.id.recicladorCobrar);
            RecyclerView.LayoutManager lManager2 = new LinearLayoutManager(getActivity());
            recycler2.setLayoutManager(lManager2);
            RecyclerView.Adapter adapter2 = new Cobrar_ventasAdapter(items2);
            recycler2.setAdapter(adapter2);*/
        return view;

    }

     /*public View Construir(ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        View view2= this.getLayoutInflater().inflate(R.layout.fragment_ventas, container, false);
        RecyclerView recycler2 = view2.findViewById(R.id.recicladorCobrar);
        RecyclerView.LayoutManager lManager2 = new LinearLayoutManager(getActivity());
        recycler2.setLayoutManager(lManager2);

        //RecyclerView.Adapter adapter2=new Cobrar_ventasAdapter(getContext(),pra.getSeleccionado(), pra.getCantidad());
        RecyclerView.Adapter adapter2=new Cobrar_ventasAdapter(getContext(), pra.getSeleccionado(), pra.getCantidad());
        recycler2.setAdapter(adapter2);

        //Toast.makeText(view2.getContext(), new Pro().getSeleccionado() , Toast.LENGTH_SHORT).show();
        //Toast.makeText(view2.getContext(), "numero "+pra.getCantidad(), Toast.LENGTH_SHORT).show();
        return view2;
    }*/
}
