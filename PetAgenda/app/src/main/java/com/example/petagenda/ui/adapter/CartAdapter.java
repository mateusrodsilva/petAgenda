package com.example.petagenda.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagenda.databinding.CartItemBinding;
import com.example.petagenda.domain.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<Service> listService;
    private final RemoveListener listener;

    public CartAdapter(List<Service> listService, RemoveListener listener) {
        this.listService = listService;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartItemBinding binding = CartItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = this.listService.get(position);
        holder.binding.tvTitle.setText(service.getTitle());
        holder.binding.tvDesc.setText(service.getDescription());
        holder.binding.txPrice.setText("R$ " + service.getPrice().toString());
        holder.binding.tvPeso.setText(service.getPeso().toString());
        Picasso.get()
                .load(service.getImg_url())
                .fit()
                .into(holder.binding.ivImage);

        holder.binding.ivTrash.setOnClickListener(view -> {
            listener.onClick(service);
            notifyItemChanged(position);
            notifyDataSetChanged();
        });

    }

    public void checkout(){
        this.listService.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;
        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface RemoveListener {
        void onClick(Service service);
    }
}