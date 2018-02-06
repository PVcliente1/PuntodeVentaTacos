package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment{     /////Fragment de categoria ventas
    private FragmentManager fm;
    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>(); ///Arraylist que contiene los productos///
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos
    private ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados
    private Button productos, escanear, historial;
    private EditText codigo;
    private Pro_DialogFragment pro;
    private Historial_DialogFragment DFhistorial;

    @SuppressLint("ValidFragment")
    public Ventas(ArrayList tipo) {
        this.itemsHistorial=tipo;   /////viene del mainActivity (Mediante Interface)
    }

    @SuppressLint("ValidFragment")
    public Ventas() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /////////////productos de ejemplo//////////////
        itemsProductos.add(new Pro_ventas_class("Tacos"));
        itemsProductos.add(new Pro_ventas_class("Tortas"));
        itemsProductos.add(new Pro_ventas_class("Quesadillas"));
        itemsProductos.add(new Pro_ventas_class("Refrescos"));
        itemsProductos.add(new Pro_ventas_class("Cervezas"));
        itemsProductos.add(new Pro_ventas_class("Pizza"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);
        fm= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto

        pro =new Pro_DialogFragment(itemsProductos, itemsCobrar);
        DFhistorial=new Historial_DialogFragment(itemsHistorial);  ////enviamos el array con las compras al fragment de historial

        productos= view.findViewById(R.id.BtnProductos);
        historial= view.findViewById(R.id.BtnHistorial);
        escanear= view.findViewById(R.id.BtnEscanear);
        codigo=view.findViewById(R.id.ETcodigo);

        codigo.setInputType(InputType.TYPE_NULL);
        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,2);//inicializar el activity con RequestCode2
            }
        });

        productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {///mandamos llamar el DialogFragment
                pro.show(fm, "Pro_DialogFragment");
            }
        });
        historial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DFhistorial.show(fm, "Historial_DialogFragment");
            }
        });

        return view;
    }
    //metodo para obtener resultados DEL ESCANER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && data != null) {
            //obtener resultados
            itemsCobrar.add(new Cobrar_ventas_class(data.getStringExtra("BARCODE")));//obtenemos el cardview seleccionado y lo agregamos a items2
            fm.beginTransaction().replace(R.id.LOcobrar, new Cobrar_ventas_Fragment(itemsCobrar)).commit(); ///sustituimos el layout frament por el del recycler de cobrar
        }
    }
}
