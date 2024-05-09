package com.e2t3.zornotzaezagutzen.ui;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.R;

import java.util.Timer;
import java.util.TimerTask;

public class PreguntasFragment extends Fragment implements MediaPlayer.OnPreparedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreguntasFragment() {
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

    Button btn1, btn2, btn3;
    String[] respuestas ={"Txina", "Tanzania", "Brasil"};
    int[] fotos ={R.drawable.txina, R.drawable.tanzania, R.drawable.brasil};
    String[][] opciones = {{"Japon", "Txina", "Australia"},{"Jamaika", "Tanzania", "Finalndia"},{"Brasil","Cuba", "Miami"}};
    int pregunta=1;
    int pasa;
    TextView txt;
    Button playButton, siguiente;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    ImageView imageView;
    private Timer timer;
    private boolean prepared;
    public boolean click = false;
    public boolean siguienteOk = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preguntas, container, false);


        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        txt = view.findViewById(R.id.pagina);
        imageView = view.findViewById(R.id.imageView);
        siguiente = view.findViewById(R.id.btnSiguiente);

        playButton = (Button) view.findViewById(R.id.playbutton);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);

        setMusica(1, false);
        seekBar.setMax(mediaPlayer.getDuration());

        prepared = false;
        timer = new Timer();
        mediaPlayer.setOnPreparedListener(this);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(prepared && b){
                    mediaPlayer.seekTo(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // needed for interface
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // needed for interface
            }
        });

        playButton.setOnClickListener(view15 -> {
            if (!prepared)
                return;

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playButton.setText("Play");
                click = true;
                timer.cancel();
                timer = new Timer();
            } else {
                mediaPlayer.start();
                playButton.setText("Pause");
                click = false;
                timer.schedule(new ProgressUpdate(),0,1000);
            }
        });

        btn1.setOnClickListener(view14 -> clickButton(btn1, btn2, btn3));
        btn2.setOnClickListener(view13 -> clickButton(btn2, btn1, btn3));
        btn3.setOnClickListener(view12 -> clickButton(btn3, btn1, btn2));

        siguiente.setOnClickListener(view1 -> { if(siguienteOk) cambiorespuestas(); });

        mediaPlayer.setOnCompletionListener(mediaPlayer -> click = true);
        return view;
    }

    /**
     * This method sets the color of the buttons when clicked and checks if the answer is correct
     */
    private void clickButton(Button buttonOna, Button buttonTxarra1, Button buttonTxarra2) {
        if(click) {
            if (buttonOna.getText().equals(respuestas[pregunta - 1])) {
                pregunta++;
                buttonOna.setBackgroundColor(Color.parseColor("#10BC04"));
                buttonTxarra1.setBackgroundColor(Color.parseColor("#00BCD4"));
                buttonTxarra2.setBackgroundColor(Color.parseColor("#00BCD4"));
                imageView.setVisibility(View.VISIBLE);
                siguienteOk = true;
            } else {
                siguienteOk = false;
                buttonOna.setBackgroundColor(Color.parseColor("#F80606"));
                buttonTxarra1.setBackgroundColor(Color.parseColor("#00BCD4"));
                buttonTxarra2.setBackgroundColor(Color.parseColor("#00BCD4"));
            }
        }
        // if the answer is the last one, the next button is enabled
        if (pregunta >= 4){
            NabButtonsFragment.enableNextButton();
            siguiente.setEnabled(false);
        }
    }

    public void setMusica(int audioRecibido, boolean audioLoop){

        //Detenemos la música si se está reproduciendo:
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

        //Elegimos el tema a reproducir:
        if(audioRecibido == 1){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.txina);
        }
        if(audioRecibido == 2){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.tanzania);
        }
        if(audioRecibido== 3){
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.brasil);
        }

        //Indicamos si se reproduce o no en loop:
        mediaPlayer.setLooping(audioLoop);

    }
    public void cambiorespuestas(){
        // set the next question and answers and reset the buttons
        btn1.setBackgroundColor(Color.parseColor("#00BCD4"));
        btn2.setBackgroundColor(Color.parseColor("#00BCD4"));
        btn3.setBackgroundColor(Color.parseColor("#00BCD4"));
        btn1.setText(opciones[pregunta-1][0]);
        btn2.setText(opciones[pregunta-1][1]);
        btn3.setText(opciones[pregunta-1][2]);
        txt.setText(pregunta+"/3");
        imageView.setVisibility(View.INVISIBLE);
        imageView.setImageResource(fotos[pregunta-1]);
        setMusica(pregunta, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            seekBar.setProgress(msg.arg1);
        }
    };

    @Override
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