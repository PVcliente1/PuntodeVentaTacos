package com.example.ricardosernam.puntodeventa.utils;

import android.widget.EditText;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.Sincronizar.Sincronizar;

/**
 * Constantes
 */
public class Constantes {
    //public EditText ip;

    public static  String GET_URL_CARRITO;

    public static  String GET_URL_PRODUCTO;

    public static  String UPDATE_URL_INVENTARIO;

    public static String GET_URL_INVENTARIO;   //////

    public static String INSERT_URL_INVENTARIO;

    public static String GET_URL_INVENTARIO_DETALLE;

    public static String UPDATE_URL_INVENTARIO_DETALLE;

    public static String INSERT_URL_VENTA;

    public static String INSERT_URL_VENTA_DETALLE;

    private static final String PUERTO_HOST = "";

    public Constantes(String ip){
        GET_URL_CARRITO = ip + PUERTO_HOST + "/Servicios%20Web/carritos/obtener_carritos.php";

        UPDATE_URL_INVENTARIO = ip + PUERTO_HOST + "/Servicios%20Web/inventarios/actualizar_inventario.php?idinventario=";   //////

        //UPDATE_URL_INVENTARIO = ip + PUERTO_HOST + "/Servicios%20Web/inventarios/actualizar_inventario.php?";   //////

        GET_URL_PRODUCTO = ip + PUERTO_HOST + "/Servicios%20Web/productos/obtener_productos.php";

        GET_URL_INVENTARIO = ip + PUERTO_HOST + "/Servicios%20Web/inventarios/obtener_inventarios.php?idcarrito=";   //////

        INSERT_URL_INVENTARIO = ip + PUERTO_HOST + "/Servicios%20Web/inventarios/insertar_inventario.php";

         GET_URL_INVENTARIO_DETALLE = ip + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/obtener_inventario_detalles.php?idinventario=";

        UPDATE_URL_INVENTARIO_DETALLE = ip + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/actualizar_inventario_detalle.php";

        //UPDATE_URL_INVENTARIO_DETALLE = ip + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/insertar_inventario_detalle.php?idinventario=inventario&idproducto=producto";

        //UPDATE_URL_INVENTARIO_DETALLE = ip + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/insertar_inventario_detalle.php?idinventario=";

        INSERT_URL_VENTA = ip + PUERTO_HOST + "/Servicios%20Web/ventas/insertar_venta.php";

       INSERT_URL_VENTA_DETALLE = ip + PUERTO_HOST + "/Servicios%20Web/venta_detalles/insertar_venta_detalle.php";
    }

    public static final String CARRITO = "carrito";

    public static final String ID_PRODUCTO = "idproducto";
    public static final String PRODUCTO = "producto";

    public static final String ID_INVENTARIO = "idinventario";
    public static final String INVENTARIO = "inventario";

    public static final String ID_INVENTARIO_DETALLE = "idinventario";//////////////id del localhost
    public static final String INVENTARIO_DETALLE = "inventario_detalle";

    public static final String ID_VENTA = "idventa";
    public static final String ESTADO = "estado";
    public static final String MENSAJE = "mensaje";

    public static final String SUCCESS = "1";
    public static final String FAILED = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.example.ricardosernam.puntodeventa.account";
}
