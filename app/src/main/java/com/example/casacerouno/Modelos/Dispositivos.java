package com.example.casacerouno.Modelos;

import com.google.gson.annotations.SerializedName;

public class Dispositivos {

    @SerializedName("nombre")
    private String nombre;
    @SerializedName("posicion")
    private String posicion;
    @SerializedName("tipo")
    private String tipo;
    @SerializedName("status")
    private String status;

    public Dispositivos() {
        String nombre;
        String posicion;
        String tipo;
        String status;
    }

    public Dispositivos(String nombre, String posicion, String tipo, String status) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.tipo = tipo;
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
