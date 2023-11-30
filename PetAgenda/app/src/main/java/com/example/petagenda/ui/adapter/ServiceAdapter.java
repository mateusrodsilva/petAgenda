package com.example.petagenda.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagenda.databinding.ServiceItemBinding;
import com.example.petagenda.domain.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private final List<Service> listService;
    private final AddToCartListener listener;

    public ServiceAdapter(List<Service> listService, AddToCartListener listener) {
        this.listService = listService;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ServiceItemBinding binding = ServiceItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = this.listService.get(position);
        holder.binding.tvTitle.setText(service.getTitle());
        holder.binding.tvDesc.setText(service.getDescription());
        holder.binding.txPrice.setText("R$ " + service.getPrice().toString());
        holder.binding.tnPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()) {
                    Double dPeso = Double.parseDouble(s.toString());
                    Double dPrice = 0.0;
                    if (dPeso >= 0 && dPeso <= 5) {
                        dPrice = 40.0;
                    } else if (dPeso >= 6 && dPeso <= 10) {
                        dPrice = 50.0;
                    } else {
                        dPrice = 60.0;
                    }
                    service.setPrice(dPrice);
                    holder.binding.txPrice.setText("R$ " + dPrice.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Picasso.get()
                .load(service.getImg_url())
                .fit()
                .into(holder.binding.ivImage);

        holder.binding.btBuy.setOnClickListener(view -> {
            if(!holder.binding.tnPeso.getText().toString().isEmpty()) {
                service.setPeso(Double.valueOf(holder.binding.tnPeso.getText().toString()));
                service.setCart(true);
                listener.onClick(service);
                notifyItemChanged(position);
            } else {
                holder.binding.tnPeso.setError("Informe o peso");
            }

        });
    }
    @Override
    public int getItemCount() {
        return listService.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ServiceItemBinding binding;
        public ViewHolder(ServiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface AddToCartListener {
        void onClick(Service service);
    }

}
