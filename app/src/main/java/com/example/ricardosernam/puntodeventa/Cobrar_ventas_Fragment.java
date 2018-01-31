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
    private Button eliminarCompra,aceptarCompra;
    private EditText cliente, descripcion, hora, fecha;
    private interfaz_historial Interface_historial;
    private String tipo;

    ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();   ///array para productos seleccionados
    ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados

    /*public interface interfaz_historial2 {
       void mandarHistorial(ArrayList<Historial_ventas_class> tipo);
    }*/

    @SuppressLint("ValidFragment")
    public Cobrar_ventas_Fragment(ArrayList itemsCobrar) {
        this.itemsCobrar = itemsCobrar; ///recibios como parametro el array
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Interface_historial=(interfaz_historial)context;
            //mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement interfaz_historial");
        }
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
        eliminarCompra = getActivity().findViewById(R.id.BtnEliminarCompra);
        aceptarCompra = getActivity().findViewById(R.id.BtnAceptarCompra);
        opcionVentas = getActivity().findViewById(R.id.RGopcionesVenta);
        pagar = getActivity().findViewById(R.id.LOpagar);
        cliente=getActivity().findViewById(R.id.ETcliente);
        descripcion=getActivity().findViewById(R.id.ETdescripcion);
        opcionVentas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  ///programamamos las opciones de ventas (RadioGroup)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RBcotizar:
                        pagar.setVisibility(View.INVISIBLE);
                        tipo= "Cotización";
                        break;
                    case R.id.RBapartar:
                        pagar.setVisibility(View.VISIBLE);
                        tipo= "Apartado";
                        break;
                    case R.id.RBvender:
                        tipo= "Venta";
                        pagar.setVisibility(View.VISIBLE);
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
        aceptarCompra.setOnClickListener(new View.OnClickListener() {  ////agregamos la venta y lo notificamos
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"La venta de tipo "+ tipo+ " ha sido enviada",Toast.LENGTH_LONG).show();
                itemsHistorial.add(new Historial_ventas_class(tipo));
                Interface_historial.mandarHistorial(itemsHistorial);

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
