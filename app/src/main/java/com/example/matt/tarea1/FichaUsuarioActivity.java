package com.example.matt.tarea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matt.tarea1.dao.UsuarioDAO;
import com.example.matt.tarea1.domain.Usuario;

public class FichaUsuarioActivity extends Activity {
    UsuarioDAO usuarioDAO;
    Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass().toString(), "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_usuario);

        usuarioDAO = new UsuarioDAO(this);
        usuarioDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("INTENT_KEY_USUARIO") != null) {
            String sUsuario = bundle.getString("INTENT_KEY_USUARIO");
            Toast.makeText(this, "Bienvenido " + sUsuario, Toast.LENGTH_LONG).show();

            usuario = usuarioDAO.get(sUsuario);

            if (usuario != null) {
                //cargo los datos del usuario
                EditText txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
                EditText txtUsuario = (EditText) findViewById(R.id.txtUser);
                EditText txtPassword = (EditText) findViewById(R.id.txtPassWrd);
                //TODO falta cargar imagen aleatoria y guardar en preferencias

                txtNombreUsuario.setText(usuario.getNombre());
                txtUsuario.setText(usuario.getUsuario());
                txtPassword.setText(usuario.getPassword());
            }else{
                Toast.makeText(this, "El usuario " + sUsuario + " no existe en BD", Toast.LENGTH_LONG).show();
                finish();
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
        usuarioDAO.close();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ficha_usuario, menu);
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

    public void accionVerPendientes(View view) {
        Intent intent = new Intent(this, AgendaActivity.class);
        intent.putExtra("INTENT_KEY_USUARIO", usuario.getUsuario());
        startActivity(intent);
    }

    public void accionActualizarUsuario(View view) {
        //TODO Pendiente
    }
}
