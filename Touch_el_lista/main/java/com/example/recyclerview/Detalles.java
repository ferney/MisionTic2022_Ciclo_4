package com.example.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detalles extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);


        String[] myKeys = getResources().getStringArray(R.array.secciones);

        TextView myTextView = (TextView) findViewById(R.id.my_textview);
        myTextView.setText(myKeys[position]);


    }

}