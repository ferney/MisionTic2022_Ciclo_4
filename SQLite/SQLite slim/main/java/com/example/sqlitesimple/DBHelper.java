package com.example.sqlitesimple;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Ciudades.db";

    public DBHelper (Context context){
        super(context, "Ciudades.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table ciudades(ciudad TEXT primary key, departamento TEXT, poblacion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists ciudades");
    }




    public Boolean checkCiudad(String ciuda) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from ciudades where ciudad = ?", new String[] {ciuda});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean insertar(String departament, String ciuda, String poblacio) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ciudad", ciuda);
        contentValues.put("departamento", departament);
        contentValues.put("poblacion", poblacio);
        long resultado = MyDB.insert("ciudades", null, contentValues);
        if (resultado==-1)
            return false;
        else
            return true;

    }

    public void getCiudad(EditText ciudad, EditText departamento, EditText poblacion) {

        String ciuda = ciudad.getText().toString();
        Cursor cursor = this.getReadableDatabase().rawQuery("select departamento,poblacion from ciudades where ciudad= ?", new String[] {ciuda});
        departamento.setText("");
        poblacion.setText("");
        ciudad.setText("");
        while (cursor.moveToNext()){
            departamento.append(cursor.getString(0));
            poblacion.append(cursor.getString(1));
            ciudad.append(ciuda);
        }
    }

    public void eliminarCiudad(EditText departamento, EditText ciudad, EditText poblacion) {
        String ciuda = ciudad.getText().toString();
        Cursor cursor = this.getWritableDatabase().rawQuery("delete from cities where city=?", new String[] {ciuda});
        departamento.setText("");
        poblacion.setText("");
        ciudad.setText("");
        while (cursor.moveToNext()){
            departamento.setText("");
            poblacion.setText("");
            ciudad.setText("");
        }
    }

    public void borrarTodo(EditText departamento, EditText ciudad, EditText poblacion) {

        String depa = departamento.getText().toString();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ciudades", "departamento=?",new String[]{depa});
        db.close();
        departamento.setText("");
        poblacion.setText("");
        ciudad.setText("");
    }

    public void editar(EditText departamento, EditText ciudad, EditText poblacion) {

        String pobla = poblacion.getText().toString();
        String ciuda = ciudad.getText().toString();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("update ciudades set poblacion=? where ciudad=?", new String[] {pobla,ciuda});
        departamento.setText("");
        poblacion.setText("");
        ciudad.setText("");
        while (cursor.moveToNext()){
            departamento.setText("");
            poblacion.setText("");
            ciudad.setText("");
        }
    }
}
