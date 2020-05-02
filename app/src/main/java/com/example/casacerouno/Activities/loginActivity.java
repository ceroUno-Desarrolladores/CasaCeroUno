package com.example.casacerouno.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.casacerouno.APIServices.API;
import com.example.casacerouno.APIServices.Manejador;
import com.example.casacerouno.Enlace.Comunicador;
import com.example.casacerouno.Enlace.conex.Conexion;
import com.example.casacerouno.Enlace.conex.onPostExecute;
import com.example.casacerouno.Enlace.conex.onScanError;
import com.example.casacerouno.R;
import com.example.casacerouno.Utils.Util;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class loginActivity extends AppCompatActivity {

    Comunicador comunicador = new Comunicador();
    private Manejador manejador = API.getApi().create(Manejador.class);
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Switch switchRemember;
    private Button buttonLogin;
    private SharedPreferences preferences;
    private String vuelta;
    public static Conexion conexion;
    private static String TAG ="ActivityLogin :";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // numero unico de apikey.
        API.setApiKey(obtenerIMEI());
        autologin();
    }

    private void autologin(){
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String email = Util.getUserEmailPreferences(preferences);
        String password = Util.getUserPasswordPreferences(preferences);

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
                    // usuario validado pasar a la siguiente pantalla.
                    verificarLocal();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                    login();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Fallo64->", t.getMessage());
                login();
            }
        });
    }
    private void login(){
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
                            // usuario validado pasar a la siguiente pantalla.
                            saveOnSharedPreferences(email, password);
                            verificarLocal();
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

/*    private boolean logIn(String email, String password) {
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
    }*/

/*    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 4;
    }*/

    public void ClearPreferences(){
        preferences.edit().clear().apply();
    }
    private void saveOnSharedPreferences(String email, String password) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("casa", casaString);
        //intent.putExtra("proyecto", "3");
        //con esta linea se evita que se vuelva a la pantalla de logIn al volver de activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void goToMain(String NombreProyecto){
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("casa", casaString);
        intent.putExtra("proyectoNombre", NombreProyecto);
        //con esta linea se evita que se vuelva a la pantalla de logIn al volver de activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void verificarLocal(){
        /*
        * este metodo verifica si estamos en un hambiente local o remoto.
        * */
        // verificar si existe una raspberry localmente..
        if (conexion == null) {
            conexion = new Conexion(this);
            conexion.setUser(obtenerIMEI());
            conexion.ScanRaspberry("4net-core"
                    , 8182
                    , "_domotica._tcp"
                    , new onPostExecute() {
                        @Override
                        public void recibirTexto(String txt, int estado) throws JSONException {
                            // al encontrar la raspberry.
                            ExisteLocal(txt);
                            Log.i(TAG,"existe local"+txt);
                        }
                    }
                    , new onScanError() {
                        @Override
                        public void ejecutarFalla(String txt) {
                            // sin encontrar la raspberry....
                            NoExisteLocal(txt);
                            Log.i(TAG,"no existe local"+txt);
                        }
                    });
        }

        /*
        conexion.getStatus(new onPostExecute() {
            @Override
            public void recibirTexto(String txt, int estado) throws JSONException {

                Toast.makeText(loginActivity.this,
                        "recibido...."+txt+" //estado:"+ String.valueOf( estado )+ "--" ,
                        //"CasaString: "+casaString,
                        Toast.LENGTH_SHORT
                        //Toast.LENGTH_LONG
                ).show();
            }
        });
         */
        goToMain();
    }
    private void ExisteLocal(String txt){
        conexion.setHash(obtenerIMEI());
        conexion.setLocalRemoto(1);
        Log.i(TAG,"nombre del proyecto:"+conexion.getNombreProyecto());
        goToMain(conexion.getNombreProyecto());
    }

    private void NoExisteLocal(String txt){
        // has no valido.
        conexion.setHash("00");
        conexion.setLocalRemoto(0);

    }


    private String obtenerIMEI() {
        final TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "351351813";
            }
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }
}


