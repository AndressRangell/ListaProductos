package com.fgardila.ejercicio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fgardila.ejercicio.bd.BaseDeDatos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductoActivty extends AppCompatActivity {

    private TextView nombreProducto, descripcionProducto, precioProducto;
    private ImageView imgProducto;
    private String id_usuario, id_producto, nombre, descripcion, imagen;
    private double precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        nombreProducto = findViewById(R.id.nombreProducto);
        descripcionProducto = findViewById(R.id.descripcionProducto);
        precioProducto = findViewById(R.id.precioProducto);
        imgProducto = findViewById(R.id.imgProducto);

        Bundle miBundle = this.getIntent().getExtras();

        if(miBundle != null){
            id_usuario = miBundle.getString("idUsuario");
            id_producto = miBundle.getString("idProducto");
            nombre = miBundle.getString("nombreProducto");
            descripcion = miBundle.getString("descripcionProducto");
            precio = miBundle.getDouble("precioProducto");
            imagen = miBundle.getString("imgProducto");
        }

        nombreProducto.setText(nombre);
        descripcionProducto.setText(descripcion);
        precioProducto.setText(String.valueOf(precio));
        Glide.with(this).load(imagen).circleCrop().error(R.drawable.ic_account).into(imgProducto);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comprar(View view){

        LocalDateTime fecha = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String fecha_compra = fecha.format(formato);

        BaseDeDatos bd = new BaseDeDatos(this);
        boolean respuesta = bd.registrarCompra(id_usuario, id_producto, fecha_compra);
        if(respuesta){
            Toast.makeText(ProductoActivty.this, "Compra realizada con exito", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(ProductoActivty.this, "La compra no se pudo realizar", Toast.LENGTH_SHORT).show();
        }
    }


}