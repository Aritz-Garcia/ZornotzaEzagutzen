package com.e2t3.zornotzaezagutzen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

// Logoak agertzen diren karga pantaila kudeatzeko klasea
public class PantallaDeCargaPrincActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga_princ);

        pantallaCarga();
    }

    private void pantallaCarga() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Pantaila honetan 2 segundu egongo da
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(PantallaDeCargaPrincActivity.this, PantallaDeCargaActivity.class);
                startActivity(i);
            }
        }).start();
    }
}