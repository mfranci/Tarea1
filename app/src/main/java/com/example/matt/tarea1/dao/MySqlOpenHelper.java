package com.example.matt.tarea1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Matias FRANCI on 08/08/2014.
 */
public class MySqlOpenHelper extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION = 7; // versión de DB
   public static final String DATABASE_NAME = "agenda.db";
   public static final String TABLA_USUARIOS = "usuarios";
   public static final String TABLA_TAREAS = "tareas";

   // tabla usuarios
   public static final String USUARIOS_KEY_USUARIO = "usuario";
   public static final String USUARIOS_KEY_NOMBRE = "nombre";
   public static final String USUARIOS_KEY_PASSWORD = "password";

   // tabla tarea
   public static final String TAREAS_KEY_ID = "id";
   public static final String TAREAS_KEY_USUARIO_ID = "usuario_id";
   public static final String TAREAS_KEY_NOMBRE = "nombre";
   public static final String TAREAS_KEY_DESCRIPCION = "descripcion";
   public static final String TAREAS_KEY_FECHA = "fecha";
   public static final String TAREAS_KEY_HORA = "hora";
   public static final String TAREAS_KEY_PRIORIDAD = "prioridad";

   //tabla USUARIOS
   private String QRY_CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLA_USUARIOS + "(" +
         USUARIOS_KEY_USUARIO + " TEXT NOT NULL PRIMARY KEY," +
         USUARIOS_KEY_NOMBRE + " TEXT NOT NULL," +
         USUARIOS_KEY_PASSWORD + " TEXT NOT NULL)";
   private String QRY_DROP_TABLE_USUARIOS = "DROP TABLE IF EXISTS " + TABLA_USUARIOS;

   //tabla TAREAS
   private String QRY_CREATE_TABLE_TAREAS = "CREATE TABLE " + TABLA_TAREAS + "(" +
         TAREAS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
         TAREAS_KEY_USUARIO_ID + " TEXT NOT NULL," +
         TAREAS_KEY_NOMBRE + " TEXT NOT NULL," +
         TAREAS_KEY_DESCRIPCION + " TEXT NOT NULL," +
         TAREAS_KEY_FECHA + " TEXT NOT NULL," +
         TAREAS_KEY_HORA + " TEXT NOT NULL," +
         TAREAS_KEY_PRIORIDAD + " INTEGER NOT NULL)";
   private String QRY_DROP_TABLE_TAREAS = "DROP TABLE IF EXISTS " + TABLA_TAREAS;

   public MySqlOpenHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
      //la base de datos se crea en la carpeta "DATABASE".
      Log.d(getClass().toString(), "Constructor-> crea databse");
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      Log.d(getClass().toString(), "onCreate: Creando tabla "+TABLA_USUARIOS);
      db.execSQL(QRY_CREATE_TABLE_USUARIOS);
      Log.d(getClass().toString(), "onCreate: Creando tabla "+TABLA_TAREAS);
      db.execSQL(QRY_CREATE_TABLE_TAREAS);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.d(getClass().toString(), "onCreate: Drop tabla "+TABLA_USUARIOS);
      db.execSQL(QRY_DROP_TABLE_USUARIOS);
      Log.d(getClass().toString(), "onCreate: Drop tabla "+TABLA_TAREAS);
      db.execSQL(QRY_DROP_TABLE_TAREAS);
      onCreate(db);
   }
}
