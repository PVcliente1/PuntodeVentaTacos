package com.example.ricardosernam.puntodeventa.Clientes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    Button BTN_ClientesAgregarNuevo,BTN_ClienteEditarSel,BTN_ClienteEliminarSel;
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

        //casting del layout de la info
        info = view.findViewById(R.id.LayoutClientesInfo);

        //evento de boton insertar nuevo
        BTN_ClientesAgregarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_cliente().show(getFragmentManager(),"dialog_fragment_agregar_cliente");
            }
        });

        //evento de boton editar
        BTN_ClienteEditarSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(idSeleccionado);

                refrescar();
            }
        });

        //evento de boton eliminar
        BTN_ClienteEliminarSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baja(idSeleccionado);

                refrescar();
            }
        });

        //evento para jalar datos cuando se selecciona un ciente
        SpinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //hacer visible la info
                info.setVisibility(View.VISIBLE);

                //se modifica la variable que almacena el id seleccionado
                idSeleccionado = (int)id;

                //llenar los campos con el 'id' seleccionado
                jalarDatos(idSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //hacer invisible la info
                info.setVisibility(View.INVISIBLE);


            }
        });

        //procedimiento para llenar el spinner con datos de la db
        adapterSpinner();

        return view;
    }


    //procedimiento para agregar datos al spinner
    public void adapterSpinner(){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idcliente AS _id, alias from Clientes", null);

        if (c.getCount() > 0)
        {
            //adapter
            String[] desde = new String[] {"alias"};
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

    //Procedimiento para extraer datos de X id y mostrarlos para su manejo
    public void jalarDatos(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select * from clientes where idcliente = " + id, null);
        //inicializamos el cursor
        c.moveToPosition(0);

        //llenar los campos a editar
        TVidCliente.setText("ID seleccionado: " + c.getString(0));
        ETClientesNombre.setText(c.getString(1));
        ETClientesApellidos.setText(c.getString(2));
        ETClienteAlias.setText(c.getString(3));
        ETClienteTelefono.setText(c.getString(4));
        ETClientesDireccion.setText(c.getString(5));
    }

    //procedimiento para modificar
    public void modificar(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //creamos nuevo registro
        ContentValues registro = new ContentValues();

        //agregamos datos al registro
        registro.put("nombre",ETClientesNombre.getText().toString());
        registro.put("apellido", ETClientesApellidos.getText().toString());
        registro.put("alias", ETClienteAlias.getText().toString());
        registro.put("telefono",ETClienteTelefono.getText().toString());
        registro.put("direccion",ETClientesDireccion.getText().toString());

        //se realiza un update en donde el id sea igual al id seleccionado
        db.update("clientes", registro, "idcliente = " + id, null);
    }

    //procedimiento para eliminar
    public void baja(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //Se borra el registro que contenga el id seleccionado
        db.delete("clientes", "idcliente = "+  id, null);
    }

    //metodo para refrescar el fragment
    void refrescar(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}
