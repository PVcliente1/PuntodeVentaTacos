package com.example.ricardosernam.puntodeventa.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa._____interfazes.agregado;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_OnClick;
import com.example.ricardosernam.puntodeventa.provider.ContractParaProductos;
import com.example.ricardosernam.puntodeventa.provider.ProviderDeProductos;
import com.example.ricardosernam.puntodeventa.utils.Constantes;
import com.example.ricardosernam.puntodeventa.utils.Utilidades;
import com.example.ricardosernam.puntodeventa.web.Inventario;
import com.example.ricardosernam.puntodeventa.web.Inventario_detalle;
import com.example.ricardosernam.puntodeventa.web.Producto;
import com.example.ricardosernam.puntodeventa.web.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ricardosernam.puntodeventa.Inventario.Inventario.db;

/**
 * Maneja la transferencia de datos entre el servidor y el cliente
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();
    public static String url;
    Cursor consulta;
    int cuenta=1;
    ContentValues values;
    ContentResolver resolver;
    private Gson gson = new Gson();

    /**
     * Proyección para las consultas
     */
    private static final String[] PROJECTION_PRODUCTOS = new String[]{
            ContractParaProductos.Columnas._ID,
            ContractParaProductos.Columnas.ID_REMOTA,
            ContractParaProductos.Columnas.NOMBRE,
            ContractParaProductos.Columnas.PRECIO,
            ContractParaProductos.Columnas.PORCION,
            ContractParaProductos.Columnas.GUISADO,
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_PRODUCTO = 0;
    public static final int COLUMNA_ID_REMOTA_PRODUCTOS = 1;
    public static final int COLUMNA_NOMBRE = 2;
    public static final int COLUMNA_PRECIO = 3;
    public static final int COLUMNA_PORCION = 4;
    public static final int COLUMNA_GUISADO = 5;
    /////////////////////////////////////////////////////////////////////////////////////
    private static final String[] PROJECTION_INVENTARIO = new String[]{
            ContractParaProductos.Columnas._ID,
            ContractParaProductos.Columnas.ID_REMOTA,
            ContractParaProductos.Columnas.ID_CARRITO,
            ContractParaProductos.Columnas.DISPONIBLE,
            ContractParaProductos.Columnas.FECHA,
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_INVENTARIO = 0;
    public static final int COLUMNA_ID_REMOTA_INVENTARIO = 1;
    public static final int COLUMNA_ID_CARRITO_INVENTARIO = 2;    ////
    public static final int COLUMNA_DISPONIBLE = 3;
    public static final int COLUMNA_FECHA_INVENTARIO = 4;    //////
    ////////////////////////////////////////////////////////////////////////////////////////
    private static final String[] PROJECTION_INVENTARIO_DETALLES = new String[]{
            ContractParaProductos.Columnas._ID,
            ContractParaProductos.Columnas.ID_REMOTA,
            ContractParaProductos.Columnas.ID_PRODUCTO,
            ContractParaProductos.Columnas.EXISTENTE,
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_INVENTARIO_DETALLES = 0;      ///////funciona solo en la exportacion
    public static final int COLUMNA_ID_REMOTA_INVENTARIO_DETALLE = 1;
    public static final int COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE = 2;
    public static final int COLUMNA_EXISTENTE = 3;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String[] PROJECTION_VENTA = new String[]{
            ContractParaProductos.Columnas._ID,
            ContractParaProductos.Columnas.ID_REMOTA,
            ContractParaProductos.Columnas.FECHA,
            ContractParaProductos.Columnas.ID_CARRITO,
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_VENTA = 0;
    public static final int COLUMNA_ID_REMOTA_VENTA = 1;
    public static final int COLUMNA_FECHA_VENTA = 2;
    public static final int COLUMNA_ID_CARRITO_VENTA = 3;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String[] PROJECTION_VENTA_DETALLE = new String[]{
            ContractParaProductos.Columnas._ID,
            ContractParaProductos.Columnas.ID_REMOTA,
            ContractParaProductos.Columnas.CANTIDAD,
            ContractParaProductos.Columnas.ID_PRODUCTO,
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID_VENTA_DETALLES = 0;      ///////funciona solo en la exportacion
    public static final int COLUMNA_ID_REMOTA_VENTA_DETALLE = 1;
    public static final int COLUMNA_CANTIDAD = 3;
    public static final int COLUMNA_ID_PRODUCTO_VENTA_DETALLE = 2;



    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context, String ur) {
        url = ur;
        obtenerCuentaASincronizar(context);
    }

    @Override    ////metodo de la clase
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            realizarSincronizacionLocal(syncResult, url);   ////sincronizar
        } else {
            realizarSincronizacionRemota(Constantes.GET_URL_INVENTARIO);   ////subir datos
        }
    }
    /////////////////////////////////////////////////////metodos de sincronizacion ///////////////////////////////////////////////////////

    /**
     * Inicia manualmente la sincronización
     *
     * @param context    Contexto para crear la petición de sincronización
     * @param onlyUpload Usa true para sincronizar el servidor o false para sincronizar el cliente
     */
    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

    public static void sincronizarAhora(Context context, boolean onlyUpload) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context), context.getString(R.string.provider_authority), bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    private void realizarSincronizacionLocal(final SyncResult syncResult, final String url) {
        Log.i(TAG, "Actualizando el cliente.");   ////hasta aqui bien

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(Request.Method.GET, url,  ////POSIBLE ERROR
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGet(response, syncResult, url);
                            }
                        },
                        new Response.ErrorListener() {  //// Si el ip es incorrecto
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Log.d(TAG, error.networkResponse.toString());   ///error
                                Toast.makeText(getContext(), "Revisa los servicios de XAMPP, tu IP, o tu conexión a Internet e intentalo nuevamente", Toast.LENGTH_LONG).show();
                            }
                        }
                )
        );
    }

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los gastos.
     *
     * @param response   Respuesta en formato Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGet(JSONObject response, SyncResult syncResult, String url) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);
            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocales(response, syncResult, url);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);

                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en formato Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
    private void actualizarDatosLocales(JSONObject response, SyncResult syncResult, String url) {   ///aqui esta el error
///////////////////////////////////////////////INVENTARIO/////////////////////////////////////////////////////7
        if (url == Constantes.GET_URL_INVENTARIO) {

            JSONArray gastos = null;

            try {
                // Obtener array "gastos"
                gastos = response.getJSONArray(Constantes.INVENTARIO);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Parsear con Gson
            com.example.ricardosernam.puntodeventa.web.Inventario[] res2 = gson.fromJson(gastos != null ? gastos.toString() : null, com.example.ricardosernam.puntodeventa.web.Inventario[].class);
            List<com.example.ricardosernam.puntodeventa.web.Inventario> data2 = Arrays.asList(res2);

            // Lista para recolección de operaciones pendientes
            ArrayList<ContentProviderOperation> ops2 = new ArrayList<ContentProviderOperation>();

            // Tabla hash para recibir las entradas entrantes
            HashMap<String, com.example.ricardosernam.puntodeventa.web.Inventario> expenseMap2 = new HashMap<String, com.example.ricardosernam.puntodeventa.web.Inventario>();   /////contiene los datos consultados
            for (com.example.ricardosernam.puntodeventa.web.Inventario e : data2) {
                expenseMap2.put(e.idinventario, e);
            }
            // Consultar registros remotos actuales
            Uri uri2 = ContractParaProductos.CONTENT_URI_INVENTARIO;
            String select2 = ContractParaProductos.Columnas.ID_REMOTA + " IS NOT NULL";
            Cursor c2 = resolver.query(uri2, PROJECTION_INVENTARIO, select2, null, null);
            assert c2 != null;

            Log.i(TAG, "Se encontraron " + c2.getCount() + " registros locales INVENTARIO.");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Encontrar datos obsoletos
            String id2;
            int idcarrito;
            int disponible;
            String fecha;

            while (c2.moveToNext()) {
                syncResult.stats.numEntries++;

                id2 = c2.getString(COLUMNA_ID_REMOTA_INVENTARIO);
                idcarrito = c2.getInt(COLUMNA_ID_CARRITO_INVENTARIO);
                disponible = c2.getInt(COLUMNA_DISPONIBLE);
                fecha = c2.getString(COLUMNA_FECHA_INVENTARIO);


                com.example.ricardosernam.puntodeventa.web.Inventario match = expenseMap2.get(id2);

                if (match != null) {  ////existen los mismos datos
                    // Esta entrada existe, por lo que se remueve del mapeado
                    expenseMap2.remove(id2);

                    Uri existingUri = ContractParaProductos.CONTENT_URI_INVENTARIO.buildUpon().appendPath(id2).build();

                    // Comprobar si el gasto necesita ser actualizado
                    boolean b1 = match.idcarrito != idcarrito;
                    boolean b2 = match.disponible != disponible;
                    boolean b3 = match.fecha != null && !match.fecha.equals(fecha);

                    if (b1 || b2 || b3) {
                        Log.i(TAG, "Programando actualización de: " + existingUri + " INVENTARIO");
                        ops2.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(ContractParaProductos.Columnas.ID_CARRITO, match.idcarrito)
                                .withValue(ContractParaProductos.Columnas.DISPONIBLE, match.disponible)
                                .withValue(ContractParaProductos.Columnas.FECHA, match.fecha)
                                .build());
                        syncResult.stats.numUpdates++;
                    } else {
                        Log.i(TAG, "No hay acciones para este registro: " + existingUri + " INVENTARIO");
                    }
                } else {
                    // eliminamos los datos que no estan en local host
                    Uri deleteUri = ContractParaProductos.CONTENT_URI_INVENTARIO.buildUpon().appendPath(id2).build();
                    Log.i(TAG, "Programando eliminación de: " + deleteUri + " INVENTARIO");
                    ops2.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            c2.close();

            ////insertamos los valores de la base de datos
            for (com.example.ricardosernam.puntodeventa.web.Inventario e : expenseMap2.values()) {
                Log.i(TAG, "Programando inserción de: " + e.idinventario + " INVENTARIO");
                ops2.add(ContentProviderOperation.newInsert(ContractParaProductos.CONTENT_URI_INVENTARIO)   /////error
                        .withValue(ContractParaProductos.Columnas.ID_REMOTA, e.idinventario)
                        .withValue(ContractParaProductos.Columnas.ID_CARRITO, e.idcarrito)
                        .withValue(ContractParaProductos.Columnas.DISPONIBLE, e.disponible)
                        .withValue(ContractParaProductos.Columnas.FECHA, e.fecha)
                        .build());
                syncResult.stats.numInserts++;
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
                Log.i(TAG, "Aplicando operaciones... INVENTARIO");
                try {
                    resolver.applyBatch(ContractParaProductos.AUTHORITY, ops2);
                } catch (RemoteException | OperationApplicationException e) {
                    e.printStackTrace();
                }
                resolver.notifyChange(ContractParaProductos.CONTENT_URI_INVENTARIO, null, false);
                Log.i(TAG, "Sincronización finalizada INVENTARIO.");
            } else {
                Log.i(TAG, "No se requiere sincronización INVENTARIO");
            }
            inicializarSyncAdapter(getContext(), Constantes.GET_URL_PRODUCTO);
            sincronizarAhora(getContext(), false);
        }
///////////////////////////////////PRODUCTOS//////////////////////////////////////////////////////7
        else if (url == Constantes.GET_URL_PRODUCTO) {
            JSONArray productos = null;

            try {
                // Obtener array "gastos"
                productos = response.getJSONArray(Constantes.PRODUCTO);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Parsear con Gson
            Producto[] res = gson.fromJson(productos != null ? productos.toString() : null, Producto[].class);
            List<Producto> data = Arrays.asList(res);

            // Lista para recolección de operaciones pendientes
            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            // Tabla hash para recibir las entradas entrantes
            HashMap<String, Producto> expenseMap = new HashMap<String, Producto>();   /////contiene los datos consultados
            for (Producto e : data) {
                expenseMap.put(e.idproducto, e);
            }

            // Consultar registros remotos actuales
            Uri uri = ContractParaProductos.CONTENT_URI_PRODUCTO;
            String select = ContractParaProductos.Columnas.ID_REMOTA + " IS NOT NULL";
            Cursor c = resolver.query(uri, PROJECTION_PRODUCTOS, select, null, null);
            assert c != null;

            Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales PRODUCTOS.");

            String id;
            String nombre;
            Double precio;
            Double porcion;
            String guisado;


            while (c.moveToNext()) {
                syncResult.stats.numEntries++;

                id = c.getString(COLUMNA_ID_REMOTA_PRODUCTOS);
                nombre = c.getString(COLUMNA_NOMBRE);
                precio = c.getDouble(COLUMNA_PRECIO);
                porcion = c.getDouble(COLUMNA_PORCION);
                guisado = c.getString(COLUMNA_GUISADO);

                Producto match = expenseMap.get(id);

                if (match != null) {  ////existen los mismos datos
                    // Esta entrada existe, por lo que se remueve del mapeado
                    expenseMap.remove(id);

                    Uri existingUri = ContractParaProductos.CONTENT_URI_PRODUCTO.buildUpon().appendPath(id).build();

                    // Comprobar si el gasto necesita ser actualizado
                    boolean b = match.nombre != null && !match.nombre.equals(nombre);
                    boolean b1 = match.precio != precio;
                    boolean b2 = match.porcion != porcion;
                    boolean b3 = match.guisado != null && !match.guisado.equals(guisado);

                    if (b || b1 || b2 || b3) {
                        Log.i(TAG, "Programando actualización de: " + existingUri + " PRODUCTOS");
                        ops.add(ContentProviderOperation.newUpdate(existingUri)
                                .withValue(ContractParaProductos.Columnas.NOMBRE, match.nombre)
                                .withValue(ContractParaProductos.Columnas.PRECIO, match.precio)
                                .withValue(ContractParaProductos.Columnas.PORCION, match.porcion)
                                .withValue(ContractParaProductos.Columnas.GUISADO, match.guisado)
                                .build());
                        syncResult.stats.numUpdates++;
                    } else {
                        Log.i(TAG, "No hay acciones para este registro: " + existingUri + " PRODUCTOS");
                    }
                } else {
                    // eliminamos los datos que no estan en local host
                    Uri deleteUri = ContractParaProductos.CONTENT_URI_PRODUCTO.buildUpon().appendPath(id).build();
                    Log.i(TAG, "Programando eliminación de: " + deleteUri + " PRODUCTOS");
                    ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            c.close();

            ////insertamos los valores de la base de datos
            for (Producto e : expenseMap.values()) {
                Log.i(TAG, "Programando inserción de: " + e.idproducto + " PRODUCTOS");
                ops.add(ContentProviderOperation.newInsert(ContractParaProductos.CONTENT_URI_PRODUCTO)
                        .withValue(ContractParaProductos.Columnas.ID_REMOTA, e.idproducto)
                        .withValue(ContractParaProductos.Columnas.NOMBRE, e.nombre)
                        .withValue(ContractParaProductos.Columnas.PRECIO, e.precio)
                        .withValue(ContractParaProductos.Columnas.PORCION, e.porcion)
                        .withValue(ContractParaProductos.Columnas.GUISADO, e.guisado)
                        .build());
                syncResult.stats.numInserts++;
            }

            if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
                Log.i(TAG, "Aplicando operaciones... PRODUCTOS");
                try {
                    resolver.applyBatch(ContractParaProductos.AUTHORITY, ops);
                } catch (RemoteException | OperationApplicationException e) {
                    e.printStackTrace();
                }
                resolver.notifyChange(ContractParaProductos.CONTENT_URI_PRODUCTO, null, false);
                Log.i(TAG, "Sincronización finalizada PRODUCTOS.");
            } else {
                Log.i(TAG, "No se requiere sincronización PRODUCTOS");
            }
            inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO_DETALLE);
            sincronizarAhora(getContext(), false);
        }

        ///////////////////////////////////INVENTARIO_DETALLES//////////////////////////////////////////////////////////////////
        else if (url == Constantes.GET_URL_INVENTARIO_DETALLE) {
            JSONArray inventario_detalles = null;

            try {
                // Obtener array "gastos"
                inventario_detalles = response.getJSONArray(Constantes.INVENTARIO_DETALLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Inventario_detalle[] res2 = gson.fromJson(inventario_detalles != null ? inventario_detalles.toString() : null, Inventario_detalle[].class);
            List<Inventario_detalle> data2 = Arrays.asList(res2);

            ArrayList<ContentProviderOperation> ops2 = new ArrayList<ContentProviderOperation>();

            // Tabla hash para recibir las entradas entrantes
            HashMap<String, Inventario_detalle> expenseMap2 = new HashMap<String, Inventario_detalle>();   /////contiene los datos consultados sin repeticion

            // Consultar registros remotos actuales
            Uri uri2 = ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE;
            String select2 = ContractParaProductos.Columnas.ID_REMOTA + " IS NOT NULL";
            Cursor c2 = resolver.query(uri2, PROJECTION_INVENTARIO_DETALLES, select2, null, null);
            assert c2 != null;

            Log.i(TAG, "Se encontraron " + c2.getCount() + " registros locales INVENTARIO_DETALLES.");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Encontrar datos obsoletos
            String id2;
            String idproducto;
            Double existente;

            while (c2.moveToNext()) {
                syncResult.stats.numEntries++;

                id2 = c2.getString(COLUMNA_ID_REMOTA_INVENTARIO_DETALLE);
                idproducto = c2.getString(COLUMNA_ID_PRODUCTO_INVENTARIO_DETALLE);
                existente = c2.getDouble(COLUMNA_EXISTENTE);

                Inventario_detalle match2 = expenseMap2.get(id2);

                if (match2 != null) {  ////existen los mismos datos
                    // Esta entrada existe, por lo que se remueve del mapeado
                    expenseMap2.remove(id2);

                    Uri existingUri2 = ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE.buildUpon().appendPath(id2).build();

                    boolean b = match2.idproducto != null && !match2.idproducto.equals(idproducto);
                    boolean b1 = match2.existente != existente;

                    if (b || b1) {
                        Log.i(TAG, "Programando actualización de: " + existingUri2 + " INVENTARIO_DETALLES");
                        ops2.add(ContentProviderOperation.newUpdate(existingUri2)
                                .withValue(ContractParaProductos.Columnas.ID_PRODUCTO, match2.idproducto)
                                .withValue(ContractParaProductos.Columnas.EXISTENTE, match2.existente)
                                .build());
                        syncResult.stats.numUpdates++;
                    } else {
                        Log.i(TAG, "No hay acciones para este registro: " + existingUri2 + " INVENTARIO_DETALLES");
                    }
                } else {
                    // eliminamos los datos que no estan en local host
                    Uri deleteUri = ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE.buildUpon().appendPath(id2).build();
                    Log.i(TAG, "Programando eliminación de: " + deleteUri + " INVENTARIO_DETALLES");
                    ops2.add(ContentProviderOperation.newDelete(deleteUri).build());
                    syncResult.stats.numDeletes++;
                }
            }
            c2.close();

            ////insertamos los valores de la base de datos
            for (Inventario_detalle e : data2) {    /////aqui se insertan //////////////////////////////////////////////////////////
                Log.i(TAG, "Programando inserción de: " + e.idinventario + " INVENTARIO_DETALLES");
                ops2.add(ContentProviderOperation.newInsert(ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE)
                        .withValue(ContractParaProductos.Columnas.ID_REMOTA, e.idinventario)
                        .withValue(ContractParaProductos.Columnas.ID_PRODUCTO, e.idproducto)
                        .withValue(ContractParaProductos.Columnas.EXISTENTE, e.existente)
                        .build());
                syncResult.stats.numInserts++;
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (syncResult.stats.numInserts > 0 || syncResult.stats.numUpdates > 0 || syncResult.stats.numDeletes > 0) {
                Log.i(TAG, "Aplicando operaciones...  INVENTARIO_DETALLES");
                try {
                    resolver.applyBatch(ContractParaProductos.AUTHORITY, ops2);
                } catch (RemoteException | OperationApplicationException e) {
                    e.printStackTrace();
                }
                resolver.notifyChange(ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE, null, false);
                Log.i(TAG, "Sincronización finalizada  INVENTARIO_DETALLES.");
                //com.example.ricardosernam.puntodeventa.Inventario.Inventario.relleno();

            } else {
                Log.i(TAG, "No se requiere sincronización INVENTARIO_DETALLES");
            }
        }
    }
    ////////////////////////////////////////////////////////metodos de incersion //////////////////////////////////////////////////////////
    private void realizarSincronizacionRemota(final String url) {
        Log.i(TAG, "Actualizando el servidor...");

        if (url == Constantes.GET_URL_INVENTARIO) {
            iniciarActualizacion(url);

            Cursor c = obtenerRegistrosSucios(url);

            Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(COLUMNA_ID_INVENTARIO);

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.POST,
                                    Constantes.INSERT_URL_INVENTARIO,
                                    Utilidades.deCursorAJSONObject(c, url),  //////////////////////////////////////////////
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            procesarRespuestaInsert(response, idLocal, url);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.getMessage());
                                        }
                                    }

                            ) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    headers.put("Accept", "application/json");
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8" + getParamsEncoding();
                                }
                            }
                    );
                }

            } else {
                Log.i(TAG, "No se requiere sincronización");
            }
            c.close();
        }
        else if (url == Constantes.GET_URL_INVENTARIO_DETALLE) {
            iniciarActualizacion(url);

            Cursor c = obtenerRegistrosSucios(url);

            Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(COLUMNA_ID_INVENTARIO_DETALLES);

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(
                            new JsonObjectRequest(Request.Method.POST, Constantes.INSERT_URL_INVENTARIO_DETALLE,
                                    Utilidades.deCursorAJSONObject(c, url),  //////////////////////////////////////////////
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //procesarRespuestaInsert(response, idLocal, url);
                                            realizarSincronizacionRemota(Constantes.INSERT_URL_VENTA);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }

                            ) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    headers.put("Accept", "application/json");
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8" + getParamsEncoding();
                                }
                            }
                    );
                }

            } else {
                Log.i(TAG, "No se requiere sincronización");
            }
            c.close();
        }
        else if (url == Constantes.INSERT_URL_VENTA) {
            iniciarActualizacion(url);

            Cursor c = obtenerRegistrosSucios(url);

            Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(COLUMNA_ID_VENTA);

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(
                            new JsonObjectRequest(
                                    Request.Method.POST, Constantes.INSERT_URL_VENTA,
                                    Utilidades.deCursorAJSONObject(c, url),  //////////////////////////////////////////////
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            procesarRespuestaInsert(response, idLocal, url);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Volley: " + error.getMessage());
                                        }
                                    }

                            ) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    headers.put("Accept", "application/json");
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8" + getParamsEncoding();
                                }
                            }
                    );
                }

            } else {
                Log.i(TAG, "No se requiere sincronización");
            }
            c.close();
        }
        else if (url == Constantes.INSERT_URL_VENTA_DETALLE) {
            iniciarActualizacion(url);

            Cursor c = obtenerRegistrosSucios(url);

            Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    final int idLocal = c.getInt(COLUMNA_ID_VENTA_DETALLES);

                    VolleySingleton.getInstance(getContext()).addToRequestQueue(
                            new JsonObjectRequest(Request.Method.POST, Constantes.INSERT_URL_VENTA_DETALLE,
                                    Utilidades.deCursorAJSONObject(c, url),  //////////////////////////////////////////////
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //procesarRespuestaInsert(response, idLocal, url);
                                            //realizarSincronizacionRemota(Constantes.INSERT_URL_VENTA);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "Error Volley: " + error.getMessage());
                                }
                            }

                            ) {
                                @Override
                                public Map<String, String> getHeaders() {
                                    Map<String, String> headers = new HashMap<String, String>();
                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                    headers.put("Accept", "application/json");
                                    return headers;
                                }

                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8" + getParamsEncoding();
                                }
                            }
                    );
                }

            } else {
                Log.i(TAG, "No se requiere sincronización");
            }
            c.close();
        }
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     *
     * @return Cursor con el registro.
     */

    private Cursor obtenerRegistrosSucios(String url) {
        String projection[] = null;
        Uri uri = null;
        String selection = null;
        String selectionArgs[] = null;
        if (url == Constantes.GET_URL_INVENTARIO) {
            uri = ContractParaProductos.CONTENT_URI_INVENTARIO;
            selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_SYNC + ""};
            projection = PROJECTION_INVENTARIO;
        }

        else if (url == Constantes.GET_URL_INVENTARIO_DETALLE) {
            uri = ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE;
            selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_SYNC + ""};
            projection=PROJECTION_INVENTARIO_DETALLES;
        }
        else if (url == Constantes.INSERT_URL_VENTA) {
            uri = ContractParaProductos.CONTENT_URI_VENTA;
            selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_SYNC + ""};
            projection=PROJECTION_VENTA;
        }
        else if (url == Constantes.INSERT_URL_VENTA_DETALLE) {
            uri = ContractParaProductos.CONTENT_URI_VENTA_DETALLE;
            selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_SYNC + ""};
            projection=PROJECTION_VENTA_DETALLE;
        }
        return resolver.query(uri, projection, selection, selectionArgs, null);
    }



    /**
     * Cambia a estado "de sincronización" el registro que se acaba de insertar localmente
     */
    private void iniciarActualizacion(String url) {

        if (url == Constantes.GET_URL_INVENTARIO) {
            Uri uri = ContractParaProductos.CONTENT_URI_INVENTARIO;
            String selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND "
                    + ContractParaProductos.Columnas.ESTADO + "=?";
            String[] selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_OK + ""};

            ContentValues v = new ContentValues();
            v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_SYNC);

            int results = resolver.update(uri, v, selection, selectionArgs);
            Log.i(TAG, "Registros puestos en cola de inserción:" + results);
        }

        else if (url == Constantes.GET_URL_INVENTARIO_DETALLE) {
                Uri uri = ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE;
                String selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
                String[] selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_OK + ""};

                ContentValues v = new ContentValues();
                v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_SYNC);

                int results = resolver.update(uri, v, selection, selectionArgs);
                Log.i(TAG, "Registros puestos en cola de inserción:" + results);
            }

        else if (url == Constantes.INSERT_URL_VENTA) {
            Uri uri = ContractParaProductos.CONTENT_URI_VENTA;
            String selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            String[] selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_OK + ""};

            ContentValues v = new ContentValues();
            v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_SYNC);

            int results = resolver.update(uri, v, selection, selectionArgs);
            Log.i(TAG, "Registros puestos en cola de inserción:" + results);
        }
        else if (url == Constantes.INSERT_URL_VENTA_DETALLE) {
            Uri uri = ContractParaProductos.CONTENT_URI_VENTA_DETALLE;
            String selection = ContractParaProductos.Columnas.PENDIENTE_INSERCION + "=? AND " + ContractParaProductos.Columnas.ESTADO + "=?";
            String[] selectionArgs = new String[]{"1", ContractParaProductos.ESTADO_OK + ""};

            ContentValues v = new ContentValues();
            v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_SYNC);

            int results = resolver.update(uri, v, selection, selectionArgs);
            Log.i(TAG, "Registros puestos en cola de inserción:" + results);
        }
        }


    /**
     * Limpia el registro que se sincronizó y le asigna la nueva id remota proveida
     * por el servidor
     *
     * @param idRemota id remota
     */
    private void finalizarActualizacion(String idRemota, int idLocal, String url) {     /////actualizamos lo insertado en la app

        if (url == Constantes.GET_URL_INVENTARIO) {
            Uri uri = ContractParaProductos.CONTENT_URI_INVENTARIO;
            String selection = ContractParaProductos.Columnas._ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(idLocal)};

            ContentValues v = new ContentValues();
            v.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, "0");
            v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_OK);
            v.put(ContractParaProductos.Columnas.ID_REMOTA, idRemota);

            resolver.update(uri, v, selection, selectionArgs);
            realizarSincronizacionRemota(Constantes.GET_URL_INVENTARIO_DETALLE);
        }
        else if (url == Constantes.INSERT_URL_VENTA) {
            Uri uri = ContractParaProductos.CONTENT_URI_VENTA;
            String selection = ContractParaProductos.Columnas._ID + "=?";
            String[] selectionArgs = new String[]{String.valueOf(idLocal)};

            ContentValues v = new ContentValues();
            v.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, "0");
            v.put(ContractParaProductos.Columnas.ESTADO, ContractParaProductos.ESTADO_OK);
            v.put(ContractParaProductos.Columnas.ID_REMOTA, idRemota);

            resolver.update(uri, v, selection, selectionArgs);
            realizarSincronizacionRemota(Constantes.INSERT_URL_VENTA_DETALLE);
        }
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsert(JSONObject response, int idLocal, String url) {
///////////////////////////////obtenemos los datos por php//////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (url == Constantes.GET_URL_INVENTARIO) {
            try {
                values=new ContentValues();
                // Obtener estado
                String estado = response.getString(Constantes.ESTADO);
                // Obtener mensaje
                String mensaje = response.getString(Constantes.MENSAJE);
                // Obtener identificador del nuevo registro creado en el servidor
                String idRemota = response.getString(Constantes.ID_INVENTARIO);

                 consulta=db.rawQuery("select * from inventario_detalles" ,null);
                if(consulta.moveToFirst()) {///si hay un elemento
                    values.put("idRemota", Integer.parseInt(idRemota));
                    values.put("idproducto", consulta.getString(1));
                    values.put("existente", consulta.getString(2));
                    values.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, 1);
                    resolver.insert(ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE, values);   ////aqui esta el error

                    while (consulta.moveToNext()) {
                        values.put("idRemota", Integer.parseInt(idRemota));
                        values.put("idproducto", consulta.getString(1));
                        values.put("existente", consulta.getString(2));
                        values.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, 1);
                        resolver.insert(ContractParaProductos.CONTENT_URI_INVENTARIO_DETALLE, values);   ////aqui esta el error
                    }
                }

                switch (estado) {
                    case Constantes.SUCCESS:
                        Log.i(TAG, mensaje);
                        finalizarActualizacion(idRemota, idLocal, url);
                        break;

                    case Constantes.FAILED:
                        Log.i(TAG, mensaje);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if (url == Constantes.INSERT_URL_VENTA) {
            try {
                values = new ContentValues();
                // Obtener estado
                String estado = response.getString(Constantes.ESTADO);
                // Obtener mensaje
                String mensaje = response.getString(Constantes.MENSAJE);
                // Obtener identificador del nuevo registro creado en el servidor
                String idRemota = response.getString(Constantes.ID_VENTA);

                consulta = db.rawQuery("select * from venta_detalles where idRemota='"+cuenta+"'", null);
                if (consulta.moveToFirst()) {///si hay un elemento
                    values.put("idRemota", Integer.parseInt(idRemota));
                    values.put("cantidad", consulta.getString(1));
                    values.put("idproducto", consulta.getString(2));
                    values.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, 1);
                    resolver.insert(ContractParaProductos.CONTENT_URI_VENTA_DETALLE, values);   ////aqui esta el error

                    while (consulta.moveToNext()) {
                        values.put("idRemota", Integer.parseInt(idRemota));
                        values.put("cantidad", consulta.getString(1));
                        values.put("idproducto", consulta.getString(2));
                        values.put(ContractParaProductos.Columnas.PENDIENTE_INSERCION, 1);
                        resolver.insert(ContractParaProductos.CONTENT_URI_VENTA_DETALLE, values);   ////aqui esta el error
                    }
                }
                cuenta++;
                //Toast.makeText(getContext(), "IdRemota VENTA " + idRemota, Toast.LENGTH_LONG).show();
                Log.i(TAG, "idRemota Ventas" + idRemota);

                switch (estado) {
                    case Constantes.SUCCESS:
                        Log.i(TAG, mensaje);
                        finalizarActualizacion(idRemota, idLocal, url);
                        break;

                    case Constantes.FAILED:
                        Log.i(TAG, mensaje);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}



