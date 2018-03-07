package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Fecha_DialogFragment;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Hora_DialogFragment;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_historial;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class Cobrar_ventas_Fragment extends android.support.v4.app.Fragment{   ////Fragment para seccion cobrar con recyclerview

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private RadioButton pagarAhora,vender;
    private RadioGroup opcionVentas, opcionCobrar;
    private LinearLayout pagar, fechaHora, tipoDescuento, cobro;
    private Button eliminarCompra,aceptarCompra;
    private EditText cliente, descripcion, hora, fecha;
    interfaz_historial Interface_historial;
    private RadioButton seleccionado, seleccionado2;
    private LinearLayout editsApartado;
    public CheckBox descuento;
    public TextView tipoD;
    FragmentActivity context= (FragmentActivity) getActivity();

    ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();   ///array para productos seleccionados

    @SuppressLint("ValidFragment")
    public Cobrar_ventas_Fragment(ArrayList itemsCobrar) {
        this.itemsCobrar = itemsCobrar; ///recibios como parametro el array
    }

    @SuppressLint("ValidFragment")
    public Cobrar_ventas_Fragment() {
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
        fechaHora=view2.findViewById(R.id.LOedittext);
        cobro=view2.findViewById(R.id.LLcobro);
        vender=view2.findViewById(R.id.RBvender);
        hora=view2.findViewById(R.id.EThora);
        fecha=view2.findViewById(R.id.ETfecha);
        vender.setChecked(true);
        pagarAhora.setChecked(true);
        fechaHora.setVisibility(View.INVISIBLE);
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
        opcionVentas = getActivity().findViewById(R.id.RGopcionesVenta);
        opcionCobrar= getActivity().findViewById(R.id.RGopcionCobrar);
        pagar = getActivity().findViewById(R.id.LOpagar);
        cliente=getActivity().findViewById(R.id.ETcliente);
        descripcion=getActivity().findViewById(R.id.ETdescripcion);
        editsApartado=getActivity().findViewById(R.id.LOedittext);
        descuento=getActivity().findViewById(R.id.CBDescuento);
        tipoD=getActivity().findViewById(R.id.TVtipoDescuento);
        tipoDescuento=getActivity().findViewById(R.id.LOdescuento);

        opcionVentas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  ///programamamos las opciones de ventas (RadioGroup)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RBcotizar:
                        pagar.setVisibility(View.GONE);
                        //editsApartado.setEnabled(false);
                        editsApartado.setVisibility(View.GONE);
                        aceptarCompra.setVisibility(View.GONE);
                        cliente.setVisibility(View.GONE);
                        descripcion.setVisibility(View.GONE);
                        fechaHora.setVisibility(View.GONE);
                        tipoDescuento.setVisibility(View.GONE);
                        break;
                    case R.id.RBapartar:
                        pagar.setVisibility(View.VISIBLE);
                        editsApartado.setVisibility(View.VISIBLE);
                        aceptarCompra.setVisibility(View.VISIBLE);
                        cliente.setText("Cliente (Obligatorio)");
                        cliente.setVisibility(View.VISIBLE);
                        descripcion.setVisibility(View.VISIBLE);
                        fechaHora.setVisibility(View.VISIBLE);
                        tipoDescuento.setVisibility(View.VISIBLE);
                        break;
                    case R.id.RBvender:
                        pagar.setVisibility(View.VISIBLE);
                        editsApartado.setVisibility(View.VISIBLE);
                        aceptarCompra.setVisibility(View.VISIBLE);
                        cliente.setText("Cliente (Opcional)");
                        cliente.setVisibility(View.VISIBLE);
                        descripcion.setVisibility(View.VISIBLE);
                        fechaHora.setVisibility(View.INVISIBLE);
                        tipoDescuento.setVisibility(View.VISIBLE);
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
                        for(int i = 0; i<itemsCobrar.size(); i++ ){
                            itemsCobrar.remove(i);
                            adapter.notifyItemRemoved(i);
                            adapter.notifyItemRangeChanged(i,itemsCobrar.size());
                        }
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
                seleccionado2=getActivity().findViewById(opcionCobrar.getCheckedRadioButtonId());
                new pagar_DialogFragment(seleccionado.getText().toString(),seleccionado2.getText().toString()).show(getFragmentManager(),"pagarDiaogFragment");
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
        descuento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    final AlertDialog.Builder tipoDescuento= new AlertDialog.Builder(getActivity());
                    tipoDescuento.setTitle("Descuentos");
                    tipoDescuento.setMessage("Seleccion un tipo de descuento");
                    tipoDescuento.setCancelable(false);
                    tipoDescuento.setPositiveButton("Normal  (%)", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface tipoDescuento, int id) {
                            tipoD.setText("Normal (%)");
                            tipoDescuento.dismiss();
                        }
                    });
                    tipoDescuento.setNegativeButton("Especial (%)", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface tipoDescuento, int id) {
                            tipoD.setText("Especial (%)");
                            tipoDescuento.dismiss();
                        }
                    });
                    tipoDescuento.show();
                }
                else{
                    tipoD.setText(" ");
                }
            }
        });
    }
}
