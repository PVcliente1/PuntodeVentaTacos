package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_descuento;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Fecha_DialogFragment;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Hora_DialogFragment;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment implements Pro_DialogFragment.agregado, Cobrar_ventasAdapter.actualizado {     /////Fragment de categoria ventas
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private android.support.v4.app.FragmentManager fm;

    private RadioGroup opcionVentas, opcionCobrar;
    private LinearLayout pagar, fechaHora, tipoDescuento;
    private Button eliminarCompra,aceptarCompra;
    private EditText cliente, descripcion, hora, fecha;
    interfaz_descuento Interface_historial;
    private RadioButton seleccionado, seleccionado2;
    private LinearLayout editsApartado, opcionDeVenta;
    private CardView cobro;
    private CheckBox descuento;
    private TextView tipoD, total;
    private String normal, especial;

    private Button productos, escanear, historial;
    private EditText codigo;
    private SQLiteDatabase db;
    private Cursor fila2, datosSeleccionado, datosEscaneado, descuentoNormal, descuentoEspecial;
    private Pro_DialogFragment pro;
    private Historial_DialogFragment DFhistorial;
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        fm= getActivity().getSupportFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        //DFhistorial=new Historial_DialogFragment(itemsHistorial);  ////enviamos el array con las compras al fragment de historial

        productos= view.findViewById(R.id.BtnProductos);
        historial= view.findViewById(R.id.BtnHistorial);
        escanear= view.findViewById(R.id.BtnEscanear);
        codigo=view.findViewById(R.id.ETcodigo);

        fechaHora=view.findViewById(R.id.LOedittext);
        hora=view.findViewById(R.id.EThora);
        fecha=view.findViewById(R.id.ETfecha);
        total=view.findViewById(R.id.TVtotal);
        cobro=view.findViewById(R.id.CVcobrar);
        opcionDeVenta=view.findViewById(R.id.LLopcionDeVenta);

        ///obtenemos los descuentos
        descuentoNormal=db.rawQuery("select porcentaje from Descuentos where tipo_descuento='Normal'" ,null);
        descuentoEspecial=db.rawQuery("select porcentaje from Descuentos where tipo_descuento='Especial'" ,null);

        if(descuentoNormal.moveToFirst()){
            normal=descuentoNormal.getString(0);
        }
        if(descuentoEspecial.moveToFirst()){
            especial=descuentoEspecial.getString(0);
        }
        recycler = view.findViewById(R.id.RVproductosSeleccionados);///declaramos el recycler
        /*if(!(itemsCobrar.isEmpty())) {
            adapter = new Cobrar_ventasAdapter(getFragmentManager().findFragmentById(R.id.LOprincipal), getActivity(), itemsCobrar);///llamamos al adaptador y le enviamos el array como parametro
            recycler = view.findViewById(R.id.RVproductosSeleccionados);///declaramos el recycler
            lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
            recycler.setLayoutManager(lManager);
            recycler.setAdapter(adapter);
        }*/

        pro =new Pro_DialogFragment();  //abrimos el menu de productos

        codigo.setInputType(InputType.TYPE_NULL);  ///cerramos el teclado

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
                pro.show(getChildFragmentManager(), "Pro_DialogFragment");
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
    public void relleno(){    ///llamamos el adapter del recycler
        adapter = new Cobrar_ventasAdapter(getFragmentManager().findFragmentById(R.id.LOprincipal), getActivity(), itemsCobrar);///llamamos al adaptador y le enviamos el array como parametro
        lManager = new LinearLayoutManager(this.getActivity());  //declaramos el layoutmanager
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adapter);
    }
    @Override
    public void agregar(String seleccionado) {    ///agregarNuevaCompra
        datosSeleccionado=db.rawQuery("select unidad, nombre, precio_venta from Productos where nombre='"+seleccionado+"'" ,null);
        if(datosSeleccionado.moveToFirst()) {
            itemsCobrar.add(new Cobrar_ventas_class(datosSeleccionado.getString(0),  datosSeleccionado.getString(1),1, datosSeleccionado.getFloat(2), datosSeleccionado.getFloat(2), 0));//obtenemos el cardview seleccionado y lo agregamos a items2
            relleno();
            cobro.setVisibility(View.VISIBLE);
            opcionDeVenta.setVisibility(View.VISIBLE);  ////actualizamos para calcular el total
            actualizar(datosSeleccionado.getString(0),  datosSeleccionado.getString(1),1, datosSeleccionado.getFloat(2), itemsCobrar.size()-1, datosSeleccionado.getFloat(2), 0);
            adapter.notifyDataSetChanged();
        }
    }
    @Override   ///modificamos los datos de una compra
    public void actualizar(String unidad, String nombre, float cantidad, float precio, int position, float subTotal, int descuento) {  ///al modificar la compra de un producto
        itemsCobrar.set(position, new Cobrar_ventas_class(unidad, nombre, cantidad, precio, subTotal, descuento));  //si actualiza correctamente
        float suma=0;
        for(int i=0; i<itemsCobrar.size(); i++){
            suma=suma+itemsCobrar.get(i).getSubTotal();
            total.setText(String.valueOf(suma));
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Interface_historial =(interfaz_descuento) getActivity();//ESTO SOLO ES POSIBLE SI MainActivity es una subclase de Comunicador por lo tanto implementa Comunicator: Polimorfismo
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
                        cerrar_compra();

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
                new pagar_DialogFragment(Float.parseFloat(String.valueOf(total.getText())), new interfaz_OnClick() {
                    @Override
                    public void onClick(View v) {////ocultamos y guardamos los datos
                        Toast.makeText(getContext(), "Se ha guardado la compra", Toast.LENGTH_LONG).show();
                        cerrar_compra();
                    }
                }).show(getFragmentManager(),"pagarDiaogFragment");
            }
        });
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(int i, int i1) {
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
        cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Clientes_DialogFragment(new interfazUnidades_OnClick() {
                    @Override
                    public void onClick(View v, String clienteSeleccionado) {
                        cliente.setText(clienteSeleccionado);
                    }
                }).show(fm, "Clientes DialogFragment");
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
    public void cerrar_compra(){
        cobro.setVisibility(View.GONE);
        opcionDeVenta.setVisibility(View.GONE);
        itemsCobrar.removeAll(itemsCobrar);
    }
    //metodo para obtener resultados DEL ESCANER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && data != null) {
            //obtener resultados
            datosEscaneado=db.rawQuery("select unidad, nombre, precio_venta from Productos where codigo_barras='"+data.getStringExtra("BARCODE")+"'" ,null);
            if(datosEscaneado.moveToFirst()) {
                itemsCobrar.add(new Cobrar_ventas_class(datosEscaneado.getString(0),  datosEscaneado.getString(1),1, datosEscaneado.getFloat(2), datosEscaneado.getFloat(2), 0));//obtenemos el cardview seleccionado y lo agregamos a items2
                relleno();
                cobro.setVisibility(View.VISIBLE);
                opcionDeVenta.setVisibility(View.VISIBLE);  ////actualizamos para calcular el total
                actualizar(datosEscaneado.getString(0),  datosEscaneado.getString(1),1, datosEscaneado.getFloat(2), itemsCobrar.size()-1, datosEscaneado.getFloat(2), 0);
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getContext(), "Este producto no esta registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
