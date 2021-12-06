package com.example.mismapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private double a=19.434385,b=-99.141516;
    private double x1=-80.434385,x2=-69.141516;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

mIntent=new Intent(MainActivity.this,MapsActivity.class);
    }


    public void xyz(View view) {
        mIntent.putExtra("nombre","la florida");
        mIntent.putExtra("1",a);
        mIntent.putExtra("2",b);
        startActivity(mIntent);
    }

    public void xyy(View view) {
        mIntent.putExtra("nombre","xxx");
        mIntent.putExtra("1",x1);
        mIntent.putExtra("2",x2);
        startActivity(mIntent);
    }
}
