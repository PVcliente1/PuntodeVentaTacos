package com.example.ricardosernam.puntodeventa.Proveedores;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;


public class AgregarProvedor extends DialogFragment {
    Button btnGuardar, btnCancelar;
    EditText ETContacto, ETTelefono, ETDireccion, ETEmpresa;

    //
    private DialogInterface.OnDismissListener onDismissListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_agregar_provedor, container, false);

        //casting de los botones
        btnGuardar = view.findViewById(R.id.BTN_InsertarProveedor);
        btnCancelar = view.findViewById(R.id.BTN_cerrar);

        //casting de los campos
        ETContacto = view.findViewById(R.id.ETcontactoNuevo);
        ETTelefono = view.findViewById(R.id.ETtelefonoNuevo);
        ETDireccion = view.findViewById(R.id.ETdireccionNuevo);
        ETEmpresa = view.findViewById(R.id.ETempresaNuevo);

        //evento de guardar nuevo
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                alta("proveedores");
                Toast.makeText(getContext(), "Guardado correctamente, refresca para visualizarlo", Toast.LENGTH_LONG).show();
                dismiss();

            }
        });
        //evento de boton de atras
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Nuevo Proveedor");

        // Inflate the layout for this fragment
        return view;
    }

    //funcion para dar de alta, si funciona regresa true, si no regresa un false
    public void alta(String tabla)
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();
        //agregar info al registro
        nuevoRegistro.put("Contacto",ETContacto.getText().toString());
        nuevoRegistro.put("Telefono",ETTelefono.getText().toString());
        nuevoRegistro.put("Direccion",ETDireccion.getText().toString());
        nuevoRegistro.put("Empresa", ETEmpresa.getText().toString());

        //insertar el nuevo registro
        db.insert(tabla,null, nuevoRegistro);

        //cerrar la base de datos
        db.close();
    }
}
