package com.example.myapplicationrlv.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplicationrlv.database.Database;
import com.example.myapplicationrlv.database.Product;
import com.example.myapplicationrlv.database.ProductDao;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>>  products;

    public ProductRepository(Application application){
        Database db = Database.getInstance(application);
        productDao = db.productDao();
        products = productDao.findAll();
    }

    public void insert(Product prod) {
        Database.databaseWriteExecutor.execute(() ->
                productDao.insert(prod));
    }
    public void update(Product prod){
        Database.databaseWriteExecutor.execute(() ->
                productDao.update(prod));
    }

    public void delete(Product prod){
        Database.databaseWriteExecutor.execute(() ->
                productDao.delete(prod));
    }

    public void deleteAll() {
        Database.databaseWriteExecutor.execute(() ->
                productDao.deleteAll());
    }

    public LiveData<List<Product>> findAll() {
        return products;
    }



}
