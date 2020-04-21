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
        /*List<Dispositivos> dispositivosList = new ArrayList<>();
        for (int i=0; i<dispositivosList.size(); i++){
            dispositivosList.add(new Dispositivos(
                    json.getAsJsonArray().get(i).getAsJsonObject().get("dispositivos").getAsJsonArray().get(0).getAsJsonObject().get("nombre").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("dispositivos").getAsJsonArray().get(1).getAsJsonObject().get("posicion").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("dispositivos").getAsJsonArray().get(2).getAsJsonObject().get("tipo").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("dispositivos").getAsJsonArray().get(3).getAsJsonObject().get("status").toString()
            ));
        }*/

        /*List<Habitacion> habitacionList = new ArrayList<>();
        for (int i=0; i<habitacionList.size(); i++){
            habitacionList.add(new Habitacion(
                    json.getAsJsonArray().get(i).getAsJsonObject().get("SpecificKey").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("nombre").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("id").toString(),
                    json.getAsJsonArray().get(i).getAsJsonObject().get("imagen").toString(),
                    dispositivosList
            ));
        }*/


        Type casaType = new TypeToken<ArrayList<Habitacion>>() {
        }.getType();
        ArrayList<Habitacion> habitacionArrayList = gson.fromJson(json, casaType);
        Casa casa = new Casa(habitacionArrayList);

        return casa;
    }


}
