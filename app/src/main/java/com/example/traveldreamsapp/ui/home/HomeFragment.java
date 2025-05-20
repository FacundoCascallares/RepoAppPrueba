package com.example.traveldreamsapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.traveldreamsapp.adapter.DestinosAdapter;
import com.example.traveldreamsapp.data.viewmodel.CarritoViewModel;
import com.example.traveldreamsapp.databinding.FragmentHomeBinding;
import com.example.traveldreamsapp.models.Destinos;
import com.example.traveldreamsapp.network.ApiClient;
import com.example.traveldreamsapp.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DestinosAdapter destinosAdapter;
    private List<Destinos> destinos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();
        showDestinos();

        return root;
    }

    private void setupRecyclerView() {
        binding.recyclerViewDestinos.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private void showDestinos() {
        Call<List<Destinos>> call = ApiClient.getClient().create(ApiService.class).getDestinos();
        call.enqueue(new Callback<List<Destinos>>() {
            @Override
            public void onResponse(Call<List<Destinos>> call, Response<List<Destinos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    destinos = response.body();

                    // Obtener el ViewModel de carrito desde la actividad
                    CarritoViewModel carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);

                    // Obtener LifecycleOwner del fragmento
                    LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

                    // Crear el adapter con los 4 parámetros que espera
                    destinosAdapter = new DestinosAdapter(destinos, requireContext(), carritoViewModel, lifecycleOwner);
                    binding.recyclerViewDestinos.setAdapter(destinosAdapter);
                } else {
                    Toast.makeText(getContext(), "No se encontraron destinos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Destinos>> call, Throwable throwable) {
                Toast.makeText(getContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
