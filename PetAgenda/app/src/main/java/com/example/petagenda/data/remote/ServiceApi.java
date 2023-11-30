package com.example.petagenda.data.remote;

import com.example.petagenda.domain.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {
    @GET("service.json")
    Call<List<Service>> getServices();
}
