package com.example.casacerouno.Enlace.conex;

import org.json.JSONException;

public interface onPostExecute {
    void recibirTexto(String txt, int estado) throws JSONException;
}
