package com.example.matt.tarea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matt.tarea1.dao.UsuarioDAO;
import com.example.matt.tarea1.domain.Usuario;

public class UsuarioActivity extends Activity {
    int contador = 0;
    private final int CONTADOR_INCORRECTO = 3;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().toString(), "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        usuarioDAO = new UsuarioDAO(this);
        usuarioDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate
    }

    @Override
    protected void onStop() {
        Log.d(getClass().toString(), "onStop()");
        usuarioDAO.close();
        super.onStop();
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
        EditText txtUsuario = (EditText) findViewById(R.id.txtUsuario1);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword1);
        TextView lblMensaje = (TextView) findViewById(R.id.txtMensaje);

        String sUsuario = txtUsuario.getText().toString();
        String sPassword = txtPassword.getText().toString();

        if (sUsuario.length() > 0 && sPassword.length() > 0) {
            Usuario usuario = usuarioDAO.get(sUsuario);

            if (usuario != null) {
                //usuario logeado
                Intent intent = new Intent(this, FichaUsuarioActivity.class);
                intent.putExtra("INTENT_KEY_USUARIO", usuario.getUsuario());
                startActivity(intent);
            } else {
                contador++;
                if (contador == CONTADOR_INCORRECTO) {
                    this.finish();
                }

                lblMensaje.setText("Usuario/Pass incorrecto, le quedan " + (CONTADOR_INCORRECTO - contador) + " intentos.");
                lblMensaje.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "Debe de completarse todos los campos!", Toast.LENGTH_LONG).show();
        }
    }
}
