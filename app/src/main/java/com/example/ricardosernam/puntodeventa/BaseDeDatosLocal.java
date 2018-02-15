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


    public BaseDeDatosLocal(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Password TEXT, Nivel BOOL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Password TEXT, Nivel BOOL)");
    }
}
