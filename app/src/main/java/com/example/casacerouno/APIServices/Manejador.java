package com.example.casacerouno.APIServices;

import com.example.casacerouno.Modelos.Casa;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Manejador {

    //metodo para solicitar LOGIN
    @GET("app.php")
    //Call<Casa> getCasa(@Query("url") String name);
    Call<String> getLogIn(@Query("key") String APIKey, @Query("cmd") String usr,
                          @Query("p1") String nombreUsuario, @Query("p2") String password);

    //metodo para solicitar INFO DE LA CASA
    @GET("app.php")
    Call<Casa> CasaInfo(@Query("key") String APIKey, @Query("cmd") String info, @Query("p1") String numeroCasa);

    @GET("app.php")
    Call<String> CasaInfoString(@Query("key") String APIKey, @Query("cmd") String info, @Query("p1") String numeroCasa);


    //metodo para solicitud en BASE64 con respuesta en objeto STRING
    @GET("app.php")
    Call<String> casaBase64(@Query("h") String base64);

    //metodo para solicitud en BASE64 con respuesta en objeto CASA
    @GET("app.php")
    Call<String> casa64(@Query("h") String base64);


/*
    //se obtiene la casa completa
    Call<Casa> CasaInfo(@Query("key") String APIKey, @Query("cmd") String info, @Query("p1") String numeroCasa);

    //para validar el usuario y password - devuelve TRUE o FALSE
    Call<String> getLogIn(@Query("key") String APIKey, @Query("cmd") String usr,
                           @Query("p1") String nombreUsuario, @Query("p2") String password));

    //para actualizar dispositivos de habitacion
    Call<String> ChkHabitacion(@Query("key") String APIKey, @Query("p1") String numeroCasa, @Query("p2") String habitacion))
*/
}
