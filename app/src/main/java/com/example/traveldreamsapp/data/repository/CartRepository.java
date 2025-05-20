package com.example.traveldreamsapp.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.traveldreamsapp.data.dao.CartItemDao;
import com.example.traveldreamsapp.data.database.CartDatabase;
import com.example.traveldreamsapp.data.models.CartItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartRepository {

    private final CartItemDao cartItemDao;
    private final LiveData<List<CartItem>> allItems;
    private final ExecutorService executorService;

    public CartRepository(Application application) {
        CartDatabase db = CartDatabase.getInstance(application);  // corregido
        cartItemDao = db.cartItemDao();
        allItems = cartItemDao.getAllItems();  // corregido para usar el método con LiveData
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CartItem>> getAllItems() {
        return allItems;
    }

    public void insert(final CartItem item) {
        executorService.execute(() -> cartItemDao.insert(item));
    }

    public void update(final CartItem item) {
        executorService.execute(() -> cartItemDao.update(item));
    }

    public void delete(final CartItem item) {
        executorService.execute(() -> cartItemDao.delete(item));
    }
}
