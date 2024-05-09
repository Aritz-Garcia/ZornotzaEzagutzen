package com.e2t3.zornotzaezagutzen.appdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.e2t3.zornotzaezagutzen.data.daos.AktibitateaDao;
import com.e2t3.zornotzaezagutzen.data.daos.BilatzekoHitzaDao;
import com.e2t3.zornotzaezagutzen.data.daos.EstadistikakDao;
import com.e2t3.zornotzaezagutzen.data.daos.GuneDao;
import com.e2t3.zornotzaezagutzen.data.daos.HitzPareaDao;
import com.e2t3.zornotzaezagutzen.data.entities.Aktibitatea;
import com.e2t3.zornotzaezagutzen.data.entities.BilatzekoHitza;
import com.e2t3.zornotzaezagutzen.data.entities.Estadistikak;
import com.e2t3.zornotzaezagutzen.data.entities.Gune;
import com.e2t3.zornotzaezagutzen.data.entities.HitzParea;


@Database(entities = {Gune.class, Aktibitatea.class, HitzParea.class, BilatzekoHitza.class, Estadistikak.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "dbZornotza")
                            .createFromAsset("databases/dbZornotza.db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract GuneDao guneDao();
    public abstract AktibitateaDao aktibitateaDao();
    public abstract BilatzekoHitzaDao bilatzekoHitzaDao();
    public abstract HitzPareaDao hitzPareaDao();
    public abstract EstadistikakDao estadistikakDao();
}