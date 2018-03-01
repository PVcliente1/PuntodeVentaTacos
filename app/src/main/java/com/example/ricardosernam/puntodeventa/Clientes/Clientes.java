package com.example.ricardosernam.puntodeventa.Clientes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;

public class Clientes extends Fragment {
    Button BTN_ClientesAgregarNuevo,BTN_ClientesRefescar,BTN_ClienteEditarSel,BTN_ClienteEliminarSel;
    LinearLayout info;
    EditText ETClientesNombre, ETClientesApellidos, ETClienteAlias, ETClienteTelefono, ETClientesDireccion;
    Spinner SpinnerClientes;
    TextView TVidCliente;
    int idSeleccionado = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);

        //Casting de botones
        BTN_ClientesAgregarNuevo = view.findViewById(R.id.BTN_ClientesAgregarNuevo);
        BTN_ClientesRefescar = view.findViewById(R.id.BTN_ClientesRefescar);
        BTN_ClienteEditarSel = view.findViewById(R.id.BTN_ClienteEditarSel);
        BTN_ClienteEliminarSel = view.findViewById(R.id.BTN_ClienteEliminarSel);

        //casting de spinner
        SpinnerClientes = view.findViewById(R.id.SpinnerClientes);

        //Casting de EditText
        ETClientesNombre = view.findViewById(R.id.ETClientesNombre);
        ETClientesApellidos = view.findViewById(R.id.ETClientesApellidos);
        ETClienteAlias = view.findViewById(R.id.ETClienteAlias);
        ETClienteTelefono = view.findViewById(R.id.ETClienteTelefono);
        ETClientesDireccion = view.findViewById(R.id.ETClientesDireccion);
        TVidCliente = view.findViewById(R.id.TVidCliente);


        BTN_ClientesAgregarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_cliente().show(getFragmentManager(),"dialog_fragment_agregar_cliente");
            }
        });
        return view;
    }


    //procedimiento para agregar datos al spinner
    public void adapterSpinner(){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idcliente AS _id, alias from Clientes", null);

        if (c.getCount() > 0){
            //adapter
            String[] desde = new String[] {"nombre"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            SpinnerClientes.setAdapter(adapter);
        }else{
            TVidCliente.setText("ID seleccionado: ");
            ETClientesNombre.setText("");
            ETClientesApellidos.setText("");
            ETClienteAlias.setText("");
            ETClienteTelefono.setText("");
            ETClientesDireccion.setText("");
        }
    }
}
