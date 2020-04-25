package com.example.casacerouno.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.casacerouno.APIServices.Adaptador;
import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.Modelos.Habitacion;
import com.example.casacerouno.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    List<Habitacion> habitacionList;
    Casa casa;
    String casaVuelta;
    Comunicador comunicador = new Comunicador();


    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuetra clase adaptador 'MyAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se recupera el String que contiene la casa
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           casaVuelta = bundle.getString("casa");
           casa = comunicador.deserialize(casaVuelta);
        }

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        habitacionList = casa.getHabitaciones();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
                Toast.makeText(MainActivity.this, "Habitación: "+habitacion.getNombre(), Toast.LENGTH_SHORT).show();
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
}


