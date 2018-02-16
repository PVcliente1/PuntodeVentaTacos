package com.example.ricardosernam.puntodeventa.Proveedores;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
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
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Proveedores.AgregarProvedor;
import com.example.ricardosernam.puntodeventa.R;

import java.util.ArrayList;

public class Proveedores extends Fragment {
    Button BTN_abrirFragmentAgregarNuevo, BTN_editarSeleccionado, BTN_eliminarSeleccionado, BTN_refrescar;
    EditText ET_contaco, ET_telefono, ET_direccion, ET_empresa;
    TextView TV_idSeleccionado;
    Spinner Spinner;
    LinearLayout info;
    ArrayList<String> listaProveedores = new ArrayList<String>();
    int idSeleccionado = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_provedores, container, false);

        //Cast de componentes
        BTN_abrirFragmentAgregarNuevo = view.findViewById(R.id.BTN_ProveedoresAgregarNuevo);
        BTN_editarSeleccionado = view.findViewById(R.id.BTN_ProveedoresEditarSel);
        BTN_eliminarSeleccionado = view.findViewById(R.id.BTN_ProveedoresEliminarSel);
        BTN_refrescar = view.findViewById(R.id.BTN_ProveedoresRefescar);
        ET_contaco = view.findViewById(R.id.ETProveedoresContacto);
        ET_telefono = view.findViewById(R.id.ETProveedoresTelefono);
        ET_direccion = view.findViewById(R.id.ETProveedoresDireccion);
        ET_empresa = view.findViewById(R.id.ETProveedoresEmpresa);
        TV_idSeleccionado = view.findViewById(R.id.TVidProveedor);
        Spinner = view.findViewById(R.id.SpinnerProveedores);
        info = view.findViewById(R.id.LayoutProveedoresInfo);

        //Adapter para pasarle datos al spinner
        adapterSpinner();

        //Eventos de botones, 4 en total
        //Evento para lanzar framentDialog de agregar nuevo
        BTN_abrirFragmentAgregarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AgregarProvedor().show(getFragmentManager(),"AgregarProvedor");
            }
        });
        //Evento para editar el seleccionado
        BTN_editarSeleccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(idSeleccionado);
                Toast.makeText(getContext(), "Editado", Toast.LENGTH_SHORT).show();
            }
        });
        //Evento para eleminar el seleccionado
        BTN_eliminarSeleccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baja(idSeleccionado);
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        //Evento para refrescar el Spinner
        BTN_refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterSpinner();
            }
        });


        //evento para cuando se selecciona un proveedor
        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        return view;

    }

    //procedimiento para agregar datos al spinner
    public void adapterSpinner(){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select id AS _id, Contacto from Proveedores", null);

        if (c.getCount() > 0){
            //adapter
            String[] desde = new String[] {"Contacto"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            Spinner.setAdapter(adapter);
        }else{
            TV_idSeleccionado.setText("ID seleccionado: ");
            ET_contaco.setText("");
            ET_telefono.setText("");
            ET_direccion.setText("");
            ET_empresa.setText("");
        }
    }

    //Procedimiento para extraer datos de X id y mostrarlos para su manejo
    public void jalarDatos(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select Contacto, Telefono, Direccion, Empresa from Proveedores where id = " + id, null);
        //inicializamos el cursor
        c.moveToPosition(0);

        //llenar los campos a editar
        TV_idSeleccionado.setText("ID seleccionado: " + id);
        ET_contaco.setText(c.getString(0));
        ET_telefono.setText(c.getString(1));
        ET_direccion.setText(c.getString(2));
        ET_empresa.setText(c.getString(3));
    }

    //procedimiento para dar de baja
    public void baja(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //Se borra el registro que contenga el id seleccionado
        db.delete("Proveedores", "id = "+  id, null);

        //actualizar espinner
        adapterSpinner();
    }

    //procedimiento para modificar
    public void modificar(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //creamos nuevo registro
        ContentValues registro = new ContentValues();
        //agregamos datos al registro
        registro.put("Contacto", ET_contaco.getText().toString());
        registro.put("Telefono", ET_telefono.getText().toString());
        registro.put("Direccion", ET_direccion.getText().toString());
        registro.put("Empresa", ET_empresa.getText().toString());
        //se realiza un update en donde el id sea igual al id seleccionado
        db.update("Proveedores", registro, "id = " + id, null);

        //actualizar espinner
        adapterSpinner();
    }

}
