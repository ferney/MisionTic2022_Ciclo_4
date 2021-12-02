package com.example.appbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.boton);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new Home()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.home:fragment=new Home();break;
                case R.id.favoritos:fragment=new Favoritos();break;
                case R.id.search:fragment=new Search();break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).commit();
            return true;
        }
    };
}