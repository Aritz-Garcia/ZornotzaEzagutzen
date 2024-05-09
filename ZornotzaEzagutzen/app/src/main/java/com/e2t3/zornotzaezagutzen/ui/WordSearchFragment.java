package com.e2t3.zornotzaezagutzen.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.BilatzekoHitza;
import com.e2t3.zornotzaezagutzen.util.FontManager;
import com.e2t3.zornotzaezagutzen.wordsearch.Palabra;
import com.e2t3.zornotzaezagutzen.wordsearch.Sopa;
import com.e2t3.zornotzaezagutzen.wordsearch.Word;
import com.e2t3.zornotzaezagutzen.wordsearch.WordSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordSearchFragment extends Fragment {

    private WordSearchView wordsGrid;
    private char[][] letters;
    private HashMap<String, TextView> wordsToFind = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wordsearch_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the words from the database
        GuneActivity guneActivity = (GuneActivity) getActivity();
        AppDatabase appDatabase = guneActivity.appDatabase;
        List<BilatzekoHitza> bilatzekoHitzak = appDatabase.bilatzekoHitzaDao().getAll();

        // get the words in an array as its needed by the wordsearch class and this
        List<String> sc = new ArrayList<>();
        for (BilatzekoHitza bilatzekoHitza : bilatzekoHitzak) {
            sc.add(bilatzekoHitza.getHitza().toUpperCase());
        }

        LinearLayout strikeLayout = view.findViewById(R.id.strikeLayout);

        for (String word : sc) {
            TextView tv = new TextView(getContext());
            tv.setText(word);
            tv.setTextSize(20);
            tv.setTextColor(Color.DKGRAY);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setTypeface(null, Typeface.BOLD);
            strikeLayout.addView(tv);
            wordsToFind.put(word, tv);
        }

        // get the wordsearch grid
        Sopa sopa = Sopa.getSopa(sc);
        letters = sopa.getMatriz();

        wordsGrid = view.findViewById(R.id.wordsGrid);
        wordsGrid.setTypeface(FontManager.getTypeface(getContext(), FontManager.POYNTER));
        wordsGrid.setLetters(letters);

        // set the words to find int the view
        Word[] words = new Word[sopa.getPalabras().size()];
        int i = 0;
        for (Palabra palabra : sopa.getPalabras()) {
            Word w = new Word(palabra.getPalabra(), false, palabra.getDesdeFila(), palabra.getDesdeColumna(), palabra.getAFila(), palabra.getAColumna());
            words[i++] = w;
        }
        wordsGrid.setWords(words);

        wordsGrid.setOnWordSearchedListener(new WordSearchView.OnWordSearchedListener() {
            @Override
            public void wordFound(String word) {
                if (!wordsToFind.isEmpty()) {
                    TextView tv = wordsToFind.get(word);
                    if (tv == null) {
                        String reverse = new StringBuilder(word).reverse().toString();
                        tv = wordsToFind.get(reverse);
                        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tv.setTextColor(Color.LTGRAY);
                        wordsToFind.remove(reverse);
                    } else {
                        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tv.setTextColor(Color.LTGRAY);
                        wordsToFind.remove(word);
                    }
                    // if all words have been found the game is over
                    if (wordsToFind.isEmpty()) {
                        NabButtonsFragment.enableNextButton();
                    }
                }
            }
        });


    }
}
