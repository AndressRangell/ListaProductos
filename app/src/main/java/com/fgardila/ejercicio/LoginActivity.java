package com.fgardila.ejercicio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fgardila.ejercicio.bd.BaseDeDatos;
import com.fgardila.ejercicio.modelos.JsonRespuesta;
import com.fgardila.ejercicio.modelos.Usuarios;
import com.fgardila.ejercicio.volley.VolleyApplication;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        sincronizar();

    }

    public void iniciarSesion(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        BaseDeDatos bd = new BaseDeDatos(this);
        Usuarios usuario = bd.validateEmailPassword(email, password);
        if (usuario != null) {
            Toast.makeText(this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle miBundle = new Bundle();
            miBundle.putString("idUsuario", usuario.getId());
            intent.putExtras(miBundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error en inicio de sesion", Toast.LENGTH_SHORT).show();
        }
    }

    public void sincronizar() {
        String url = "https://pruebaswposs.000webhostapp.com/ejercicio.json";

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, response ->  {
            //Exzito y obtuvimos el json
            Log.d("JSON", response.toString());
            Gson gson = new Gson();
            JsonRespuesta jsonRespuesta = gson.fromJson(response.toString(), JsonRespuesta.class);
            // Guardar esto en nuestra base de datos
            BaseDeDatos baseDeDatos = new BaseDeDatos(LoginActivity.this);
            baseDeDatos.addUsuarios(jsonRespuesta.getUsuarios());
            baseDeDatos.addProductos(jsonRespuesta.getProductos());
            realizarToast(true);
        }, error -> {
            //Se presento un error
            Log.d("JSON", "Fail");
            realizarToast(false);
        }
        );
        VolleyApplication.getInstance(LoginActivity.this).addToRequestQueue(jsonObject);
    }

    private void realizarToast(boolean respuesta) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!respuesta)
                    Toast.makeText(LoginActivity.this, "Falló la sincronización", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}