package com.example.ricardosernam.puntodeventa.Benvenida;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas;

import static android.app.Activity.RESULT_OK;

public class Bienvenida extends Fragment {
    private static final String TAG = "SignupActivity";
    private Button registrarse;
    private AppBarLayout bar;
    private TextView iniciarSesion;
    private EditText nombre, correo, contraseña, telefono;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppBarLayout bar=findViewById(R.id.APLappBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bienvenida, container, false);
        bar=getActivity().findViewById(R.id.APLappBar);
        registrarse=view.findViewById(R.id.BtnRegistrar);
        ///editText
        nombre=view.findViewById(R.id.ETnombreAdministrador);
        correo=view.findViewById(R.id.ETcorreoAdministrador);
        contraseña=view.findViewById(R.id.ETcontraseñaAdministrador);
        nombre=view.findViewById(R.id.ETnombreAdministrador);
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
                // Finish the registration screen and return to the Login activity
                //finish();
                //bar.setVisibility(View.VISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
            }
        });
        return view;
    }
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {////si hay un error
            onSignupFailed();
            return;
        }
        else {  ///si cargo correctamente (Mostramos un progress dialog)

            registrarse.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight);  ////dialogo de carga
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creando cuenta...");
            progressDialog.show();

            // TODO: Implement your own signup logic here.

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onSignupSuccess();////cuando cargue
                            progressDialog.dismiss();
                            bar.setVisibility(View.VISIBLE);
                        }
                    }, 3000);
        }

        }


    public void onSignupSuccess() {  ///es correcto
        registrarse.setEnabled(true);
        //setResult(RESULT_OK, null);
        bar.setVisibility(View.VISIBLE);
        //getFragmentManager().beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.CLcontenedorTotal)).commit();
        getFragmentManager().beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
    }

    public void onSignupFailed() {  //es incorrecto
        Toast.makeText(getContext(), "Registro fallido", Toast.LENGTH_LONG).show();

        registrarse.setEnabled(true);
    }

    public boolean validate() {  ///validamos que los campos cumplan los requisitos
        boolean valid = true;

        String name = nombre.getText().toString();
        String email = correo.getText().toString();
        String password = contraseña.getText().toString();
        String phone = telefono.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nombre.setError("Minimo 3 caracteres");
            valid = false;
        } else {
            nombre.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            correo.setError("enter a valid email address");
            valid = false;
        } else {
            correo.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            contraseña.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            contraseña.setError(null);
        }

        return valid;
    }
}
