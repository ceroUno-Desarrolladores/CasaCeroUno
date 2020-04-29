package com.example.casacerouno.Enlace;

import android.util.Base64;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Habitacion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Comunicador {

    //metodo para tomar los datos para las solicitudes de LOGIN
    public String setDataLogin(String key, String usr, String nombreUsuario, String password) {
        String mensaje = "key=" + key + "&cmd=" + usr + "&p1=" + nombreUsuario + "&p2=" + password;
        return mensaje;
    }

    //metodo para tomar los datos para las solicitudes de DATOS DE LA CASA
    public String setCasaInfo(String key, String cmd, String p1) {
        return "key=" + key + "&cmd=" + cmd + "&p1=" + p1;
    }

    //metodo para codificar en BASE64
    public String codificar64(String data) {
        byte[] data64 = data.getBytes();
        String base64 = Base64.encodeToString(data64, Base64.DEFAULT);
        return base64;
    }

    //metodo para decodificar en BASE64
    public String decodificar64(String data) {
        byte[] byte64 = Base64.decode(data, Base64.DEFAULT);
        String vuelta = new String(byte64);
        return vuelta.trim();
    }

    //metodo para deserealizar el string con la info de la casa
    public Casa deserialize(String respose) {
        Gson gson = new Gson();
        JsonElement json;
        json = gson.fromJson(respose, JsonElement.class);
        Type casaType = new TypeToken<ArrayList<Habitacion>>() {
        }.getType();
        ArrayList<Habitacion> habitacionArrayList = gson.fromJson(json, casaType);
        Casa casa = new Casa(habitacionArrayList);
        return casa;
    }
}


