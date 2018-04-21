package com.example.ricardosernam.puntodeventa.Ventas;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.actualizado;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
//public class Ventas extends Fragment implements Pro_DialogFragment.agregado {     /////Fragment de categoria ventas
public class Ventas extends Fragment {     /////Fragment de categoria ventas
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private android.support.v4.app.FragmentManager fm;
    private FragmentManager fm2;
    private Button eliminarCompra,aceptarCompra;
    private ContentValues values;
    private CardView cobro;
    private TextView total;
    private Button historial;
    private SQLiteDatabase db;
    private Cursor datosSeleccionado, datosEscaneado, consultaIdCliente, consultaIdProducto, consultaIdDescuento, consultaIdVentas;
    private Cursor fila;
    private ArrayList<Pro_ventas_class> itemsProductos= new ArrayList <>();
    private ArrayList<Cobrar_ventas_class> itemsCobrar= new ArrayList <>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ventas, container, false);
        onViewCreated(view, savedInstanceState);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        fm= getActivity().getSupportFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        fm2= getActivity().getFragmentManager(); ////lo utilizamos para llamar el DialogFragment de producto
        //historial= view.findViewById(R.id.BtnHistorial);

        values = new ContentValues();

        total=view.findViewById(R.id.TVtotal);
        cobro=view.findViewById(R.id.CVcobrar);

        eliminarCompra = view.findViewById(R.id.BtnEliminarCompra);
        aceptarCompra = view.findViewById(R.id.BtnAceptarCompra);


        fila=db.rawQuery("select nombre, precio_venta from Productos" ,null);

        if(fila.moveToFirst()) {///si hay un elemento
            itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getFloat(1)));
            while (fila.moveToNext()) {
                itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getFloat(1)));
            }
        }

        recycler = view.findViewById(R.id.RVrecicladorPro); ///declaramos el recycler
        lManager = new GridLayoutManager(this.getActivity(),2);  //declaramos el GridLayoutManager con dos columnas
        recycler.setLayoutManager(lManager);
        adapter = new Pro_ventasAdapter(itemsProductos, new actualizado() {  ///obtenemos los datos capturados para cada producto
        @Override
            public void actualizar(int cantidad, String seleccionado) {
                datosSeleccionado=db.rawQuery("select precio_venta from Productos where nombre='"+seleccionado+"'" ,null);
                if(datosSeleccionado.moveToFirst()) {
                    itemsCobrar.add(new Cobrar_ventas_class(seleccionado, cantidad, datosSeleccionado.getFloat(0), cantidad*datosSeleccionado.getFloat(0)));//obtenemos el cardview seleccionado y lo agregamos a items2
                }
                ///comprobamos si se repite
                for(int i=0; i<itemsCobrar.size(); i++) {
                    String dato=itemsCobrar.get(i).getNombre();
                    for(int j=i+1; j<itemsCobrar.size(); j++) {
                        if(dato.equals(itemsCobrar.get(j).getNombre())){  ///si se repite
                            if(itemsCobrar.get(j).getCantidad()==0){  ///si es cero, lo eliminamos de la lista
                                itemsCobrar.remove(j);   ////eliminamos el previamente agregado
                                itemsCobrar.remove(i);   ////eliminamos el previamente agregado
                            }
                            else {
                                itemsCobrar.remove(i);   ////eliminamos el previamente agregado
                            }
                        }
                    }
                }
                calcularTotal();
                if(itemsCobrar.isEmpty()){
                    total.setText("0.0");
                    eliminarCompra.setVisibility(View.INVISIBLE);
                    aceptarCompra.setVisibility(View.INVISIBLE);
                }
                else{
                    eliminarCompra.setVisibility(View.VISIBLE);
                    aceptarCompra.setVisibility(View.VISIBLE);
                }
            }
        });
        recycler.setAdapter(adapter);

        /*historial.setOnClickListener(new View.OnClickListener() {  ///abrimos el historial de ventas
            @Override
            public void onClick(View view) {
                new Historial_DialogFragment().show(fm, "Historial_DialogFragment");
            }
        });*/

        return view;
    }
    public void calcularTotal(){
        float suma=0;
        for(int i=0; i<itemsCobrar.size(); i++){
            suma=suma+itemsCobrar.get(i).getSubTotal();
            total.setText(String.valueOf(suma));
        }
    }
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                            db.insertOrThrow("Ventas", null, values);     ///cerrrar aqui
                            cerrar_compra();
                            Toast.makeText(getContext(), "Se ha guardado tu compra", Toast.LENGTH_LONG).show();
                    }
                }).show(getFragmentManager(),"pagarDiaogFragment");
            }
        });
    }*/
    /*public void cerrar_compra(){
        cobro.setVisibility(View.GONE);
        itemsCobrar.removeAll(itemsCobrar);
    }*/
}
