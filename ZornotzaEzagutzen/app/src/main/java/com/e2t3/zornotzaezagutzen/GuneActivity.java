package com.e2t3.zornotzaezagutzen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.Aktibitatea;
import com.e2t3.zornotzaezagutzen.data.entities.Gune;
import com.e2t3.zornotzaezagutzen.shared.Values;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

// Gune bakoitzaren aktibitateak erakusteko pantaila
public class GuneActivity extends AppCompatActivity {

    public Gune gunea;
    public int aktibitateZenbakia;
    public List<Aktibitatea> aktibitateak;
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle objetobundle = getIntent().getExtras();
        gunea = (Gune) objetobundle.getSerializable("gunea");

        // Datu-basea irekitzen du
        appDatabase = AppDatabase.getDatabase(getApplicationContext());
        // Aktibitateak hartzen ditu
        aktibitateak = appDatabase.aktibitateaDao().getGuneAktibitatea(gunea.zenbakia);

        setContentView(R.layout.activity_gune);
        ConstraintLayout constrainLayout = findViewById(R.id.constrainLayout);

        // Gune bakoitzeko atzeko planoak jartzen ditu
        constrainLayout.setBackgroundResource(Values.BACKGROUNDS.get(gunea.zenbakia));
    }
}