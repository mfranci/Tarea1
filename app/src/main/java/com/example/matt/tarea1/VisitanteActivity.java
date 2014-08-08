package com.example.matt.tarea1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matt.tarea1.R;
import com.example.matt.tarea1.dao.UsuarioDAO;
import com.example.matt.tarea1.domain.Usuario;

public class VisitanteActivity extends Activity {
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante);

        Log.d(getClass().toString(), "onCreate()");

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
        getMenuInflater().inflate(R.menu.visitante, menu);
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

    public void accionCrearUsuario(View view) {
        EditText txtUsuario, txtNombre, txtPassword, txtPassword2;
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword2 = (EditText) findViewById(R.id.txtPassword2);

        String sUsuario = txtUsuario.getText().toString();
        String sNombre = txtNombre.getText().toString();
        String sPassword = txtPassword.getText().toString();
        String sPassword2 = txtPassword2.getText().toString();

        //valido que estén completados todos los campos
        if(sUsuario.length()>0 && sNombre.length()>0 && sPassword.length()>0 && sPassword2.length()>0 ){
            //verifico que los passwords sean iguales
            if(sPassword.equals(sPassword2)){
                //valido que el usuario no esté repetido
                Usuario usuario = usuarioDAO.get(sUsuario);
                if(usuario==null){
                    Log.d(getClass().toString(), "accionCrearUsuario->guardar");
                    usuarioDAO.insert(new Usuario(sUsuario, sNombre, sPassword));
                    Toast.makeText(this, "Usuario creado!", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(this, "Usuario repedido, intente otro!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Los passwords son distintos!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Debe de completarse todos los campos!", Toast.LENGTH_LONG).show();
        }
    }
}
