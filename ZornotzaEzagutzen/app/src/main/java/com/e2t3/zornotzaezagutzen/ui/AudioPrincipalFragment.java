package com.e2t3.zornotzaezagutzen.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.Guneak.Typewriter;
import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.appdatabase.AppDatabase;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioPrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioPrincipalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AudioPrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button playButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Timer timer;
    private boolean prepared;
    private int delay = 30;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio_principal, container, false);

        // get elements
        playButton = (Button) view.findViewById(R.id.btnplay);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar2);

        GuneActivity guneActivity = (GuneActivity) getActivity();
        String testua = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).testua;
        int audioa = guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).animazioa;

        String[] testuak = testua.split(":;"); // get the name of the character that speaks and the text they say

        mediaPlayer = MediaPlayer.create(getContext(), audioa);
        seekBar.setMax(mediaPlayer.getDuration());

        // set the text on the typewriter

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

        prepared = false;
        timer = new Timer();
        mediaPlayer.setOnPreparedListener(this::onPrepared);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (prepared && b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // method required for interface
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // method required for interface
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!prepared)
                    return;

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setText("Play");
                    timer.cancel();
                    timer = new Timer();
                } else {
                    mediaPlayer.start();
                    playButton.setText("Pause");
                    timer.schedule(new ProgressUpdate(), 0, 1000);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                NabButtonsFragment.enableNextButton();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
//        playButton.setOnClickListener(null);
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            seekBar.setProgress(msg.arg1);
        }
    };


    public void onPrepared(MediaPlayer mediaPlayer) {
        prepared = true;
    }


    private class ProgressUpdate extends TimerTask {

        public ProgressUpdate() {
            super();
        }

        @Override
        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            Message msg = new Message();
            msg.arg1 = currentPosition;
            mHandler.dispatchMessage(msg);
        }
    }
}