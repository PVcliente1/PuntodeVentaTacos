package com.example.ricardosernam.puntodeventa;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ricardo Serna M on 06/02/2018.
 */

public class nuevoProducto_DialogFragment extends DialogFragment {
    private EditText nombreP, precio;
    private Button aceptarM, cancelarM, imagen;
    //private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView=inflater.inflate(R.layout.dialog_fragment_nuevo_producto,container);
        nombreP = rootView.findViewById(R.id.ETnombre);  ////Textview donde se coloca el nombre del producto
        precio=rootView.findViewById(R.id.ETprecio);
        aceptarM=rootView.findViewById(R.id.BtnAceptarProducto);
        cancelarM=rootView.findViewById(R.id.BtnCancelarProducto);
        imagen=rootView.findViewById(R.id.BtnImagen);

        aceptarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getActivity(), "Se han guardado el producto", Toast.LENGTH_SHORT).show();
            }
        });
        cancelarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Nuevo Producto");
        return rootView;
    }
}
