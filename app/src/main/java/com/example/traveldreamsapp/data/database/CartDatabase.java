package com.example.traveldreamsapp.data.database;

import android.content.Context;
import com.example.traveldreamsapp.data.dao.CartItemDao;
import com.example.traveldreamsapp.data.models.CartItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartItem.class}, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    private static CartDatabase instance;

    // Expone el DAO para poder acceder a la base
    public abstract CartItemDao cartItemDao();

    // Singleton: se asegura de que solo haya una instancia de la base
    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, "cart_db")
                    .fallbackToDestructiveMigration() // elimina la DB si cambia la estructura
                    .allowMainThreadQueries() // permite consultas en el hilo principal (en proyectos reales se recomienda evitarlo)
                    .build();
        }
        return instance;
    }
}
