package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.Guneak.Typewriter;
import com.e2t3.zornotzaezagutzen.R;

public class ArkazkiTestuaFragment extends Fragment {

    private ImageView ivArgazkia;
    private String testua;
    private int delay = 30;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arkazki_testua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivArgazkia = getActivity().findViewById(R.id.ivArgazkia);

        GuneActivity guneActivity = (GuneActivity) getActivity();

        int argazkia = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).animazioa;

        if (argazkia > 0) { // if there is a photo
            ivArgazkia.setImageResource(argazkia);
        }
        testua = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).testua;
        String[] testuak = testua.split(":;");
        int background = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).background;
        if (background > 0) {
            view.setBackgroundResource(background);
        }

        // setup text animation with typewriter effect
        Typewriter txtView = view.findViewById(R.id.type_CharactersTestu);
        txtView.setText("");
        txtView.setCharacterDelay(delay);

        Typewriter izena = view.findViewById(R.id.tvNombre);
        izena.setText("");
        izena.setCharacterDelay(delay);

        if (testuak.length != 1) {
            izena.setText(testuak[0]);
            txtView.animateText(testuak[1]);
        } else {
            txtView.animateText(testuak[0]);
        }
        // wait for animation to finish
        txtView.postDelayed(new Runnable() {
            @Override
            public void run() {
                NabButtonsFragment.enableNextButton();
            }
        }, testua.length() * delay + 500);
    }

}