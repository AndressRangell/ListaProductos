package com.fgardila.ejercicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fgardila.ejercicio.bd.BaseDeDatos;
import com.fgardila.ejercicio.modelos.Productos;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {

    RecyclerView recyclerViewCompras;
    ArrayList<Productos> listaProductos;
    String id_usuario;
    BaseDeDatos bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Bundle miBundle = this.getIntent().getExtras();
        id_usuario = miBundle.getString("idUsuario");

        bd = new BaseDeDatos(getApplicationContext());
        recyclerViewCompras = findViewById(R.id.recyclerViewCompras);
        recyclerViewCompras.setLayoutManager(new LinearLayoutManager(this));
        listaProductos = bd.listarCompras(id_usuario);
        MyAdapterHistorial myAdapter = new MyAdapterHistorial(listaProductos, this);

        recyclerViewCompras.setAdapter(myAdapter);

    }
}