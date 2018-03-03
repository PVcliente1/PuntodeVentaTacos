package com.example.ricardosernam.puntodeventa.Proveedores;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    Button BTN_abrirFragmentAgregarNuevo, BTN_editarSeleccionado, BTN_eliminarSeleccionado,BTNBuscarProveedores,BTNAceptarEditar,BTNCancelarEditar;
    EditText ET_contaco, ET_telefono, ET_direccion, ET_empresa,ET_BuscarProveedor;
    TextView TV_idSeleccionado;
    Spinner Spinner;
    LinearLayout info,LayoutBotonesAceptarEditar;
    int idSeleccionado = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_provedores, container, false);

        //Cast de componentes
        BTN_abrirFragmentAgregarNuevo = view.findViewById(R.id.BTN_ProveedoresAgregarNuevo);
        BTNBuscarProveedores = view.findViewById(R.id.BTNBuscarProveedores);
        BTN_editarSeleccionado = view.findViewById(R.id.BTN_ProveedoresEditarSel);
        BTN_eliminarSeleccionado = view.findViewById(R.id.BTN_ProveedoresEliminarSel);
        BTNCancelarEditar = view.findViewById(R.id.BTNCancelarEditar);
        BTNAceptarEditar = view.findViewById(R.id.BTNAceptarEditar);
        ET_contaco = view.findViewById(R.id.ETProveedoresContacto);
        ET_telefono = view.findViewById(R.id.ETProveedoresTelefono);
        ET_direccion = view.findViewById(R.id.ETProveedoresDireccion);
        ET_empresa = view.findViewById(R.id.ETProveedoresEmpresa);
        ET_BuscarProveedor = view.findViewById(R.id.ET_BuscarProveedor);
        TV_idSeleccionado = view.findViewById(R.id.TVidProveedor);
        Spinner = view.findViewById(R.id.SpinnerProveedores);
        info = view.findViewById(R.id.LayoutProveedoresInfo);
        LayoutBotonesAceptarEditar = view.findViewById(R.id.LayoutBotonesAceptarEditarProveedores);

        //Adapter para pasarle datos al spinner
        adapterSpinner();
        info.setVisibility(View.INVISIBLE);
        //Eventos de botones, 4 en total
        //Evento para lanzar framentDialog de agregar nuevo
        BTN_abrirFragmentAgregarNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se abre el dialog fragment
                new AgregarProvedor().show(getFragmentManager(),"AgregarProvedor");
            }
        });


        //Evento para editar el seleccionado
        BTN_editarSeleccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_contaco.setEnabled(true);
                ET_telefono.setEnabled(true);
                ET_direccion.setEnabled(true);
                ET_empresa.setEnabled(true);

                LayoutBotonesAceptarEditar.setVisibility(View.VISIBLE);
            }
        });
        //evento aceptar editado
        BTNAceptarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(idSeleccionado);
                Toast.makeText(getContext(), "Editado", Toast.LENGTH_SHORT).show();
                refrescar();
            }
        });
        //evento cancelar editado
        BTNCancelarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_contaco.setEnabled(false);
                ET_telefono.setEnabled(false);
                ET_direccion.setEnabled(false);
                ET_empresa.setEnabled(false);

                LayoutBotonesAceptarEditar.setVisibility(View.INVISIBLE);
            }
        });

        //Evento para eleminar el seleccionado
        BTN_eliminarSeleccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baja(idSeleccionado);
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                //limpiar campos
                TV_idSeleccionado.setText("ID seleccionado: ");
                ET_contaco.setText("");
                ET_telefono.setText("");
                ET_direccion.setText("");
                ET_empresa.setText("");
                refrescar();
            }
        });
        //Evento de boton buscar
        BTNBuscarProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });

        //evento para cuando se selecciona un proveedor
        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //checar si no es el anuncio de "seleccionar"
                if (id != 1)
                {
                    //hacer visible la info
                    info.setVisibility(View.VISIBLE);

                    //se modifica la variable que almacena el id seleccionado
                    idSeleccionado = (int)id;

                    //llenar los campos con el 'id' seleccionado
                    jalarDatos(idSeleccionado);
                }
                else
                {
                    info.setVisibility(View.INVISIBLE);
                }
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
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idproveedor AS _id, contacto from proveedores", null);

        if (c.getCount() > 0){
            //adapter
            String[] desde = new String[] {"contacto"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            Spinner.setAdapter(adapter);
        }else{
            info.setVisibility(View.INVISIBLE);
        }
    }

    //Procedimiento para extraer datos de X id y mostrarlos para su manejo
    public void jalarDatos(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select * from proveedores where idproveedor = " + id, null);
        //inicializamos el cursor
        c.moveToPosition(0);

        //llenar los campos a editar
        TV_idSeleccionado.setText("ID seleccionado: " + c.getString(0));
        ET_contaco.setText(c.getString(1));
        ET_telefono.setText(c.getString(2));
        ET_direccion.setText(c.getString(3));
        ET_empresa.setText(c.getString(4));
    }

    //procedimiento para dar de baja
    public void baja(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //Se borra el registro que contenga el id seleccionado
        db.delete("proveedores", "idproveedor = "+  id, null);
    }

    //procedimiento para modificar
    public void modificar(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //creamos nuevo registro
        ContentValues registro = new ContentValues();
        //agregamos datos al registro
        registro.put("contacto", ET_contaco.getText().toString());
        registro.put("telefono", ET_telefono.getText().toString());
        registro.put("direccion", ET_direccion.getText().toString());
        registro.put("nombre_empresa", ET_empresa.getText().toString());
        //se realiza un update en donde el id sea igual al id seleccionado
        db.update("proveedores", registro, "idproveedor = " + id, null);
    }

    void refrescar(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    void buscar(){
        String nombre = String.valueOf(ET_BuscarProveedor.getText());

        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();


        //cursor
        Cursor c = db.rawQuery("select * from proveedores where contacto = " + "'" +nombre + "'", null);
        //inicializamos el cursor
        c.moveToPosition(0);

        if (c.getCount() > 0){
           jalarDatos(c.getInt(0));
            info.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), c.getString(0), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Ese proveedor no existe", Toast.LENGTH_SHORT).show();
        }
    }

}
