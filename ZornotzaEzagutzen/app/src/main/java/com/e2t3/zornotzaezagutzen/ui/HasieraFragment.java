package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.data.entities.Gune;

public class HasieraFragment extends Fragment {

    private Gune gunea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        GuneActivity guneActivity = (GuneActivity) getActivity();
        assert guneActivity != null;
        gunea = guneActivity.gunea;

        return inflater.inflate(R.layout.fragment_hasiera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupData(view);
    }
    private void setupData(View view){
        TextView zenbakiaText = view.findViewById(R.id.guneZenbakiaText);
        TextView izenaText = view.findViewById(R.id.guneIzenaText);

        // set text
        zenbakiaText.setText(String.valueOf(gunea.zenbakia) + ".) Gunea");
        zenbakiaText.setTextColor(getResources().getColor(R.color.button_berdea_pressed));
        izenaText.setText(gunea.izena);
        izenaText.setTextColor(getResources().getColor(R.color.button_berdea_pressed));

        // get image
        ImageView guneIrudia = view.findViewById(R.id.guneIrudia);
        if (gunea.irudia > 0) {
            guneIrudia.setVisibility(View.VISIBLE);
            guneIrudia.setImageResource(gunea.irudia);
        } else {
            guneIrudia.setVisibility(View.GONE);
        }
    }
}
