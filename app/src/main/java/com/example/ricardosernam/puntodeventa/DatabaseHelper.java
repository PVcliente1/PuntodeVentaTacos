package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ricardosernam.puntodeventa.provider.ContractParaProductos;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase database) {
        productos(database); // Crear la tabla "gasto"
        //inventario_detalles(database); // Crear la tabla "gasto"
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void productos(SQLiteDatabase database) {
        /*String cmd = "CREATE TABLE " + ContractParaProductos.PRODUCTO + " (" +
                ContractParaProductos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractParaProductos.Columnas.NOMBRE + " TEXT, " +
                ContractParaProductos.Columnas.PRECIO + " TEXT, " +
                ContractParaProductos.Columnas.PORCION + " DOUBLE, " +
                ContractParaProductos.Columnas.GUISADO + " VARCHAR(45), " +
                ContractParaProductos.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractParaProductos.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaProductos.ESTADO_OK+"," +
                ContractParaProductos.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd);
        String cmd2 = "CREATE TABLE " + ContractParaProductos.INVENTARIO + " (" +
                ContractParaProductos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractParaProductos.Columnas.ID_CARRITO + " TEXT UNIQUE, " +
                ContractParaProductos.Columnas.DISPONIBLE + " INT, " +
                ContractParaProductos.Columnas.FECHA + " INT, " +
                ContractParaProductos.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractParaProductos.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaProductos.ESTADO_OK+"," +
                ContractParaProductos.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd2);
        String cmd3 = "CREATE TABLE " + ContractParaProductos.INVENTARIO_DETALLE + " (" +
                ContractParaProductos.Columnas._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContractParaProductos.Columnas.ID_PRODUCTO + " TEXT UNIQUE, " +
                ContractParaProductos.Columnas.EXISTENTE + " DOUBLE, " +
                ContractParaProductos.Columnas.ID_REMOTA + " TEXT UNIQUE," +
                ContractParaProductos.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaProductos.ESTADO_OK+"," +
                ContractParaProductos.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd3);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table " + ContractParaProductos.PRODUCTO);
            db.execSQL("drop table " + ContractParaProductos.INVENTARIO_DETALLE);
            db.execSQL("drop table " + ContractParaProductos.INVENTARIO);

        }
        catch (SQLiteException e) { }
        onCreate(db);
    }

}
