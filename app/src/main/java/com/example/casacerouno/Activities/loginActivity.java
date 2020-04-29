package com.example.casacerouno.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.casacerouno.APIServices.API;
import com.example.casacerouno.APIServices.Manejador;
import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Modelos.Casa;
import com.example.casacerouno.R;
import com.example.casacerouno.Utils.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serializable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginActivity extends AppCompatActivity{

    Comunicador comunicador = new Comunicador();
    private Manejador manejador = API.getApi().create(Manejador.class);
    private Casa casa;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button buttonLogin;
    private SharedPreferences preferences;
    private String vuelta, devolucion, vuelta2;
    private Boolean logueo = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                //llamado para logueo en la casa
                String dataLogin = comunicador.setDataLogin(API.APIKEY, "login", email, password);
                String datalogin64 = comunicador.codificar64(dataLogin);

                //llamado para logueo en la casa
                Call<String> base64Call = manejador.casaBase64(datalogin64);
                base64Call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String respuesta = response.body();
                        vuelta = comunicador.decodificar64(respuesta);
                        if (vuelta.equals("es valido")) {
                            saveOnSharedPreferences(email, password);

                            String casaData = comunicador.setCasaInfo(API.APIKEY, "info", "3");
                            String casaData64 = comunicador.codificar64(casaData);

                            Call<String> casa64 = manejador.casa64(casaData64);
                            casa64.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    devolucion = comunicador.decodificar64(response.body());
                                    goToMain(devolucion);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.d("Fallo->", t.getMessage());
                                }
                            });

                        } else
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Fallo64->", t.getMessage());
                    }
                });
            }
        });
    }

    private void bindUI() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        switchRemember = findViewById(R.id.switchRemember);
        buttonLogin = findViewById(R.id.buttonLogIn);
    }

    private void setCredentialsIfExist() {
        String email = Util.getUserEmailPreferences(preferences);
        String password = Util.getUserPasswordPreferences(preferences);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
        }
    }

    private boolean logIn(String email, String password) {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "email incorrecto, pruebe de vuelta", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!isValidPassword(password)) {
            Toast.makeText(this, "password incorrecto, 4 caracteres o más!", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 4;
    }


    private void saveOnSharedPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
        }
    }

    private void goToMain(String casaString) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("casa", casaString);
        //con esta linea se evita que se vuelva a la pantalla de logIn al volver de activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}


