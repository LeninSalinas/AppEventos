package com.example.appeventos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventoDao {

    @Insert
    void insert(Evento nuevoEvento);

    @Update
    void Update(Evento modEvento);

    @Delete
    void delete(Evento delEvento);

    @Query("DELETE FROM evento_table")
    void deleteAll();

    @Query("SELECT * FROM evento_table order by fecha")
    LiveData<List<Evento>> getEvento();
}
