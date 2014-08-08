package com.example.matt.tarea1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.matt.tarea1.domain.Tarea;
import com.example.matt.tarea1.domain.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 05/07/2014.
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

        //insertar con ContentValue
        ContentValues valores = new ContentValues();
        valores.put("id", tarea.getId());
        valores.put("nombre", tarea.getNombre());
        valores.put("descripcion", tarea.getDescripcion());
        valores.put("fecha", tarea.getFecha());
        valores.put("hora", tarea.getHora());
        db.insert("tareas", null, valores);
    }

    public void update(Tarea tarea) {
        Log.d(getClass().toString(), "update()");
        String strSQL = "UPDATE tareas" +
                " SET nombre='" + tarea.getNombre() + "'" +
                ",descripcion='" + tarea.getDescripcion() + "'" +
                ",fecha='" + tarea.getFecha() + "'" +
                ",hora='" + tarea.getHora() + "'" +
                " WHERE id='" + tarea.getId() + "'";
        db.execSQL(strSQL);
    }

    private String[] columnas = {"id", "nombre", "descripcion", "fecha", "hora"};

    public List<Tarea> getAll() {
        List<Tarea> tareas = new ArrayList<Tarea>();
        Cursor cursor = db.query("tareas", columnas, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tareas.add(new Tarea(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            cursor.moveToNext();
        }
        cursor.close();

        Log.d(getClass().toString(), "getAll()");
        return tareas;
    }

    public Tarea get(String argTareaId) {
        Tarea tarea = null;

        String selectQuery = "SELECT * FROM tareas WHERE usuario = '" + argTareaId + "'";
        Log.d(getClass().toString(), "Query->" + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if(cursor.getCount()>0){
                cursor.moveToFirst();

                tarea = new Tarea(cursor.getInt(cursor.getColumnIndex("id"))
                        , cursor.getString(cursor.getColumnIndex("nombre"))
                        , cursor.getString(cursor.getColumnIndex("descripcion"))
                        , cursor.getString(cursor.getColumnIndex("fecha"))
                        , cursor.getString(cursor.getColumnIndex("hora")));
            }
            cursor.close();
        }

        Log.d(getClass().toString(), "get()");
        return tarea;
    }

    public void delete(int id) {

    }
}
