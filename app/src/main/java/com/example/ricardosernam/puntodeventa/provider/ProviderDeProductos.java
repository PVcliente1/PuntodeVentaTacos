package com.example.ricardosernam.puntodeventa.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.example.ricardosernam.puntodeventa.DatabaseHelper;

/**
 * Content Provider personalizado para los gastos
 */
public class ProviderDeProductos extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    public static final String DATABASE_NAME = "db_tacos.db";

    /**
     * Versión actual de la base de datos
     */
    public static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION);

        resolver = getContext().getContentResolver();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContractParaProductos.uriMatcherInventario.match(uri);
        int match2 = ContractParaProductos.uriMatcherProducto.match(uri);
        int match3 = ContractParaProductos.uriMatcherInventarioDetalles.match(uri);


        Cursor c=null;

        if(uri==ContractParaProductos.CONTENT_URI_PRODUCTO) {
            switch (match2) {
                case ContractParaProductos.ALLROWS:
                    // Consultando todos los registros
                    c = db.query(ContractParaProductos.PRODUCTO, projection, selection, selectionArgs, null, null, sortOrder);
                    c.setNotificationUri(resolver, ContractParaProductos.CONTENT_URI_PRODUCTO);
                    break;
                case ContractParaProductos.SINGLE_ROW:
                    // Consultando un solo registro basado en el Id del Uri
                    long idGasto = ContentUris.parseId(uri);
                    c = db.query(ContractParaProductos.PRODUCTO, projection,
                            ContractParaProductos.Columnas._ID + " = " + idGasto, selectionArgs, null, null, sortOrder);
                    c.setNotificationUri(
                            resolver,
                            ContractParaProductos.CONTENT_URI_PRODUCTO);
                    break;
                default:
                    throw new IllegalArgumentException("URI no soportada: " + uri);
            }
        }
        else if(uri==ContractParaProductos.CONTENT_URI_INVENTARIO) {
            switch (match) {
                case ContractParaProductos.ALLROWS:
                    // Consultando todos los registros
                    c = db.query(ContractParaProductos.INVENTARIO, projection,
                            selection, selectionArgs,
                            null, null, sortOrder);
                    c.setNotificationUri(
                            resolver,
                            ContractParaProductos.CONTENT_URI_INVENTARIO);
                    break;
                case ContractParaProductos.SINGLE_ROW:
                    // Consultando un solo registro basado en el Id del Uri
                    long idGasto = ContentUris.parseId(uri);
                    c = db.query(ContractParaProductos.INVENTARIO, projection,
                            ContractParaProductos.Columnas._ID + " = " + idGasto,
                            selectionArgs, null, null, sortOrder);
                    c.setNotificationUri(
                            resolver,
                            ContractParaProductos.CONTENT_URI_INVENTARIO);
                    break;
                default:
                    throw new IllegalArgumentException("URI no soportada: " + uri);
            }
        }
        else if(uri==ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE) {
            switch (match3) {
                case ContractParaProductos.ALLROWS:
                    // Consultando todos los registros
                    c = db.query(ContractParaProductos.INVENTARIO_DETALLE, projection,
                            selection, selectionArgs,
                            null, null, sortOrder);
                    c.setNotificationUri(
                            resolver,
                            ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE);
                    break;
                case ContractParaProductos.SINGLE_ROW:
                    // Consultando un solo registro basado en el Id del Uri
                    long idGasto = ContentUris.parseId(uri);
                    c = db.query(ContractParaProductos.INVENTARIO_DETALLE, projection,
                            ContractParaProductos.Columnas._ID + " = " + idGasto,
                            selectionArgs, null, null, sortOrder);
                    c.setNotificationUri(
                            resolver,
                            ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE);
                    break;
                default:
                    throw new IllegalArgumentException("URI no soportada: " + uri);
            }
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        String caso="";
        if(ContractParaProductos.uriMatcherProducto.match(uri)!=0) {
            switch (ContractParaProductos.uriMatcherProducto.match(uri)) {
                case ContractParaProductos.ALLROWS:
                    //return ContractParaProductos.MULTIPLE_MIME_PRODUCTO;
                    caso=ContractParaProductos.MULTIPLE_MIME_PRODUCTO;
                case ContractParaProductos.SINGLE_ROW:
                    //return ContractParaProductos.SINGLE_MIME_PRODUCTO;
                    caso=ContractParaProductos.SINGLE_MIME_PRODUCTO;
                default:
                    throw new IllegalArgumentException("Tipo de gasto desconocido: " + uri);
            }
            //return caso;
        }
        else if (ContractParaProductos.uriMatcherInventario.match(uri)!=0){
            switch (ContractParaProductos.uriMatcherInventario.match(uri)) {
                case ContractParaProductos.ALLROWS:
                    //return ContractParaProductos.MULTIPLE_MIME_INVENTARIO;
                    caso=ContractParaProductos.MULTIPLE_MIME_INVENTARIO;
                case ContractParaProductos.SINGLE_ROW:
                    //return ContractParaProductos.SINGLE_MIME_INVENTARIO;
                    caso=ContractParaProductos.SINGLE_MIME_INVENTARIO;
                default:
                    throw new IllegalArgumentException("Tipo de gasto desconocido: " + uri);
          }
            //return caso;
        }
        else if (ContractParaProductos.uriMatcherInventarioDetalles.match(uri)!=0){
            switch (ContractParaProductos.uriMatcherInventarioDetalles.match(uri)) {
                case ContractParaProductos.ALLROWS:
                    //return ContractParaProductos.MULTIPLE_MIME_INVENTARIO;
                    caso=ContractParaProductos.MULTIPLE_MIME_INVENTARIO_DETALLE;
                case ContractParaProductos.SINGLE_ROW:
                    //return ContractParaProductos.SINGLE_MIME_INVENTARIO;
                    caso=ContractParaProductos.SINGLE_MIME_INVENTARIO_DETALLE;
                default:
                    throw new IllegalArgumentException("Tipo de gasto desconocido: " + uri);
            }
            //return caso;
        }
        return caso;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ContentValues contentValues;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Validar la uri
        if (uri == ContractParaProductos.CONTENT_URI_PRODUCTO) {
            if (ContractParaProductos.uriMatcherProducto.match(uri) != ContractParaProductos.ALLROWS) {
                throw new IllegalArgumentException("URI desconocida : " + uri);
            }
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }
            // Inserción de nueva fila
            long rowIdProducto = db.insert(ContractParaProductos.PRODUCTO, null, contentValues);
            if (rowIdProducto > 0) {
                Uri uri_gasto = ContentUris.withAppendedId(
                        ContractParaProductos.CONTENT_URI_PRODUCTO, rowIdProducto);
                resolver.notifyChange(uri_gasto, null, false);
                return uri_gasto;
            }
        }
        /////////inventario////////////////////////
        else if(uri==ContractParaProductos.CONTENT_URI_INVENTARIO){
            // Validar la uri
            if (ContractParaProductos.uriMatcherInventario.match(uri) != ContractParaProductos.ALLROWS) {
                throw new IllegalArgumentException("URI desconocida : " + uri);
            }
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }
        // Inserción de nueva fila
        long rowIdInventario = db.insert(ContractParaProductos.INVENTARIO, null, contentValues);
        if (rowIdInventario > 0) {
            Uri uri_gasto = ContentUris.withAppendedId(
                    ContractParaProductos.CONTENT_URI_INVENTARIO, rowIdInventario);
            resolver.notifyChange(uri_gasto, null, false);
            return uri_gasto;
        }
    }
        /////////inventario_detalle////////////////////////
        else if(uri==ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE){
            // Validar la uri
            if (ContractParaProductos.uriMatcherInventarioDetalles.match(uri) != ContractParaProductos.ALLROWS) {
                throw new IllegalArgumentException("URI desconocida : " + uri);
            }
            if (values != null) {
                contentValues = new ContentValues(values);
            } else {
                contentValues = new ContentValues();
            }
            // Inserción de nueva fila
            long rowIdInventario = db.insert(ContractParaProductos.INVENTARIO_DETALLE, null, contentValues);
            if (rowIdInventario > 0) {
                Uri uri_gasto = ContentUris.withAppendedId(
                        ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE, rowIdInventario);
                resolver.notifyChange(uri_gasto, null, false);
                return uri_gasto;
            }
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = ContractParaProductos.uriMatcherProducto.match(uri);
        int match2 = ContractParaProductos.uriMatcherInventario.match(uri);
        int match3 = ContractParaProductos.uriMatcherInventarioDetalles.match(uri);


        int affected;

        switch (match) {
            case ContractParaProductos.ALLROWS:
                affected = db.delete(ContractParaProductos.PRODUCTO, selection, selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                long idGasto = ContentUris.parseId(uri);
                affected = db.delete(ContractParaProductos.PRODUCTO,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Elemento gasto desconocido: " + uri);
        }

        switch (match2) {
            case ContractParaProductos.ALLROWS:
                affected = db.delete(ContractParaProductos.INVENTARIO,
                        selection,
                        selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                long idGasto = ContentUris.parseId(uri);
                affected = db.delete(ContractParaProductos.INVENTARIO,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Elemento gasto desconocido: " +
                        uri);
        }

        switch (match3) {
            case ContractParaProductos.ALLROWS:
                affected = db.delete(ContractParaProductos.INVENTARIO_DETALLE, selection, selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                long idGasto = ContentUris.parseId(uri);
                affected = db.delete(ContractParaProductos.INVENTARIO_DETALLE,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Elemento gasto desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContractParaProductos.uriMatcherProducto.match(uri)) {
            case ContractParaProductos.ALLROWS:
                affected = db.update(ContractParaProductos.PRODUCTO, values,
                        selection, selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                String idGasto = uri.getPathSegments().get(1);
                affected = db.update(ContractParaProductos.PRODUCTO, values,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        switch (ContractParaProductos.uriMatcherInventario.match(uri)) {
            case ContractParaProductos.ALLROWS:
                affected = db.update(ContractParaProductos.INVENTARIO, values,
                        selection, selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                String idGasto = uri.getPathSegments().get(1);
                affected = db.update(ContractParaProductos.INVENTARIO, values,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        switch (ContractParaProductos.uriMatcherInventarioDetalles.match(uri)) {
            case ContractParaProductos.ALLROWS:
                affected = db.update(ContractParaProductos.INVENTARIO_DETALLE, values,
                        selection, selectionArgs);
                break;
            case ContractParaProductos.SINGLE_ROW:
                String idGasto = uri.getPathSegments().get(1);
                affected = db.update(ContractParaProductos.INVENTARIO_DETALLE, values,
                        ContractParaProductos.Columnas.ID_REMOTA + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        resolver.notifyChange(uri, null, false);
        return affected;
    }

}

