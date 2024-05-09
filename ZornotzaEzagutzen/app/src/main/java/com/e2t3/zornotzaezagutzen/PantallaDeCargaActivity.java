package com.e2t3.zornotzaezagutzen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

// Etxano eta barra de karga animazioak erakusten dituen karga pantaila kudeatzeko klasea
public class PantallaDeCargaActivity extends AppCompatActivity {

    private LottieAnimationView etxanoAnimation, animacionCarga;
    private SharedPreferences spZornotza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        etxanoAnimation = findViewById(R.id.etxanoAnimazioa);
        animacionCarga = findViewById(R.id.animacionCarga);

        spZornotza = getSharedPreferences("zornotza", Context.MODE_PRIVATE);

        cargarProgreso();
    }

    private void cargarProgreso() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    animazioa1(etxanoAnimation, R.raw.etxano_animacion1);
                    animazioa1(animacionCarga, R.raw.barra_de_carga);
                    // Pantaila honetan 2 segundu eta erdi egongo da
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (spZornotza.getBoolean("tutoriala", false)) {
                    // Tutoriala ikusita badago exekutatzen da
                    Intent i = new Intent(PantallaDeCargaActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    // Tutoriala ikusi gabe badago exekutatzen da
                    Intent i = new Intent(PantallaDeCargaActivity.this, TutorialaZinematika.class);
                    startActivity(i);
                }
            }
        }).start();
    }

    // Animazioa hasieratzeko metodoa
    private void animazioa1(LottieAnimationView imageView, int animazioa) {
        imageView.setAnimation(animazioa);
        imageView.setRepeatCount(LottieDrawable.INFINITE);
        imageView.playAnimation();
    }

}