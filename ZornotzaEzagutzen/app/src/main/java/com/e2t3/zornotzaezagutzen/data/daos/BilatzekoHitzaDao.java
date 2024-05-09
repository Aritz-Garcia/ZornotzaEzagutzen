package com.e2t3.zornotzaezagutzen.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.e2t3.zornotzaezagutzen.data.entities.BilatzekoHitza;

import java.util.List;

@Dao
public interface BilatzekoHitzaDao {
    // Bilatzeko hitz guztiak lortzeko kontzulta
    @Query("SELECT * FROM bilatzekoHitza")
    List<BilatzekoHitza> getAll();

    // Bilatzeko hitza bat lortzeko kontzulta
    @Insert
    void insertAll(BilatzekoHitza... bilatzekoHitzak);

    // Bilatzeko hitza bat ezabatzeko kontzulta
    @Delete
    void delete(BilatzekoHitza bilatzekoHitza);
}