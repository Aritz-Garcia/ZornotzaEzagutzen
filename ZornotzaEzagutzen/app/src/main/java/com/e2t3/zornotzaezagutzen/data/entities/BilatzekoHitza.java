package com.e2t3.zornotzaezagutzen.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class BilatzekoHitza implements Serializable {
    @PrimaryKey
    public int id;
    @ColumnInfo
    public String hitza;

    public BilatzekoHitza(int id, String hitza) {
        this.id = id;
        this.hitza = hitza;
    }

    public String getHitza() {
        return hitza;
    }
}