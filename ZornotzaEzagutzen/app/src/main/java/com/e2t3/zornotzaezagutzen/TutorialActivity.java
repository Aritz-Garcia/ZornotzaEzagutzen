package com.e2t3.zornotzaezagutzen;

import android.app.Activity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.Guneak.Typewriter;
import com.e2t3.zornotzaezagutzen.ui.NabButtonsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.atomic.AtomicReference;

public class TutorialActivity extends Activity {

    //Elementuak
    private TextView name;
    //TypeWriter clasea testua pizkanaka printgeatzen du pantailatik
    private Typewriter typewriter;
    private int delay = 30;
    private int i = 0;
    private FloatingActionButton fab;
    private ImageView imageView;

    //Bi deminsioko array bat sortzen dugu tutorialaren informazioiarekin eta irudiarekin
    private int[][] tutorialParts = {{R.string.tuto_posizioa, R.drawable.tuto_posizioa},
            {R.string.tuto_gunea, R.drawable.tuto_gunea},
            {R.string.tuto_radioa, R.drawable.tuto_radioa},
            {R.string.tuto_camara_mugitu, R.drawable.tuto_camara_mugitu},
            {R.string.tuto_progressbar, R.drawable.tuto_progressbar},
            {R.string.tuto_progressbar_erdi, R.drawable.tuto_progressbar_erdi},
            {R.string.tuto_progressbar_full, R.drawable.tuto_progressbar_full},
            {R.string.tuto_stats, R.drawable.tuto_stats},
            {R.string.tuto_tutorial, R.drawable.tuto_tutorial}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        name = findViewById(R.id.tvNombre);
        typewriter = findViewById(R.id.type_CharactersTestu);
        this.name.setText(R.string.tuto);
        imageView = findViewById(R.id.imageViewTutorial);
        fab = findViewById(R.id.floatingActionButton);
        fab.setEnabled(false);
        //drawable hartzen du int batetik
        imageView.setImageResource(tutorialParts[i][1]);
        this.typewriter.setText("");
        this.typewriter.setCharacterDelay(delay);
        // String resource-tik textua artzen du
        AtomicReference<String> text = new AtomicReference<>(getResources().getString(tutorialParts[i][0]));
        this.typewriter.animateText(text.get());                // wait for animation to finish
        typewriter.postDelayed(() -> fab.setEnabled(true), text.get().length() * 30 + 500);
        fab.setOnClickListener(v -> {
            if (i < tutorialParts.length - 1) {
                i++;
                fab.setEnabled(false);
                imageView.setImageResource(tutorialParts[i][1]);
                text.set(getResources().getString(tutorialParts[i][0]));
                this.typewriter.setText("");
                this.typewriter.setCharacterDelay(delay);
                // get the text from the string resource

                this.typewriter.animateText(text.get());                // wait for animation to finish
                typewriter.postDelayed(() -> fab.setEnabled(true), text.get().length() * 30 + 500);

            } else {
                finish();
            }
        });

    }
}