package com.example.ricardosernam.puntodeventa.Compras;


import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.R;


public class Compras extends Fragment{
    private LinearLayout existentes,agregar, campos , foto;
    private TextView nombre, cantidadExistentes, totalCompra;
    private Button escan,aceptar,cancelar;
    private EditText capturarProducto,cantidad,precioCompra, precioVenta, nombreProducto;
    private RadioGroup opciones;
    private CheckBox agregaraproductos;     //checkbox para agregar a productos
    private Spinner unidad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compras, container, false);
        //Econtramos los valores de nuestros Radio Button dentro del XML
        opciones=view.findViewById(R.id.RGopcionesCompra);
        ////editText
        capturarProducto=view.findViewById(R.id.ETCapturarProducto);
        cantidad=view.findViewById(R.id.ETcantidadCompra);
        precioCompra=view.findViewById(R.id.ETprecioCompra);
        precioVenta=view.findViewById(R.id.ETprecioVenta);
        nombreProducto=view.findViewById(R.id.ETnombreProducto);
        ///botones
        aceptar=view.findViewById(R.id.BtnAceptarCompra);
        cancelar=view.findViewById(R.id.BtnCancelarCompra);
        escan = (Button)view.findViewById(R.id.BtnEscanearCodigo);

        //textviews
        cantidadExistentes= view.findViewById(R.id.TVexistentes);
        nombre= view.findViewById(R.id.TVnombreCompra);
        totalCompra= view.findViewById(R.id.TVtotalCompra);



        agregaraproductos=view.findViewById(R.id.CBagregarProductos);
        unidad=view.findViewById(R.id.SpnUnidad);

        ///layouts
        agregar=view.findViewById(R.id.LLagregarProductos);
       existentes=view.findViewById(R.id.LLexistentes);
        campos=view.findViewById(R.id.LLcamposDatos);
        foto=view.findViewById(R.id.LLfoto);



        String[] unidades= {"Litro","Kilo","Gramos","Metro","Pieza"};
        unidad.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,unidades));


        opciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RBexistente:
                        nombre.setVisibility(View.VISIBLE);
                        nombreProducto.setVisibility(View.GONE);
                        foto.setVisibility(View.GONE);
                        campos.setVisibility(View.GONE);
                        capturarProducto.setHint("Ingresa Nombre");
                        existentes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.RBnuevo:
                        nombre.setVisibility(View.GONE);
                        nombreProducto.setVisibility(View.VISIBLE);
                        foto.setVisibility(View.VISIBLE);
                        campos.setVisibility(View.VISIBLE);
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
                    agregar.setVisibility(View.VISIBLE);
                }else{
                    agregar.setVisibility(View.GONE);
                }
            }
        });



        escan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,2);//inicializar el activity con RequestCode2
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaciarFormulario();
                Toast.makeText(getContext(), "Se ha guardado tu Compra", Toast.LENGTH_LONG).show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaciarFormulario();
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
            capturarProducto.setText(data.getStringExtra("BARCODE"));
        }
    }
    public void VaciarFormulario(){
        capturarProducto.setText(" ");
        cantidad.setText(" ");
        precioCompra.setText(" ");
        precioVenta.setText(" ");

        //textviews
        cantidadExistentes.setText(" ");
        nombre.setText(" ");
        totalCompra.setText(" ");


        agregaraproductos.setChecked(false);
        unidad.setSelection(0);
    }

}
