package com.example.casacerouno.Modelos;

import com.google.gson.annotations.SerializedName;


public class Proyecto {

    @SerializedName("NombreProyecto")
    private String NombreProyecto ;
    @SerializedName("idProyecto")
    private String idProyecto ;
    @SerializedName("NombreUsuario")
    private String NombreUsuario ;
    @SerializedName("idUsuario")
    private String idUsuario ;

    public Proyecto() {
    }
    public Proyecto(String NombreProyecto,
                    String idProyecto, String NombreUsuario,
                    String idUsuario) {
        this.idProyecto=idProyecto;
        this.NombreProyecto=NombreProyecto;
        this.NombreUsuario=NombreUsuario;
        this.idUsuario=idUsuario;

    }

    public String getNombreProyecto() {
        return NombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        NombreProyecto = nombreProyecto;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
