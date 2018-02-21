package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatosLocal extends SQLiteOpenHelper {
    public BaseDeDatosLocal(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Miembros (\n" +
                "  `idmiembro` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellidos` VARCHAR(45),\n" +
                "  `puesto` VARCHAR(45),\n" +
                "  `telefono` INT ,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `contrasena` VARCHAR(45),\n" +
                "  `usuario` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Proveedores (\n" +
                "  `idproveedor` INT NOT NULL,\n" +
                "  `contacto` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `nombre_empresa` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idproveedor`))\n");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Productos (\n" +
                "  `idproducto` INT NOT NULL,\n" +
                "  `codigo_barras` INT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `ruta_imagen` VARCHAR(45),\n" +
                "  `precio_venta` VARCHAR(45),\n" +
                "  `idproveedorFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idproducto`, `idproveedorFK`),\n" +
                "  CONSTRAINT `fk_productos_proveedores1`\n" +
                "    FOREIGN KEY (`idproveedorFK`)\n" +
                "    REFERENCES `proveedores` (`idproveedor`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Compras (\n" +
                "  `idcompra` INT NOT NULL,\n" +
                "  `codigo_barras` INT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `cantidad` VARCHAR(45),\n" +
                "  `existentes` VARCHAR(45),\n" +
                "  `precio_compra` VARCHAR(45),\n" +
                "  `unidad` VARCHAR(45),\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idcompra`, `idmiembroFK`),\n" +
                "  CONSTRAINT `fk_compras_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_compras_miembros1_idx` ON Compras (`idmiembroFK` ASC);\n");



        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Clientes (\n" +
                "  `idcliente` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45) ,\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `alias` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `descuento` INT,\n" +
                "  PRIMARY KEY (`idcliente`))");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Ventas (\n" +
                "  `idventa` INT NOT NULL,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `fecha` DATETIME,\n" +
                "  `idclienteFK` INT NOT NULL,\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventa`, `idclienteFK`, `idmiembroFK`),\n" +
                "  CONSTRAINT `fk_ventas_clientes`\n" +
                "    FOREIGN KEY (`idclienteFK`)\n" +
                "    REFERENCES Clientes (`idcliente`)\n" +
                ",  CONSTRAINT `fk_ventas_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_clientes_idx` ON Ventas (`idclienteFK` ASC)");
        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_miembros1_idx` ON Ventas (`idmiembroFK` ASC)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Descuentos (\n" +
                "  `iddescuento` INT NOT NULL,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `porcentaje` INT,\n" +
                "  PRIMARY KEY (`iddescuento`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Empresa (\n" +
                "  `idempresa` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idempresa`))");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Venta_detalles (\n" +
                "  `cantidad` INT,\n" +
                "  `precio` INT,\n" +
                "  `idventaFK` INT NOT NULL,\n" +
                "  `iddescuentoFK` INT NOT NULL,\n" +
                "  `idproductoFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventaFK`, `iddescuentoFK`, `idproductoFK`),\n" +
                "  CONSTRAINT `fk_venta_detalles_ventas1`\n" +
                "    FOREIGN KEY (`idventaFK`)\n" +
                "    REFERENCES Ventas (`idventa`)\n" +
                " , CONSTRAINT `fk_venta_detalles_descuentos1`\n" +
                "    FOREIGN KEY (`iddescuentoFK`)\n" +
                "    REFERENCES Descuentos (`iddescuento`)\n" +
                " , CONSTRAINT `fk_venta_detalles_productos1`\n" +
                "    FOREIGN KEY (`idproductoFK`)\n" +
                "    REFERENCES Productos (`idproducto`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_ventas1_idx` ON Venta_detalles (`idventaFK` ASC)");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_descuentos1_idx` ON Venta_detalles (`iddescuentoFK` ASC)");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_productos1_idx` ON Venta_detalles (`idproductoFK` ASC)");


    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Clientes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Ventas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Venta_detalles");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Descuentos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Proveedores");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Compras");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Miembros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Productos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Empresa");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Miembros (\n" +
                "  `idmiembro` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellidos` VARCHAR(45),\n" +
                "  `puesto` VARCHAR(45),\n" +
                "  `telefono` INT ,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `contrasena` VARCHAR(45),\n" +
                "  `usuario` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Proveedores (\n" +
                "  `idproveedor` INT NOT NULL,\n" +
                "  `contacto` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `nombre_empresa` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idproveedor`))\n");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Productos (\n" +
                "  `idproducto` INT NOT NULL,\n" +
                "  `codigo_barras` INT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `ruta_imagen` VARCHAR(45),\n" +
                "  `precio_venta` VARCHAR(45),\n" +
                "  `idproveedorFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idproducto`, `idproveedorFK`),\n" +
                "  CONSTRAINT `fk_productos_proveedores1`\n" +
                "    FOREIGN KEY (`idproveedorFK`)\n" +
                "    REFERENCES `proveedores` (`idproveedor`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Compras (\n" +
                "  `idcompra` INT NOT NULL,\n" +
                "  `codigo_barras` INT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `cantidad` VARCHAR(45),\n" +
                "  `existentes` VARCHAR(45),\n" +
                "  `precio_compra` VARCHAR(45),\n" +
                "  `unidad` VARCHAR(45),\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idcompra`, `idmiembroFK`),\n" +
                "  CONSTRAINT `fk_compras_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_compras_miembros1_idx` ON Compras (`idmiembroFK` ASC);\n");



        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Clientes (\n" +
                "  `idcliente` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45) ,\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `alias` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `descuento` INT,\n" +
                "  PRIMARY KEY (`idcliente`))");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Ventas (\n" +
                "  `idventa` INT NOT NULL,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `fecha` DATETIME,\n" +
                "  `fecha_entrega` DATETIME,\n" +
                "  `hora_entrega` TIME,\n" +
                "  `descripcion` VARCHAR(45),\n" +
                "  `tipo_cobro` VARCHAR(45),\n" +
                "  `idclienteFK` INT NOT NULL,\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventa`, `idclienteFK`, `idmiembroFK`),\n" +
                "  CONSTRAINT `fk_ventas_clientes`\n" +
                "    FOREIGN KEY (`idclienteFK`)\n" +
                "    REFERENCES Clientes (`idcliente`)\n" +
                ",  CONSTRAINT `fk_ventas_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_clientes_idx` ON Ventas (`idclienteFK` ASC)");
        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_miembros1_idx` ON Ventas (`idmiembroFK` ASC)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Descuentos (\n" +
                "  `iddescuento` INT NOT NULL,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `porcentaje` INT,\n" +
                "  PRIMARY KEY (`iddescuento`))");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Empresa (\n" +
                "  `idempresa` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idempresa`))");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Venta_detalles (\n" +
                "  `cantidad` INT,\n" +
                "  `precio` INT,\n" +
                "  `idventaFK` INT NOT NULL,\n" +
                "  `iddescuentoFK` INT NOT NULL,\n" +
                "  `idproductoFK` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventaFK`, `iddescuentoFK`, `idproductoFK`),\n" +
                "  CONSTRAINT `fk_venta_detalles_ventas1`\n" +
                "    FOREIGN KEY (`idventaFK`)\n" +
                "    REFERENCES Ventas (`idventa`)\n" +
                " , CONSTRAINT `fk_venta_detalles_descuentos1`\n" +
                "    FOREIGN KEY (`iddescuentoFK`)\n" +
                "    REFERENCES Descuentos (`iddescuento`)\n" +
                " , CONSTRAINT `fk_venta_detalles_productos1`\n" +
                "    FOREIGN KEY (`idproductoFK`)\n" +
                "    REFERENCES Productos (`idproducto`))");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_ventas1_idx` ON Venta_detalles (`idventaFK` ASC)");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_descuentos1_idx` ON Venta_detalles (`iddescuentoFK` ASC)");

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_productos1_idx` ON Venta_detalles (`idproductoFK` ASC)");
    }
}
