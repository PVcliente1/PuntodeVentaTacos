package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import static android.webkit.WebSettings.PluginState.ON;


public class BaseDeDatosLocal extends SQLiteOpenHelper {

    private static final String nombre_bd = "punto_de_venta.db";
    private static final int version_bd = 1;

    public BaseDeDatosLocal(Context context) {
        super(context, nombre_bd, null, version_bd);
    }


    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
            } else {
                sqLiteDatabase.execSQL("PRAGMA foreign_keys='ON'");
            }
        }
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //Creamos la tabla de turnos

        sqLiteDatabase.execSQL("CREATE TABLE Turnos (idturno INTEGER PRIMARY KEY, " +
                "  tipo_turno text)");

        sqLiteDatabase.execSQL("INSERT INTO Turnos (idturno, tipo_turno) values (1,'Matutino')");
        sqLiteDatabase.execSQL("INSERT INTO Turnos (idturno, tipo_turno) values (2,'Vespertino')");

        //Creamos la tabla de puestos

        sqLiteDatabase.execSQL("CREATE TABLE Puestos (idpuesto INTEGER PRIMARY KEY, " +
                "  `nombre_puesto` text)");
//                "  `supervisor` INT REFERENCES Puestos (idpuesto))");

        sqLiteDatabase.execSQL("INSERT INTO Puestos (idpuesto, nombre_puesto) values (1,'Administrador')");
        sqLiteDatabase.execSQL("INSERT INTO Puestos (idpuesto, nombre_puesto) values (2,'Revisor')");
        //Creamos el index de Puestos para hacer la relación con la columna supervisor  sqLiteDatabase.execSQL("CREATE INDEX `fk_puestos_puestos1_idx` ON Puestos (`supervisor` ASC);");



        //Creación de la tabla Miembros

        sqLiteDatabase.execSQL("CREATE TABLE Miembros ( idmiembro INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  `nombre` text, " +
                " `telefono` text, " +
                "  `correo` text, " +
                "  `contrasena` text, " +
                "  `idturno` INTEGER NOT NULL, " +
                "  `idpuesto` INTEGER NOT NULL, " +
                "  `foto` text, " +
                "  `apellido` text)");
        sqLiteDatabase.execSQL("INSERT INTO Miembros(idmiembro, nombre,telefono,correo,contrasena,idturno,idpuesto,foto,apellido) "+
                        "values (1,'Seleccionar',' ',' ',' ',1,1,' ',' ')");

        //        " FOREIGN KEY (idturno) REFERENCES Turnos(idturno), " +
        //        " FOREIGN KEY (idpuesto)REFERENCES Puestos(idpuesto))");



        //Creación de la tabla Clientes_DialogFragment

        sqLiteDatabase.execSQL("CREATE TABLE Unidades (\n" +
                "  `idunidad` INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                "  `nombre_unidad` VARCHAR(45))");


        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (1,'Gramos')");
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (2,'Kilogramos')");
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (3,'Mililitros')");
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (4,'Litros')");
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (5,'Centimetros')");
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (6,'Metros')");//Creamos la tabla de productos
        sqLiteDatabase.execSQL("INSERT INTO Unidades (idunidad, nombre_unidad) values (7,'Piezas')");


        /////creacion de productos
        sqLiteDatabase.execSQL("CREATE TABLE Productos (\n" +
                "  `idproducto` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `codigo_barras` VARCHAR(45),\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `precio_venta` float(45)," +
                "  `ruta_imagen` VARCHAR(45)," +
                "  `unidad` varchar(30),\n" +
                "  `cantidad` float,\n" +
                "  `precio_compra` float,\n" +
                "  `idproveedorFK` VARCHAR(40))");                //Llave foranea

        //        "    FOREIGN KEY (`idproveedorFK`) REFERENCES Proveedores (`idproveedor`))");

        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (1,'Tacos-pastor', 8.0,'no')");
        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (2,'Tacos-bistec', 8.0,'no')");
        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (3,'Tacos-frijoles', 8.0,'no')");
        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (4,'Tortas-pastor', 18.0,'no')");
        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (5,'Tortas-bistec', 18.0,'no')");
        sqLiteDatabase.execSQL("INSERT INTO Productos (idproducto, nombre, precio_venta, unidad) values (6,'Refresco', 12.0,'no')");


        //Creación el INDEX para la  relación de la tabla de miembros con la tabla de puestos      sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_puestos1_idx` ON Miembros (`idpuesto` ASC)");


        //Creación el INDEX para la relación de la tabla de miembros con la tabla de turnos    sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_turnos1_idx` ON Miembros(`idturno` ASC)");

        //Creación de la tabla proveedores

        sqLiteDatabase.execSQL("CREATE TABLE Proveedores (\n" +
                "  `idproveedor` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `contacto` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `nombre_empresa` VARCHAR(45))");

        sqLiteDatabase.execSQL("INSERT INTO Proveedores (idproveedor,contacto) VALUES (1, 'Seleccionar proveedor')");

        //Creación de la tabla clientes

        sqLiteDatabase.execSQL("CREATE TABLE Clientes (\n" +
                "  `idcliente` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `alias` VARCHAR(45),\n" +
                "  `telefono` INT ,\n" +
                "  `direccion` VARCHAR(45))");
                //"  PRIMARY KEY (`idcliente`))");

        sqLiteDatabase.execSQL("INSERT INTO Clientes (idcliente,nombre) VALUES (1, 'Cliente')");


        //Creamos el index para relacionar productos con proveedores        sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_proveedores1_idx` ON Productos (`idproveedorFK` ASC)");

        //Creamos el index para relacionar productos con unidades           sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_unidad1_idx` ON Productos (`idunidad` ASC)");



        //Creación de la tabla cobros

        sqLiteDatabase.execSQL("CREATE TABLE Cobros (\n" +
                "  `idcobro` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre_cobro` VARCHAR(45))");


