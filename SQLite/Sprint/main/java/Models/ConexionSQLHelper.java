package Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ConexionSQLHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_USUARIO = "CREATE TABLE usuarios (id INTEGER, nombres TEXT, apellidos TEXT," +
            "sexo TEXT, direccion TEXT, correo TEXT, celular TEXT, ciudad TEXT," +
            "usuario TEXT, contrasena TEXT)";

    public ConexionSQLHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
