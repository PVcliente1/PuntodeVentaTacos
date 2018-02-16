package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatosLocal extends SQLiteOpenHelper {
    public BaseDeDatosLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Proveedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono TEXT, Direccion TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Contrasena TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE Proveedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono TEXT, Direccion TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Contrasena TEXT)");
    }
}
