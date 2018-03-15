package com.example.ricardosernam.puntodeventa.Compras;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.Productos.Unidades_DialogFragment;
import com.example.ricardosernam.puntodeventa.Proveedores.Proveedores;
import com.example.ricardosernam.puntodeventa._____interfazes.interfazUnidades_OnClick;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Compras extends Fragment{
    private LinearLayout existentes,agregar, campos , foto;
    private TextView nombre, cantidadExistentes, totalCompra;
    private Button escan,aceptar,cancelar, seleccionarImagen;
    private EditText capturarProducto,cantidad,precioCompra, precioVenta, nombreProducto, unidad,proveedor;
    private RadioGroup opciones;
    private String rutaImagen;
    private Uri selectedImage;
    private CheckBox agregaraproductos;     //checkbox para agregar a productos
    private ContentValues values;
    private SQLiteDatabase db;
    private FragmentManager fm;
    private Cursor fila;
    private ImageView ponerImagen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compras, container, false);
        //Econtramos los valores de nuestros Radio Button dentro del XML

        fm=getActivity().getFragmentManager();
        opciones=view.findViewById(R.id.RGopcionesCompra);
        ////editText
        capturarProducto=view.findViewById(R.id.ETCapturarProducto);
        cantidad=view.findViewById(R.id.ETcantidadCompra);
        precioCompra=view.findViewById(R.id.ETprecioCompra);
        precioVenta=view.findViewById(R.id.ETprecioVenta);
        nombreProducto=view.findViewById(R.id.ETnombreProducto);
        proveedor=view.findViewById(R.id.ETproveedorCompra);
        ///botones
        aceptar=view.findViewById(R.id.BtnAceptarCompra);
        cancelar=view.findViewById(R.id.BtnCancelarCompra);
        escan = (Button)view.findViewById(R.id.BtnEscanearCodigo);
        seleccionarImagen = (Button)view.findViewById(R.id.BtnImagen);

        //textviews
        cantidadExistentes= view.findViewById(R.id.TVexistentes);
        //nombre= view.findViewById(R.id.TVnombreCompra);
        totalCompra= view.findViewById(R.id.TVtotalCompra);

        agregaraproductos=view.findViewById(R.id.CBagregarProductos);
        unidad=view.findViewById(R.id.ETunidad);

        ///layouts
        agregar=view.findViewById(R.id.LLagregarProductos);
        existentes=view.findViewById(R.id.LLexistentes);
        campos=view.findViewById(R.id.LLcamposDatos);
        foto=view.findViewById(R.id.LLfoto);

        ponerImagen=view.findViewById(R.id.IVimagenProducto);

        unidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Unidades_DialogFragment(new interfazUnidades_OnClick() {
                    @Override
                    public void onClick(View v, String unidadSeleccionada) {
                        unidad.setText(unidadSeleccionada);
                    }
                }).show(fm, "Clientes_DialogFragment");
            }
        });


        //creación de base de datos




        opciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.RBexistente:
                        //nombre.setVisibility(View.VISIBLE);
                        nombreProducto.setVisibility(View.GONE);
                        foto.setVisibility(View.GONE);
                        ponerImagen.setVisibility(View.GONE);
                        campos.setVisibility(View.GONE);
                        capturarProducto.setHint("Ingresa Nombre");
                        existentes.setVisibility(View.VISIBLE);
                        break;


                    case R.id.RBnuevo:
                        //nombre.setVisibility(View.GONE);
                        nombreProducto.setVisibility(View.VISIBLE);
                        foto.setVisibility(View.VISIBLE);
                        ponerImagen.setVisibility(View.VISIBLE);
                        campos.setVisibility(View.VISIBLE);
                        capturarProducto.setHint("Código (Opcional)");
                        existentes.setVisibility(View.GONE);
                        break;
                }

            }
        });

        agregaraproductos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    agregar.setVisibility(View.VISIBLE);
                }else{
                    agregar.setVisibility(View.GONE);
                }
            }
        });



        escan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Escanner.class);//intanciando el activity del scanner
                startActivityForResult(intent,3);//inicializar el activity con RequestCode2
            }
        });

        seleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new traerImagen(new interfaz_SeleccionarImagen() {
                    @Override
                    public void onClick(Intent intent, int requestCode) {
                        startActivityForResult(intent, requestCode);
                    }
                });
                dialog.show(fm, "NoticeDialogFragment");
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaciarFormulario();
//                Toast.makeText(getContext(), "Se ha guardado tu Compra", Toast.LENGTH_LONG).show();
                //alta("Productos");
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();

                //todo este desmadre es para que se refresque xD
                /*Compras frag = new Compras();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.LOprincipal, frag);
                ft.addToBackStack(null);
                ft.commit();
*/
                //cerrar el dialog
                dismiss();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VaciarFormulario();
            }
        });

        return view;
    }
    public void VaciarFormulario(){
        capturarProducto.setText(" ");
        cantidad.setText(" ");
        precioCompra.setText(" ");
        precioVenta.setText(" ");

        //textviews
        cantidadExistentes.setText(" ");
//        nombre.setText(" ");
        totalCompra.setText(" ");
        agregaraproductos.setChecked(false);
        unidad.setSelection(0);
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

                    /*Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                    ponerImagen.setImageBitmap(bmp);*/
                }
            }
        }
        ///3 para escanear
        if (requestCode == 3 && data != null) {
            //obtener resultados
            capturarProducto.setText(data.getStringExtra("BARCODE"));
        }

    }


    //funcion para dar de alta, si funciona regresa true, si no regresa un false
    public void alta(String tabla)
    {
        BaseDeDatosLocal admin = new BaseDeDatosLocal(this.getContext());
        SQLiteDatabase db = admin.getWritableDatabase();

        //nuevo registro
        ContentValues nuevoRegistro = new ContentValues();


        /*
                         "  `idproducto` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `codigo_barras` VARCHAR(45),\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `precio_venta` VARCHAR(45)," +
                "  `ruta_imagen` VARCHAR(45)," +
                "  `unidad` varchar(30),\n" +
                "  `cantidad` INTEGER,\n" +
                "  `precio_compra` INTEGER,\n" +
                "  `idproveedorFK` VARCHAR(40))");                //Llave foranea

        */

        //agregar info al registro
        nuevoRegistro.put("codigo_barras",capturarProducto.getText().toString());
        nuevoRegistro.put("nombre",nombreProducto.getText().toString());
        nuevoRegistro.put("precio_venta",precioVenta.getText().toString());
        nuevoRegistro.put("ruta_imagen", "dsfdsfdsf");
        nuevoRegistro.put("unidad",unidad.getText().toString());
        nuevoRegistro.put("cantidad",Integer.parseInt(cantidad.getText().toString()));
        nuevoRegistro.put("precio_compra",Integer.parseInt(precioCompra.getText().toString()));
        nuevoRegistro.put("idproveedorFK",proveedor.getText().toString());

/*
        nuevoRegistro.put("codigo_barras","sdfdsfd");
        nuevoRegistro.put("nombre","dsfdf");
        nuevoRegistro.put("precio_venta",3534);
        nuevoRegistro.put("ruta_imagen", "3333rf");
        nuevoRegistro.put("unidad","afasds");
        nuevoRegistro.put("cantidad",55);
        nuevoRegistro.put("precio_compra",4345);
        nuevoRegistro.put("idproveedorFK","PEPSI");

        */
        //insertar el nuevo registro
        db.insert(tabla,null, nuevoRegistro);
        Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_LONG).show();
        //cerrar la base de datos
        db.close();
    }

    private void dismiss() {

    }


}