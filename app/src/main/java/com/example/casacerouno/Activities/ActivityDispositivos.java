package com.example.casacerouno.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casacerouno.APIServices.AdaptadorDispositivos;
import com.example.casacerouno.Aparatos.Persiana;
import com.example.casacerouno.Aparatos.Televisor;
import com.example.casacerouno.Aparatos.Termostato;
import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Dispositivos;
import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.R;

import java.util.List;

import static com.example.casacerouno.Activities.loginActivity.conexion;

public class ActivityDispositivos extends AppCompatActivity {

    Casa casa;
    Habitacion habitacion;
    Comunicador comunicador = new Comunicador();
    int posicion;
    String casaString;
    TextView textView;



    List<Dispositivos> dispositivosList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        //se recupera el String que contiene la casa
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           casaString = bundle.getString("casa");
           casa = comunicador.deserialize(casaString);

           //se recupera la habitacion elegida
           posicion = bundle.getInt("numeroHabitacion");
           habitacion = casa.getHabitaciones().get(posicion);
        }

        dispositivosList = habitacion.getDispositivos();
        mRecyclerView = findViewById(R.id.recyclerViewDispositivos);
        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this,3);
        mAdapter = new AdaptadorDispositivos(dispositivosList, R.layout.dispositivos_item, new AdaptadorDispositivos.OnItemClickListener() {
            @Override

//ACA SE REALIZA LA ACCION CON EL ITEM CLICKEADO!!!
            public void onItemClick(Dispositivos dispositivos, int posicion) {
                //Toast.makeText(ActivityDispositivos.this,posicion+" - : "+dispositivos.getNombre(),Toast.LENGTH_SHORT ).show();
                cambioPantalla(dispositivos);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        textView = findViewById(R.id.textViewDispositivo);
        textView.setText(habitacion.getNombre());
    }

    private void cambioPantalla(Dispositivos dispositivo){
        String tipo = dispositivo.getTipo();
        switch (tipo){
            case "TV":
                Intent intent = new Intent(ActivityDispositivos.this, Televisor.class);
                intent.putExtra("nombre", nombreAparato(dispositivo));
                Log.i("CAMBIO A  ---------->", dispositivo.getNombre());
                startActivity(intent);
                break;
            case "TM":
                Intent intent2 = new Intent(ActivityDispositivos.this, Termostato.class);
                intent2.putExtra("nombre", nombreAparato(dispositivo));
                Log.i("CAMBIO A  ---------->", dispositivo.getNombre());
                startActivity(intent2);
                break;
            case "PR":
                Intent intent3 = new Intent(ActivityDispositivos.this, Persiana.class);
                intent3.putExtra("nombre", nombreAparato(dispositivo));
                Log.i("CAMBIO A  ---------->", dispositivo.getNombre());
                startActivity(intent3);
                break;
            case "GP":
                String valor = nombreAparato(dispositivo);
                if(dispositivo.getStatus().equals("on")) {
                    dispositivo.setStatus("off");
                    //Toast.makeText(this, "Luz apagada", Toast.LENGTH_SHORT).show();
                    Log.i("se apagó luz  ---->", dispositivo.getNombre());
                }else{
                    dispositivo.setStatus("on");
                    //Toast.makeText(this, "Luz prendida", Toast.LENGTH_SHORT).show();
                    Log.i("se prendió luz  >", dispositivo.getNombre());
                }
                conexion.send(nombreAparato(dispositivo),"A", "0");
                Log.i("conexion dormi", String.valueOf(conexion));
            }

        }

    public String nombreAparato(Dispositivos dispositivo){
        return dispositivo.getTipo()+habitacion.getId()+dispositivo.getPosicion();
    }
}
