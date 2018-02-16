package com.example.ricardosernam.puntodeventa.Benvenida;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;

public class Inicio_sesion extends Fragment {
    private Button iniciarSesion;
    private EditText nombreUsuario, contraseñaUsuario;
    private Cursor fila;
    private AppBarLayout bar;
    private String usuario, contraseña;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_incio_sesion, container, false);
        bar=getActivity().findViewById(R.id.APLappBar);
        iniciarSesion=view.findViewById(R.id.BtnIniciarSesion);
        nombreUsuario=view.findViewById(R.id.ETnombreUsuario);
        contraseñaUsuario=view.findViewById(R.id.ETcontraseñaUsuario);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ingresar();
            }
        });

        return view;
    }
    public void onSignupSuccess() {  ///es correcto
        //iniciarSesion.setEnabled(true);
        bar.setVisibility(View.VISIBLE);
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.CLcontenedorTotal)).commit();
    }
    public void ingresar(){
        BaseDeDatosLocal admin=new BaseDeDatosLocal(getContext(),"Usuarios",null,1);
        SQLiteDatabase db=admin.getWritableDatabase();
        usuario=nombreUsuario.getText().toString();
        contraseña=contraseñaUsuario.getText().toString();
        //Toast.makeText(getContext(), "esta"+ usuario+" y "+contraseña, Toast.LENGTH_SHORT).show();
        fila=db.rawQuery("select Nombre, Contrasena from Usuarios where Nombre='"+usuario+"' and Contrasena='"+contraseña+"'",null);
                //preguntamos si el cursor tiene algun valor almacenado
        //Toast.makeText(getContext(), "No hay nada", Toast.LENGTH_SHORT).show();
        if(fila.moveToFirst()==true){
            //capturamos los valores del cursos y lo almacenamos en variable
            //preguntamos si los datos ingresados son iguales
            String usua=fila.getString(0);
            String pass=fila.getString(1);
            if (usuario.equals(usua)&&contraseña.equals(pass)){
                //si son iguales entonces vamos a otra ventana
                //Menu es una nueva actividad empty
                onSignupSuccess();
                //limpiamos las las cajas de texto
                nombreUsuario.setText("");
                contraseñaUsuario.setText("");
            }
            else{
                Toast.makeText(getContext(), "Datos incorrectos, vuelve a intentar", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getContext(), "No hay nada", Toast.LENGTH_SHORT).show();
        }
    }

}
