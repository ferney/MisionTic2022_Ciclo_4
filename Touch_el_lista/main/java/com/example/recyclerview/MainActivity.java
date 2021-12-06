package com.example.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_main);


                ListView listview = (ListView) findViewById(R.id.listView1);
                listview.setOnItemClickListener(this);
        }

        public void onItemClick(AdapterView<?> l, View v, int position, long id) {


                Intent intent = new Intent();
                intent.setClass(this, Detalles.class);
                intent.putExtra("position", position);

                intent.putExtra("id", id);
                startActivity(intent);
        }
}