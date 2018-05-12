package com.example.ricardosernam.puntodeventa.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class ContractParaProductos {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY = "com.example.ricardosernam.puntodeventa";

public static final String INVENTARIO = "inventarios";

public static final String PRODUCTO = "productos";

public static final String INVENTARIO_DETALLE = "inventario_detalles";

public static final String VENTA = "ventas";

public static final String VENTA_DETALLE = "venta_detalles";



/**
 * Tipo MIME que retorna la consulta de una sola fila
 */
public final static String SINGLE_MIME_INVENTARIO = "vnd.android.cursor.item/vnd." + AUTHORITY + INVENTARIO;

public final static String MULTIPLE_MIME_INVENTARIO = "vnd.android.cursor.dir/vnd." + AUTHORITY + INVENTARIO;

public final static String SINGLE_MIME_INVENTARIO_DETALLE = "vnd.android.cursor.item/vnd." + AUTHORITY + INVENTARIO_DETALLE;

public final static String MULTIPLE_MIME_INVENTARIO_DETALLE = "vnd.android.cursor.dir/vnd." + AUTHORITY + INVENTARIO_DETALLE;

public final static String SINGLE_MIME_PRODUCTO = "vnd.android.cursor.item/vnd." + AUTHORITY + PRODUCTO;

public final static String MULTIPLE_MIME_PRODUCTO = "vnd.android.cursor.dir/vnd." + AUTHORITY + PRODUCTO;
/**
 * URI de contenido principal
 */
public final static Uri CONTENT_URI_INVENTARIO = Uri.parse("content://" + AUTHORITY + "/" + INVENTARIO);

public final static Uri CONTENT_URI_PRODUCTO = Uri.parse("content://" + AUTHORITY + "/" + PRODUCTO);

public final static Uri CONTENT_URI_INVENTARIO_DETALLE = Uri.parse("content://" + AUTHORITY + "/" + INVENTARIO_DETALLE);


/**
 * Comparador de URIs de contenido
 */
public static final UriMatcher uriMatcherInventario;
public static final UriMatcher uriMatcherProducto;
public static final UriMatcher uriMatcherInventarioDetalles;

/**
 * Código para URIs de multiples registros
 */
public static final int ALLROWS = 1;
/**
 * Código para URIS de un solo registro
 */
public static final int SINGLE_ROW = 2;


// Asignación de URIs
static {
        uriMatcherInventario = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcherInventario.addURI(AUTHORITY, INVENTARIO, ALLROWS);
        uriMatcherInventario.addURI(AUTHORITY, INVENTARIO + "/#", SINGLE_ROW);

        uriMatcherProducto = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcherProducto.addURI(AUTHORITY, PRODUCTO, ALLROWS);
        uriMatcherProducto.addURI(AUTHORITY, PRODUCTO + "/#", SINGLE_ROW);

        uriMatcherInventarioDetalles = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcherInventarioDetalles.addURI(AUTHORITY, INVENTARIO_DETALLE, ALLROWS);
        uriMatcherInventarioDetalles.addURI(AUTHORITY, INVENTARIO_DETALLE + "/#", SINGLE_ROW);
        }

// Valores para la columna ESTADO
public static final int ESTADO_OK = 0;
public static final int ESTADO_SYNC = 1;


/**
 * Estructura de la tabla
 */
public static class Columnas implements BaseColumns {

    private Columnas() {
        // Sin instancias
    }
//////////////inventario//////////////////
    public final static String ID_CARRITO = "idcarrito";
    public final static String DISPONIBLE = "disponible";
    public final static String FECHA = "fecha";
////////////////////productos////////////
    public final static String NOMBRE = "nombre";
    public final static String PRECIO = "precio";
    public final static String PORCION = "porcion";
    public final static String GUISADO = "guisado";
///////////////////inventario_detalles/////////////////
    public final static String ID_PRODUCTO = "idproducto";
    public final static String EXISTENTE = "existente";
/////////////////////ventas///////////////////7
    public final static String CANTIDAD = "cantidad";
    public final static String ID_VENTA = "idventa";




    public static final String ESTADO = "estado";
    public static final String ID_REMOTA = "idRemota";
    public final static String PENDIENTE_INSERCION = "pendiente_insercion";

}
}
