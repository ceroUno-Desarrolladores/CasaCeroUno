package com.example.casacerouno.Modelos;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Casa {

    @SerializedName("habitaciones")
    private List<Habitacion> habitaciones;

    public Casa() {
    }

    public Casa(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }


}
