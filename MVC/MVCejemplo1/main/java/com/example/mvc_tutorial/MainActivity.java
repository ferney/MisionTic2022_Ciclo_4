package com.example.mvc_tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvc_tutorial.Controller.ILoginController;
import com.example.mvc_tutorial.Controller.LoginController;
import com.example.mvc_tutorial.View.ILoginView;

public class MainActivity extends AppCompatActivity implements ILoginView {

    EditText email,password;
    AppCompatButton login;
    ILoginController iLoginController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iLoginController = new LoginController(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iLoginController.onLogin(email.getText().toString(),password.getText().toString());
            }
        });
    }

    @Override
    public void OnLoginSuccess(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}