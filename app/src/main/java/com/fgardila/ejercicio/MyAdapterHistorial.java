package com.fgardila.ejercicio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fgardila.ejercicio.modelos.Productos;

import java.util.ArrayList;

public class MyAdapterHistorial extends RecyclerView.Adapter<MyAdapterHistorial.MyViewHolder>{

    Context context;
    private View.OnClickListener listener;
    private ArrayList<Productos> productos;

    public MyAdapterHistorial(ArrayList<Productos> productos, Context context){
        this.productos = productos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_compra, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nombreCompra.setText(productos.get(position).getName());
        holder.precioCompra.setText(String.valueOf(productos.get(position).getAmount()));
        holder.fechaCompra.setText(productos.get(position).getFechaCompra());
        Glide.with(context).load(productos.get(position).getImgUrl()).circleCrop().error(R.drawable.ic_account).into(holder.imgCompra);

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreCompra, precioCompra, fechaCompra;
        private ImageView imgCompra;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreCompra = itemView.findViewById(R.id.nombreCompra);
            precioCompra = itemView.findViewById(R.id.precioCompra);
            fechaCompra = itemView.findViewById(R.id.fechaCompra);
            imgCompra = itemView.findViewById(R.id.imgCompra);

        }
    }

}
