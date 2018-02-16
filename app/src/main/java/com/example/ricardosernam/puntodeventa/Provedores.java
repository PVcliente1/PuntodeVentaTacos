package com.example.ricardosernam.puntodeventa;

import android.content.ContentValues;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ricardosernam.puntodeventa.R.id.Layout1;

public class Provedores extends Fragment {
    Button btnNuevoProvedor,BTNBajaProveedor,BTNeditarProveedor;
    Spinner spinner;
    //String[] items;
    ArrayList<String> items1 = new ArrayList<String>();
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,SpinnerLayout;
    EditText ETNombreMOD,ETApellidosMOD,ETTelefonoMOD,ETDireccionMOD;
    Boolean visible;

    Integer selectedID = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provedores, container, false);


        visible = false;//variable para saber si los proveedores estan visibles
        //casting de los layouts a ocultar
        layout1 = view.findViewById(R.id.Layout1);
        layout2 = view.findViewById(R.id.Layout2);
        layout3 = view.findViewById(R.id.Layout3);
        layout4 = view.findViewById(R.id.Layout4);
        layout5 = view.findViewById(R.id.Layout5);
        layout6 = view.findViewById(R.id.Layout1);
        SpinnerLayout = view.findViewById(R.id.SpinnerLayout);
        btnNuevoProvedor = view.findViewById(R.id.BtnAgregarProvedor);
        BTNBajaProveedor = view.findViewById(R.id.BTNBajaProveedores);
        BTNeditarProveedor = view.findViewById(R.id.BTNeditarProveedor);
        spinner = view.findViewById(R.id.SPprovedores);

        //casting de los campos
        ETNombreMOD = view.findViewById(R.id.ETNombreMOD);
        ETApellidosMOD = view.findViewById(R.id.ETApellidosMOD);
        ETTelefonoMOD = view.findViewById(R.id.ETTelefonoMOD);
        ETDireccionMOD = view.findViewById(R.id.ETDireccionMOD);

        //funcion para llenar la lista con la base de datos
        llenarLista();
        //primer item es igual a '----'
        items1.set(0, "----");

        //adapter para llenar el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items1); //como se vera la lista, en XML
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //abrir formulario para agregar proveedores
        btnNuevoProvedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AgregarProvedor().show(getFragmentManager(),"Agregar Provedor");
            }
        });

        //evento de baja del proveedor seleccionado
        BTNBajaProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baja(selectedID);
                selectedID = 0;
                ocultarCampos();
            }
        });

        BTNeditarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar(selectedID);
            }
        });

        //evento para cuando se elija un item en el spiner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinner.getSelectedItem().toString();

                //falta arreglar esto, la posicion del spinner no es la misma que el id en la basede datos
                selectedID = spinner.getSelectedItemPosition();

                //si no estan visibles que los muestre
                if (!visible && item != "----") {
                    mostrarCampos();
                }

                //mostrar nombre elejido si no es el primero y llenar los campos a modificar
                if(item != "----") {
                    //Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
                    //llenarCamposModificar(spinner.getSelectedItemPosition());
                    llenarCamposModificar(selectedID);
                }else {
                    ocultarCampos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;

    }

    public void llenarLista(){
        //sacar datos de la base de datos
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor datos = db.rawQuery("Select * from Proveedores", null);

        if(datos.getCount() > 0){
            for (int i = 0; i < datos.getCount(); i++) {
                datos.moveToNext();
                items1.add(datos.getString(0) + " " + datos.getString(1));
            }
        }

        datos.close();
        db.close();
    }

    public void llenarCamposModificar(int id){
        //sacar datos de la base de datos
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor datos = db.rawQuery("Select * from Proveedores where id = " + id, null);
        datos.moveToNext();

        //llenar campos
        ETNombreMOD.setText(datos.getString(1));
        ETApellidosMOD.setText(datos.getString(2));
        ETTelefonoMOD.setText(datos.getString(3));
        ETDireccionMOD.setText(datos.getString(4));

        datos.close();
        db.close();
    }
    //funcion para dar de alta, si funciona regresa true, si no regresa un false
    public void modificar(int id)
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();
        //agregar info al registro
        nuevoRegistro.put("Nombre",ETNombreMOD.getText().toString());
        nuevoRegistro.put("Apellidos",ETApellidosMOD.getText().toString());
        nuevoRegistro.put("Telefono",ETTelefonoMOD.getText().toString());
        nuevoRegistro.put("Direccion", ETDireccionMOD.getText().toString());

        //insertar el nuevo registro
        db.update("Proveedores",nuevoRegistro,"id ="+ id, null);

        //cerrar la base de datos
        db.close();
    }
    public void baja(int id){
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext(),"Proveedores",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.delete("Proveedores","id ="+id,null);
        db.close();
    }

    public void ocultarCampos(){
        layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        layout5.setVisibility(View.INVISIBLE);
        layout6.setVisibility(View.INVISIBLE);
        visible = false;
    }
    public void mostrarCampos(){
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.VISIBLE);
        layout3.setVisibility(View.VISIBLE);
        layout4.setVisibility(View.VISIBLE);
        layout5.setVisibility(View.VISIBLE);
        layout6.setVisibility(View.VISIBLE);
        visible = true;
    }

}
