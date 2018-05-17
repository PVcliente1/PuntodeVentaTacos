package com.example.ricardosernam.puntodeventa.utils;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.example.ricardosernam.puntodeventa.provider.ContractParaProductos;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilidades {
    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_INVENTARIO = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO = 1;
    public static final int COLUMNA_ID_CARRITO = 2;
    public static final int COLUMNA_DISPONIBLE = 3;
    public static final int COLUMNA_FECHA = 4;

    public static final int COLUMNA_ID_INVENTARIO_DETALLES = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO_DETALLE = 1;
    public static final int COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE = 2;
    public static final int COLUMNA_EXISTENTE = 3;

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

        if (url == Constantes.GET_URL_INVENTARIO) {
            int idcarrito;
            int disponible;
            String fecha;

            idcarrito = c.getInt(COLUMNA_ID_CARRITO);
            disponible = c.getInt(COLUMNA_DISPONIBLE);
            fecha = c.getString(COLUMNA_FECHA);

            try {
                jObject.put(ContractParaProductos.Columnas.ID_CARRITO, idcarrito);
                jObject.put(ContractParaProductos.Columnas.DISPONIBLE, disponible);
                jObject.put(ContractParaProductos.Columnas.FECHA, fecha);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if (url == Constantes.GET_URL_INVENTARIO_DETALLE) {
            int idinventario;
            int idproducto;
            Double existente;

            idinventario = c.getInt(COLUMNA_ID_REMOTA_INVENTARIO_DETALLE);
            idproducto = c.getInt(COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE);
            existente = c.getDouble(COLUMNA_EXISTENTE);

            try {
                jObject.put("idinventario", idinventario);
                jObject.put(ContractParaProductos.Columnas.ID_PRODUCTO, idproducto);
                jObject.put(ContractParaProductos.Columnas.EXISTENTE, existente);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("Cursor a JSONObject", String.valueOf(jObject));    ////mostramos que valores se han insertado
        return jObject;
    }
}
/*public class Utilidades {
    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_INVENTARIO = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO = 1;
    public static final int COLUMNA_ID_CARRITO = 2;
    public static final int COLUMNA_DISPONIBLE = 3;
    public static final int COLUMNA_FECHA = 4;

    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación*/

    /*public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason*/

    /*public static JSONObject deCursorAJSONObject(Cursor c) {
        JSONObject jObject = new JSONObject();

        int idcarrito;
        int disponible;
        String fecha;

        idcarrito = c.getInt(COLUMNA_ID_CARRITO);
        disponible = c.getInt(COLUMNA_DISPONIBLE);
        fecha = c.getString(COLUMNA_FECHA);

        try {
            jObject.put(ContractParaProductos.Columnas.ID_CARRITO, idcarrito);
            jObject.put(ContractParaProductos.Columnas.DISPONIBLE, disponible);
            jObject.put(ContractParaProductos.Columnas.FECHA, fecha);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));    ////mostramos que valores se han insertado

        return jObject;
    }
}*/

