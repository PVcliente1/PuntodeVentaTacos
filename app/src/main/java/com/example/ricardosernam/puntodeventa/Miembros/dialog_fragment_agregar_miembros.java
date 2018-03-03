package com.example.ricardosernam.puntodeventa.Miembros;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;


public class dialog_fragment_agregar_miembros extends DialogFragment {
    Button btnGuardar, btnCancelar;
    EditText EtNombre, EtApellido, EtTelefono, ETcontraseñaAdministrador,ETcorreo;
    Spinner SPpuestos, SPturnos;
    String[] puestos;
    String[] turnos;
    String puesto;
    String turno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_agregar_miembro, container, false);

        btnGuardar = view.findViewById(R.id.btnGuardarMiembro);
        btnCancelar = view.findViewById(R.id.btnCancelarAgregarMiembro);

        EtNombre = view.findViewById(R.id.EtNombreMiembro);
        EtApellido = view.findViewById(R.id.EtApellidoMiembro);
        EtTelefono=view.findViewById(R.id.ETTelefonomiembro);
        ETcorreo=view.findViewById(R.id.ETcorreoMiembro);
        ETcontraseñaAdministrador=view.findViewById(R.id.ETcontraseñaAdministrador);
        SPturnos=view.findViewById(R.id.SPturnos);
        SPpuestos=view.findViewById(R.id.SPpuestos);

        puestos = new String[]{
                "Administrador","Supervisor","Vendedor"
        };

        turnos = new String[]{
                "1","2"
        };

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, puestos); //selected item will look like a spinner set from XML
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPpuestos.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, turnos); //selected item will look like a spinner set from XML
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SPturnos.setAdapter(adapter2);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        getDialog().setTitle("Agregar Miembro");


        // Inflate the layout for this fragment
        return view;
    }

    public void alta(String tabla)
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext());
        SQLiteDatabase db = admin.getWritableDatabase();


        /*
        idmiembro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  `nombre` text, " +
                " `telefono` text, " +
                "  `correo` text, " +
                "  `contrasena` text, " +
                "  `idturno` INTEGER NOT NULL, " +
                "  `idpuesto` INTEGER NOT NULL, " +
                "  `foto` text, " +
                "  `apellido` text)");


         */
        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();
        puesto=SPpuestos.getSelectedItem().toString();
        turno=SPturnos.getSelectedItem().toString();
        //agregar info al registro
        nuevoRegistro.put("nombre",EtNombre.getText().toString());
        nuevoRegistro.put("apellido",EtApellido.getText().toString());
        nuevoRegistro.put("telefono",EtTelefono.getText().toString());
        nuevoRegistro.put("correo", ETcorreo.getText().toString());
        nuevoRegistro.put("contrasena", ETcontraseñaAdministrador.getText().toString());
        nuevoRegistro.put("idturno", turno);
        nuevoRegistro.put("idpuesto", puesto);
        nuevoRegistro.put("foto", puesto);

        //insertar el nuevo registro
        db.insert(tabla,null, nuevoRegistro);

        //cerrar la base de datos
        db.close();
    }

}
