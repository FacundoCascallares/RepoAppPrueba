package com.example.traveldreamsapp.data.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traveldreamsapp.data.models.CartItem;
import com.example.traveldreamsapp.data.repository.CartRepository;

import java.util.List;

public class CarritoViewModel extends AndroidViewModel {

    private final CartRepository repository;
    private final LiveData<List<CartItem>> allItems;

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allItems = repository.getAllItems();
    }

    public LiveData<List<CartItem>> obtenerItems() {
        return allItems;
    }

    public void insertarItem(CartItem item) {
        repository.insert(item);
    }

    public void actualizarItem(CartItem item) {
        repository.update(item);
    }

    public void eliminarItem(CartItem item) {
        repository.delete(item);
    }
}
