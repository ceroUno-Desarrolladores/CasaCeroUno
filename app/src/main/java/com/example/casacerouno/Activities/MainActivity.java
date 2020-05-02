package com.example.casacerouno.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casacerouno.APIServices.API;
import com.example.casacerouno.APIServices.Adaptador;
import com.example.casacerouno.APIServices.Manejador;
import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Enlace.conex.Conexion;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.Modelos.Proyecto;
import com.example.casacerouno.R;

import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Manejador manejador = API.getApi().create(Manejador.class);
    private String devolucion;
    private static String TAG ="--MainActivity:";
    List<Habitacion> habitacionList;
    List <Proyecto> Proyecto;
    Casa casa;
    String casaVuelta;
    Comunicador comunicador = new Comunicador();


    Timer timer = new Timer();


    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuetra clase adaptador 'MyAdapter'
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if(bundle != null){
            String NroProyecto = bundle.getString("proyecto");
            if (NroProyecto !=null) {
                obtenerCasa(NroProyecto);
            }else{
                String NombreProyecto =bundle.getString("proyectoNombre");
                ElegirProyecto(NombreProyecto);
            }
        }else{
            // no hay proyecto seleccionado..
            // seleccionar proyecto.
            seleccionarProyecto();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                logOut();
                return true;

            case R.id.menu_forget_logout:
                removeSharedPreferences();
                logOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {

        Intent intent = new Intent(this, loginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences() {
        preferences.edit().clear().apply();

    }
    private void seleccionarProyecto(){
        /* funcion para listar y seleccionar los proyectos */
        Call<String> casa64 = manejador.casa64(
                comunicador.codificar64(
                        comunicador.setListaProyectos(API.APIKEY)));

        casa64.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                devolucion = comunicador.decodificar64(response.body());
                ListarProyectos(devolucion);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Fallo->", t.getMessage());
            }
        });
    }
    private void obtenerCasa(String NroProyecto) {

        Call<String> casa64 = manejador.casa64(
                comunicador.codificar64(
                        comunicador.setCasaInfo(API.APIKEY, "info", NroProyecto)));

        casa64.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                devolucion = comunicador.decodificar64(response.body());
                ConstructCasa(devolucion);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Fallo->", t.getMessage());
            }
        });
    }
    public void ListarProyectos(String ListaProyectos){
        setContentView(R.layout.activity_main);
        Proyecto = comunicador.deserealizeProyectos(ListaProyectos);
        mRecyclerView = findViewById(R.id.recyclerView);
        gridLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new Adaptador(Proyecto, R.layout.habitaciones_item,
                new Adaptador.OnItemClickListenerProyectos() {
            @Override
            public void onItemClick(Proyecto proyecto, int position) {
                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                intent.putExtra("proyecto", proyecto.getIdProyecto());
                startActivity(intent);
            }
        }
        ,false);
        // Enlazamos el layout manager y adaptor directamente al recycler view
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void ConstructCasa(String StringCasa){

        setContentView(R.layout.activity_main);
        casa = comunicador.deserialize(StringCasa);
        this.casaVuelta=StringCasa;
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        habitacionList = casa.getHabitaciones();

        mRecyclerView = findViewById(R.id.recyclerView);
        //mLayoutManager = new LinearLayoutManager(this);

        //contexto = this, y numero de columnas = 2
        gridLayoutManager = new GridLayoutManager(this, 2);

        // 1 linea de dif. acabo de build y error en interfaz xk pojo. esto y luego xml con card, textview image

        // Implementamos nuestro OnItemClickListener propio, sobreescribiendo el método que nosotros
        // definimos en el adaptador, y recibiendo los parámetros que necesitamos
        mAdapter = new Adaptador(habitacionList, R.layout.habitaciones_item, new Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(Habitacion habitacion, int position) {
                final String casaString = casaVuelta;
                Toast.makeText(MainActivity.this,
                        "Habitación: "+habitacion.getNombre(),
                        //"CasaString: "+casaString,
                        Toast.LENGTH_SHORT
                        //Toast.LENGTH_LONG
                ).show();
                Intent intent = new Intent(MainActivity.this, ActivityDispositivos.class);
                intent.putExtra("casa", casaString);
                intent.putExtra("numeroHabitacion", position);
                startActivity(intent);

            }

        });


        // Lo usamos en caso de que sepamos que el layout no va a cambiar de tamaño, mejorando la performance
        mRecyclerView.setHasFixedSize(true);
        // Añade un efecto por defecto, si le pasamos null lo desactivamos por completo
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Enlazamos el layout manager y adaptor directamente al recycler view
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void CargarProyectos(String NombreProyecto){
        /* funcion para listar y seleccionar los proyectos */
        Call<String> casa64 = manejador.casa64(
                comunicador.codificar64(
                        comunicador.setListaProyectos(API.APIKEY)));

        casa64.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                devolucion = comunicador.decodificar64(response.body());
                CargarProyectos(devolucion,NombreProyecto);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Fallo->", t.getMessage());
            }
        });
    }
    private void CargarProyectos(String JSonProyectos,String NombreProyecto){
        Proyecto = comunicador.deserealizeProyectos(JSonProyectos);
        ElegirProyecto(NombreProyecto);
    }

    public void ElegirProyecto(String NombreProyecto){
        /*
        funcion que permite elegir un proyecto de los mostrados.

         */

        if (Proyecto != null ){
            // estan cargados los proyectos.
            Proyecto p=null;
            for (Proyecto n : this.Proyecto){
                if (n.getNombreProyecto().equals(NombreProyecto)){
                    // es el proyecto elegido.
                    p=n;
                    break;
                }
            }
            if ( p != null ){
                Log.i(TAG,"el proyecto autoelegido es:"+p.getIdProyecto());
                obtenerCasa( p.getIdProyecto().toString() );
            }
        }else{
            CargarProyectos(NombreProyecto);
        }
    }
}


