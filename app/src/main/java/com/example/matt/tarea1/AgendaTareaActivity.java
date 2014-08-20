package com.example.matt.tarea1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.tarea1.dao.TareaDAO;
import com.example.matt.tarea1.domain.Tarea;

public class AgendaTareaActivity extends Activity {
   TareaDAO tareaDAO;
   Tarea tarea = null;
   String sUsuario;

   TextView txtNombreTareaNew;
   TextView txtDescripcionTareaNew;
   TextView txtFechaTareaNew;
   TextView txtHoraTareaNew;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_agenda_tarea);

      txtNombreTareaNew = (TextView) findViewById(R.id.txtNombreTareaNew);
      txtDescripcionTareaNew = (TextView) findViewById(R.id.txtDescripcionTareaNew);
      txtFechaTareaNew = (TextView) findViewById(R.id.txtFechaTareaNew);
      txtHoraTareaNew = (TextView) findViewById(R.id.txtHoraTareaNew);

      tareaDAO = new TareaDAO(this);
      //tareaDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

      //traigo por extra el id del usuario.
      Bundle bundle = getIntent().getExtras();

      if (bundle.getString("INTENT_KEY_USUARIO") != null) {
         sUsuario = bundle.getString("INTENT_KEY_USUARIO");
      } else if (bundle.getString("TAREA_KEY_TAREA") != null) {
         Toast.makeText(this, "Editando tarea", Toast.LENGTH_LONG).show();

         String sTareaId = bundle.getString("TAREA_KEY_TAREA");
         String sTareaUsuarioId = bundle.getString("TAREA_KEY_USUARIO_ID");
         txtNombreTareaNew.setText(bundle.getString("TAREA_KEY_NOMBRE"));
         txtDescripcionTareaNew.setText(bundle.getString("TAREA_KEY_DESCRIPCION"));
         txtFechaTareaNew.setText(bundle.getString("TAREA_KEY_FECHA"));
         txtHoraTareaNew.setText(bundle.getString("TAREA_KEY_HORA"));

      }
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

      if (sNombreTareaNew.length() > 0 && sDescripcionTareaNew.length() > 0
            && sFechaTareaNew.length() > 0 && sHoraTareaNew.length() > 0) {
         //guardamos en BD
         tareaDAO.insert(new Tarea(sUsuario, sNombreTareaNew, sDescripcionTareaNew, sFechaTareaNew, sHoraTareaNew));
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
