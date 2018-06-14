package com.example.ricardosernam.puntodeventa.Inventario;

public class Inventario_class {  ///clase para obtener productos para cobrar
    public String nombre;
    public double existente;
    public String tipo;

    public Inventario_class(String nombre, Double existente, String tipo) {   ///se manda desde el arrayProductos
        this.nombre = nombre;
        this.existente = existente;
        this.tipo=tipo;
    }
    public String getNombre() {
        return nombre;
    }
    public double getExistente() {
        return existente;
    }
    public String getTipo() {
        return tipo;
    }
}
