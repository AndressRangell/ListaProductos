package com.fgardila.ejercicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.fgardila.ejercicio.bd.BaseDeDatos;
import com.fgardila.ejercicio.modelos.Productos;

import java.util.ArrayList;

public class ListProductosActivity extends AppCompatActivity {

    ArrayList<Productos> listaProductos;
    RecyclerView recyclerViewProductos;
    String id_usuario;
    BaseDeDatos bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_productos);

        Bundle miBundle = this.getIntent().getExtras();
        id_usuario = miBundle.getString("idUsuario");

        bd = new BaseDeDatos(getApplicationContext());
        listaProductos = new ArrayList<>();
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        listaProductos = bd.listarProductos();
        MyAdapterProducto myAdapter = new MyAdapterProducto(listaProductos, this);

        myAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListProductosActivity.this, ProductoActivty.class);
                Bundle miBundle = new Bundle();
                miBundle.putString("idUsuario", id_usuario);
                miBundle.putString("idProducto", listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getId());
                miBundle.putString("nombreProducto", listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getName());
                miBundle.putString("descripcionProducto", listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getDescription());
                miBundle.putDouble("precioProducto", listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getAmount());
                miBundle.putString("imgProducto", listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getImgUrl());
                intent.putExtras(miBundle);
                startActivity(intent);
            }
        });

        recyclerViewProductos.setAdapter(myAdapter);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}