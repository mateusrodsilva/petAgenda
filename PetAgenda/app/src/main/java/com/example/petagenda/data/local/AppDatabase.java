package com.example.petagenda.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.petagenda.domain.Service;

@Database(entities = {Service.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ServiceDao ServiceDao();

}
