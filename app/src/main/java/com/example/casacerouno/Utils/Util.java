package com.example.casacerouno.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserEmailPreferences(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getUserPasswordPreferences(SharedPreferences preferences){
        return preferences.getString("password", "");
    }

    /*public static String getApiKeyPreferences(SharedPreferences preferences){
        return preferences.getString("apiKey", "00000000");
    }*/

}
