package com.example.fireejercicio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.google.firebase.*;
import com.google.firebase.database.*;


import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Persona> listPersona=new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;
    ListViewPersonasAdapter mListViewPersonasAdapter;
    LinearLayout mLayout;

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
        btnCancelar=findViewById(R.id.btnCanccelar);

        listViewPersonas=findViewById(R.id.listViewPersonas);
        mLayout=findViewById(R.id.linearLayoutEditar);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
                personaSeleccionada=(Persona) adapterView.getItemAtPosition(i);
                inputNombre.setText(personaSeleccionada.getNombre());
                inputTelefono.setText(personaSeleccionada.getTelefono());
                mLayout.setVisibility(View.VISIBLE);

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                mLayout.setVisibility(View.GONE);
                personaSeleccionada=null;

            }
        });

        iniciarFirebase();
        listarPersonas();

    }



    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String nombre=inputNombre.getText().toString();
        String telefono=inputTelefono.getText().toString();

        switch (item.getItemId()){
            case R.id.menu_agregar: insertar();break;
            case R.id.menu_guardar: actualizar(nombre,telefono);break;
            case R.id.menu_eliminar: eliminar() ;break;

        }


        return false;
    }

    private void eliminar() {
        if (personaSeleccionada != null) {
            Persona persona=new Persona();
            persona.setId(personaSeleccionada.getId());
            mDatabaseReference.child("Personas").child(persona.getId()).removeValue();
            Toast.makeText(MainActivity.this,"datos eliminados",Toast.LENGTH_SHORT).show();
            mLayout.setVisibility(View.GONE);
            personaSeleccionada=null;
        }else{
            Toast.makeText(MainActivity.this,"seleccione un registro a borrar",Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizar(String nombre, String telefono) {
        if (personaSeleccionada != null) {
            Persona persona=new Persona();
            persona.setId(personaSeleccionada.getId());
            persona.setNombre(nombre);
            persona.setTelefono(telefono);
            mDatabaseReference.child("Personas").child(persona.getId()).setValue(persona);
            Toast.makeText(MainActivity.this,"datos actualizados",Toast.LENGTH_SHORT).show();
            mLayout.setVisibility(View.GONE);
            personaSeleccionada=null;
        }else{
            Toast.makeText(MainActivity.this,"seleccione un registro",Toast.LENGTH_SHORT).show();
        }

    }

    private void insertar() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

        View view=getLayoutInflater().inflate(R.layout.insertar,null);
        Button button=(Button) view.findViewById(R.id.btnInsertar);
        EditText nombre=(EditText) view.findViewById(R.id.inputNombre);
        EditText telefono=(EditText) view.findViewById(R.id.inputTelefono);

        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombres=nombre.getText().toString();
                String telefonos=telefono.getText().toString();

                if(nombres.isEmpty() || nombres.length()<3){
                    showError(inputNombre,"nombre menor a 3 letras");
                }else if(telefonos.isEmpty() || telefonos.length()<10){
                    showError(inputTelefono,"telefono menor a 10 digitos");
                }else{
                    Persona p=new Persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombres);
                    p.setTelefono(telefonos);

                    mDatabaseReference.child("Personas").child(p.getId()).setValue(p);
                    Toast.makeText(MainActivity.this,"datos registrados",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
    }

    private void showError( EditText input, String s) {
        input.requestFocus();
        input.setError(s);
    }


    private void listarPersonas() {
        mDatabaseReference.child("Personas").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listPersona.clear();
                        for (DataSnapshot snap:snapshot.getChildren()){
                            Persona p=snap.getValue(Persona.class);
                            listPersona.add(p);
                        }
                        mListViewPersonasAdapter=new ListViewPersonasAdapter(MainActivity.this,listPersona);
                        /*arrayAdapterPersona=new ArrayAdapter<Persona>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                listPersona
                        );*/

                        listViewPersonas.setAdapter(mListViewPersonasAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.crud_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
}