package com.example.petagenda.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petagenda.domain.Service;

import java.util.List;

public class CartViewModel extends ViewModel {

    private final MutableLiveData<List<Service>> listServices = new MutableLiveData<>();

    public CartViewModel() {

    }

}