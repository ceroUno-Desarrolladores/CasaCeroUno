package com.example.casacerouno.APIServices;

import android.telephony.TelephonyManager;

import com.example.casacerouno.Modelos.Casa;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class API {

    public static final String BASE_URL = "http://www.cerouno.com.ar/";
    private static Retrofit retrofit = null;
    public static String APIKEY = "0000000";

    public static Retrofit getApi() {
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder().setLenient();
            builder.registerTypeAdapter(Casa.class, new Deserealizar());
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //este Converter evita los errores por malformacion de JSON en el response
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }
    public static void setApiKey(String keyApi){
        APIKEY=keyApi;
    }

}
