package com.example.matt.tarea1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Matias FRANCI on 08/08/2014.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper {
    //tabla USUARIOS
    private String QRY_CREATE_TABLE_USUARIOS = "CREATE TABLE usuarios(usuario TEXT NOT NULL PRIMARY KEY," +
            "nombre TEXT NOT NULL," +
            "password TEXT NOT NULL)";
    private String QRY_DROP_TABLE_USUARIOS = "DROP TABLE IF EXISTS usuarios";

    //tabla TAREAS
    private String QRY_CREATE_TABLE_TAREAS = "CREATE TABLE tareas(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "usuario_id TEXT NOT NULL," +
            "nombre TEXT NOT NULL," +
            "descripcion TEXT NOT NULL," +
            "fecha TEXT NOT NULL," +
            "hora TEXT NOT NULL)";
    private String QRY_DROP_TABLE_TAREAS = "DROP TABLE IF EXISTS tareas";

    public MySqlOpenHelper(Context context) {
        super(context, "agenda.db", null, 3);
        //la base de datos se crea en la carpeta "DATABASE".
        Log.d("MySqlOpenHelper", "Constructor-> crea databse");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MySqlOpenHelper->onCreate", "Creando tabla USUARIOS");
        db.execSQL(QRY_CREATE_TABLE_USUARIOS);
        Log.d("MySqlOpenHelper->onCreate", "Creando tabla TAREAS");
        db.execSQL(QRY_CREATE_TABLE_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MySqlOpenHelper->onUpgrade", "Drop tabla USUARIOS");
        db.execSQL(QRY_DROP_TABLE_USUARIOS);
        Log.d("MySqlOpenHelper->onUpgrade", "Drop tabla TAREAS");
        db.execSQL(QRY_DROP_TABLE_TAREAS);
        onCreate(db);
    }
}
