package com.example.traveldreamsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.traveldreamsapp.R;
import com.example.traveldreamsapp.adapter.CarritoAdapter;
import com.example.traveldreamsapp.data.viewmodel.CarritoViewModel;
import com.example.traveldreamsapp.data.models.CartItem;

import java.util.ArrayList;

public class CarritoFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private CarritoViewModel carritoViewModel;
    private TextView tvTotal;

    public CarritoFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerView = view.findViewById(R.id.recycler_carrito);
        tvTotal = view.findViewById(R.id.tv_total);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);

        carritoAdapter = new CarritoAdapter(new ArrayList<>(), requireContext(), carritoViewModel);
        recyclerView.setAdapter(carritoAdapter);

        carritoViewModel.obtenerItems().observe(getViewLifecycleOwner(), items -> {
            carritoAdapter = new CarritoAdapter(items, requireContext(), carritoViewModel);
            recyclerView.setAdapter(carritoAdapter);

            double total = 0;
            for (CartItem item : items) {
                total += item.getPrecio() * item.getCantidad();
            }
            tvTotal.setText(String.format("Total: USD %.2f", total));
        });

        return view;
    }
}
