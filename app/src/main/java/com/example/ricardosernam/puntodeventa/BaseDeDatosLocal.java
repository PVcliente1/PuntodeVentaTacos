package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatosLocal extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PuntoDeVenta.db";
    public static final String TABLE_NAME = "Usuarios";
    public static final String COL_1 = "id";
    public static final String COL_2 = "Nivel";
    public static final String COL_3 = "Nombre";
    public static final String COL_4 = "Password";


    public BaseDeDatosLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Usuarios(id integer primary key autoincrement, Nombre text, Contrasena text)");
        sqLiteDatabase.execSQL("insert into Usuarios values (01, 'admin', 'admin')");
        //sqLiteDatabase.execSQL("CREATE TABLE Usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Contraseña TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("create table Usuarios(id integer primary key autoincrement, Nombre text, Contrasena text)");
        sqLiteDatabase.execSQL("insert into Usuarios values (01, 'admin', 'admin')");
        //onCreate(sqLiteDatabase);
    }
}
