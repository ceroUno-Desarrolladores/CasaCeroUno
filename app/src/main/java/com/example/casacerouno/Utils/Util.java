package com.example.casacerouno.Utils;

import android.content.SharedPreferences;

import com.example.casacerouno.Activities.MainActivity;
import com.example.casacerouno.Modelos.Proyecto;



public class Util {


    public static String getUserEmailPreferences(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getUserPasswordPreferences(SharedPreferences preferences){
        return preferences.getString("password", "");
    }

    public static String getNombreProyectoPreferences(SharedPreferences preferences){
        return preferences.getString("nombreProyecto", "");
    }

    /*public static String getApiKeyPreferences(SharedPreferences preferences){
        return preferences.getString("apiKey", "00000000");
    }*/

}
