package com.example.ricardosernam.puntodeventa.Miembros;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Compras.Compras;
import com.example.ricardosernam.puntodeventa.Proveedores.Proveedores;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class dialog_fragment_agregar_miembros extends DialogFragment {
    Button btnGuardar, btnCancelar, imagen;
    EditText EtNombre, EtApellido, EtTelefono, ETcontraseñaAdministrador,ETcorreo;
    Spinner SPpuestos, SPturnos;
    FragmentManager fm;
    ImageView ponerImagen;
    String rutaImagen;
    Uri selectedImage;
    String[] puestos;
    String[] turnos;
    String puesto;
    String turno;
    int puestoid;
    int turnoid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_agregar_miembro, container, false);

        btnGuardar = view.findViewById(R.id.btnGuardarMiembro);
        btnCancelar = view.findViewById(R.id.btnCancelarAgregarMiembro);
        imagen = view.findViewById(R.id.BtnimagenMiembro);

        ponerImagen = view.findViewById(R.id.IVimagenMiembro);

        EtNombre = view.findViewById(R.id.EtNombreMiembro);
        EtApellido = view.findViewById(R.id.EtApellidoMiembro);
        EtTelefono=view.findViewById(R.id.ETTelefonomiembro);
        ETcorreo=view.findViewById(R.id.ETcorreoMiembro);
        ETcontraseñaAdministrador=view.findViewById(R.id.ETcontraseñaAdministrador);
        SPturnos=view.findViewById(R.id.SPturnos);
        SPpuestos=view.findViewById(R.id.SPpuestos);

        fm=getActivity().getFragmentManager();

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

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.DialogFragment dialog = new traerImagen(new interfaz_SeleccionarImagen() {
                    @Override
                    public void onClick(Intent intent, int requestCode) {
                        startActivityForResult(intent, requestCode);
                    }
                });
                dialog.show(fm, "NoticeDialogFragment");
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alta("Miembros");
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();
                Miembros frag = new Miembros();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.LOprincipal, frag);
                ft.addToBackStack(null);
                ft.commit();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////1 para entrar a galeria y tomar una foto
        if (requestCode == 1) {
            //Uri selectedImage;///uri es la ruta
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen=selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ponerImagen.setImageURI(selectedImage);
                }
            }
        }
        //2 Captura de foto
        if(requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen=selectedImage.getPath();///ruta de la imagen

                if (rutaImagen != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ponerImagen.setImageURI(selectedImage);
                }
            }
        }

    }

    public void alta(String tabla)
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext());
        SQLiteDatabase db = admin.getWritableDatabase();
        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();
        puesto=SPpuestos.getSelectedItem().toString();
        turno=SPturnos.getSelectedItem().toString();
        if (puesto=="Administrador"){
            puestoid=1;
        }else if (puesto=="Supervisor"){
            puestoid=2;
        }else if(puesto=="Vendedor"){
            puestoid=3;
        }
        turno=SPturnos.getSelectedItem().toString();

        if (turno=="1"){
            turnoid=1;
        }else if (turno=="2"){
            turnoid=2;
        };

        //agregar info al registro
        nuevoRegistro.put("nombre",EtNombre.getText().toString());
        nuevoRegistro.put("apellido",EtApellido.getText().toString());
        nuevoRegistro.put("telefono",EtTelefono.getText().toString());
        nuevoRegistro.put("correo", ETcorreo.getText().toString());
        nuevoRegistro.put("contrasena", ETcontraseñaAdministrador.getText().toString());
        nuevoRegistro.put("idturno", turnoid);
        nuevoRegistro.put("idpuesto", puestoid);
        nuevoRegistro.put("foto", String.valueOf(selectedImage));

        //insertar el nuevo registro
        db.insert(tabla,null, nuevoRegistro);

        //cerrar la base de datos
        db.close();
        Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();

        //todo este desmadre es para que se refresque xD
        Compras frag = new Compras();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.replace(R.id.LOprincipal, frag);
        ft.addToBackStack(null);
        ft.commit();

        //cerrar el dialog
        dismiss();
    }

}
