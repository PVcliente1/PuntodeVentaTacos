package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class Ventas extends Fragment implements Pro_DialogFragment.agregado {     /////Fragment de categoria ventas
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private android.support.v4.app.FragmentManager fm;
    private FragmentManager fm2;
    private Button eliminarCompra,aceptarCompra;
    private ContentValues values;
    private CardView cobro;
    private TextView total;
    private Button productos, escanear, historial;
    private EditText codigo;
    private SQLiteDatabase db;
    private Cursor datosSeleccionado, datosEscaneado, consultaIdCliente, consultaIdProducto, consultaIdDescuento, consultaIdVentas;
    private ArrayList<Cobrar_ventas_class> itemsCobrar = new ArrayList<>();  ///Arraylist que contiene los cardviews seleccionados de productos

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        fm= getActivity().getSupportFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        fm2= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        productos= view.findViewById(R.id.BtnProductos);
        historial= view.findViewById(R.id.BtnHistorial);
        escanear= view.findViewById(R.id.BtnEscanear);
        codigo=view.findViewById(R.id.ETcodigo);
        values = new ContentValues();

        total=view.findViewById(R.id.TVtotal);
        cobro=view.findViewById(R.id.CVcobrar);
        recycler = view.findViewById(R.id.RVproductosSeleccionados);///declaramos el recycler
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
                new Pro_DialogFragment().show(getChildFragmentManager(), "Pro_DialogFragment");
            }
        });

        historial.setOnClickListener(new View.OnClickListener() {  ///abrimos el historial de ventas
            @Override
            public void onClick(View view) {
                new Historial_DialogFragment().show(fm, "Historial_DialogFragment");
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
    public void agregar(ArrayList<Cobrar_ventas_class> itemsCobrar) {    ///agregarNuevaCompra
        this.itemsCobrar = itemsCobrar;
        relleno();
        cobro.setVisibility(View.VISIBLE);
        calcularTotal();
        adapter.notifyDataSetChanged();
    }

    public void calcularTotal(){
        float suma=0;
        for(int i=0; i<itemsCobrar.size(); i++){
            suma=suma+itemsCobrar.get(i).getSubTotal();
            total.setText(String.valueOf(suma));
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eliminarCompra = getActivity().findViewById(R.id.BtnEliminarCompra);
        aceptarCompra = getActivity().findViewById(R.id.BtnAceptarCompra);
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
                new pagar_DialogFragment(Float.parseFloat(String.valueOf(total.getText())), new interfaz_OnClick() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {////ocultamos y guardamos los datos de la compra
                        /////obtener fecha actual
                        /*java.util.Calendar c = java.util.Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("d-M-yyyy H:m");
                            String formattedDate = df.format(c.getTime());*/

                            /////DATOS DE LA VENTA
                        /*consultaIdCliente=db.rawQuery("select idcliente from Clientes where nombre='"+String.valueOf(cliente.getText())+"'" ,null);
                            values.put("tipo", RBseleccionadoTipo);
                            values.put("fecha", formattedDate);
                            values.put("fecha_entrega", String.valueOf(fechaEntrega.getText()) + " " + (horaEntrega.getText())); //
                            values.put("descripcion", String.valueOf(descripcion.getText()));
                            values.put("tipo_cobro", tipoCobro);
                            if(consultaIdCliente.moveToFirst()) {
                                values.put("idclienteFK", consultaIdCliente.getInt(0));
                            }
                            values.put("idmiembroFK", 1);
                            db.insertOrThrow("Ventas", null, values);*/
                            cerrar_compra();
                            Toast.makeText(getContext(), "Se ha guardado tu compra", Toast.LENGTH_LONG).show();
                    }
                }).show(getFragmentManager(),"pagarDiaogFragment");
            }
        });
    }
    public void cerrar_compra(){
        cobro.setVisibility(View.GONE);
        itemsCobrar.removeAll(itemsCobrar);
    }
    //metodo para obtener resultados DEL ESCANER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && data != null) {
            //obtener resultados
            datosEscaneado=db.rawQuery("select nombre, precio_venta from Productos where codigo_barras='"+data.getStringExtra("BARCODE")+"'" ,null);
            if(datosEscaneado.moveToFirst()) {
                itemsCobrar.add(new Cobrar_ventas_class(datosEscaneado.getString(0), 1, datosEscaneado.getFloat(1), datosEscaneado.getFloat(1)));//obtenemos el cardview seleccionado y lo agregamos a items2
                relleno();
                cobro.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getContext(), "Este producto no esta registrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
