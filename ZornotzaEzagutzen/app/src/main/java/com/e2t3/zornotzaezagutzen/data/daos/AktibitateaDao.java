package com.e2t3.zornotzaezagutzen.data.daos;

import androidx.room.*;

import com.e2t3.zornotzaezagutzen.data.entities.Aktibitatea;

import java.io.Serializable;
import java.util.List;

@Dao
public interface AktibitateaDao extends Serializable {
    // Aktibitate guztiak lortzeko kontzulta
    @Query("SELECT * FROM aktibitatea ORDER BY guneZenb ASC")
    List<Aktibitatea> getAll();

    // Gune bateko aktibitate guztiak lortzeko kontzulta gune zenbakiaren arabera.
    @Query("SELECT * FROM aktibitatea WHERE guneZenb LIKE :guneZnb ORDER BY aktibitateZenb ASC")
    List<Aktibitatea> getGuneAktibitatea(int guneZnb);

    // Aktibitate bakarreko datuak lortzeko kontzulta gune zenbaki eta akibitate zenbakia sartuz.
    @Query("SELECT * FROM aktibitatea WHERE guneZenb LIKE :guneZnb AND aktibitateZenb LIKE :aktibitateaZnb LIMIT 1")
    Aktibitatea findById(int guneZnb, int aktibitateaZnb);

    // Aktibitate berri bat gehitzeko kontzulta
    @Insert
    void insertAll(Aktibitatea... Aktibitatea);

    // Aktibitate bat ezabatzeko kontzulta
    @Delete
    void delete(Aktibitatea aktibitatea);

    // Aktibitate bat eguneratzeko kontzulta
    @Update
    void update(Aktibitatea aktibitatea);
}