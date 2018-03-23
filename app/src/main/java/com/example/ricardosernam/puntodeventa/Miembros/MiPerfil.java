package com.example.ricardosernam.puntodeventa.Miembros;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.BaseDeDatosLocal;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;

public class MiPerfil extends Fragment {


    private TextView nombre, apellidos, puesto, turno, telefono, correo;
    private ImageView foto;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.fragment_mi_perfil, container, false);


        nombre=view.findViewById(R.id.TVnombreMiembro);
        apellidos=view.findViewById(R.id.TVapellidosMiembro);
        puesto=view.findViewById(R.id.TVpuestoMiembro);
        turno=view.findViewById(R.id.TVturno);
        telefono=view.findViewById(R.id.TVtelefonoMiembro);
        correo=view.findViewById(R.id.TVcorreoMiembro);
        foto=view.findViewById(R.id.IVfotoMiembro);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getActivity());
        db=admin.getWritableDatabase();
        values = new ContentValues();

        fila=db.rawQuery("select nombre, apellido, telefono,correo, foto from Miembros where idmiembro=2" ,null);

        if(fila.moveToFirst()) {
            //Toast.makeText(getContext(),fila.getString(1),Toast.LENGTH_LONG).show();
               nombre.setText(fila.getString(0));
               apellidos.setText(fila.getString(1));
               puesto.setText("Administrador");
               turno.setText("Sin turno");
               telefono.setText(fila.getString(2));
               correo.setText(fila.getString(3));
               foto.setImageURI(Uri.parse(fila.getString(4)));

        }
        return view;
    }
}
