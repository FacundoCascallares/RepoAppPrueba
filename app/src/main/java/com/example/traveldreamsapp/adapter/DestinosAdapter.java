package com.example.traveldreamsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveldreamsapp.R;
import com.example.traveldreamsapp.data.models.CartItem;
import com.example.traveldreamsapp.data.viewmodel.CarritoViewModel;
import com.example.traveldreamsapp.models.Destinos;

import java.util.List;

public class DestinosAdapter extends RecyclerView.Adapter<DestinosAdapter.ViewHolder> {

    private final List<Destinos> destinos;
    private final Context context;
    private final CarritoViewModel carritoViewModel;
    private final LifecycleOwner lifecycleOwner;

    public DestinosAdapter(List<Destinos> destinos, Context context, CarritoViewModel carritoViewModel, LifecycleOwner lifecycleOwner) {
        this.destinos = destinos;
        this.context = context;
        this.carritoViewModel = carritoViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_destino, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destinos destino = destinos.get(position);

        holder.tv_nombre_Destino.setText(destino.getNombre_Destino());
        holder.tv_descripcion.setText(destino.getDescripcion());
        holder.tv_precio.setText(String.format("USD %.2f", destino.getPrecio_Destino()));
        Glide.with(context).load(destino.getImage()).into(holder.iv_image);

        // Botón "Comprar"
        holder.btn_comprar.setOnClickListener(v -> {
            CartItem item = new CartItem(
                    destino.getId_destino(),
                    destino.getNombre_Destino(),
                    destino.getDescripcion(),
                    destino.getImage(),
                    destino.getPrecio_Destino(),
                    1 // Cantidad inicial (puede ser 1 o lo que quieras)
            );

            carritoViewModel.insertarItem(item);
            Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return destinos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button btn_comprar;
        private final ImageView iv_image;
        private final TextView tv_nombre_Destino;
        private final TextView tv_descripcion;
        private final TextView tv_precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_nombre_Destino = itemView.findViewById(R.id.tv_nombre_Destino);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            btn_comprar = itemView.findViewById(R.id.btn_comprar);
        }
    }
}
