package com.e2t3.zornotzaezagutzen.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity (primaryKeys = {"guneZenb", "fecha"})
public class Estadistikak {

    @ColumnInfo
    public int guneZenb;
    @ColumnInfo
    @NotNull
    public String fecha;
    @ColumnInfo
    public int vecesJugado;

    public Estadistikak(int guneZenb, String fecha, int vecesJugado) {
        this.guneZenb = guneZenb;
        this.fecha = fecha;
        this.vecesJugado = vecesJugado;
    }
}
