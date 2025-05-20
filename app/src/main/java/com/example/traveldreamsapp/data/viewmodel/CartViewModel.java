package com.example.traveldreamsapp.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.traveldreamsapp.data.models.CartItem;
import com.example.traveldreamsapp.data.repository.CartRepository;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final CartRepository cartRepository;
    private final LiveData<List<CartItem>> cartItems;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepository = new CartRepository(application);
        cartItems = cartRepository.getAllItems();
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public void insertarItem(CartItem item) {
        cartRepository.insert(item);
    }

    public void actualizarItem(CartItem item) {
        cartRepository.update(item);
    }

    public void eliminarItem(CartItem item) {
        cartRepository.delete(item);
    }
}
