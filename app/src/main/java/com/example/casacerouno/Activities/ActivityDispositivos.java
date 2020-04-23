package com.example.casacerouno.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Dispositivos;
import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.R;

import java.util.List;

public class ActivityDispositivos extends AppCompatActivity {

    Casa casa;
    Habitacion habitacion;
    Comunicador comunicador = new Comunicador();
    List<Dispositivos> dispositivosList;
    Dispositivos dispositivos;
    int posicion;
    String casaString;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        //se recupera el String que contiene la casa
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           casaString = bundle.getString("casa");
           casa = comunicador.deserialize(casaString);
           posicion = bundle.getInt("numeroHabitacion");
           habitacion = casa.getHabitaciones().get(posicion);
        }

        dispositivosList = habitacion.getDispositivos();

        textView = findViewById(R.id.textViewDispositivo);
        textView.setText(habitacion.getNombre());

    }
}
