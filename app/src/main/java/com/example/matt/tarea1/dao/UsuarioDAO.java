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
        //db.execSQL("INSERT INTO usuarios(usuario,nombre,password)
        // values('" + usuario.getUsuario() + "','" + usuario.getNombre() + "','" + usuario.getPassword() + "')");

        //insertar con ContentValue
        ContentValues valores = new ContentValues();
        valores.put("usuario", usuario.getUsuario());
        valores.put("nombre", usuario.getNombre());
        valores.put("password", usuario.getPassword());
        db.insert("usuarios", null, valores);
    }

    public void update(Usuario usuario) {
        Log.d(getClass().toString(), "update()");
        String strSQL = "UPDATE usuarios" +
                " SET nombre='" + usuario.getNombre() + "'" +
                ",password='" + usuario.getPassword() + "'" +
                " WHERE usuario='" + usuario.getUsuario() + "'";
        db.execSQL(strSQL);
    }

    private String[] columnas = {"usuario", "nombre", "password"};

    public List<Usuario> getAll() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Cursor cursor = db.query("usuarios", columnas, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usuarios.add(new Usuario(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();

        Log.d(getClass().toString(), "getAll()");
        return usuarios;
    }

    public Usuario get(String argUsuario) {
        Usuario usuario = null;
        /*
        String[] tableColumns = new String[]{
                "usuario,nombre,password"
        };
        String whereClause = "usuario = ?";
        String[] whereArgs = new String[]{argUsuario};
        Cursor cursor = db.query("usuarios", tableColumns, whereClause, whereArgs, null, null, null);
        */
        String selectQuery = "SELECT * FROM usuarios WHERE usuario = '" + argUsuario + "'";
        Log.d(getClass().toString(), "Query->" + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if(cursor.getCount()>0){
                cursor.moveToFirst();

                usuario = new Usuario(cursor.getString(cursor.getColumnIndex("usuario"))
                        , cursor.getString(cursor.getColumnIndex("nombre"))
                        , cursor.getString(cursor.getColumnIndex("password")));
            }
            cursor.close();
        }

        Log.d(getClass().toString(), "get()");
        return usuario;
    }

    public void delete(int id) {

    }
}
