package com.example.ricardosernam.puntodeventa.Productos;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Cobrar_ventasAdapter;

/**
 * Created by Ricardo Serna M on 27/02/2018.
 */
public class Unidades extends android.app.DialogFragment {
    Button agregarNuevaUnidad, cancelar;
    EditText capturarNuevaUnidad;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.dialog_fragment_unidades, container, false);
        getDialog().setTitle("Unidades");
        agregarNuevaUnidad=view2.findViewById(R.id.BtnNuevaUnidad);
        cancelar=view2.findViewById(R.id.BtnCancelar);
        capturarNuevaUnidad=view2.findViewById(R.id.ETnuevaUnidad);

        agregarNuevaUnidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Se ha agregado a Unidadades", Toast.LENGTH_LONG).show();
                capturarNuevaUnidad.setText(" ");
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ////mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        /*adapter = new Cobrar_ventasAdapter(view2.getContext(), this.getActivity(), itemsCobrar);///llamamos al adaptador y le enviamos el array como parametro
        recycler = view2.findViewById(R.id.RVrecicladorCobrar);///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);*/

        return view2;
    }

}
