package com.example.ricardosernam.puntodeventa.utils;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.example.ricardosernam.puntodeventa.provider.ContractParaProductos;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilidades {
    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_DESCRIPCION = 2;
    public static final int COLUMNA_UBICACION = 3;
    public static final int COLUMNA_DISPONIBLE = 4;

    public static final int COLUMNA_ID_INVENTARIO = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO = 1;
    public static final int COLUMNA_ID_CARRITO = 2;
    public static final int COLUMNA_FECHA = 3;

    public static final int COLUMNA_ID_INVENTARIO_DETALLES = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO_DETALLE = 1;
    public static final int COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE = 2;
    public static final int COLUMNA_EXISTENTE_INICIAL = 3;
    public static final int COLUMNA_EXISTENTE_FINAL = 4;


    public static final int COLUMNA_ID_VENTA = 0;
    public static final int COLUMNA_ID_REMOTA_VENTA = 1;
    public static final int COLUMNA_ID_CARRITO_VENTA = 2;
    public static final int COLUMNA_FECHA_VENTA = 3;

    public static final int COLUMNA_ID_VENTA_DETALLES = 0;
    public static final int COLUMNA_ID_REMOTA_VENTA_DETALLE = 1;
    public static final int COLUMNA_CANTIDAD = 2;
    public static final int COLUMNA_ID_PRODUCTO_VENTA_DETALLE = 3;

    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
*/
    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
*/
    public static JSONObject deCursorAJSONObject(Cursor c, String url) {
        JSONObject jObject = new JSONObject();

        if (url.equals(Constantes.UPDATE_URL_INVENTARIO)) {

            try {
                jObject.put(ContractParaProductos.Columnas.DISPONIBLE, 0);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (url.equals(Constantes.UPDATE_URL_INVENTARIO_DETALLE)) {
            int idinventario;
            int idproducto;
            Double existente_inicial;
            Double existente_final;


            idinventario = c.getInt(COLUMNA_ID_REMOTA_INVENTARIO_DETALLE);
            idproducto = c.getInt(COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE);
            existente_inicial = c.getDouble(COLUMNA_EXISTENTE_INICIAL);
            existente_final = c.getDouble(COLUMNA_EXISTENTE_FINAL);


            try {
                jObject.put("idinventario", idinventario);
                jObject.put(ContractParaProductos.Columnas.ID_PRODUCTO, idproducto);
                jObject.put(ContractParaProductos.Columnas.INVENTARIO_INICIAL, existente_inicial);
                jObject.put(ContractParaProductos.Columnas.INVENTARIO_FINAL, existente_final);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (url.equals(Constantes.INSERT_URL_VENTA)) {
            int idcarrito;
            String fecha;

            idcarrito = c.getInt(COLUMNA_ID_CARRITO_VENTA);
            fecha = c.getString(COLUMNA_FECHA_VENTA);

            try {
                jObject.put(ContractParaProductos.Columnas.ID_CARRITO, idcarrito);
                jObject.put(ContractParaProductos.Columnas.FECHA, fecha);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (url.equals(Constantes.INSERT_URL_VENTA_DETALLE)) {
            int idventa;
            int cantidad;
            int idproducto;

            idventa = c.getInt(COLUMNA_ID_REMOTA_VENTA_DETALLE);
            cantidad = c.getInt(COLUMNA_CANTIDAD);
            idproducto = c.getInt(COLUMNA_ID_PRODUCTO_VENTA_DETALLE);


            try {
                jObject.put("idventa", idventa);
                jObject.put(ContractParaProductos.Columnas.CANTIDAD, cantidad);
                jObject.put(ContractParaProductos.Columnas.ID_PRODUCTO, idproducto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Cursor a JSONObject", String.valueOf(jObject));    ////mostramos que valores se han insertado
        return jObject;
    }
}


