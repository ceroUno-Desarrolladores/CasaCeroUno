package com.example.casacerouno.APIServices;

import com.example.casacerouno.Modelos.Casa;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BASE_URL = "http://www.cerouno.com.ar/";
    private static Retrofit retrofit = null;
    public static final String APIKEY = "7890";

    public static Retrofit getApi() {
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder().setLenient();
            builder.registerTypeAdapter(Casa.class, new Deserealizar());
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }
}
