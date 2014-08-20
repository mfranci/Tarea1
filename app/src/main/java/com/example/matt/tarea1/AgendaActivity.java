package com.example.matt.tarea1;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.matt.tarea1.adapter.TareaAdapter;
import com.example.matt.tarea1.dao.TareaDAO;
import com.example.matt.tarea1.domain.Tarea;

import java.util.List;

public class AgendaActivity extends ListActivity {
    TareaDAO tareaDAO;
    Tarea tarea = null;
    List<Tarea> tareas;
    String sUsuario;
    Tarea tareaSeleccionada;

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
        if (tareas == null || tareas.size() == 0) {
            //cargo temporalmente varias tareas.
            for (int i = 0; i < 5; i++) {
                tareaDAO.insert(new Tarea(i, sUsuario, "Nombre tarea " + i, "Descripcion", "01/08/2014","01:00", R.drawable.ic_prioridad_1));
            }
            Toast.makeText(this, "Carga de tareas de ejemplo finalizado", Toast.LENGTH_LONG).show();
            Log.d(getClass().toString(), "Carga de tareas de ejemplo finalizado");

            //vuelvo a leer
            tareas = tareaDAO.getAll(sUsuario);
        }

        if (tareas != null && tareas.size() > 0) {
            Toast.makeText(this, "Mostrando listado de tareas", Toast.LENGTH_LONG).show();
            Log.d(getClass().toString(), "Mostrando listado de tareas");

            TareaAdapter adapter = new TareaAdapter(this, R.layout.item_tarea, tareas);

            //por si cambió el adpter
            adapter.notifyDataSetChanged(); //nuevo
            getListView().setAdapter(adapter);

            registerForContextMenu(getListView()); //con getListView traigo el listview existente en el layout
        } else {
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
            Intent intent = new Intent(this, AgendaTareaActivity.class);
            intent.putExtra("INTENT_KEY_USUARIO", sUsuario);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual_tarea, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo(); //para identificar los datos del menú seleccionado
        int valorSeleccionado = (int) info.id; //posición del elemento
        tareaSeleccionada = tareas.get(valorSeleccionado);

        switch (item.getItemId()) {
            case R.id.action_editar_tarea:
                Toast.makeText(this, "Editando item:" + tareaSeleccionada.getId(), Toast.LENGTH_LONG).show();

                //llamo a editar tarea
                Intent intent = new Intent(this, AgendaTareaActivity.class);
                intent.putExtra("TAREA_KEY_TAREA", tareaSeleccionada.getId());
                intent.putExtra("TAREA_KEY_USUARIO_ID", tareaSeleccionada.getUsuario_id());
                intent.putExtra("TAREA_KEY_NOMBRE", tareaSeleccionada.getNombre());
                intent.putExtra("TAREA_KEY_DESCRIPCION", tareaSeleccionada.getDescripcion());
                intent.putExtra("TAREA_KEY_FECHA", tareaSeleccionada.getFecha());
                intent.putExtra("TAREA_KEY_HORA", tareaSeleccionada.getHora());
                intent.putExtra("TAREA_KEY_PRIORIDAD", tareaSeleccionada.getPrioridad());
                startActivity(intent);
                return true;

            case R.id.action_eliminar_tarea:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirmar...");
                alertDialog.setMessage("¿Está seguro de eliminar?");
                alertDialog.setIcon(R.drawable.ic_action_delete);

                alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tareaDAO.delete(tareaSeleccionada.getId());
                        onResume(); //llamo para releer la lista
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();

                return true;
        }
        return super.onContextItemSelected(item);
    }
}
