package com.example.petagenda.ui.service;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petagenda.data.remote.ServiceApi;
import com.example.petagenda.domain.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceViewModel extends ViewModel {

    private final MutableLiveData<List<Service>> listServices = new MutableLiveData<>();
    private final ServiceApi service;

    public ServiceViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://felipeymyamada.github.io/petagenda-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ServiceApi.class);

        findServices();
    }

    private void findServices() {
        service.getServices().enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()) {
                    listServices.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
    }
    public LiveData<List<Service>> getListServices() {
        return listServices;
    }
}