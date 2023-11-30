package com.example.petagenda.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.petagenda.MainActivity;
import com.example.petagenda.databinding.FragmentCartBinding;
import com.example.petagenda.domain.Service;
import com.example.petagenda.ui.adapter.CartAdapter;

import java.util.List;


public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private List<Service> serviceList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);

        MainActivity activity = (MainActivity) getActivity();
        serviceList = activity.getDb().ServiceDao().getServiceInCart();
        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCart.setAdapter(new CartAdapter(serviceList, updatedService -> {
            removeProduct(serviceList, updatedService);
            activity.getDb().ServiceDao().delete(updatedService);
            Toast.makeText(getActivity(), "Item removido do carrinho", Toast.LENGTH_LONG).show();
        }));
        binding.btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvTotal.setText("0.0");
                for (Service s : serviceList) {
                    activity.getDb().ServiceDao().delete(s);
                }
                new CartAdapter(serviceList, ).checkout();
                Toast.makeText(getActivity(), "Compra realizada com sucesso", Toast.LENGTH_LONG).show();
            }
        });

        updateTotalValue(serviceList);

        return binding.getRoot();
    }
    private void updateTotalValue(List<Service> serviceList) {
        double total = 0;
        for(Service s : serviceList) {
            total += s.getPrice();
        }
        binding.tvTotal.setText(String.valueOf(total));
    }

    private void removeProduct(List<Service> serviceList, Service service) {
        serviceList.remove(service);
        updateTotalValue(serviceList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}