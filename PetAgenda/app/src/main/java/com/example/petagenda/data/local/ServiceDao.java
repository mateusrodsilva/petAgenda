package com.example.petagenda.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.petagenda.domain.Service;

import java.util.List;

@Dao
public interface ServiceDao {
    @Insert
    void create(Service service);

    @Query("SELECT * FROM service WHERE cart = 1")
    List<Service> getServiceInCart();

    @Delete
    void delete(Service service);
}