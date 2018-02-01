package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Cobrar_ventas_Fragment extends Fragment {   ////Fragment para seccion cobrar con recyclerview

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private RadioButton pagarAhora,vender;
    private RadioGroup opcionVentas;
    private LinearLayout pagar;
    private Button eliminarCompra,aceptarCompra, descuento;
    private EditText cliente, descripcion, hora, fecha;
    interfaz_historial Interface_historial;
    private RadioButton seleccionado;
    private LinearLayout editsApartado;

    ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();   ///array para productos seleccionados

    @SuppressLint("ValidFragment")
    public Cobrar_ventas_Fragment(ArrayList itemsCobrar) {
        this.itemsCobrar = itemsCobrar; ///recibios como parametro el array
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.recyclercobrar, container, false);
        pagarAhora = view2.findViewById(R.id.RBpagarAhora);
        vender=view2.findViewById(R.id.RBvender);
        hora=view2.findViewById(R.id.EThora);
        fecha=view2.findViewById(R.id.ETfecha);
        vender.setChecked(true);
        pagarAhora.setChecked(true);
        hora.setInputType(InputType.TYPE_NULL);///evitamos que se abra el teclado automaticamente
        fecha.setInputType(InputType.TYPE_NULL);
        ////mandamos llamar al adaptador del recycerview para acomodarlo en este el DialogFragment/////
        adapter = new Cobrar_ventasAdapter(view2.getContext(), this.getActivity(), itemsCobrar);///llamamos al adaptador y le enviamos el array como parametro
        recycler = view2.findViewById(R.id.RVrecicladorCobrar);///declaramos el recycler
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);

        return view2;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Interface_historial =(interfaz_historial) getActivity();//ESTO SOLO ES POSIBLE SI MainActivity es una subclase de Comunicador por lo tanto implementa Comunicator: Polimorfismo

        eliminarCompra = getActivity().findViewById(R.id.BtnEliminarCompra);
        aceptarCompra = getActivity().findViewById(R.id.BtnAceptarCompra);
        descuento=getActivity().findViewById(R.id.BtnDescuento);
        opcionVentas = getActivity().findViewById(R.id.RGopcionesVenta);
        pagar = getActivity().findViewById(R.id.LOpagar);
        cliente=getActivity().findViewById(R.id.ETcliente);
        descripcion=getActivity().findViewById(R.id.ETdescripcion);
        editsApartado=getActivity().findViewById(R.id.LOedittext);
        opcionVentas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  ///programamamos las opciones de ventas (RadioGroup)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RBcotizar:
                        pagar.setVisibility(View.INVISIBLE);
                        //editsApartado.setEnabled(false);
                        editsApartado.setVisibility(View.INVISIBLE);
                        aceptarCompra.setVisibility(View.INVISIBLE);
                        descuento.setVisibility(View.INVISIBLE);
                        cliente.setVisibility(View.INVISIBLE);
                        descripcion.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.RBapartar:
                        pagar.setVisibility(View.VISIBLE);
                        editsApartado.setVisibility(View.VISIBLE);
                        aceptarCompra.setVisibility(View.VISIBLE);
                        descuento.setVisibility(View.VISIBLE);
                        cliente.setText("Cliente (Obligatorio)");
                        cliente.setVisibility(View.VISIBLE);
                        descripcion.setVisibility(View.VISIBLE);
                        break;
                    case R.id.RBvender:
                        pagar.setVisibility(View.VISIBLE);
                        editsApartado.setVisibility(View.VISIBLE);
                        aceptarCompra.setVisibility(View.VISIBLE);
                        descuento.setVisibility(View.VISIBLE);
                        cliente.setText("Cliente (Opcional)");
                        cliente.setVisibility(View.VISIBLE);
                        descripcion.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });
        eliminarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  ///al eliminar compra mostramos un AlertDialog
                AlertDialog.Builder cancelarCompra = new AlertDialog.Builder(getActivity());
                cancelarCompra.setTitle("Cuidado");
                cancelarCompra.setMessage("¿Seguro que quieres cancelar la venta?");
                cancelarCompra.setCancelable(false);
                cancelarCompra.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.LOcobrar)).commit();
                        //aceptar();
                    }
                });
                cancelarCompra.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface cancelarCompra, int id) {
                        cancelarCompra.dismiss();
                    }
                });
                cancelarCompra.show();
            }
        });
        aceptarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionado= getActivity().findViewById(opcionVentas.getCheckedRadioButtonId());  ////obtenemos el RadioButton seleccionado
                Toast.makeText(getActivity(),"Tu "+ seleccionado.getText().toString()+ " se envió",Toast.LENGTH_LONG).show();
                Interface_historial.mandarHistorial(seleccionado.getText().toString());  ///usamos la interface para "puentear" con el activity y mandar a historial
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.LOcobrar)).commit();///cerramos la venta
            }
        });
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        hora.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Hora_apartado");
            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de DatePicker
                new Fecha_DialogFragment(new interfaz_OnClickFecha() {
                    @Override
                    public void onClick(View v, int i, int i1, int i2) {
                        fecha.setText(i2 + "/" + i1+1 + "/" + i);
                    }
                }).show(getFragmentManager(),"Fecha_apartado");
            }
        });

    }
}
