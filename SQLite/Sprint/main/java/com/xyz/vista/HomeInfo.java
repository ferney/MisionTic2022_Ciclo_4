package com.example.vista;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.vista.databinding.ActivityHomeInfoBinding;

public class HomeInfo extends AppCompatActivity {

    ActivityHomeInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_info);
        getSupportActionBar().hide();


    }

    public void redirectionToLogin(View view) {
        Intent newView = new Intent(this, login.class);
        startActivity(newView);
    }

    public void redirectionRegistro(View view) {
        Intent newView = new Intent(this, Registro.class);
        startActivity(newView);
    }
}



