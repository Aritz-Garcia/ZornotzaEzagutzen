package com.e2t3.zornotzaezagutzen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.e2t3.zornotzaezagutzen.Guneak.Typewriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Klase hau aplikazioa lehenengo aldian irekitzeralkoan agertzen den tutoriala erakusteko klasea da
public class TutorialaZinematika extends AppCompatActivity {

    private FloatingActionButton aurreraButton;
    private int dialogoKontagailua = 0;
    private int zenbakia = 0;
    private static int delay = 30;
    private LottieAnimationView animacionEtxano;
    private static final int PERMISO_LOCALIZACION = 0;
    private SharedPreferences spZornotza;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.zinematika_tutoriala);
        //Aldagaien definizioa:
        Typewriter txtView = findViewById(R.id.twEtxanoTutorial);
        aurreraButton = findViewById(R.id.aurreraButton);
        aurreraButton.setEnabled(false);

        spZornotza = getSharedPreferences("zornotza", Context.MODE_PRIVATE);
        editor = spZornotza.edit();

        animacionEtxano = findViewById(R.id.laEtxanoAnimacion);
        animazioa1(animacionEtxano, R.raw.etxano_animacion1);

        //Daialogoak gordeko dituen array-a:
        String[]tutoriala_Gidoia = new String[4];

        //Gordetako dialogoak:
        tutoriala_Gidoia[0]=getString(R.string.txtTutorialaGidoia1);
        tutoriala_Gidoia[1]=getString(R.string.txtTutorialaGidoia2);
        tutoriala_Gidoia[2]=getString(R.string.txtTutorialaGidoia3);
        tutoriala_Gidoia[3]=getString(R.string.txtTutorialaGidoia4);


        txtView.setCharacterDelay(delay);
        txtView.postDelayed(new Runnable() {
            @Override
            public void run() {
                permisos();
                aurreraButton.setEnabled(true);
            }
        }, (long) tutoriala_Gidoia[zenbakia].length() * (delay + 20));
        txtView.animateText(tutoriala_Gidoia[dialogoKontagailua]);
        dialogoKontagailua++;
        aurreraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogoKontagailua < tutoriala_Gidoia.length) {
                    zenbakia++;
                    aurreraButton.setEnabled(false);
                    txtView.setText("");
                    txtView.setCharacterDelay(delay);
                    txtView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            permisos();
                            aurreraButton.setEnabled(true);
                        }
                    }, (long) tutoriala_Gidoia[zenbakia].length() * delay + 500);
                    txtView.animateText(tutoriala_Gidoia[dialogoKontagailua]);
                    dialogoKontagailua++;
                } else {
                    editor.putBoolean("tutoriala", true);
                    editor.putBoolean("gune1", false);
                    editor.putBoolean("gune2", false);
                    editor.putBoolean("gune3", false);
                    editor.putBoolean("gune4", false);
                    editor.putBoolean("gune5", false);
                    editor.putBoolean("gune6", false);
                    editor.putBoolean("gune7", false);
                    editor.putBoolean("gune8", false);
                    editor.putBoolean("gune9", false);
                    editor.commit();
                    Intent intent = new Intent(TutorialaZinematika.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Animazioa hasieratzeko metodoa
    private void animazioa1(LottieAnimationView imageView, int animazioa) {
        imageView.setAnimation(animazioa);
        imageView.setRepeatCount(LottieDrawable.INFINITE);
        imageView.playAnimation();
    }

    // Baimenak eskatuta badauden ala ez konprobatzen duen metodoa. Eskatuta badude ez dira berriro eskatuko, bestela eskatuko dira.
    private void permisos() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            localizacionAceptada();
        }
    }

    // Kokapen eskaera onartuta badagoen konprobatzen duen metodoa, bestela eskera egingo du.
    private void localizacionAceptada() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            denaOndo();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISO_LOCALIZACION);
        }
    }

    // Dena ondo egiten bada exekutatzen den metodoa
    private void denaOndo() {
        Toast.makeText(this, "Bahimenak onartuta", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Dena ondo", Toast.LENGTH_SHORT).show();
    }

}