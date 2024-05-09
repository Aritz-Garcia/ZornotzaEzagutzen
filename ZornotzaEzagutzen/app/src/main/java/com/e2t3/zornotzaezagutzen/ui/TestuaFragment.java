package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.Guneak.Typewriter;
import com.e2t3.zornotzaezagutzen.R;

public class TestuaFragment extends Fragment {

    private String testua;
    private int delay = 30;
    private LottieAnimationView lottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottieAnimationView = getActivity().findViewById(R.id.laEtxano);

        GuneActivity guneActivity = (GuneActivity) getActivity();
        testua = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).testua;

        animazioa1(lottieAnimationView, guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).animazioa);
        testua = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).testua;
        String[] testuak = testua.split(":;");
        int background = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).background;
        if (background > 0) {
            view.setBackgroundResource(background);
        }

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
        txtView.postDelayed(() -> NabButtonsFragment.enableNextButton(), testua.length() * delay + 500);
    }

    @Override
    public void onResume() {
        super.onResume();
        GuneActivity guneActivity = (GuneActivity) getActivity();
        lottieAnimationView = getActivity().findViewById(R.id.laEtxano);
        animazioa1(lottieAnimationView, guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).animazioa);
    }

    private void animazioa1(LottieAnimationView imageView, int animazioa) {
        imageView.setAnimation(animazioa);
        imageView.setRepeatCount(LottieDrawable.INFINITE);
        imageView.playAnimation();
    }
}