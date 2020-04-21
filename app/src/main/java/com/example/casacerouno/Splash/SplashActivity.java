package com.example.casacerouno.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casacerouno.Activities.MainActivity;
import com.example.casacerouno.Activities.loginActivity;
import com.example.casacerouno.Utils.Util;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this, loginActivity.class);
        Intent intentMain = new Intent(this, MainActivity.class);

        if (!TextUtils.isEmpty(Util.getUserEmailPreferences(preferences)) &&
                !TextUtils.isEmpty(Util.getUserPasswordPreferences(preferences))) {
            startActivity(intentMain);
        } else {
            startActivity(intentLogin);
        }
        finish();
    }
}
