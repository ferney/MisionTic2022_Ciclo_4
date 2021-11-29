package com.example.sqlitesimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Departamento,Ciudad, Poblacion;
    Button consultar, guardar, editar, limpiar, borrar, eliminar_todo;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Departamento=(EditText) findViewById(R.id.departamento);
        Ciudad=(EditText) findViewById(R.id.ciudad);
        Poblacion=(EditText) findViewById(R.id.poblacion);

        consultar=(Button) findViewById(R.id.boton_getCiudad);
        guardar=(Button) findViewById(R.id.boton_newCiudad);
        editar=(Button) findViewById(R.id.boton_editar);
        limpiar=(Button) findViewById(R.id.boton_limpiar);
        borrar=(Button) findViewById(R.id.boton_eliminar);
        eliminar_todo=(Button) findViewById(R.id.boton_eliminarTodo);

        DB=new DBHelper(this);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Departamento.setText("");
                Ciudad.setText("");
                Poblacion.setText("");
            }
        });


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String departament, ciuda, poblacio;
                departament=Departamento.getText().toString();
                ciuda=Ciudad.getText().toString();
                poblacio=Poblacion.getText().toString();

                if(departament.equals("")||ciuda.equals("")||poblacio.equals("")){
                    Toast.makeText(MainActivity.this, "por favor, completa los campos", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkCiudad=DB.checkCiudad(ciuda);
                    if(checkCiudad){
                        Toast.makeText(MainActivity.this, "esa ciudad esta en la BD", Toast.LENGTH_SHORT).show();
                        Ciudad.setText("");
                    }else{
                        Boolean insertar=DB.insertar(departament,ciuda,poblacio);
                        if(insertar){
                        Toast.makeText(MainActivity.this, "se añadieron los datos", Toast.LENGTH_SHORT).show();
                            Departamento.setText("");
                            Ciudad.setText("");
                            Poblacion.setText("");
                        }else{
                            Toast.makeText(MainActivity.this, "error al añadir los datos", Toast.LENGTH_SHORT).show();
                            Departamento.setText("");
                            Ciudad.setText("");
                            Poblacion.setText("");
                        }

                    }
                }
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DB.getCiudad(Ciudad,Departamento,Poblacion);
                Toast.makeText(MainActivity.this, "consultado", Toast.LENGTH_SHORT).show();

            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DB.eliminarCiudad(Departamento,Ciudad,Poblacion);
                Toast.makeText(MainActivity.this, "eliminada ciudad", Toast.LENGTH_SHORT).show();
            }
        });

        eliminar_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DB.borrarTodo(Departamento,Ciudad,Poblacion);
                Toast.makeText(MainActivity.this, "borrado total", Toast.LENGTH_SHORT).show();

            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DB.editar(Departamento,Ciudad,Poblacion);
                Toast.makeText(MainActivity.this, "datos actualizados", Toast.LENGTH_SHORT).show();
            }
        });

    }
}