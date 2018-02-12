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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Compras extends Fragment{
    private LinearLayout existentes;
    private Button escan;
    private EditText codigoBarras, capturarProducto;
    private RadioGroup opciones;
    private TextView codigo;
    private CheckBox agregaraproductos;     //checkbox para agregar a productos
    private LinearLayout precioventa;
    private Spinner unidad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compras, container, false);
        //Econtramos los valores de nuestros Radio Button dentro del XML
        opciones=view.findViewById(R.id.RGopcionesCompra);
        capturarProducto=view.findViewById(R.id.ETCapturarProducto);
        agregaraproductos=view.findViewById(R.id.CBagregarProductos);
        precioventa=view.findViewById(R.id.LLprecioVenta);
       existentes=view.findViewById(R.id.LLexistentes);
        unidad=view.findViewById(R.id.SpnUnidad);

        String[] unidades= {"Litro","Kilo","Gramos","Metro","Pieza"};
        unidad.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,unidades));


        opciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RBexistente:
                        capturarProducto.setHint("Nombre");
                        existentes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.RBnuevo:
                        capturarProducto.setHint("Código (Opcional)");
                        existentes.setVisibility(View.GONE);
                        break;
                }

            }
        });

        agregaraproductos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    precioventa.setVisibility(View.VISIBLE);
                }else{
                    precioventa.setVisibility(View.GONE);
                }
            }
        });

        escan = (Button)view.findViewById(R.id.BtnEscanearCodigo);
        codigoBarras = (EditText)view.findViewById(R.id.ETCapturarProducto);

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
