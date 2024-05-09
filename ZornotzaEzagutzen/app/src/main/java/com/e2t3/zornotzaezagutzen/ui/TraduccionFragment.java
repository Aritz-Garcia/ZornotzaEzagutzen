package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;
import com.e2t3.zornotzaezagutzen.data.entities.HitzParea;

import java.util.ArrayList;
import java.util.List;

public class TraduccionFragment extends Fragment {

    private static List<TraduccionPalabraLayout> palabras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        palabras = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_traduccion, container, false);

        // get the words to translate from the database
        GuneActivity guneActivity = (GuneActivity) getActivity();
        AppDatabase appDatabase = guneActivity.appDatabase;
        List<HitzParea> hitzPareak = appDatabase.hitzPareaDao().getAll();

        // create the layout for each word
        for (HitzParea parea : hitzPareak) {
            TraduccionPalabraLayout traduccion = new TraduccionPalabraLayout(getContext(), parea.bizkaieraz, parea.batuaz);
            palabras.add(traduccion);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = view.findViewById(R.id.layoutTraduccion);
        for (TraduccionPalabraLayout palabra : palabras) {
            linearLayout.addView(palabra);
        }
    }

    // check if all the words have been translated
    public static void checkfinish() {
        boolean finish = true;
        for (TraduccionPalabraLayout palabra : palabras) {
            if (!palabra.ondo()) finish = false;
        }
        if (finish) {
            NabButtonsFragment.enableNextButton();
        }
    }
}