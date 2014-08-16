package com.example.matt.tarea1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matt.tarea1.domain.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias FRANCI on 08/08/2014.
 */
public class UsuarioDAO {
   MySqlOpenHelper mySqlOpenHelper;
   private SQLiteDatabase db;

   public UsuarioDAO(Context context) {
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

   public void insert(Usuario usuario) {
      Log.d(getClass().toString(), "insert()");

      //conecto BD
      open();

      //db.execSQL("INSERT INTO usuarios(usuario,nombre,password)
      // values('" + usuario.getUsuario() + "','" + usuario.getNombre() + "','" + usuario.getPassword() + "')");

      //insertar con ContentValue
      ContentValues valores = new ContentValues();
      valores.put(MySqlOpenHelper.USUARIOS_KEY_USUARIO, usuario.getUsuario());
      valores.put(MySqlOpenHelper.USUARIOS_KEY_NOMBRE, usuario.getNombre());
      valores.put(MySqlOpenHelper.USUARIOS_KEY_PASSWORD, usuario.getPassword());
      db.insert(MySqlOpenHelper.TABLA_USUARIOS, null, valores);

      //desconecto BD
      close();
   }

   public void update(Usuario usuario) {
      Log.d(getClass().toString(), "update()");

      //conecto BD
      open();

      String strSQL = "UPDATE " + MySqlOpenHelper.TABLA_USUARIOS +
            " SET " + MySqlOpenHelper.USUARIOS_KEY_NOMBRE + "='" + usuario.getNombre() + "'" +
            "," + MySqlOpenHelper.USUARIOS_KEY_PASSWORD + "='" + usuario.getPassword() + "'" +
            " WHERE " + MySqlOpenHelper.USUARIOS_KEY_NOMBRE + "='" + usuario.getUsuario() + "'";
      db.execSQL(strSQL);

      //desconecto BD
      close();
   }

   private String[] columnas = {MySqlOpenHelper.USUARIOS_KEY_USUARIO, MySqlOpenHelper.USUARIOS_KEY_NOMBRE, MySqlOpenHelper.USUARIOS_KEY_PASSWORD};

   public List<Usuario> getAll() {
      //conecto BD
      open();

      List<Usuario> usuarios = new ArrayList<Usuario>();
      Cursor cursor = db.query(MySqlOpenHelper.TABLA_USUARIOS, columnas, null, null, null, null, null);
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
         usuarios.add(new Usuario(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
         cursor.moveToNext();
      }
      cursor.close();

      Log.d(getClass().toString(), "getAll()");

      //desconecto BD
      close();

      return usuarios;
   }

   public Usuario get(String argUsuario) {
      //conecto BD
      open();

      Usuario usuario = null;
        /*
        String[] tableColumns = new String[]{
                "usuario,nombre,password"
        };
        String whereClause = "usuario = ?";
        String[] whereArgs = new String[]{argUsuario};
        Cursor cursor = db.query("usuarios", tableColumns, whereClause, whereArgs, null, null, null);
        */
      String selectQuery = "SELECT *" +
                           " FROM " + MySqlOpenHelper.TABLA_USUARIOS +
                           " WHERE "+MySqlOpenHelper.USUARIOS_KEY_USUARIO+" = '" + argUsuario + "'";
      Log.d(getClass().toString(), "Query->" + selectQuery);
      Cursor cursor = db.rawQuery(selectQuery, null);

      if (cursor != null) {
         if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            usuario = new Usuario(cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.USUARIOS_KEY_USUARIO))
                  , cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.USUARIOS_KEY_NOMBRE))
                  , cursor.getString(cursor.getColumnIndex(MySqlOpenHelper.USUARIOS_KEY_PASSWORD)));
         }
         cursor.close();
      }

      //desconecto BD
      close();

      Log.d(getClass().toString(), "get()");
      return usuario;
   }

   public void delete(int id) {

   }
}
