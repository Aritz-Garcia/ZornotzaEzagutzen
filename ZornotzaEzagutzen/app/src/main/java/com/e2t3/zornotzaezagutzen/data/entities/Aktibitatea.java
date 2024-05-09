package com.e2t3.zornotzaezagutzen.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (primaryKeys = {"guneZenb", "aktibitateZenb"})
public class Aktibitatea implements Serializable {

    @ColumnInfo
    public int guneZenb;
    @ColumnInfo
    public int aktibitateZenb;
    @ColumnInfo
    public String clazz;
    @ColumnInfo
    public String testua;
    @ColumnInfo
    public boolean amaituta;
    @ColumnInfo
    public int animazioa;
    @ColumnInfo
    public boolean jokoaDa;
    public Integer background;

    public Aktibitatea(int guneZenb, int aktibitateZenb, String clazz, String testua, boolean amaituta, int animazioa, boolean jokoaDa, Integer background) {
        this.guneZenb = guneZenb;
        this.aktibitateZenb = aktibitateZenb;
        this.clazz = clazz;
        this.testua = testua;
        this.amaituta = amaituta;
        this.animazioa = animazioa;
        this.jokoaDa = jokoaDa;
        this.background = background;
    }
}
