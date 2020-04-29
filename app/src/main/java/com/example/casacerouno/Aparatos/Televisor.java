package com.example.casacerouno.Aparatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.casacerouno.R;

public class Televisor extends AppCompatActivity implements View.OnClickListener {

    public ImageView btn_input;
    public ImageView btn_power;
    public ImageView btn_mute;
    public ImageView btn_anterior;

    public Button btn_ch0;
    public Button btn_ch1;
    public Button btn_ch2;
    public Button btn_ch3;
    public Button btn_ch4;
    public Button btn_ch5;
    public Button btn_ch6;
    public Button btn_ch7;
    public Button btn_ch8;
    public Button btn_ch9;
    public Button btn_botonOk;

    public ImageButton btn_volumeUp;
    public ImageButton btn_volumeDw;
    public ImageButton btn_canalUp;
    public ImageButton btn_canalDw;

    public ImageButton btn_flechaUp;
    public ImageButton btn_flechaDw;
    public ImageButton btn_flechaRight;
    public ImageButton btn_flechaLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_televisor);

        btn_input = findViewById(R.id.input_tv);
        btn_input.setOnClickListener(this);

        btn_power = findViewById(R.id.onOff_tv);
        btn_power.setOnClickListener(this);

        btn_mute = findViewById(R.id.mute);
        btn_mute.setOnClickListener(this);

        btn_anterior = findViewById(R.id.can_ant);
        btn_anterior.setOnClickListener(this);

        btn_ch0 = findViewById(R.id.canal0);
        btn_ch0.setOnClickListener(this);

        btn_ch1 = findViewById(R.id.canal1);
        btn_ch1.setOnClickListener(this);

        btn_ch2 = findViewById(R.id.canal2);
        btn_ch2.setOnClickListener(this);

        btn_ch3 = findViewById(R.id.canal3);
        btn_ch3.setOnClickListener(this);

        btn_ch4 = findViewById(R.id.canal4);
        btn_ch4.setOnClickListener(this);

        btn_ch5 =findViewById(R.id.canal5);
        btn_ch5.setOnClickListener(this);

        btn_ch6 = findViewById(R.id.canal6);
        btn_ch6.setOnClickListener(this);

        btn_ch7 = findViewById(R.id.canal7);
        btn_ch7.setOnClickListener(this);

        btn_ch8 = findViewById(R.id.canal8);
        btn_ch8.setOnClickListener(this);

        btn_ch9 = findViewById(R.id.canal9);
        btn_ch9.setOnClickListener(this);

        btn_volumeUp = findViewById(R.id.volumeUp);
        btn_volumeUp.setOnClickListener(this);

        btn_volumeDw = findViewById(R.id.volumeDw);
        btn_volumeDw.setOnClickListener(this);

        btn_canalUp = findViewById(R.id.canalUp);
        btn_canalUp.setOnClickListener(this);

        btn_canalDw = findViewById(R.id.canalDw);
        btn_canalDw.setOnClickListener(this);

        btn_flechaUp = findViewById(R.id.flechaUp);
        btn_flechaUp.setOnClickListener(this);

        btn_flechaDw = findViewById(R.id.flechaDw);
        btn_flechaDw.setOnClickListener(this);

        btn_flechaLeft = findViewById(R.id.flechaLeft);
        btn_flechaLeft.setOnClickListener(this);

        btn_flechaRight = findViewById(R.id.flechaRight);
        btn_flechaRight.setOnClickListener(this);

        btn_botonOk = findViewById(R.id.botonOk);
        btn_botonOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i("-----------------------", "BOTON: "+v.getTag());
        //ambiente.recibeBotones(dev, "A", String.valueOf(v.getTag()));
//        conex.send( dev,"A", String.valueOf(v.getTag()));
        Toast.makeText(getApplicationContext(),"Boton"+v.getTag(),Toast.LENGTH_SHORT).show();


    }
}
