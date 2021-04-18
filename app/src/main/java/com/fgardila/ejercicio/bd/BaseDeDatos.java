package com.fgardila.ejercicio.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fgardila.ejercicio.modelos.Productos;
import com.fgardila.ejercicio.modelos.Usuarios;

import java.util.ArrayList;

public class BaseDeDatos extends SQLiteOpenHelper {

    private Context context;
    private static final String name = "tienda.db";
    private static final int version = 1;

    private static final String tableUsuarios = "usuarios";
    private static final String column_id_usuarios = "id_usuarios";
    private static final String column_email = "email_usuarios";
    private static final String column_password = "password_usuarios";
    private static final String column_name = "name_usuarios";

    private static final String tableProductos = "productos";
    private static final String column_id_productos = "id_productos";
    private static final String column_name_producto = "name_productos";
    private static final String column_description = "description_productos";
    private static final String column_amount = "amount_productos";
    private static final String column_imageUrl = "imageUrl_productos";

    private static final String tableHistorial = "historial";
    private static final String column_id_compra = "id_compra";
    private static final String column_usuario_compra = "id_usuario_compra";
    private static final String column_producto_compra = "id_producto_compra";
    private static final String column_fecha_compra = "fecha_compra";
    private static final String column_hora_compra = "hora_compra";

    public BaseDeDatos(@Nullable Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUsuarios = "Create table " + tableUsuarios + "(" +
                column_id_usuarios + " integer primary key," +
                column_email + " text unique," +
                column_password + " text," +
                column_name + " text);";
        db.execSQL(queryUsuarios);

        String queryProductos = "Create table " + tableProductos + "(" +
                column_id_productos + " integer primary key," +
                column_name_producto + " text," +
                column_description + " text," +
                column_amount + " text," +
                column_imageUrl + " text);";
        db.execSQL(queryProductos);

        String queryHistorial = "Create table " + tableHistorial + "(" +
                column_id_compra + " integer primary key AUTOINCREMENT," +
                column_usuario_compra + " integer," +
                column_producto_compra + " integer," +
                column_fecha_compra + " text," +
                "foreign key(" + column_producto_compra + ") references " + tableProductos + "(" + column_id_productos + ")," +
                "foreign key(" + column_usuario_compra + ") references " + tableUsuarios + "(" + column_id_usuarios + "));";
        db.execSQL(queryHistorial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableUsuarios);
        db.execSQL("DROP TABLE IF EXISTS " + tableProductos);
        db.execSQL("DROP TABLE IF EXISTS " + tableHistorial);
        onCreate(db);
    }

    public boolean addUsuarios (ArrayList<Usuarios> usuarios) {
        boolean proceso = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tableUsuarios);
        for (Usuarios usu : usuarios) {
            ContentValues cv = new ContentValues();
            cv.put(column_id_usuarios, usu.getId());
            cv.put(column_email, usu.getEmail());
            cv.put(column_password, usu.getPassword());
            cv.put(column_name, usu.getName());
            long result = db.insert(tableUsuarios, null, cv);
            if (result == -1) {
                proceso = false;
                return proceso;
            } else {
                proceso = true;
            }
        }
        return proceso;
    }

    public boolean addProductos (ArrayList<Productos> productos) {
        boolean proceso = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tableProductos);
        for (Productos pro : productos) {
            ContentValues cv = new ContentValues();
            cv.put(column_id_productos, pro.getId());
            cv.put(column_name_producto, pro.getName());
            cv.put(column_description, pro.getDescription());
            cv.put(column_amount, String.valueOf(pro.getAmount()));
            cv.put(column_imageUrl, pro.getImgUrl());
            long result = db.insert(tableProductos, null, cv);
            if (result == -1) {
                proceso = false;
                return proceso;
            } else {
                proceso = true;
            }
        }
        return proceso;
    }

    public Usuarios validateEmailPassword (String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableUsuarios  + " WHERE " + column_email + " = '" + email + "'";
        Usuarios usuario = new Usuarios();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                usuario.setId(cursor.getString(0));
                usuario.setEmail(cursor.getString(1));
                usuario.setPassword(cursor.getString(2));
                usuario.setName(cursor.getString(3));
            }
            if (usuario.getEmail() != null && password.equals(usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }

    public ArrayList<Productos> listarProductos(){
        ArrayList<Productos> listaProductos = new ArrayList<Productos>();
        Productos producto = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableProductos;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                producto = new Productos();
                producto.setId(cursor.getString(0));
                producto.setName(cursor.getString(1));
                producto.setDescription(cursor.getString(2));
                producto.setAmount(cursor.getDouble(3));
                producto.setImgUrl(cursor.getString(4));

                listaProductos.add(producto);
            }
        }
        return listaProductos;
    }

    public Usuarios verUsuario(String idUsuario){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = Integer.parseInt(idUsuario);
        String query = "SELECT * FROM " + tableUsuarios  + " WHERE " + column_id_usuarios + " = " + id;
        Usuarios usuario = new Usuarios();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                usuario.setId(String.valueOf(cursor.getInt(0)));
                usuario.setEmail(cursor.getString(1));
                usuario.setPassword(cursor.getString(2));
                usuario.setName(cursor.getString(3));
            }
        }
        return usuario;
    }

    public Productos verProducto(String idProducto){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = Integer.parseInt(idProducto);
        String query = "SELECT * FROM " + tableProductos  + " WHERE " + column_id_productos + " = " + id;
        Productos producto = new Productos();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                producto.setId(String.valueOf(cursor.getInt(0)));
                producto.setName(cursor.getString(1));
                producto.setDescription(cursor.getString(2));
                producto.setAmount(Double.parseDouble(cursor.getString(3)));
                producto.setImgUrl(cursor.getString(4));
            }
        }
        return producto;
    }

    public boolean registrarCompra(String id_usuario, String id_producto, String fecha){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        String nulo = null;
        cv.put(column_id_compra, nulo);
        cv.put(column_producto_compra, id_producto);
        cv.put(column_usuario_compra, id_usuario);
        cv.put(column_fecha_compra, fecha);
        long result = db.insert(tableHistorial, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Productos> listarCompras(String idUsuario){
        ArrayList<Productos> listaProductos = new ArrayList<Productos>();
        Productos producto = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableProductos + ", " + tableHistorial + " where " + column_usuario_compra + " = " +
                Integer.parseInt(idUsuario) + " AND " + column_producto_compra + " = " + column_id_productos;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                producto = new Productos();
                producto.setId(cursor.getString(0));
                producto.setName(cursor.getString(1));
                producto.setDescription(cursor.getString(2));
                producto.setAmount(cursor.getDouble(3));
                producto.setImgUrl(cursor.getString(4));
                producto.setFechaCompra(cursor.getString(8));

                listaProductos.add(producto);
            }
        }
        return listaProductos;
    }

}
