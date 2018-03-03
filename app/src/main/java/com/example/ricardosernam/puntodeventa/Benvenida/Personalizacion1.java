package com.example.ricardosernam.puntodeventa.Benvenida;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.pagar_DialogFragment;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickFecha;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClickHora;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_historial;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Fecha_DialogFragment;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Hora_DialogFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Personalizacion1 extends Fragment {
    private EditText nombre, direccion, telefono, correo, paginaWEb, entradaHorario, salidaHorario, entradaTurno1,salidaTurno1, entradaTurno2,salidaTurno2;
    private Button aceptar, imagen;
    private ImageView ponerImagen;
    private String rutaImagen;
    private Uri selectedImage;
    private ContentValues values;
    private SQLiteDatabase db;
    AppBarLayout bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.fragment_personalizacion1, container, false);
      bar=getActivity().findViewById(R.id.APLappBar);
      ///editTexts/////
      nombre=view.findViewById(R.id.ETnombreEmpresa);
      direccion=view.findViewById(R.id.ETdireccionEmpresa);
      telefono=view.findViewById(R.id.ETtelefonoEmpresa);
      correo=view.findViewById(R.id.ETcorreoEmpresa);
      paginaWEb=view.findViewById(R.id.ETpaginaEmpresa);
      entradaHorario=view.findViewById(R.id.ETentradaHorario);
      salidaHorario=view.findViewById(R.id.ETsalidaHorario);
      entradaTurno1=view.findViewById(R.id.ETentradaTurno1);
      salidaTurno1=view.findViewById(R.id.ETsalidaTurno1);
      entradaTurno2=view.findViewById(R.id.ETentradaTurno2);
      salidaTurno2=view.findViewById(R.id.ETsalidaTurno2);
      imagen = view.findViewById(R.id.BtnLogoEmpresa);
      ponerImagen = view.findViewById(R.id.IVimagen);
      ///Botones///
      aceptar=view.findViewById(R.id.BtnAceptarPersonalización);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();

      entradaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        entradaHorario.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        salidaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        salidaHorario.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });

        entradaTurno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        entradaTurno1.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        salidaTurno1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        salidaTurno1.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });

        entradaTurno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        entradaTurno2.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        salidaTurno2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {   ///abrimos el dialogo de TimePicker
                new Hora_DialogFragment(new interfaz_OnClickHora() {
                    @Override
                    public void onClick(View v, int i, int i1) {
                        salidaTurno2.setText(i + ":" + i1);
                    }
                }).show(getFragmentManager(),"Entrada_Horario");
            }
        });
        /*sqLiteDatabase.execSQL("CREATE TABLE Datos_Empresa (\n" +
                "  `idempresa` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` INTEGER,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  `horario` VARCHAR(45))");*/


      aceptar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              values.put("nombre", String.valueOf(nombre.getText()));
              //values.put("encargado", String.valueOf(nombreP.getText()));
              values.put("direccion", String.valueOf(direccion.getText()));
              values.put("telefono", String.valueOf(telefono.getText()));
              values.put("correo", String.valueOf(correo.getText()));
              values.put("pagina", String.valueOf(paginaWEb.getText()));
              values.put("logo", String.valueOf(selectedImage));
              db.insertOrThrow("Datos_Empresa",null, values);

              db.close();
              getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.CLcontenedorTotal)).commit();
              bar.setVisibility(View.VISIBLE);
          }
      });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ////1 para entrar a galeria y tomar una foto
        if (requestCode == 1) {
            //Uri selectedImage;///uri es la ruta
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen = selectedImage.getPath();///ruta de la imagen

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
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                selectedImage = data.getData();////data.get data es como mi file
                assert selectedImage != null;
                rutaImagen = selectedImage.getPath();///ruta de la imagen

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
}
