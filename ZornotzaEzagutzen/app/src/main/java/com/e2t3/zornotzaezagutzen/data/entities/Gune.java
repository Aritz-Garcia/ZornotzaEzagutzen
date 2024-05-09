package com.e2t3.zornotzaezagutzen.data.entities;

import androidx.room.*;

import com.e2t3.zornotzaezagutzen.shared.Values;
import com.e2t3.zornotzaezagutzen.R;

import java.io.Serializable;
import java.util.LinkedList;

@Entity
public class Gune implements Serializable {

    @ColumnInfo
    public String izena;
    @PrimaryKey
    public int zenbakia;
    @ColumnInfo
    public double latitude;
    @ColumnInfo
    public double longitude;
    @ColumnInfo
    public int irudia;

    public Gune(String izena, int zenbakia, double latitude, double longitude, int irudia) {
        this.izena = izena;
        this.zenbakia = zenbakia;
        this.latitude = latitude;
        this.longitude = longitude;
        this.irudia = irudia;
    }

//    public Gune(String izena, int zenbakia, double latitude, double longitude, LinkedList<Aktibitatea> aktibitateak, int irudia) {
//        this.izena = izena;
//        this.zenbakia = zenbakia;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.aktibitateak = aktibitateak;
//        this.irudia = irudia;
//    }
}
