package com.example.traveldreamsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveldreamsapp.R;
import com.example.traveldreamsapp.data.models.CartItem;
import com.example.traveldreamsapp.data.viewmodel.CarritoViewModel;



import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private final List<CartItem> carritoList;
    private final Context context;
    private final CarritoViewModel carritoViewModel;

    public CarritoAdapter(List<CartItem> carritoList, Context context, CarritoViewModel carritoViewModel) {
        this.carritoList = carritoList;
        this.context = context;
        this.carritoViewModel = carritoViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = carritoList.get(position);

        holder.tvNombre.setText(item.getNombreDestino());
        holder.tvPrecio.setText(String.format("USD %.2f", item.getPrecio()));
        holder.tvCantidad.setText(String.valueOf(item.getCantidad()));
        holder.tvSubtotal.setText(String.format("Subtotal: USD %.2f", item.getPrecio() * item.getCantidad()));

        Glide.with(context).load(item.getImageUrl()).into(holder.ivImagen);

        // Aumentar cantidad
        holder.btnMas.setOnClickListener(v -> {
            item.setCantidad(item.getCantidad() + 1);
            carritoViewModel.actualizarItem(item);
        });

        // Disminuir cantidad
        holder.btnMenos.setOnClickListener(v -> {
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                carritoViewModel.actualizarItem(item);
            }
        });

        // Eliminar
        holder.btnEliminar.setOnClickListener(v -> carritoViewModel.eliminarItem(item));
    }

    @Override
    public int getItemCount() {
        return carritoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvCantidad, tvSubtotal;
        ImageView ivImagen;
        Button btnMas, btnMenos;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvPrecio = itemView.findViewById(R.id.tv_precio);
            tvCantidad = itemView.findViewById(R.id.tv_cantidad);
            tvSubtotal = itemView.findViewById(R.id.tv_subtotal);
            ivImagen = itemView.findViewById(R.id.iv_imagen);
            btnMas = itemView.findViewById(R.id.btn_sumar);
            btnMenos = itemView.findViewById(R.id.btn_restar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
