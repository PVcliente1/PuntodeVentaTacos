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
        /*database.execSQL("CREATE TABLE carritos (\n" +
                "  `idcarrito` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `descripcion` VARCHAR(45) not null,\n" +
                "  `ubicacion` VARCHAR(45) not null)");

        database.execSQL("CREATE TABLE inventarios (\n" +
                "  `idinventario` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `idcarrito` INT NOT NULL,\n" +
                "  `disponible` CHAR(1),\n" +
                "  `fecha` DATETIME ,\n" +
                "  CONSTRAINT `fk_inventarios_carritos1`\n" +
                "    FOREIGN KEY (`idcarrito`)\n" +
                "    REFERENCES carritos (`idcarrito`))");

        //"  PRIMARY KEY (`idinventario`, `idcarrito`),\n" +
          //      "  INDEX `fk_inventarios_carritos1_idx` (`idcarrito` ASC),\n" +

        database.execSQL("CREATE TABLE productos (\n" +
                "  `idproducto` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45) ,\n" +
                "  `precio` DOUBLE ,\n" +
                "  `porcion` DOUBLE ,\n" +
                "  `guisado` VARCHAR(45))");

        database.execSQL("CREATE TABLE IF NOT EXISTS inventario_detalles (\n" +
                "  `idinventario` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `idproducto` INT NOT NULL,\n" +
                "  `existente` DOUBLE ,\n" +
                //"  PRIMARY KEY (`idinventario`, `idproducto`),\n" +
                "  CONSTRAINT `fk_inventarios_has_productos_inventarios1`\n" +
                "    FOREIGN KEY (`idinventario`)\n" +
                "    REFERENCES inventarios (`idinventario`),\n" +

                "  CONSTRAINT `fk_inventarios_has_productos_productos1`\n" +
                "    FOREIGN KEY (`idproducto`)\n" +
                "    REFERENCES productos (`idproducto`))");

        database.execSQL("CREATE TABLE IF NOT EXISTS ventas (\n" +
                "  `idventa` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `fecha` VARCHAR(45) ,\n" +
                "  `idcarrito` INT NOT NULL,\n" +
                //"  PRIMARY KEY (`idventa`, `idcarrito`),\n" +
                "  CONSTRAINT `fk_ventas_carritos1`\n" +
                "    FOREIGN KEY (`idcarrito`)\n" +
                "    REFERENCES carritos (`idcarrito`))");

        database.execSQL("CREATE TABLE venta_detalles (\n" +
                "  `idventa` INT NOT NULL,\n" +
                "  `cantidad` INT,\n" +
                "  `idproducto` INT NOT NULL,\n" +
                //"  PRIMARY KEY (`idventa`, `idproducto`),\n" +
                "  CONSTRAINT `fk_venta_detalles_ventas`\n" +
                "    FOREIGN KEY (`idventa`)\n" +
                "    REFERENCES ventas (`idventa`),\n" +
                "  CONSTRAINT `fk_venta_detalles_productos1`\n" +
                "    FOREIGN KEY (`idproducto`)\n" +
                "    REFERENCES productos (`idproducto`))");*/

             String cmd = "CREATE TABLE " + ContractParaProductos.PRODUCTO + " (" +
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
                ContractParaProductos.Columnas.ID_REMOTA + " TEXT," +
                ContractParaProductos.Columnas.ESTADO + " INTEGER NOT NULL DEFAULT "+ ContractParaProductos.ESTADO_OK+"," +
                ContractParaProductos.Columnas.PENDIENTE_INSERCION + " INTEGER NOT NULL DEFAULT 0)";
        database.execSQL(cmd3);
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
