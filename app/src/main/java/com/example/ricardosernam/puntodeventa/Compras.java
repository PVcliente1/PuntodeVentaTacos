package com.example.ricardosernam.puntodeventa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Compras extends Fragment{
    private Button escan;
    private EditText codigoBarras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compras, container, false);

        escan = (Button)view.findViewById(R.id.BtnEscanearQR);
        codigoBarras = (EditText)view.findViewById(R.id.ETCodigo_compras);

        escan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,2);//inicializar el activity con RequestCode2
            }
        });

        return view;
    }

    //metodo para obtener resultados
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Codigo 2
        if (requestCode == 2 && data != null) {
            //obtener resultados
            codigoBarras.setText(data.getStringExtra("BARCODE"));
        }
    }

}
