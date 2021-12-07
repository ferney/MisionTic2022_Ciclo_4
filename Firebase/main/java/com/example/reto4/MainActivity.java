package com.example.reto4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Persona> listPersona= new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    ListViewPersonasAdapter mListViewPersonasAdapter;
    LinearLayout mLinearLayout;

    EditText inputNombre,inputTelefono;
    Button btnCancelar;
    ListView listViewPersonas;

    Persona personaSeleccionada;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    inputNombre=findViewById(R.id.inputNombre);
    inputTelefono=findViewById(R.id.inputTelefono);
    btnCancelar=findViewById(R.id.btnCancelar);

    listViewPersonas=findViewById(R.id.listViewPersonas);
    mLinearLayout=findViewById(R.id.linearLayoutEditar);

    listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick( AdapterView<?> adapterView,  View view, int i,  long l) {
personaSeleccionada=(Persona) adapterView.getItemAtPosition(i);
inputNombre.setText(personaSeleccionada.getNombre());
inputTelefono.setText(personaSeleccionada.getTelefono());
//hacer visible el linear layour
            mLinearLayout.setVisibility(View.VISIBLE);
        }
    });
btnCancelar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mLinearLayout.setVisibility(View.VISIBLE);
        personaSeleccionada=null;
    }
});
    iniciarFirebase();
    listarPersonas();

    }

    private void listarPersonas() {
        mDatabaseReference.child("Personas").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for(DataSnapshot objSnaptshot:snapshot.getChildren()){
                    Persona p= objSnaptshot.getValue(Persona.class);
                    listPersona.add(p);
                }
                mListViewPersonasAdapter= new ListViewPersonasAdapter(MainActivity.this,listPersona);
                arrayAdapterPersona=new ArrayAdapter<Persona>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        listPersona
                );
                listViewPersonas.setAdapter(mListViewPersonasAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate(R.menu.crud_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String nombres=inputNombre.getText().toString();
        String telefonos=inputTelefono.getText().toString();

        switch (item.getItemId()){
            case R.id.menu_agregar:insertar();break;
            case R.id.menu_guardar:
                if(personaSeleccionada != null){
                    if(validarInputs()==false){
                        Persona p = new Persona();
                        p.setIdPersona(personaSeleccionada.getIdPersona());
                        p.setNombre(nombres);
                        p.setTelefono(telefonos);
                        p.setFecharegistro(personaSeleccionada.getFecharegistro());
                        p.setTimestamp(personaSeleccionada.getTimestamp());
                        mDatabaseReference.child("Personas").child(p.getIdPersona()).setValue(p);
                        Toast.makeText(this, "Actualizado Correctamente", Toast.LENGTH_LONG).show();
                        mLinearLayout.setVisibility(View.VISIBLE);
                        personaSeleccionada = null;
                    }
                }else{
                    Toast.makeText(this, "Seleccione una Persona", Toast.LENGTH_LONG).show();

                }
                ;break;
            case R.id.menu_eliminar:
                if(personaSeleccionada!=null){
                    Persona p2 = new Persona();
                    p2.setIdPersona(personaSeleccionada.getIdPersona());
                    mDatabaseReference.child("Personas").child(p2.getIdPersona()).removeValue();
                    mLinearLayout.setVisibility(View.VISIBLE);
                    personaSeleccionada = null;
                    Toast.makeText(this, "Eliminado Correctamente", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Seleccione una Persona para eliminar", Toast.LENGTH_LONG).show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validarInputs() {
        String nombres=inputNombre.getText().toString();
        String telefonos=inputTelefono.getText().toString();
        if(nombres.isEmpty() || nombres.length()<3){
            showError(inputNombre,"menor a 3 letras");
            return true;
        }else if(telefonos.isEmpty() || telefonos.length()<9){
            showError(inputTelefono,"menor a 9 numeros");
            return true;
        }else{
            return false;
        }
    }

    private void insertar() {
        AlertDialog.Builder builder=new AlertDialog.Builder(
                MainActivity.this
        );
        View view=getLayoutInflater().inflate(R.layout.insertar,null);
        Button btnInsertar=(Button) view.findViewById(R.id.btnInsertar);
        final EditText inputNombre=(EditText) view.findViewById(R.id.inputNombre);
        final EditText inputTelefono=(EditText) view.findViewById(R.id.inputTelefono);
        builder.setView(view);
        final AlertDialog dialog =builder.create();
        dialog.show();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                String nombres=inputNombre.getText().toString();
                String telefonos=inputTelefono.getText().toString();
                if(nombres.isEmpty() || nombres.length()<3){
                    showError(inputNombre,"menor a 3 letras");
                }else if(telefonos.isEmpty() || telefonos.length()<9){
                    showError(inputTelefono,"menor a 9 numeros");
                }else{
                    Persona p= new Persona();
                    p.setIdPersona(UUID.randomUUID().toString());
                    p.setNombre(nombres);
                    p.setTelefono(telefonos);
                    p.setFecharegistro(getFechaNormal((Long) getFechaMilisegundos()));
                    p.setTimestamp((Long) getFechaMilisegundos()*-1);
                    mDatabaseReference.child("personas").child(p.getIdPersona()).setValue(p);
                    Toast.makeText(MainActivity.this,"registrado",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
    }

    private String getFechaNormal( long fechaMilisegundos) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha=simpleDateFormat.format(fechaMilisegundos);
        return fecha;
    }

    private Object getFechaMilisegundos() {
        Calendar calendar=Calendar.getInstance();
        long tiempo=calendar.getTimeInMillis();
        return tiempo;
    }

    private void showError(EditText input,String s) {
        input.requestFocus();
        input.setError(s);
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
    }

}





