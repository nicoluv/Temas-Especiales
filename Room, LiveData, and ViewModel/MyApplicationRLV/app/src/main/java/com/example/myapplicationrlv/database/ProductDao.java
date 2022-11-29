package com.example.myapplicationrlv.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM myProducts")
    List<Product> getAll();

    @Query("SELECT * FROM myProducts WHERE pid = :pid")
    Product findById(int pid);

    @Query("SELECT * FROM myProducts")
    LiveData<List<Product>> findAll();


    @Query("DELETE FROM myProducts")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Insert
    void insertAll(List<Product> products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}