//        sqLiteDatabase.execSQL("INSERT INTO Cobros (idcobro, nombre_cobro) values ('Piezas')");

        //Creación de tabla ventas

        sqLiteDatabase.execSQL("CREATE TABLE Ventas (\n" +
                "  `idventa` INTEGER ,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `fecha` DATETIME ,\n" +
                "  `fecha_entrega` DATETIME ,\n" +
                "  `descripcion` VARCHAR(45) ,\n" +
                "  `tipo_cobro` VARCHAR(45),\n" +
                "  `idmiembroFK` INTEGER ,\n" +
                "  `idclienteFK` INTEGER)");


        //        "    FOREIGN KEY (`idclienteFK`) REFERENCES Clientes (`idcliente`), " +
        //        "    FOREIGN KEY (`idmiembroFK`) REFERENCES Miembros (`idmiembro`), " +
        //        "    FOREIGN KEY (`idcobro`) REFERENCES Cobros (`idcobro`))");

        //Creación de index para relacionar ventas con clientes         sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_clientes_idx` ON Ventas (`idclienteFK` ASC)");

        //Creación de index para relacionar ventas con miembros         sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_miembros1_idx` ON Ventas (`idmiembroFK` ASC)");

        //Creación de index para relacionar ventas con cobros           sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_cobros1_idx` ON Ventas (`idcobro` ASC)");

        //Creación de tabla datos_empresa

        sqLiteDatabase.execSQL("CREATE TABLE Datos_Empresa (\n" +
                "  `idempresa` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` VARCHAR(45),\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  `horario` VARCHAR(45))");

        //Creación de tabla descuentos

        sqLiteDatabase.execSQL("CREATE TABLE Descuentos(\n" +
                "  `iddescuento` INTEGER,\n" +
                "  `tipo_descuento` VARCHAR(45),\n" +
                "  `porcentaje` INTEGER)");

        sqLiteDatabase.execSQL("INSERT INTO Descuentos (iddescuento, tipo_descuento, porcentaje) values (1,'Normal',5)");
        sqLiteDatabase.execSQL("INSERT INTO Descuentos (iddescuento, tipo_descuento, porcentaje) values (2,'Especial',10)");

        //Creación de tabla venta_detalles

        sqLiteDatabase.execSQL("CREATE TABLE Venta_detalles (\n" +
                "  `cantidad` float,\n" +
                "  `precio` float,\n" +
                "  `idventaFK` INTEGER,\n" +
                "  `idproductoFK` INTEGER,\n" +
                "  `iddescuentoFK` INTEGER)");

         //       "    FOREIGN KEY (`idventaFK`) REFERENCES Ventas (`idventa`), " +
         //       "    FOREIGN KEY (`idproductoFK`) REFERENCES Productos (`idproducto`), " +
         //       "    FOREIGN KEY (`iddescuentos`) REFERENCES Descuentos (`iddescuento`))");

        //Creación de index para la relación de venta_Detalles con ventas           sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_ventas1_idx` ON venta_detalles (`idventaFK` ASC)");

        //Creación de index para la relación de venta_Detalles con productos        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_productos1_idx` ON venta_detalles(`idproductoFK` ASC)");

        //Creación de index para la relación de venta_Detalles con Descuentos       sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_descuentos1_idx` ON venta_detalles(`iddescuentos` ASC)");


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS Puestos");
        db.execSQL("DROP TABLE IF EXISTS Cobros");
        db.execSQL("DROP TABLE IF EXISTS Turnos");
        db.execSQL("DROP TABLE IF EXISTS Miembros");
        db.execSQL("DROP TABLE IF EXISTS Clientes");
        db.execSQL("DROP TABLE IF EXISTS Ventas");
        db.execSQL("DROP TABLE IF EXISTS Venta_detalles");
        db.execSQL("DROP TABLE IF EXISTS Descuentos");
        db.execSQL("DROP TABLE IF EXISTS Proveedores");
        db.execSQL("DROP TABLE IF EXISTS Miembros");
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL("DROP TABLE IF EXISTS Empresa");

        onCreate(db);


    }

}
