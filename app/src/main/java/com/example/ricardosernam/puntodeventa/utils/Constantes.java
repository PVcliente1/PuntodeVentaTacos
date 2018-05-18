package com.example.ricardosernam.puntodeventa.utils;

/**
 * Constantes
 */
public class Constantes {
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PUERTO_HOST = "";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "http://192.168.0.8";

    /**
     * URLs del Web Service
     */

    public static final String GET_URL_PRODUCTO = IP + PUERTO_HOST + "/Servicios%20Web/productos/obtener_productos.php";

    public static final String GET_URL_INVENTARIO = IP + PUERTO_HOST + "/Servicios%20Web/inventarios/obtener_inventarios.php";
    public static final String INSERT_URL_INVENTARIO = IP + PUERTO_HOST + "/Servicios%20Web/inventarios/insertar_inventario.php";

    public static final String GET_URL_INVENTARIO_DETALLE = IP + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/obtener_inventario_detalles.php";
    public static final String INSERT_URL_INVENTARIO_DETALLE = IP + PUERTO_HOST + "/Servicios%20Web/inventario_detalles/insertar_inventario_detalle.php";

    public static final String INSERT_URL_VENTA = IP + PUERTO_HOST + "/Servicios%20Web/ventas/insertar_venta.php";

    public static final String INSERT_URL_VENTA_DETALLE = IP + PUERTO_HOST + "/Servicios%20Web/venta_detalles/insertar_venta_detalle.php";



    /**
     * Campos de las respuestas Json
     */
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
