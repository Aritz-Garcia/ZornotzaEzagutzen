package com.e2t3.zornotzaezagutzen.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;

@Entity
public class HitzParea implements Serializable {

    @PrimaryKey
    public int id;
    @ColumnInfo
    public String bizkaieraz;
    @ColumnInfo
    public String batuaz;

    public HitzParea(int id, String bizkaieraz, String batuaz) {
        this.id = id;
        this.bizkaieraz = bizkaieraz;
        this.batuaz = batuaz;
    }
}