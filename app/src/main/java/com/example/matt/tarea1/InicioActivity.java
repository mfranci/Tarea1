package com.example.matt.tarea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


public class InicioActivity extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_inicio);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.inicio, menu);
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

   public void seleccionarRadio(View view) {
      RadioButton radioButton = (RadioButton) view;
      boolean valor = radioButton.isChecked();

      Intent intent = null;

      switch (radioButton.getId()) {
         case R.id.optUsuario:
            if (valor) {
               Log.d(this.getLocalClassName(), "Se marcó optUsuario");
               intent = new Intent(this, UsuarioActivity.class);
            }
            break;
         case R.id.optVisitante:
            if (valor) {
               Log.d(this.getLocalClassName(), "Se marcó optVisitante");
               intent = new Intent(this, VisitanteActivity.class);
            }
            break;
      }
      startActivity(intent);
   }
}
