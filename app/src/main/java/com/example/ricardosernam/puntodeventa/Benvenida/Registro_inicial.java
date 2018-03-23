package com.example.ricardosernam.puntodeventa.Benvenida;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_SeleccionarImagen;
import com.example.ricardosernam.puntodeventa.____herramientas_app.traerImagen;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.lang.Integer.parseInt;

public class Registro_inicial extends Fragment {
    private static final String TAG = "SignupActivity";
    private Button registrarse, imagen;
    private AppBarLayout bar;
    private Uri selectedImage;
    private String rutaImagen;
    private TextView iniciarSesion;
    private FragmentManager fm;
    private EditText nombre,apellidos, contraseña,telefono, correo;
    private String name, lastname, phone, email, password, foto;
    private ImageView ponerImagen;
    private Integer idturno=1;
    private Integer idpuesto=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registro, container, false);
        bar=getActivity().findViewById(R.id.APLappBar);
        registrarse=view.findViewById(R.id.BtnRegistrar);
        imagen=view.findViewById(R.id.BtnImagen);
        ///editText
        nombre=view.findViewById(R.id.ETnombreAdministrador);
        apellidos=view.findViewById(R.id.ETapellidosAdministrador);
        correo=view.findViewById(R.id.ETcorreoAdministrador);
        contraseña=view.findViewById(R.id.ETcontraseñaAdministrador);
        telefono=view.findViewById(R.id.ETtelefonoAdministrador);
        iniciarSesion=view.findViewById(R.id.TViniciarSesion);

        ponerImagen=view.findViewById(R.id.IVImagen);

        fm=getActivity().getFragmentManager();
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.CLcontenedorTotal, new Inicio_sesion()).commit();
            }
        });
        imagen.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {////si hay un error
            onSignupFailed();
            return ;
        }
        else {  ///si cargo correctamente (Mostramos un progress dialog)
            insertarUsuario();///insertamos y actualizamos

            registrarse.setEnabled(false);
            final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creando cuenta...");
            progressDialog.show();

            // TODO: Implement your own signup logic here.

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            //onSignupSuccess();////cuando cargue
                            getFragmentManager().beginTransaction().replace(R.id.CLcontenedorTotal, new Inicio_sesion()).commit();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }

        }
    public void onSignupFailed() {  //es incorrecto
        Toast.makeText(getContext(), "Registro fallido", Toast.LENGTH_LONG).show();

        registrarse.setEnabled(true);
    }

    public boolean validate() {  ///validamos que los campos cumplan los requisitos
        boolean valid = true;
        name = nombre.getText().toString();
        lastname =apellidos.getText().toString();
        email = correo.getText().toString();
        password = contraseña.getText().toString();
        phone = telefono.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nombre.setError("Minimo 3 caracteres");
            valid = false;
        } else {
            nombre.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            correo.setError("Ingrea un correo correcto");
            valid = false;
        } else {
            correo.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            contraseña.setError("Ingresa 6 o más caracteres alfanuméricos");
            valid = false;
        } else {
            contraseña.setError(null);
        }

        return valid;
    }
    public void insertarUsuario(){
        //int idUsuario = 0;
        name = nombre.getText().toString();
        lastname =apellidos.getText().toString();
        email = correo.getText().toString();
        password = contraseña.getText().toString();
        phone = telefono.getText().toString();

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();
        //if(db!=null){
            ContentValues values = new ContentValues();

            values.put("nombre", name);
            values.put("telefono", phone);
            values.put("correo", email);
            values.put("contrasena", password);
            values.put("idturno", idturno);
            values.put("idpuesto", idpuesto);
            values.put("foto", String.valueOf(selectedImage));
            values.put("apellido", lastname);
            db.insertOrThrow("Miembros",null, values);
        db.close();
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
