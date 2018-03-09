package com.example.ricardosernam.puntodeventa.Miembros;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Productos.Unidades_class;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Miembros extends Fragment {
    LinearLayout contenedor, infoMiembro, miembroL,LLaceptarCancelarEdicionMiembro;
    String[] items;
    Spinner spinner;
    Button btnNuevoMiembro, editarMiembro, imagen, btneliminarMiembro,cancelarEditar, aceptarEditar;
    private ArrayList<Unidades_class> itemsUnidades;
    FragmentManager fm;
    String rutaImagen;
    Uri selectedImage;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    private ImageView ponerImagen;
    int idSeleccionado=2;
    EditText nombreMiembro, apellidos, telefono, correo,contrasena,confirmarContrasena;
    Spinner turno, puesto;
    String [] puestos;
    String [] turnos;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_miembros, container, false);
        btnNuevoMiembro = view.findViewById(R.id.BtnAgregarMiembro);
        editarMiembro = view.findViewById(R.id.BtnEditarMiembro);
        btneliminarMiembro = view.findViewById(R.id.BtnEliminarMiembro);
        imagen = view.findViewById(R.id.BtnImagen);
        ponerImagen = view.findViewById(R.id.ImgImagen);
        spinner = view.findViewById(R.id.SPvendedores);
        infoMiembro = view.findViewById(R.id.LLinformaciónMiembros);
        fm = getActivity().getFragmentManager();
        nombreMiembro = view.findViewById(R.id.ETnombreMiembroSeleccionado);
        apellidos = view.findViewById(R.id.ETapellidoMiembroSeleccionado);
        telefono = view.findViewById(R.id.ET_telefonoMiembroSeleccionado);
        correo = view.findViewById(R.id.ETcorreoMiembroSeleccionado);
        aceptarEditar=view.findViewById(R.id.BtnAceptarMiembro);
        cancelarEditar=view.findViewById(R.id.BtnCancelarMiembro);
        LLaceptarCancelarEdicionMiembro=view.findViewById(R.id.LLaceptarCancelarEdicionMiembro);
        contrasena=view.findViewById(R.id.ETcontraseñaAdministrador);
        confirmarContrasena=view.findViewById(R.id.ETconfirmarContraseña);
        turno=view.findViewById(R.id.SPturnos);
        puesto=view.findViewById(R.id.SPpuestos);


        //Llenamos el Spinner con los datos almacenados en nuestra base de datos
        adapterSpinner();


        //Al inicio de la aplicación ocultamos la información del miembro seleccionado
        infoMiembro.setVisibility(View.INVISIBLE);


        //Botón agregar miembro
        btnNuevoMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new dialog_fragment_agregar_miembros().show(getFragmentManager(),"Agregar Miembro");
            }
        });


        //Botón editar miembro
        editarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                puestos = new String[]{
                        "Administrador","Supervisor","Vendedor"
                };

                turnos = new String[]{
                        "1","2"
                };

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, puestos); //selected item will look like a spinner set from XML
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                puesto.setAdapter(adapter1);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, turnos); //selected item will look like a spinner set from XML
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                turno.setAdapter(adapter2);

                imagen.setVisibility(View.VISIBLE);
                nombreMiembro.setEnabled(true);
                apellidos.setEnabled(true);
                telefono.setEnabled(true);
                correo.setEnabled(true);
                contrasena.setEnabled(true);
                confirmarContrasena.setEnabled(true);

                LLaceptarCancelarEdicionMiembro.setVisibility(View.VISIBLE);
            }
        });

        //evento aceptar editado
        aceptarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(idSeleccionado);
                Toast.makeText(getContext(), "Editado", Toast.LENGTH_SHORT).show();
                refrescar();
                infoMiembro.setVisibility(View.INVISIBLE);
                Miembros frag = new Miembros();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.LOprincipal, frag);
                spinner.setSelection(0);
                //ft.addToBackStack(null);
                //ft.commit();
                dismiss();
            }

            private void dismiss() {
            }
        });


        //evento cancelar editado
        cancelarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagen.setVisibility(View.INVISIBLE);
                nombreMiembro.setEnabled(false);
                apellidos.setEnabled(false);
                telefono.setEnabled(false);
                correo.setEnabled(false);
                LLaceptarCancelarEdicionMiembro.setVisibility(View.INVISIBLE);
                infoMiembro.setVisibility(view.INVISIBLE);
            }
        });


        //Evento para eleminar el seleccionado
        btneliminarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baja(idSeleccionado);
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                //limpiar campos
                nombreMiembro.setText("");
                apellidos.setText("");
                telefono.setText("");
                correo.setText("");
                refrescar();
            }
        });


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


        //evento para cuando se selecciona un Miembro
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (l != 1)
                {
                    //hacer visible la info
                    infoMiembro.setVisibility(View.VISIBLE);

                    //se modifica la variable que almacena el id seleccionado
                    idSeleccionado = (int)l;
                    //llenar los campos con el 'id' seleccionado
                    jalarDatos(idSeleccionado);
                }
                else
                {
                    infoMiembro.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                infoMiembro.setVisibility(View.INVISIBLE);
            }
        });
        return view;

    }

    //procedimiento para agregar datos al spinner
    public void adapterSpinner(){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select idmiembro AS _id, nombre from Miembros", null);


        if (c.getCount() > 0){
            //adapter
            String[] desde = new String[] {"nombre"};
            int[] para = new int[] {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, c, desde, para);
            //activar al layout del adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //configurar adapter
            spinner.setAdapter(adapter);
        }{
            infoMiembro.setVisibility(View.INVISIBLE);
        }
    }

    //procedimiento para traernos los datos del miembro seleccionado
    public void jalarDatos(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getReadableDatabase();

        //cursor
        Cursor c = db.rawQuery("select * from Miembros where idmiembro = " + id, null);
        //inicializamos el cursor
        c.moveToPosition(0);

        //llenar los campos a editar
        nombreMiembro.setText(c.getString(1));
        apellidos.setText(c.getString(2));
        telefono.setText(c.getString(3));
        correo.setText(c.getString(4));
    }

    //procedimiento para dar de baja
    public void baja(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //Se borra el registro que contenga el id seleccionado
        db.delete("Miembros", "idmiembro = "+  id, null);
    }


    //procedimiento para modificar
    public void modificar(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //creamos nuevo registro
        ContentValues registro = new ContentValues();
        //agregamos datos al registro
        //agregar info al registro
        int puestoid=0;
        int turnoid=0;
        String puestoseleccionado= puesto.getSelectedItem().toString();
        String turnoseleccionado = turno.getSelectedItem().toString();

        if (puestoseleccionado=="Administrador"){
            puestoid=1;
        }else if (puestoseleccionado=="Supervisor"){
            puestoid=2;
        }else if (puestoseleccionado=="Vendedor"){
            puestoid=3;
        }

        if (turnoseleccionado=="1"){
            turnoid=1;
        }else if(turnoseleccionado=="2"){
            turnoid=2;
        }

        registro.put("nombre",nombreMiembro.getText().toString());
        registro.put("apellido",apellidos.getText().toString());
        registro.put("telefono",telefono.getText().toString());
        registro.put("correo", correo.getText().toString());
        //registro.put("contrasena", ETcontraseñaAdministrador.getText().toString());
        registro.put("idturno", turnoid);
        registro.put("idpuesto", puestoid);
        registro.put("foto", String.valueOf(selectedImage));

        //se realiza un update en donde el id sea igual al id seleccionado
        db.update("Miembros", registro, "idmiembro = " + id, null);
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


    void refrescar(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }



}
