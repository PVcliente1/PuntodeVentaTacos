package com.example.ricardosernam.puntodeventa.Benvenida;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;

import static java.lang.Integer.parseInt;

public class Registro_inicial extends Fragment {
    private static final String TAG = "SignupActivity";
    private Button registrarse;
    private AppBarLayout bar;
    private TextView iniciarSesion;
    private EditText nombre,apellidos, contraseña,telefono, correo;
    private String name, lastname, phone, email, password,foto;
    private Integer idturno=1;
    private Integer idpuesto=1;


    /*
    "  `idmiembro` INT NOT NULL,\n" +
        "  `nombre` VARCHAR(45),\n" +
        "  `apellido` VARCHAR(45),\n" +
        "  `telefono` varchar(45),\n" +
        "  `correo` VARCHAR(45),\n" +
        "  `contrasena` VARCHAR(45),\n" +
        "  `idturno` INT NOT NULL,\n" +
        "  `idpuesto` INT NOT NULL,\n" +
        "  `foto` VARCHAR(45),\n" +
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppBarLayout bar=findViewById(R.id.APLappBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registro, container, false);
        bar=getActivity().findViewById(R.id.APLappBar);
        registrarse=view.findViewById(R.id.BtnRegistrar);
        ///editText
        nombre=view.findViewById(R.id.ETnombreAdministrador);
        apellidos=view.findViewById(R.id.ETapellidosAdministrador);
        correo=view.findViewById(R.id.ETcorreoAdministrador);
        contraseña=view.findViewById(R.id.ETcontraseñaAdministrador);
        telefono=view.findViewById(R.id.ETtelefonoAdministrador);
        iniciarSesion=view.findViewById(R.id.TViniciarSesion);

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


    public void onSignupSuccess() {  ///es correcto
        registrarse.setEnabled(true);
        bar.setVisibility(View.VISIBLE);
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.CLcontenedorTotal)).commit();
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

/*
        "  `idmiembro` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `telefono` varchar(45),\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `contrasena` VARCHAR(45),\n" +
                "  `idturno` INT NOT NULL,\n" +
                "  `idpuesto` INT NOT NULL,\n" +
                "  `foto` VARCHAR(45),\n" +


                "  `nombre` VARCHAR(45), " +
                " `telefono` varchar(45), " +
                "  `correo` VARCHAR(45), " +
                "  `contrasena` VARCHAR(45), " +
                "  `idturno` INTEGER NOT NULL REFERENCES Turnos (idturno), " +
                "  `idpuesto` INTEGER NOT NULL REFERENCES Puestos(idpuesto), " +
                "  `foto` VARCHAR(45), " +
                "  `apellido` VARCHAR(45))");


 */
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getContext());
        SQLiteDatabase db = admin.getWritableDatabase();
        //if(db!=null){
            ContentValues values = new ContentValues();
            //values.put("idmiembro",0);
            values.put("nombre", name);
            values.put("telefono", phone);
            values.put("correo", email);
            values.put("contrasena", password);
            values.put("idturno", idturno);
            values.put("idpuesto", idpuesto);
            values.put("foto", foto);
            values.put("apellido", lastname);
            db.insertOrThrow("Miembros",null, values);

            //long idUsuario = db.insert("Usuarios", null , values);
            //db.update("Usuarios", values, "id = ?", n.ew String[]{String.valueOf(idUsuario)});
            //Toast.makeText(getContext(), "Registro: "+ idUsuario , Toast.LENGTH_SHORT).show();
        //}
        db.close();
        //return  idUsuario;
    }
}
