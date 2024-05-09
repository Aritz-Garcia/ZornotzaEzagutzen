package com.e2t3.zornotzaezagutzen.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.e2t3.zornotzaezagutzen.data.entities.HitzParea;

import java.util.List;

@Dao
public interface HitzPareaDao {
    // Hitz pare guztiak lortzeko kontzulta
    @Query("SELECT * FROM hitzparea")
    List<HitzParea> getAll();

    // Hitz pare bat lortzeko kontzulta
    @Insert
    void insertAll(HitzParea... hitzParea);

    // Hitz pare bat ezabatzeko kontzulta
    @Delete
    void delete(HitzParea hitzParea);
}
