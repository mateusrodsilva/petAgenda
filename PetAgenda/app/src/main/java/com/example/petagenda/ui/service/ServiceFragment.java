package com.example.petagenda.ui.service;

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
import com.example.petagenda.databinding.FragmentServiceBinding;
import com.example.petagenda.ui.adapter.ServiceAdapter;

public class ServiceFragment extends Fragment {

    private FragmentServiceBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ServiceViewModel servicesViewModel =
                new ViewModelProvider(this).get(ServiceViewModel.class);

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvServices.setLayoutManager(new LinearLayoutManager(getContext()));
        servicesViewModel.getListServices().observe(getViewLifecycleOwner(), services -> {
            binding.rvServices.setAdapter(new ServiceAdapter(services, view -> {
                MainActivity activity = (MainActivity) getActivity();

                if(activity != null) {
                    view.setId(null);
                    activity.getDb().ServiceDao().create(view);
                    Toast.makeText(getActivity(), "Item adicionado ao carrinho", Toast.LENGTH_LONG).show();
                }
            }));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}