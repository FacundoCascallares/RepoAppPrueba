package com.example.traveldreamsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.traveldreamsapp.data.models.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {

    @Insert
    void insert(CartItem item);

    // Cambié List<CartItem> a LiveData<List<CartItem>> para observar cambios
    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItem>> getAllItems();

    @Query("SELECT * FROM cart_items WHERE idDestino = :idDestino LIMIT 1")
    CartItem getItemByDestinoId(int idDestino);

    @Update
    void update(CartItem item);

    @Delete
    void delete(CartItem item);

    @Query("DELETE FROM cart_items")
    void clearCart();
}
