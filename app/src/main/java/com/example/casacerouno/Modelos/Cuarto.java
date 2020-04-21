package com.example.casacerouno.Modelos;

public class Cuarto extends Habitacion{

    private String nombreCuarto;
    private int poster;

    public Cuarto(String nombreCuarto, int poster) {
        this.nombreCuarto = nombreCuarto;
        this.poster = poster;
    }

    public String getNombreCuarto() {
        return nombreCuarto;
    }

    public void setNombreCuarto(String nombreCuarto) {
        this.nombreCuarto = nombreCuarto;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }
}
