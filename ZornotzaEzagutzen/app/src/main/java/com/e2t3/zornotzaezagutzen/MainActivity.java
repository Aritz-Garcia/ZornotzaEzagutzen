package com.e2t3.zornotzaezagutzen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.Gune;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Aplikazioaren hasiera-eremua duen klasea
public class MainActivity extends AppCompatActivity {

    static ProgressBar progressBarGuneak;
    private FloatingActionButton btnEtxanoInfo, btnEzarpenakMain;
    public AppDatabase appDatabase;
    private static SharedPreferences spZornotza;
    private static SharedPreferences.Editor editor;


    private long startTime = 0L;
    private long timeInterval = 0L;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEtxanoInfo = findViewById(R.id.btnEtxanoInfo);
        btnEzarpenakMain = findViewById(R.id.btnEzarpenakMain);
        progressBarGuneak = findViewById(R.id.progressBar);
        SeperatedProgressbar bgProgress= new SeperatedProgressbar(ContextCompat.getColor(this,R.color.progressPg),ContextCompat.getColor(this,R.color.progressBg),this);
        progressBarGuneak.setProgressDrawable(bgProgress);
        progressBarGuneak.setMax(80); // must be 80 if you want it to be so incrementing 1 is 10% of 8 so x10
        progressBarGuneak.setProgress(0);
        appDatabase = AppDatabase.getDatabase(getApplicationContext());

        spZornotza = getSharedPreferences("zornotza", Context.MODE_PRIVATE);
        editor = spZornotza.edit();
        progressBarKonp();



        btnEzarpenakMain.setOnTouchListener((v, event) -> {
            Intent intent = new Intent(this, Estats.class);
            startActivity(intent);
            return true;
        });

        btnEtxanoInfo.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                //USER START PRESSING THE BUTTON
                startTime = SystemClock.elapsedRealtime();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //USER STOP PRESSING THE BUTTON
                timeInterval = SystemClock.elapsedRealtime() - startTime;
                if (timeInterval > 5000) {
                    MapsFragment.initializeDeveloperMode();
                    timeInterval = 0;
                    Toast.makeText(MainActivity.this, "Developer mode on", Toast.LENGTH_SHORT).show();
                } else {
                    // Etxanok informazioa eman
                    Intent intent = new Intent(this, TutorialActivity.class);
                    startActivity(intent);
                }
            }
            return true;
        });
    }

    // Progress bar-ari bat gehitzen dion metodoa
    @SuppressLint("NewApi")
    public static void sumar(int gunea) {
        String guneaString = "gune" + gunea;
        boolean guneaBoolean = spZornotza.getBoolean(guneaString, false);
        if (!guneaBoolean) {
            if (gunea != 9) {
                progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
            }
            editor.putBoolean(guneaString, true);
            editor.commit();
        }
    }

    // Progress bar-a eguneratzen duen metodoa
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void progressBarKonp() {
        boolean gune1 = spZornotza.getBoolean("gune1", false);
        boolean gune2 = spZornotza.getBoolean("gune2", false);
        boolean gune3 = spZornotza.getBoolean("gune3", false);
        boolean gune4 = spZornotza.getBoolean("gune4", false);
        boolean gune5 = spZornotza.getBoolean("gune5", false);
        boolean gune6 = spZornotza.getBoolean("gune6", false);
        boolean gune7 = spZornotza.getBoolean("gune7", false);
        boolean gune8 = spZornotza.getBoolean("gune8", false);
        boolean gune9 = spZornotza.getBoolean("gune9", false);

        if (gune1) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune2) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune3) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune4) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune5) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune6) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune7) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (gune8) {
            progressBarGuneak.setProgress(progressBarGuneak.getProgress() + 1, true);
        }
        if (progressBarGuneak.getProgress() == 8 && !gune9) {
            Gune gune9Info = new Gune("Agurra", 9, 0, 0, 0);
            Intent intent = new Intent(this, GuneActivity.class);
            intent.putExtra("gunea", gune9Info);
            startActivity(intent);
        }

    }
}
