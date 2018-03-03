package com.example.ricardosernam.puntodeventa.Clientes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Proveedores.Proveedores;
import com.example.ricardosernam.puntodeventa.R;


public class dialog_fragment_agregar_cliente extends DialogFragment {
    Button guardar_cliente, cancelar_cliente;
    EditText ET_clienteNombreNuevo, ET_clienteApellidosNuevo,ET_clienteAliasNuevo,ET_clienteTelefonoNuevo,ET_clienteDireccionNueva;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_agregar_cliente, container, false);

        //casting de botones
        guardar_cliente=view.findViewById(R.id.BtnGuardarCliente);
        cancelar_cliente=view.findViewById(R.id.BtnCancelarAgregarCliente);

        //casting de editTexts
        ET_clienteNombreNuevo = view.findViewById(R.id.ET_clienteNombreNuevo);
        ET_clienteApellidosNuevo = view.findViewById(R.id.ET_clienteApellidosNuevo);
        ET_clienteAliasNuevo = view.findViewById(R.id.ET_clienteAliasNuevo);
        ET_clienteTelefonoNuevo = view.findViewById(R.id.ET_clienteTelefonoNuevo);
        ET_clienteDireccionNueva = view.findViewById(R.id.ET_clienteDireccionNueva);

        guardar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alta();//se llama al procedimiento de altas
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();

                //todo este desmadre es para que se refresque xD
                Clientes frag = new Clientes();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.LOprincipal, frag);
                ft.addToBackStack(null);
                ft.commit();

                //cerrar el dialog
                dismiss();
            }
        });
        cancelar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Agregar Cliente");
        return view;

    }

    public void alta()
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();
        //agregar info al registro
        nuevoRegistro.put("nombre",ET_clienteNombreNuevo.getText().toString());
        nuevoRegistro.put("apellido", ET_clienteApellidosNuevo.getText().toString());
        nuevoRegistro.put("alias", ET_clienteAliasNuevo.getText().toString());
        nuevoRegistro.put("telefono",ET_clienteTelefonoNuevo.getText().toString());
        nuevoRegistro.put("direccion",ET_clienteDireccionNueva.getText().toString());

        //insertar el nuevo registro
        db.insert("Clientes",null, nuevoRegistro);

        //cerrar la base de datos
        db.close();
    }
}

