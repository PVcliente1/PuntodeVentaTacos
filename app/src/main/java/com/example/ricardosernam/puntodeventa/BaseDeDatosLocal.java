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


        //Creamos la tabla de turnos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Turnos (\n" +
                "  `idturno` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `entrada` DATETIME ,\n" +
                "  `salida` DATETIME ,\n" +
                "  `tipo_turno` VARCHAR(45))");

        //Creamos la tabla de puestos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Puestos (\n" +
                "  `idpuesto` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre_puesto` VARCHAR(45),\n" +
                "  `vender` TINYINT ,\n" +
                "  `comprar` TINYINT ,\n" +
                "  `configurar_miembro` TINYINT ,\n" +
                "  `supervisor` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idpuesto`, `supervisor`),\n" +
                "  CONSTRAINT `fk_puestos_puestos1`\n" +
                "    FOREIGN KEY (`supervisor`)\n" +
                "    REFERENCES Puestos (`idpuesto`)\n" +
                "    ON DELETE NO ACTION)");

        //Creamos el index de Puestos para hacer la relación con la columna supervisor

        sqLiteDatabase.execSQL("CREATE INDEX `fk_puestos_puestos1_idx` ON Puestos (`supervisor` ASC);");
        //Creación de la tabla Miembros

        sqLiteDatabase.execSQL("CREATE TABLE Miembros (\n" +
                "  `idmiembro` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                " `telefono` varchar(45),\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `contrasena` VARCHAR(45),\n" +
                "  `idturno` INTEGER ,\n" +
                "  `idpuesto` INTEGER ,\n" +
                "  `foto` VARCHAR(45),\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idmiembro`, `idturno`, `idpuesto`),\n" +
                "  CONSTRAINT `fk_miembros_turnos1`\n" +
                "    FOREIGN KEY (`idturno`)\n" +
                "    REFERENCES Turnos (`idturno`)\n" +
                "    ON DELETE NO ACTION , \n" +
                "  CONSTRAINT `fk_miembros_puestos1`\n" +
                "    FOREIGN KEY (`idpuesto`)\n" +
                "    REFERENCES Puestos (`idpuesto`)\n" +
                "    ON DELETE NO ACTION);");

        //Creación el INDEX para la relación de la tabla de miembros con la tabla de puestos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_puestos1_idx` ON Miembros (`idpuesto` ASC)");

        //Creación el INDEX para la relación de la tabla de miembros con la tabla de turnos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_turnos1_idx` ON Miembros(`idturno` ASC)");

        //Creación de la tabla proveedores

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Proveedores (\n" +
                "  `idproveedor` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `contacto` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `nombre_empresa` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idproveedor`))");

        //Creación de la tabla Unidades

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Unidades (\n" +
                "  `idunidad` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre_unidad` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idunidad`))");

        //Creamos la tabla de productos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Productos (\n" +
                "  `idproducto` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `codigo_barras` INT ,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `ruta_imagen` VARCHAR(45),\n" +
                "  `precio_venta` VARCHAR(45),\n" +
                "  `idproveedorFK` INT NOT NULL,\n" +
                "  `cantidad` INT,\n" +
                "  `existentes` INT,\n" +
                "  `precio_compra` INT,\n" +
                "  `idunidad` INT NOT NULL,\n" +
                "  `comercializable` TINYINT,\n" +
                "  PRIMARY KEY (`idproducto`, `idproveedorFK`, `idunidad`),\n" +
                "  CONSTRAINT `fk_productos_proveedores1`\n" +
                "    FOREIGN KEY (`idproveedorFK`)\n" +
                "    REFERENCES Proveedores (`idproveedor`)\n" +
                "    ON DELETE NO ACTION, \n" +
                "  CONSTRAINT `fk_productos_unidad1`\n" +
                "    FOREIGN KEY (`idunidad`)\n" +
                "    REFERENCES Unidades(`idunidad`)\n" +
                "    ON DELETE NO ACTION)");

        //Creamos el index para relacionar productos con proveedores

        sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_proveedores1_idx` ON Productos (`idproveedorFK` ASC)");

        //Creamos el index para relacionar productos con unidades

        sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_unidad1_idx` ON Productos (`idunidad` ASC)");

        //Creación de la tabla clientes

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Clientes (\n" +
                "  `idcliente` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `alias` VARCHAR(45),\n" +
                "  `telefono` INT ,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idcliente`))");

        //Creación de la tabla cobros

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Cobros (\n" +
                "  `idcobro` INT PRIMARY KEY AUTOINCREMENT,\n" +
                "  `nombre_cobro` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idcobro`))");

        //Creación de tabla ventas

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Ventas (\n" +
                "  `idventa` ,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `fecha` DATETIME ,\n" +
                "  `fecha_entrega` DATETIME ,\n" +
                "  `hora_entrega` DATETIME ,\n" +
                "  `descripcion` VARCHAR(45) ,\n" +
                "  `tipo_cobro` VARCHAR(45),\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  `idclienteFK` INT NOT NULL,\n" +
                "  `idcobro` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventa`, `idmiembroFK`, `idclienteFK`, `idcobro`),\n" +
                "  CONSTRAINT `fk_ventas_clientes`\n" +
                "    FOREIGN KEY (`idclienteFK`)\n" +
                "    REFERENCES Clientes (`idcliente`)\n" +
                "    ON DELETE NO ACTION, \n" +
                "  CONSTRAINT `fk_ventas_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`)\n" +
                "    ON DELETE NO ACTION , \n" +
                "  CONSTRAINT `fk_ventas_cobros1`\n" +
                "    FOREIGN KEY (`idcobro`)\n" +
                "    REFERENCES Cobros (`idcobro`)\n" +
                "    ON DELETE NO ACTION)");

        //Creación de index para relacionar ventas con clientes

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_clientes_idx` ON Ventas (`idclienteFK` ASC)");

        //Creación de index para relacionar ventas con miembros

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_miembros1_idx` ON Ventas (`idmiembroFK` ASC)");

        //Creación de index para relacionar ventas con cobros

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_cobros1_idx` ON Ventas (`idcobro` ASC)");

        //Creación de tabla datos_empresa

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS datos_empresa (\n" +
                "  `idempresa` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  `horario` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idempresa`))");

        //Creación de tabla descuentos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Descuentos(\n" +
                "  `iddescuento` INT NOT NULL,\n" +
                "  `tipo_descuento` VARCHAR(45),\n" +
                "  `porentaje` INT,\n" +
                "  PRIMARY KEY (`iddescuento`))");

        //Creación de tabla venta_detalles

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS venta_detalles (\n" +
                "  `cantidad` INT ,\n" +
                "  `precio` INT ,\n" +
                "  `idventaFK` INT NOT NULL,\n" +
                "  `idproductoFK` INT NOT NULL,\n" +
                "  `iddescuentos` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventaFK`, `idproductoFK`, `iddescuentos`),\n" +
                "  CONSTRAINT `fk_venta_detalles_ventas1`\n" +
                "    FOREIGN KEY (`idventaFK`)\n" +
                "    REFERENCES Ventas (`idventa`)\n" +
                "    ON DELETE NO ACTION,\n" +
                "  CONSTRAINT `fk_venta_detalles_productos1`\n" +
                "    FOREIGN KEY (`idproductoFK`)\n" +
                "    REFERENCES Productos (`idproducto`)\n" +
                "    ON DELETE NO ACTION , \n" +
                "  CONSTRAINT `fk_venta_detalles_descuentos1`\n" +
                "    FOREIGN KEY (`iddescuentos`)\n" +
                "    REFERENCES Descuentos (`iddescuento`)\n" +
                "    ON DELETE NO ACTION)");

        //Creación de index para la relación de venta_Detalles con ventas

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_ventas1_idx` ON venta_detalles (`idventaFK` ASC)");

        //Creación de index para la relación de venta_Detalles con productos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_productos1_idx` ON venta_detalles(`idproductoFK` ASC)");

        //Creación de index para la relación de venta_Detalles con Descuentos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_descuentos1_idx` ON venta_detalles(`iddescuentos` ASC)");


    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
  /*

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Puestos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Cobros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Turnos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Miembros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Clientes");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Ventas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Venta_detalles");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Descuentos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Proveedores");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Miembros");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Productos");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Empresa");

        //Creamos la tabla de turnos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Turnos (\n" +
                "  `idturno` INT NOT NULL,\n" +
                "  `entrada` DATETIME ,\n" +
                "  `salida` DATETIME ,\n" +
                "  `tipo_turno` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idturno`))\n");

        //Creamos la tabla de puestos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Puestos (\n" +
                "  `idpuesto` INT NOT NULL,\n" +
                "  `nombre_puesto` VARCHAR(45),\n" +
                "  `vender` TINYINT ,\n" +
                "  `comprar` TINYINT ,\n" +
                "  `configurar_miembro` TINYINT ,\n" +
                "  `supervisor` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idpuesto`, `supervisor`),\n" +
                "  CONSTRAINT `fk_puestos_puestos1`\n" +
                "    FOREIGN KEY (`supervisor`)\n" +
                "    REFERENCES Puestos (`idpuesto`)\n" +
                "    ON DELETE NO ACTION)");

        //Creamos el index de Puestos para hacer la relación con la columna supervisor

        sqLiteDatabase.execSQL("CREATE INDEX `fk_puestos_puestos1_idx` ON Puestos (`supervisor` ASC);");

        //Creación de la tabla Miembros

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Miembros (\n" +
                "  `idmiembro` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45) ,\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `teléfono` INT,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `contrasena` VARCHAR(45),\n" +
                "  `usuario` VARCHAR(45),\n" +
                "  `turnoFK` VARCHAR(45),\n" +
                "  `idturno` INT NOT NULL,\n" +
                "  `idpuesto` INT NOT NULL,\n" +
                "  `foto` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idmiembro`, `idturno`, `idpuesto`),\n" +
                "  CONSTRAINT `fk_miembros_turnos1`\n" +
                "    FOREIGN KEY (`idturno`)\n" +
                "    REFERENCES Turnos (`idturno`)\n" +
                "    ON DELETE NO ACTION,\n" +
                "  CONSTRAINT `fk_miembros_puestos1`\n" +
                "    FOREIGN KEY (`idpuesto`)\n" +
                "    REFERENCES Puestos (`idpuesto`)\n" +
                "    ON DELETE NO ACTION)");

        //Creación el INDEX para la relación de la tabla de miembros con la tabla de puestos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_puestos1_idx` ON Miembros (`idpuesto` ASC)");

        //Creación el INDEX para la relación de la tabla de miembros con la tabla de turnos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_miembros_turnos1_idx` ON Miembros(`idturno` ASC)");

        //Creación de la tabla proveedores

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Proveedores (\n" +
                "  `idproveedor` INT NOT NULL,\n" +
                "  `contacto` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `nombre_empresa` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idproveedor`))");

        //Creación de la tabla Unidades

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Unidades (\n" +
                "  `idunidad` INT NOT NULL,\n" +
                "  `nombre_unidad` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idunidad`))");

        //Creamos la tabla de productos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Productos (\n" +
                "  `idproducto` INT NOT NULL,\n" +
                "  `codigo_barras` INT ,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `ruta_imagen` VARCHAR(45),\n" +
                "  `precio_venta` VARCHAR(45),\n" +
                "  `idproveedorFK` INT NOT NULL,\n" +
                "  `cantidad` INT,\n" +
                "  `existentes` INT,\n" +
                "  `precio_compra` INT,\n" +
                "  `idunidad` INT NOT NULL,\n" +
                "  `comercializable` TINYINT,\n" +
                "  PRIMARY KEY (`idproducto`, `idproveedorFK`, `idunidad`),\n" +
                "  CONSTRAINT `fk_productos_proveedores1`\n" +
                "    FOREIGN KEY (`idproveedorFK`)\n" +
                "    REFERENCES Proveedores (`idproveedor`)\n" +
                "    ON DELETE NO ACTION, \n" +
                "  CONSTRAINT `fk_productos_unidad1`\n" +
                "    FOREIGN KEY (`idunidad`)\n" +
                "    REFERENCES Unidades(`idunidad`)\n" +
                "    ON DELETE NO ACTION)");

        //Creamos el index para relacionar productos con proveedores

        sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_proveedores1_idx` ON Productos (`idproveedorFK` ASC)");

        //Creamos el index para relacionar productos con unidades

        sqLiteDatabase.execSQL("CREATE INDEX `fk_productos_unidad1_idx` ON Productos (`idunidad` ASC)");

        //Creación de la tabla clientes

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Clientes (\n" +
                "  `idcliente` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `apellido` VARCHAR(45),\n" +
                "  `alias` VARCHAR(45),\n" +
                "  `telefono` INT ,\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idcliente`))");

        //Creación de la tabla cobros

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Cobros (\n" +
                "  `idcobro` INT NOT NULL,\n" +
                "  `nombre_cobro` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idcobro`))");

        //Creación de tabla ventas

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Ventas (\n" +
                "  `idventa` INT NOT NULL,\n" +
                "  `tipo` VARCHAR(45),\n" +
                "  `fecha` DATETIME ,\n" +
                "  `fecha_entrega` DATETIME ,\n" +
                "  `hora_entrega` DATETIME ,\n" +
                "  `descripcion` VARCHAR(45) ,\n" +
                "  `tipo_cobro` VARCHAR(45),\n" +
                "  `idmiembroFK` INT NOT NULL,\n" +
                "  `idclienteFK` INT NOT NULL,\n" +
                "  `idcobro` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventa`, `idmiembroFK`, `idclienteFK`, `idcobro`),\n" +
                "  CONSTRAINT `fk_ventas_clientes`\n" +
                "    FOREIGN KEY (`idclienteFK`)\n" +
                "    REFERENCES Clientes (`idcliente`)\n" +
                "    ON DELETE NO ACTION, \n" +
                "  CONSTRAINT `fk_ventas_miembros1`\n" +
                "    FOREIGN KEY (`idmiembroFK`)\n" +
                "    REFERENCES Miembros (`idmiembro`)\n" +
                "    ON DELETE NO ACTION , \n" +
                "  CONSTRAINT `fk_ventas_cobros1`\n" +
                "    FOREIGN KEY (`idcobro`)\n" +
                "    REFERENCES Cobros (`idcobro`)\n" +
                "    ON DELETE NO ACTION)");

        //Creación de index para relacionar ventas con clientes

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_clientes_idx` ON Ventas (`idclienteFK` ASC)");

        //Creación de index para relacionar ventas con miembros

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_miembros1_idx` ON Ventas (`idmiembroFK` ASC)");

        //Creación de index para relacionar ventas con cobros

        sqLiteDatabase.execSQL("CREATE INDEX `fk_ventas_cobros1_idx` ON Ventas (`idcobro` ASC)");

        //Creación de tabla datos_empresa

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS datos_empresa (\n" +
                "  `idempresa` INT NOT NULL,\n" +
                "  `nombre` VARCHAR(45),\n" +
                "  `encargado` VARCHAR(45),\n" +
                "  `direccion` VARCHAR(45),\n" +
                "  `telefono` INT,\n" +
                "  `correo` VARCHAR(45),\n" +
                "  `pagina` VARCHAR(45),\n" +
                "  `logo` VARCHAR(45),\n" +
                "  `horario` VARCHAR(45),\n" +
                "  PRIMARY KEY (`idempresa`))");

        //Creación de tabla descuentos

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Descuentos(\n" +
                "  `iddescuento` INT NOT NULL,\n" +
                "  `tipo_descuento` VARCHAR(45),\n" +
                "  `porentaje` INT,\n" +
                "  PRIMARY KEY (`iddescuento`))");

        //Creación de tabla venta_detalles

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS venta_detalles (\n" +
                "  `cantidad` INT ,\n" +
                "  `precio` INT ,\n" +
                "  `idventaFK` INT NOT NULL,\n" +
                "  `idproductoFK` INT NOT NULL,\n" +
                "  `iddescuentos` INT NOT NULL,\n" +
                "  PRIMARY KEY (`idventaFK`, `idproductoFK`, `iddescuentos`),\n" +
                "  CONSTRAINT `fk_venta_detalles_ventas1`\n" +
                "    FOREIGN KEY (`idventaFK`)\n" +
                "    REFERENCES Ventas (`idventa`)\n" +
                "    ON DELETE NO ACTION,\n" +
                "  CONSTRAINT `fk_venta_detalles_productos1`\n" +
                "    FOREIGN KEY (`idproductoFK`)\n" +
                "    REFERENCES Productos (`idproducto`)\n" +
                "    ON DELETE NO ACTION , \n" +
                "  CONSTRAINT `fk_venta_detalles_descuentos1`\n" +
                "    FOREIGN KEY (`iddescuentos`)\n" +
                "    REFERENCES Descuentos (`iddescuento`)\n" +
                "    ON DELETE NO ACTION)");

        //Creación de index para la relación de venta_Detalles con ventas

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_ventas1_idx` ON venta_detalles (`idventaFK` ASC)");

        //Creación de index para la relación de venta_Detalles con productos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_productos1_idx` ON venta_detalles(`idproductoFK` ASC)");

        //Creación de index para la relación de venta_Detalles con Descuentos

        sqLiteDatabase.execSQL("CREATE INDEX `fk_venta_detalles_descuentos1_idx` ON venta_detalles(`iddescuentos` ASC)");
    */


    }

}
