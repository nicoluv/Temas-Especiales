package com.example.myapplicationrlv.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrlv.R;
import com.example.myapplicationrlv.database.Product;
import com.example.myapplicationrlv.database.RecyclerViewInterface;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> myList;
    FirebaseStorage storage;
    StorageReference reference;
    private final RecyclerViewInterface recyclerViewInterface;

    public ProductAdapter(List<Product> myList,RecyclerViewInterface recycler) {
        this.myList=myList;
        this.recyclerViewInterface = recycler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, parent, false),recyclerViewInterface);

    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        System.out.println(myList.get(position).getImage());

            StorageReference path = reference.child("images/"+myList.get(position).getImage());
            path.getBytes(1024*1024).addOnSuccessListener(bytes -> {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.img.setImageBitmap(bm);
                    });

            holder.name.setText(myList.get(position).getName());
            holder.brand.setText(myList.get(position).getBrand());
            holder.price.setText("$ "+myList.get(position).getPrice().toString());
        }

    @Override
    public int getItemCount() {

        if(myList != null){
            return myList.size();
        }
        return 0;
    }

    public List<Product> getProducts() {
        return myList;
    }

    public void setList(List<Product> myListProd) {
        myList = myListProd;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView name, brand, price;
         Button btnEdit;
         ImageView img;

        public ViewHolder(@NonNull @NotNull View itemView, RecyclerViewInterface myInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            brand = itemView.findViewById(R.id.brand);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.imageView);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            btnEdit.setOnClickListener(view -> {
                if (myInterface != null){
                    int viewPosition = getAbsoluteAdapterPosition();

                    if(viewPosition != RecyclerView.NO_POSITION){
                        myInterface.onEdit(viewPosition);
                    }

                }
            });


        }


    }
}


