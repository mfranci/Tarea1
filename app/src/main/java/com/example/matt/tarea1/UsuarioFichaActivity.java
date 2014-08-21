package com.example.matt.tarea1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matt.tarea1.dao.UsuarioDAO;
import com.example.matt.tarea1.domain.Usuario;

import java.util.Random;

public class UsuarioFichaActivity extends Activity {
   UsuarioDAO usuarioDAO;
   Usuario usuario = null;

   ImageView imgUsuario;

   EditText txtNombreUsuario;
   EditText txtUsuario;
   EditText txtPassword;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      Log.d(getClass().toString(), "onCreate()");
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_usuario_ficha);

      usuarioDAO = new UsuarioDAO(this);
      //usuarioDAO.open(); //se ejecuta el metodo MySqlOpenHelper.onCreate

      Bundle bundle = getIntent().getExtras();

      if (bundle.getString("INTENT_KEY_USUARIO") != null) {
         String sUsuario = bundle.getString("INTENT_KEY_USUARIO");
         Toast.makeText(this, "Bienvenido " + sUsuario, Toast.LENGTH_LONG).show();

         usuario = usuarioDAO.get(sUsuario);

         if (usuario != null) {
            //cargo los datos del usuario
            txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
            txtUsuario = (EditText) findViewById(R.id.txtUser);
            txtPassword = (EditText) findViewById(R.id.txtPassWrd);

            //imagen avatar
            imgUsuario = (ImageView) findViewById(R.id.imgUsuario);

            txtNombreUsuario.setText(usuario.getNombre());
            txtUsuario.setText(usuario.getUsuario());
            txtPassword.setText(usuario.getPassword());
         } else {
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
      //usuarioDAO.close();
      super.onStop();
   }

   @Override
   protected void onResume() {
      Log.d(getClass().toString(), "onResume()");

      //Preferencias del avatar.
      SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.NombreArchivoPreferenciasXML), Context.MODE_PRIVATE);

      int imgRsrcAvatar = sharedPreferences.getInt("PREF_KEY_USER_" + usuario.getUsuario(), 0);

      if (imgRsrcAvatar == 0) {
         Log.d(this.getClass().toString(), "Asignar aleatoreo");

         //genero id aleatoreo del avatar
         int[] lstImgRsAvatar = {
               R.drawable.ic_avatar_01,
               R.drawable.ic_avatar_02,
               R.drawable.ic_avatar_03,
               R.drawable.ic_avatar_04,
               R.drawable.ic_avatar_05,
               R.drawable.ic_avatar_06
         };

         Random random = new Random();
         int randomNumber = random.nextInt(5);
         imgRsrcAvatar = lstImgRsAvatar[randomNumber];

         //guardo avatar random en preferencias
         SharedPreferences.Editor editor = sharedPreferences.edit();
         editor.putInt("PREF_KEY_USER_" + usuario.getUsuario(), imgRsrcAvatar);
         editor.commit();
      }

      //cargo una imagen
      imgUsuario.setImageResource(imgRsrcAvatar);

      super.onResume();
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
      Toast.makeText(this,"Guardamdo...",Toast.LENGTH_SHORT).toString();
      usuario.setNombre(txtNombreUsuario.getText().toString());
      usuario.setUsuario(txtUsuario.getText().toString());
      usuario.setPassword(txtPassword.getText().toString());
      usuarioDAO.update(usuario);
      Log.d(getClass().toString(), "accionActualizarUsuario()");
   }
}
