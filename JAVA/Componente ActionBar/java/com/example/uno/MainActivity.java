package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.opcion1) {
            Toast.makeText(this,R.string.mensaje_1,Toast.LENGTH_LONG).show();
        }
        if (id==R.id.opcion2) {
            Toast.makeText(this,R.string.mensaje_1,Toast.LENGTH_LONG).show();
        }
        if (id==R.id.opcion3) {
            Toast.makeText(this,R.string.mensaje_1, Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}