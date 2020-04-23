package com.example.casacerouno.APIServices;

import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Habitacion;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Deserealizar implements JsonDeserializer<Casa> {


    @Override
    public Casa deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        Type casaType = new TypeToken<ArrayList<Habitacion>>() {
        }.getType();
        ArrayList<Habitacion> habitacionArrayList = gson.fromJson(json, casaType);
        Casa casa = new Casa(habitacionArrayList);

        return casa;
    }

}
