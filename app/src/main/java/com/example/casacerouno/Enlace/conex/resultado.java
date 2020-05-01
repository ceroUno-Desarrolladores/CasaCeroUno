package com.example.casacerouno.Enlace.conex;

import org.json.JSONException;
import org.json.JSONObject;

public class resultado{
    public String staus;
    public String usuario;
    public Object respuesta;
    public int error;

    public resultado(JSONObject objSon){
        try {
            staus = objSon.getString("status");
            usuario=objSon.getString("usuario");
            respuesta=objSon.getJSONObject("respuesta");
            error=objSon.getInt("error");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}