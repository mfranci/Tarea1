package com.example.matt.tarea1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matt.tarea1.domain.Tarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias FRANCI on 08/08/2014.
 */
public class TareaDAO {
   MySqlOpenHelper mySqlOpenHelper;
   private SQLiteDatabase db;

   public TareaDAO(Context context) {
      mySqlOpenHelper = new MySqlOpenHelper(context);
   }

   public void open() {
      Log.d(getClass().toString(), "open");
      db = mySqlOpenHelper.getWritableDatabase(); //abrimos la BD para soportar sentencias de escritura.
   }

   public void close() {
      Log.d(getClass().toString(), "close");
      db.close();
   }

   public void insert(Tarea tarea) {
      Log.d(getClass().toString(), "insert()");

      //conecto BD
      open();

      ContentValues valores = new ContentValues();
      valores.put(MySqlOpenHelper.TAREAS_KEY_USUARIO_ID, tarea.getUsuario_id());
      valores.put(MySqlOpenHelper.TAREAS_KEY_NOMBRE, tarea.getNombre());
      valores.put(MySqlOpenHelper.TAREAS_KEY_DESCRIPCION, tarea.getDescripcion());
      valores.put(MySqlOpenHelper.TAREAS_KEY_FECHA, tarea.getFecha());
      valores.put(MySqlOpenHelper.TAREAS_KEY_HORA, tarea.getHora());
      valores.put(MySqlOpenHelper.TAREAS_KEY_PRIORIDAD, tarea.getPrioridad());
      db.insert(MySqlOpenHelper.TABLA_TAREAS, null, valores);

      //conecto BD
      open();
   }

   public void update(Tarea tarea) {
      Log.d(getClass().toString(), "update()");
      //conecto BD
      open();

      String strSQL = "UPDATE " + MySqlOpenHelper.TABLA_TAREAS +
            " SET " + MySqlOpenHelper.TAREAS_KEY_USUARIO_ID + "='" + tarea.getUsuario_id() + "'" +
            "," + MySqlOpenHelper.TAREAS_KEY_NOMBRE + "='" + tarea.getNombre() + "'" +
            "," + MySqlOpenHelper.TAREAS_KEY_DESCRIPCION + "='" + tarea.getDescripcion() + "'" +
            "," + MySqlOpenHelper.TAREAS_KEY_FECHA + "='" + tarea.getFecha() + "'" +
            "," + MySqlOpenHelper.TAREAS_KEY_HORA + "='" + tarea.getHora() + "'" +
            "," + MySqlOpenHelper.TAREAS_KEY_PRIORIDAD + "='" + tarea.getPrioridad() + "'" +
            " WHERE " + MySqlOpenHelper.TAREAS_KEY_ID + "='" + tarea.getId() + "'";
      db.execSQL(strSQL);

      //conecto BD
      open();
   }

   private String[] columnas = {
                           MySqlOpenHelper.TAREAS_KEY_ID,
                           MySqlOpenHelper.TAREAS_KEY_USUARIO_ID,
                           MySqlOpenHelper.TAREAS_KEY_NOMBRE,
                           MySqlOpenHelper.TAREAS_KEY_DESCRIPCION,
                           MySqlOpenHelper.TAREAS_KEY_FECHA,
                           MySqlOpenHelper.TAREAS_KEY_HORA,
                           MySqlOpenHelper.TAREAS_KEY_PRIORIDAD
                   };

   public List<Tarea> getAll(String usuario_id) {
      //conecto BD
      open();

      List<Tarea> tareas = new ArrayList<Tarea>();
      String qryWHERE = MySqlOpenHelper.TAREAS_KEY_USUARIO_ID + "='" + usuario_id + "'";
      Cursor cursor = db.query(MySqlOpenHelper.TABLA_TAREAS, columnas, qryWHERE, null, null, null, null);
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
         tareas.add(
               new Tarea(
                     cursor.getInt(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_ID)),
                     cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_USUARIO_ID)),
                     cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_NOMBRE)),
                     cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_DESCRIPCION)),
                     cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_FECHA)),
                     cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_HORA)),
                     cursor.getInt(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_PRIORIDAD))
               )
         );
         cursor.moveToNext();
      }
      cursor.close();

      //conecto BD
      open();

      Log.d(getClass().toString(), "getAll()");
      return tareas;
   }

   public Tarea get(String argTareaId) {
      //conecto BD
      open();

      Tarea tarea = null;

      String selectQuery = "SELECT *" +
                           " FROM " + MySqlOpenHelper.TABLA_TAREAS +
                           " WHERE " + MySqlOpenHelper.TAREAS_KEY_ID + " = '" + argTareaId + "'";
      Log.d(getClass().toString(), "Query->" + selectQuery);

      Cursor cursor = db.rawQuery(selectQuery, null);
      if (cursor != null) {
         if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            tarea = new Tarea(cursor.getInt(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_ID)),
                  cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_USUARIO_ID)),
                  cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_NOMBRE)),
                  cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_DESCRIPCION)),
                  cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_FECHA)),
                  cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_HORA)),
                  cursor.getInt(cursor.getColumnIndex(MySqlOpenHelper.TAREAS_KEY_PRIORIDAD)));
         }
         cursor.close();
      }

      //conecto BD
      open();

      Log.d(getClass().toString(), "get()");
      return tarea;
   }

   public void delete(int id) {
       Log.d(getClass().toString(), "delete()");
       //conecto BD
       open();

       String strSQL = "DELETE FROM " + MySqlOpenHelper.TABLA_TAREAS +
               " WHERE " + MySqlOpenHelper.TAREAS_KEY_ID + "='" + id + "'";
       db.execSQL(strSQL);

       //conecto BD
       open();
   }
}
