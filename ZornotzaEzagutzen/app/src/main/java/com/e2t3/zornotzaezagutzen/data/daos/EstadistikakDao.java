package com.e2t3.zornotzaezagutzen.data.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.e2t3.zornotzaezagutzen.data.entities.Estadistikak;

import java.sql.Date;
import java.util.List;

@Dao
public interface EstadistikakDao {
    // Estadistikak guztiak lortzeko kontzulta
    @Query("SELECT * FROM estadistikak ORDER BY fecha ASC")
    List<Estadistikak> getAll();

    // Gune bateko estadistika guztiak lortzeko kontzulta gune zenbakiaren arabera.
    @Query("SELECT * FROM estadistikak WHERE guneZenb LIKE :guneZnb")
    List<Estadistikak> getEstadistikakGune(int guneZnb);

    // Estadistika bakarreko datuak lortzeko kontzulta gune zenbaki eta eguna sartuz.
    @Query("SELECT * FROM estadistikak WHERE guneZenb LIKE :guneZnb AND fecha LIKE :fecha LIMIT 1")
    Estadistikak findById(int guneZnb, String fecha);

    // Estadistika bat eguneratzeko kontzulta
    @Query("UPDATE estadistikak SET guneZenb = :guneZnb, fecha = :fecha, vecesJugado = vecesJugado + 1 WHERE guneZenb = :guneZnb AND fecha = :fecha")
    void update(int guneZnb, String fecha);

    // Estadistika berri bat gehitzeko kontzulta
    @Insert
    void insertAll(Estadistikak... estadistikak);

    // Estadistika bat ezabatzeko kontzulta
    @Delete()
    void delete(Estadistikak estadistikak);

    // Estadistika bat ezabatzeko kontzulta egunaren arabera
    @Query("DELETE FROM estadistikak WHERE fecha=:data")
    void deleteBydAY(String data);
}
