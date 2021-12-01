package com.example.Realm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
     * Generamos las variables globales para lograr acceder desde cualquier parte del codigo
     * las inicializamos practicamente
     * */
    Realm realm;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * El metodo getDefaultInstance() crea una instancia de Realm con un valor predeterminado
         * por defecto que viene desde la misma libreria.
         * */
        realm=Realm.getDefaultInstance();
        /*realizamos la referencia a los componentes de la interfaz
        que desarrollamos en activity_main.xml
        */
        Button insert=findViewById(R.id.insertar);
        Button update=findViewById(R.id.actualizar);
        Button read=findViewById(R.id.leer);
        Button delete=findViewById(R.id.eliminar);
        output=findViewById(R.id.mostrar);
         /*
        generamos los listeners por cada boton al hacer clic en estos
         */
        insert.setOnClickListener(this);
        update.setOnClickListener(this);
        read.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.insertar){
            ShowInsertDialog();
        }
        if(v.getId()==R.id.actualizar){
            showUpdateDialog();
            }
        if(v.getId()==R.id.eliminar){

            ShowDeleteDialog();
        }
        if(v.getId()==R.id.leer){
            showData();

        }
    }
    /*
     * si queremos actualizar los datos guardados dentro de la base de datos
     * */
    private void showUpdateDialog() {
        /*
         * Llamamos a la vista eliminar_dialog.xml en modo ventana emergente.
         *
         * La clase LayoutInflater se utiliza para instanciar el archivo XML de presentación
         * en sus objetos de vista correspondientes. En otras palabras, toma como entrada un
         * archivo XML y genera los objetos que se vana ver de éste mimso.
         * en pocas palabras LayoutInflater se utiliza para crear un
         * nuevo objeto View (o Layout ) de uno de los diseños xml.
         * */
        final AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        /*
         * capturamos los datos que ingresamos enla ventana emergente
         * */
        final EditText data_id=view.findViewById(R.id.data_id);
        Button delete=view.findViewById(R.id.delete);
        final AlertDialog alertDialog=al.show();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                /*
                 * verificamos si el dato a actualizar es el correcto siempre y cuando exista
                 * vs el dato de ingreso por la ventana
                 * */
                long id= Long.parseLong(data_id.getText().toString());
                final DataModel dataModel=realm.where(DataModel.class).equalTo("id",id).findFirst();
                ShowUpdateDialog(dataModel);
            }
        });
    }
    /*
     * si queremos eliminar los datos guardados dentro de la base de datos
     * */
    private void ShowDeleteDialog() {
        /*
         * Llamamos a la vista eliminar_dialog.xml en modo ventana emergente.
         *
         * La clase LayoutInflater se utiliza para instanciar el archivo XML de presentación
         * en sus objetos de vista correspondientes. En otras palabras, toma como entrada un
         * archivo XML y genera los objetos que se vana ver de éste mimso.
         * en pocas palabras LayoutInflater se utiliza para crear un
         * nuevo objeto View (o Layout ) de uno de los diseños xml.
         * */
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete_dialog,null);
        al.setView(view);
        /*
         * capturamos los datos que ingresamos en la ventana emergente
         * */
        final EditText data_id=view.findViewById(R.id.data_id);
        Button delete=view.findViewById(R.id.delete);
        final AlertDialog alertDialog=al.show();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * verificamos si el dato a eliminar es el correcto siempre y cuando exista
                 * vs el dato de ingreso por la ventana
                 * */
                long id= Long.parseLong(data_id.getText().toString());
                final DataModel dataModel=realm.where(DataModel.class).equalTo("id",id).findFirst();
                realm.executeTransaction(new Realm.Transaction() {
                    /*
                     * El cambio de datos (guardado, eliminado, actualizado) de Realm sólo
                     * se puede hacer desde dentro de una transacción
                     * */
                    @Override
                    public void execute(Realm realm) {
                        alertDialog.dismiss();
                        dataModel.deleteFromRealm();
                    }
                });
            }
        });
    }
    /**
     * si queremos insertar los datos dentro de la base de datos
     * */
    private void ShowInsertDialog() {
        /*
         * Llamamos a la vista input_dialog.xml en modo ventana emergente.
         *
         * La clase LayoutInflater se utiliza para instanciar el archivo XML de presentación
         * en sus objetos de vista correspondientes. En otras palabras, toma como entrada un
         * archivo XML y genera los objetos que se vana ver de éste mimso.
         * en pocas palabras LayoutInflater se utiliza para crear un
         * nuevo objeto View (o Layout ) de uno de los diseños xml.
         * */
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.data_input_dialog,null);
        al.setView(view);
        /*
         * capturamos los datos que ingresamos enla ventana emergente
         * */
        final EditText name=view.findViewById(R.id.name);
        final EditText age=view.findViewById(R.id.age);
        final Spinner gender=view.findViewById(R.id.gender);
        Button save=view.findViewById(R.id.save);
        final AlertDialog alertDialog=al.show();
        /*
         * cuando hacemos clic en el boton de la ventana emergente, haga esto
         * */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * con dismiss() se cierran las ventans emergentes de android
                 * */
                alertDialog.dismiss();
                //instanciamos un nuevo datamodel
                final DataModel dataModel=new DataModel();
                /*
                 * verificamos si el id que se esta ingresando a la base de datos existe,
                 * si es asi, sume 1 al id que exixte para generar uno nuevo, si no exixte, ese
                 * es el id inicial
                 * */
                Number current_id=realm.where(DataModel.class).max("id");
                long nextId;
                if(current_id==null){
                    nextId=1;
                }
                else{
                    nextId=current_id.intValue()+1;
                }
                /*
                 * enviamos los datos para ser guardados
                 * */
                dataModel.setId(nextId);
                dataModel.setAge(Integer.parseInt(age.getText().toString()));
                dataModel.setName(name.getText().toString());
                dataModel.setGender(gender.getSelectedItem().toString());
                /*
                 * El cambio de datos (guardado, eliminado, actualizado) de Realm sólo
                 * se puede hacer desde dentro de una transacción
                 * */
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealm(dataModel);
                    }
                });
            }
        });

    }
    /*
     * si queremos actualizar los datos guardados dentro de la base de datos
     * */
    private void ShowUpdateDialog(final DataModel dataModel) {
        /*
         * Llamamos a la vista eliminar_dialog.xml en modo ventana emergente.
         *
         * La clase LayoutInflater se utiliza para instanciar el archivo XML de presentación
         * en sus objetos de vista correspondientes. En otras palabras, toma como entrada un
         * archivo XML y genera los objetos que se vana ver de éste mimso.
         * en pocas palabras LayoutInflater se utiliza para crear un
         * nuevo objeto View (o Layout ) de uno de los diseños xml.
         * */
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.data_input_dialog,null);
        al.setView(view);
        /*
         * capturamos los datos que ingresamos enla ventana emergente
         * */
        final EditText name=view.findViewById(R.id.name);
        final EditText age=view.findViewById(R.id.age);
        final Spinner gender=view.findViewById(R.id.gender);
        Button save=view.findViewById(R.id.save);
        final AlertDialog alertDialog=al.show();
        /*
         * enviamos los datos para ser actualizados
         * */
        name.setText(dataModel.getName());
        age.setText(""+dataModel.getAge());
        if(dataModel.getGender().equalsIgnoreCase("Masculino")) {
            gender.setSelection(0);
        }
        else{
            gender.setSelection(1);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                /*
                 * El cambio de datos (guardado, eliminado, actualizado) de Realm sólo
                 * se puede hacer desde dentro de una transacción
                 * */
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        dataModel.setAge(Integer.parseInt(age.getText().toString()));
                        dataModel.setName(name.getText().toString());
                        dataModel.setGender(gender.getSelectedItem().toString());
                        realm.copyToRealmOrUpdate(dataModel);
                    }
                });
            }
        });

    }

    /*
     * si queremos mostrar los datos guardados dentro de la base de datos
     * */
    private void showData(){
        /*
         * Generamos una lista con todos los archivos que se extraen desde la base de datos
         * con la clase DataModel
         * */
        List<DataModel> dataModels=realm.where(DataModel.class).findAll();
        output.setText("");
        /*
         * recorremos la variable datamodels, extraemos los archivos y los mostramos en
         * textView de l ainterfaz
         * */
        for(int i=0;i<dataModels.size();i++){
            output.append("ID : "+dataModels.get(i).getId()+" Name : "+dataModels.get(i).getName()+" Age : "+dataModels.get(i).getAge()+" Gender : "+dataModels.get(i).getGender()+" \n");
        }
    }
}
