package com.example.myapplicationrlv.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationrlv.database.Product;
import com.example.myapplicationrlv.repositories.ProductRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyViewModel extends AndroidViewModel {

    private LiveData<List<Product>> allProducts;

    private ProductRepository productRepository;


    public MyViewModel(@NonNull @NotNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProducts = productRepository.findAll();
    }

    public void insert(Product prod) {
        productRepository.insert(prod);
    }
    public void update(Product prod){
        productRepository.update(prod);
    }
    public void delete(Product product){
        productRepository.delete(product); }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public LiveData<List<Product>> findAll() {
        return allProducts;
    }

}