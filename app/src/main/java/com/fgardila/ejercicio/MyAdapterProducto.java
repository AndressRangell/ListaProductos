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

public class MyAdapterProducto extends RecyclerView.Adapter<MyAdapterProducto.MyViewHolder> implements View.OnClickListener{

    Context context;
    ArrayList<Productos> listaProductos;
    private View.OnClickListener listener;

    public MyAdapterProducto(ArrayList<Productos> listaProductos, Context context){
        this.listaProductos = listaProductos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_producto, null, false);

        view.setOnClickListener(this);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nombreProducto.setText(listaProductos.get(position).getName());
        holder.precioProducto.setText(String.valueOf(listaProductos.get(position).getAmount()));
        Glide.with(context).load(listaProductos.get(position).getImgUrl()).circleCrop().error(R.drawable.ic_account).into(holder.imgProducto);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void setListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreProducto, precioProducto;
        private ImageView imgProducto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreProducto = itemView.findViewById(R.id.nombreProducto);
            precioProducto = itemView.findViewById(R.id.precioProducto);
            imgProducto = itemView.findViewById(R.id.imgProducto);

        }
    }

}
