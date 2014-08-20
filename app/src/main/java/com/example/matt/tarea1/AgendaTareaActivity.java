package com.example.matt.tarea1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.tarea1.dao.TareaDAO;
import com.example.matt.tarea1.domain.Tarea;

public class AgendaTareaActivity extends Activity {
    TareaDAO tareaDAO;
    Tarea tarea = null;
    int tareaId = 0;
    String tareaUsuarioId;

    TextView txtNombreTareaNew;
    TextView txtDescripcionTareaNew;
    TextView txtFechaTareaNew;
    TextView txtHoraTareaNew;
    Spinner spPrioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_tarea);

        txtNombreTareaNew = (TextView) findViewById(R.id.txtNombreTareaNew);
        txtDescripcionTareaNew = (TextView) findViewById(R.id.txtDescripcionTareaNew);
        txtFechaTareaNew = (TextView) findViewById(R.id.txtFechaTareaNew);
        txtHoraTareaNew = (TextView) findViewById(R.id.txtHoraTareaNew);

        spPrioridad = (Spinner) findViewById(R.id.spPrioridad);

        tareaDAO = new TareaDAO(this);
        //tareaDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

        //traigo por extra los datos de la tarea
        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("INTENT_KEY_USUARIO") != null) {
            tareaUsuarioId = bundle.getString("INTENT_KEY_USUARIO");
        } else if (bundle.getInt("TAREA_KEY_TAREA") != 0) {
            //cargo tarea con los parametros (extras)
            tarea = new Tarea(bundle.getInt("TAREA_KEY_TAREA"),
                    bundle.getString("TAREA_KEY_USUARIO_ID"),
                    bundle.getString("TAREA_KEY_NOMBRE"),
                    bundle.getString("TAREA_KEY_DESCRIPCION"),
                    bundle.getString("TAREA_KEY_FECHA"),
                    bundle.getString("TAREA_KEY_HORA"),
                    bundle.getInt("TAREA_KEY_PRIORIDAD"));

            tareaId = tarea.getId();
            tareaUsuarioId = tarea.getUsuario_id();

            txtNombreTareaNew.setText(tarea.getNombre());
            txtDescripcionTareaNew.setText(tarea.getDescripcion());
            txtFechaTareaNew.setText(tarea.getFecha());
            txtHoraTareaNew.setText(tarea.getHora());

            //pongo la prioridad
            String prioridad = "";
            switch (tarea.getPrioridad()){
                case R.drawable.ic_prioridad_3:
                    prioridad = "Alta";
                    break;
                case R.drawable.ic_prioridad_2:
                    prioridad = "Media";
                    break;
                case R.drawable.ic_prioridad_1:
                    prioridad = "Baja";
                    break;
            }

            spPrioridad.setSelection(getIndex(spPrioridad, prioridad));
        }
    }

    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nueva_tarea, menu);
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

    public void accion_guardar_tarea(View view) {
        //Guardo en BD el usuario
        String sNombreTareaNew = txtNombreTareaNew.getText().toString();
        String sDescripcionTareaNew = txtDescripcionTareaNew.getText().toString();
        String sFechaTareaNew = txtFechaTareaNew.getText().toString();
        String sHoraTareaNew = txtHoraTareaNew.getText().toString();
        //prioridad
        String prioridad = spPrioridad.getSelectedItem().toString();

        int prioridadImgResource = 0;
        if (prioridad.equals("Alta")) {
            prioridadImgResource = R.drawable.ic_prioridad_3;
        } else if (prioridad.equals("Media")) {
            prioridadImgResource = R.drawable.ic_prioridad_2;
        } else if (prioridad.equals("Baja")) {
            prioridadImgResource = R.drawable.ic_prioridad_1;
        }

        if (sNombreTareaNew.length() > 0 && sDescripcionTareaNew.length() > 0
                && sFechaTareaNew.length() > 0 && sHoraTareaNew.length() > 0) {

            tarea = new Tarea(tareaUsuarioId, sNombreTareaNew, sDescripcionTareaNew, sFechaTareaNew, sHoraTareaNew, prioridadImgResource);
            if(tareaId == 0){ //insert
                tareaDAO.insert(tarea);
            }else{ //update
                tarea.setId(tareaId);
                tareaDAO.update(tarea);
            }

            finish();
        } else {
            Toast.makeText(this, "Debe de completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void abrirDatePicker(View view) {
        //abro un datePicker
        MyDatePicker myDatePicker = new MyDatePicker();
        myDatePicker.show(getFragmentManager(), "IDENTIFICADOR_1");
    }

    public void abrirTimePicker(View view) {
        //abro un datePicker
        MyTimePicker myTimePicker = new MyTimePicker();
        myTimePicker.show(getFragmentManager(), "IDENTIFICADOR_2");
    }
}
