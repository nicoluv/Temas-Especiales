package com.example.myapplicationrlv.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplicationrlv.R;
import com.example.myapplicationrlv.database.Product;
import com.example.myapplicationrlv.databinding.FragmentSecondBinding;
import com.example.myapplicationrlv.models.MyViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.IOException;
import java.util.UUID;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private MyViewModel myViewModel;
    private Product prod;
    private Uri path;
    FirebaseStorage storage;
    StorageReference reference;
    FirebaseAuth mAuth;
    Product example;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();


        if(getArguments() != null){

            prod = (Product) getArguments().getSerializable("myProducts");

            StorageReference path = reference.child("images/"+prod.getImage());
            path.getBytes(1024*1024).addOnSuccessListener(bytes -> {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.fImage.setImageBitmap(bm);
            });

            binding.fName.setText(prod.getName());
            binding.fBrand.setText(prod.getBrand());
            binding.fPrice.setText(prod.getPrice().toString());
        }


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnClear.setOnClickListener(this::clear);
        binding.btnSave.setOnClickListener(this::save);
        binding.btnDelete.setOnClickListener(this::delete);
        binding.fImage.setOnClickListener(view1 -> {
            getProdImage();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 22 ){
            path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),path);
                binding.fImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }


    public void clear(View view){
        binding.fBrand.setText("");
        binding.fName.setText("");
        binding.fPrice.setText("");
    }

    public void delete(View view) {
        myViewModel.delete(prod);
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

    private void getProdImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose your image"),22);
    }


    public void save(View view){

        if(getArguments() != null) {
            mAuth.signInAnonymously();

            prod.setName(binding.fName.getText().toString());
            prod.setBrand(binding.fBrand.getText().toString());
            prod.setPrice(Double.parseDouble(binding.fPrice.getText().toString()));

            updateImage(prod);

        }else {

            String name = binding.fName.getText().toString();
            Double price = Double.parseDouble(binding.fPrice.getText().toString());
            String brand = binding.fBrand.getText().toString();

            if( name != null && price!=null && brand !=null){


                Product myNewProd = new Product(name, price ,brand);
                createImage(myNewProd);

            }else {

                Toast.makeText(requireContext(), "Please, complete all the fields", Toast.LENGTH_SHORT).show();

            }




        }
    }


    private void updateImage(Product producto){
        if(path == null){
            myViewModel.update(producto);
            NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
        }
        else {
            StorageReference ref = reference.child("images/"+ producto.getImage());
            ref.putFile(path).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(requireContext(), "Image uploaded succesfully", Toast.LENGTH_SHORT).show();

                myViewModel.update(producto);
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }). addOnFailureListener(e -> {
                Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
            });

        }
    }

    private void createImage(Product producto){
        if(path == null){
            return;
        }
        else {
            producto.setImage(UUID.randomUUID().toString());
            StorageReference ref = reference.child("images/"+ producto.getImage());

            ref.putFile(path).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(requireContext(), "Image uploaded succesfully", Toast.LENGTH_SHORT).show();
                myViewModel.insert(producto);

                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }). addOnFailureListener(e -> {
                Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
            });

        }
    }



}