package com.example.matt.tarea1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matt.tarea1.adapter.TareaAdapter;
import com.example.matt.tarea1.dao.TareaDAO;
import com.example.matt.tarea1.domain.Tarea;

import java.util.List;

public class AgendaActivity extends ListActivity {
    TareaDAO tareaDAO;
    Tarea tarea= null;
    List<Tarea> tareas;
    String sUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().toString(), "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        tareaDAO = new TareaDAO(this);
        //tareaDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

        //traigo por extra el id del usuario.
        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("INTENT_KEY_USUARIO") != null) {
            sUsuario = bundle.getString("INTENT_KEY_USUARIO");
            Toast.makeText(this, "Visualizando agenda de " + sUsuario, Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this, "No existe parametro intent INTENT_KEY_USUARIO", Toast.LENGTH_LONG).show();
            Log.e(getClass().toString(), "No existe parametro intent INTENT_KEY_USUARIO");
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       Log.d(getClass().toString(), "onResume()");

       //tareas = new ArrayList<Tarea>(); //no es necesario hacer esto
       tareas = tareaDAO.getAll(sUsuario);

       //si está vacío entonces cargo
       if(tareas == null || tareas.size() == 0){
          //cargo temporalmente varias tareas.
          for (int i = 0; i < 5; i++) {
             tareaDAO.insert(new Tarea(i,sUsuario, "Nombre tarea "+i,"Descripcion", i+"/08/2014",i+":00"));
          }
          Toast.makeText(this, "Carga de tareas de ejemplo finalizado", Toast.LENGTH_LONG).show();
          Log.d(getClass().toString(), "Carga de tareas de ejemplo finalizado");

          //vuelvo a leer
          tareas = tareaDAO.getAll(sUsuario);
       }

       if (tareas != null && tareas.size() > 0 ) {
          Toast.makeText(this, "Mostrando listado de tareas", Toast.LENGTH_LONG).show();
          Log.d(getClass().toString(), "Mostrando listado de tareas");

          TareaAdapter adapter = new TareaAdapter(this, R.layout.item_tarea, tareas);

          //por si cambió el adpter
          adapter.notifyDataSetChanged(); //nuevo
          setListAdapter(adapter);
       }else{
          Log.e(getClass().toString(), "Error al intentar leer la tabla tareas");
       }

        //hacer que acceda a BD para que refresque.
    }

    @Override
    protected void onStop() {
        Log.d(getClass().toString(), "onStop()");
        //tareaDAO.close();
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
        if (id == R.id.action_tarea_new) {
            Intent intent = new Intent(this,NuevaTareaActivity.class);
            intent.putExtra("INTENT_KEY_USUARIO", sUsuario);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
