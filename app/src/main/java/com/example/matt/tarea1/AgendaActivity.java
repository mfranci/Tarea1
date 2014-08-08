package com.example.matt.tarea1;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matt.tarea1.dao.TareaDAO;
import com.example.matt.tarea1.domain.Tarea;

import java.util.ArrayList;
import java.util.List;

public class AgendaActivity extends ListActivity {
    TareaDAO tareaDAO;
    Tarea tarea= null;
    List<Tarea> tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().toString(), "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        tareaDAO = new TareaDAO(this);
        tareaDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("INTENT_KEY_USUARIO") != null) {
            String sUsuario = bundle.getString("INTENT_KEY_USUARIO");
            Toast.makeText(this, "Visualizando agenda de " + sUsuario, Toast.LENGTH_LONG).show();

            tareas = new ArrayList<Tarea>();
            tareas = tareaDAO.getAll();

            if (tareas != null && tareas.size() > 0 ) {
                Toast.makeText(this, "Mostrando listado de tareas", Toast.LENGTH_LONG).show();
                Log.d(getClass().toString(), "Mostrando listado de tareas");

                TareaAdapter adapter = new TareaAdapter(this, R.layout.item_tarea, tareas);
                setListAdapter(adapter);
            }else{
                //cargo temporalmente varias tareas.
                for (int i = 0; i < 20; i++) {
                    tareaDAO.insert(new Tarea(i,"Nombre tarea "+i,"Descripcion", i+"/08/2014",i+":00"));
                }
                Toast.makeText(this, "Carga de tareas de ejemplo finalizado", Toast.LENGTH_LONG).show();
                Log.d(getClass().toString(), "Carga de tareas de ejemplo finalizado");
            }
        } else {
            Toast.makeText(this, "No existe parametro intent INTENT_KEY_USUARIO", Toast.LENGTH_LONG).show();
            Log.e(getClass().toString(), "No existe parametro intent INTENT_KEY_USUARIO");
            finish();
        }
    }

    @Override
    protected void onStop() {
        Log.d(getClass().toString(), "onStop()");
        tareaDAO.close();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
