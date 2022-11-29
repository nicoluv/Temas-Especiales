package com.example.myapplicationrlv.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {


    public abstract ProductDao productDao();

    private static volatile Database Instancia;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static Database getInstance(Context ctx){
        if(Instancia == null){
            synchronized (Database.class){
                if(Instancia == null){
                    Instancia = Room.databaseBuilder(ctx.getApplicationContext(), Database.class, "RLV")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return Instancia;
    }



}
