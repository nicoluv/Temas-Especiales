package com.pucmm.examente;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
{
    private List<Producto> list;
    public ItemAdapter(List<Producto> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout , parent , false);

        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {

        Producto producto = list.get(position);

        holder.articulo.setText(producto.getArticulo());
        holder.precio.setText(Integer.toString(producto.getPrecio()));
        holder.descripcion.setText(producto.getDescripcion());

        holder.compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Producto- " + producto.getArticulo(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView articulo;
        TextView descripcion;
        TextView precio;
        Button compartir,eliminar;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            articulo = itemView.findViewById(R.id.articulo);
            precio = itemView.findViewById(R.id.precio);
            descripcion = itemView.findViewById(R.id.descripcion);
            compartir = itemView.findViewById(R.id.btnCompartir);
            eliminar= itemView.findViewById(R.id.btnEliminar);

        }
    }
}
