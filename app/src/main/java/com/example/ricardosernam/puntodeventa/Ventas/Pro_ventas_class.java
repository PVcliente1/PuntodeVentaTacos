package com.example.ricardosernam.puntodeventa.Ventas;

public class Pro_ventas_class {  ///clase para obtener productos para cobrar
    public String codigo;
    public String nombre;
    public String precio;
    public String foto;
    public String unidad;

    public Pro_ventas_class(String codigo, String nombre, String precio, String foto, String unidad ) {   ///se manda desde el arrayProductos
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.unidad = unidad;
    }
    public Pro_ventas_class(String nombre) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
    }
    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getPrecio() {
        return precio;
    }
    public String getFoto() {
        return foto;
    }
    public String getUnidad() {
        return unidad;
    }
}
