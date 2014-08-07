package com.example.matt.tarea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UsuarioActivity extends Activity {
   int contador = 0;
   int contador_limite = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.usuario, menu);
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

   public void accionCancelar(View view) {
      finish();
   }

   public void accionIngresar(View view) {
      auntenticarUsuario(view);
   }

   public void auntenticarUsuario(View view) {
      final int CONTADOR_INCORRECTO = 3;
      EditText txtUsuario = (EditText) findViewById(R.id.txtUsuario1);
      EditText txtPassword = (EditText) findViewById(R.id.txtPassword1);
      TextView lblMensaje = (TextView) findViewById(R.id.txtMensaje);

      String usuario = txtUsuario.getText().toString();
      String password = txtPassword.getText().toString();
      if (usuario.equals("1") && password.equals("2")) {
         Toast.makeText(this, "Ingresando como usuario...", Toast.LENGTH_SHORT).show();
         Intent intent = new Intent(this, FichaUsuarioActivity.class);
         intent.putExtra("USUARIO", txtUsuario.getText().toString());
         startActivity(intent);
      } else {
         contador++;
         if (contador == CONTADOR_INCORRECTO) {
            this.finish();
         }

         lblMensaje.setText("Usuario/Pass incorrecto, le quedan " + (CONTADOR_INCORRECTO - contador) + " intentos.");
         lblMensaje.setVisibility(View.VISIBLE);
      }
   }
}
