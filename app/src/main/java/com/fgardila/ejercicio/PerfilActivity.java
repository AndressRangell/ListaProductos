package com.fgardila.ejercicio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fgardila.ejercicio.bd.BaseDeDatos;
import com.fgardila.ejercicio.modelos.Usuarios;

public class PerfilActivity extends AppCompatActivity {

    private String id_usuario;
    private TextView txtIdUsuario, txtNombre, txtEmail;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Bundle miBundle = this.getIntent().getExtras();
        id_usuario = miBundle.getString("idUsuario");
        BaseDeDatos bd = new BaseDeDatos(this);
        usuario = bd.verUsuario(id_usuario);

        txtIdUsuario = findViewById(R.id.Código);
        txtNombre = findViewById(R.id.nombreUsuario);
        txtEmail = findViewById(R.id.emailUsuario);

        txtIdUsuario.setText(usuario.getId());
        txtNombre.setText(usuario.getName());
        txtEmail.setText(usuario.getEmail());

    }

    public void cambiarContraseña(View view){

    }

}