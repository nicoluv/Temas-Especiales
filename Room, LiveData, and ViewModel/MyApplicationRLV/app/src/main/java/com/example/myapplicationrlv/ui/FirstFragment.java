package com.example.myapplicationrlv.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrlv.R;
import com.example.myapplicationrlv.database.Product;
import com.example.myapplicationrlv.database.RecyclerViewInterface;
import com.example.myapplicationrlv.databinding.FragmentFirstBinding;
import com.example.myapplicationrlv.models.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements RecyclerViewInterface {

    private FragmentFirstBinding binding;
    List<Product> products;
    ProductAdapter productAdapter;
    MyViewModel viewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        productAdapter = new ProductAdapter( null,(RecyclerViewInterface) this);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        viewModel.findAll().observe(getViewLifecycleOwner(), products -> productAdapter.setList(products));

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerview.setAdapter(productAdapter);

        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onEdit(int position) {

        Bundle mybundle = new Bundle();

        products = productAdapter.getProducts();
        Product product = products.get(position);
        mybundle.putSerializable("myProducts", product);
        System.out.println(product.getBrand());

        NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, mybundle);


    }


}