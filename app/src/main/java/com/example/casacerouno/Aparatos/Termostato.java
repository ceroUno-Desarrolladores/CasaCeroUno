package com.example.casacerouno.Aparatos;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.casacerouno.Enlace.conex.Conexion;
import com.example.casacerouno.R;

import static com.example.casacerouno.Activities.loginActivity.conexion;

public class Termostato extends AppCompatActivity {

    private static int temperatura;

    public static int getTemperatura() {
        Log.i("getTEMPERATURA -->", String.valueOf(temperatura));
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        Termostato.temperatura = temperatura;
    }

    private TextView mostrarPorcentaje;

    public Termostato() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termostato);

        mostrarPorcentaje = findViewById(R.id.tv_porcentaje);

        // SeekBar
        SeekBar seekBar = findViewById(R.id.seekBar);
        // Valor Inicial
        seekBar.setProgress(temperatura);

        /*
        falta hacer la consulta de la temperatura en la raspi
         */
        mostrarPorcentaje.setText(seekBar.getProgress()+15+"°C");



        // Valot Final
        seekBar.setMax(30);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    //hace un llamado a la perilla cuando se arrastra
                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        mostrarPorcentaje.setText(progress+15+"ºC");
                        setTemperatura(seekBar.getProgress());
                    }
                    //hace un llamado  cuando se toca la perilla
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mostrarPorcentaje.setText(seekBar.getProgress()+15 + "ºC");
                        setTemperatura(seekBar.getProgress());
                    }
                    //hace un llamado  cuando se detiene la perilla
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mostrarPorcentaje.setText(seekBar.getProgress()+15 + "ºC");
                        setTemperatura(seekBar.getProgress());

                        conexion.send(String.valueOf(mostrarPorcentaje.getTag()),"A", String.valueOf(mostrarPorcentaje));

                        Log.i("TEMPERATURA ---------->", String.valueOf(seekBar.getProgress()));
                    }
                });


    }
}
