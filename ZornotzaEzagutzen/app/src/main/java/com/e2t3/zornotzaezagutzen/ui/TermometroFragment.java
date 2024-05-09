package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.R;

public class TermometroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TermometroFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // wait 3 seconds before enabling the next button
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        NabButtonsFragment.enableNextButton();
                    }
                },
                3000);
    }

    ImageView estado;
    SeekBar sek;
    TextView temperatura;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_termometro, container, false);

        temperatura = view.findViewById(R.id.temperatura);

        sek = view.findViewById(R.id.seek);
        estado = view.findViewById(R.id.estado);
        temperatura.setText(sek.getProgress() - 20 + "Cº");

        sek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int process, boolean b) {
                process = process - 20;
                temperatura.setText(process + "Cº");

                // change the image depending on the temperature
                if (process < 0) {
                    estado.setImageResource(R.drawable.hielo);
                } else if (process <= 99) {
                    estado.setImageResource(R.drawable.agua);
                } else {
                    estado.setImageResource(R.drawable.gas);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // needed to implement the interface
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // needed to implement the interface
            }
        });
        return view;
    }
}