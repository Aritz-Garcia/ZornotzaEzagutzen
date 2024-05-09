package com.e2t3.zornotzaezagutzen.data.daos;

import androidx.room.*;

import com.e2t3.zornotzaezagutzen.data.entities.Gune;

import java.util.List;

@Dao
public interface GuneDao {
    // Gune guztiak lortzeko kontzulta
    @Query("SELECT * FROM gune")
    List<Gune> getAll();

    // Gune bat lortzeko kontzulta
    @Query("SELECT * FROM gune WHERE zenbakia IN (:userZenbakiak)")
    List<Gune> loadAllByIds(int[] userZenbakiak);

    // Gune bat lortzeko kontzulta izenaren arabera
    @Query("SELECT * FROM gune WHERE izena LIKE :izena LIMIT 1")
    Gune findByName(String izena);

    // Gune berri bat gehitzeko kontzulta
    @Insert
    void insertAll(Gune... gune);

    // Gune bat ezabatzeko kontzulta
    @Delete
    void delete(Gune gune);
}