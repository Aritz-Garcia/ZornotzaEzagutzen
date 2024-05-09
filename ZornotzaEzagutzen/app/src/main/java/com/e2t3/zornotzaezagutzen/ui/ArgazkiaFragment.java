package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.R;

public class ArgazkiaFragment extends Fragment {

    private ImageView ivArgazkia;
    private String testua;
    private int delay = 30;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arkazkia, container, false);
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

        int background = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).background;
        if (background > 0) { // if there is a background
            view.setBackgroundResource(background);
        }

        // wait for the photo to finish
        ivArgazkia.postDelayed(new Runnable() {
            @Override
            public void run() {
                NabButtonsFragment.enableNextButton();
            }
        },  delay + 500);
    }

}