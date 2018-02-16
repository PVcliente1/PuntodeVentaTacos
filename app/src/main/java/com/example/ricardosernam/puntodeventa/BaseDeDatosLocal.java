package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daniel-linux on 2/11/18.
 * aun no funciona
 * https://androidstudiofaqs.com/tutoriales/usar-sqlite-en-android-studio
 */

public class BaseDeDatosLocal extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PuntoDeVenta.db";

    public BaseDeDatosLocal(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Proveedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono INTEGER, Direccion TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Vendedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono INTEGER, Direccion TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("drop table if exists Proveedores");
        sqLiteDatabase.execSQL("CREATE TABLE Proveedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono INTEGER, Direccion TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Vendedores(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellidos TEXT, Telefono INTEGER, Direccion TEXT)");

    }
}
