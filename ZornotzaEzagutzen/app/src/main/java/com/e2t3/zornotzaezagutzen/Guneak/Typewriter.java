package com.e2t3.zornotzaezagutzen.Guneak;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class Typewriter extends androidx.appcompat.widget.AppCompatTextView {
    //Elementuak
    private CharSequence myText;
    private int myIndez;
    private long myDelay = 150;
    //Bi eraikitzaile bata context-arekin eta beste berdina eta AttributeSet
    public Typewriter(Context context){
        super(context);
    }

    public Typewriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //Handler bat sortzen dugu
    private Handler myhandler = new Handler();
    //
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(myText.subSequence(0, myIndez++));
            if(myIndez<=myText.length()){
                myhandler.postDelayed(characterAdder, myDelay);
            }
        }
    };
    //String bat pasatzerakoan printetuko du pizkanaka karaktereak, delay denborarekin
    public void animateText(CharSequence myTxt){
        myText= myTxt;
        myIndez =0;

        setText("");

        myhandler.removeCallbacks(characterAdder);
        myhandler.postDelayed(characterAdder, myDelay);
    }

    //Se denbora eramango du testuaren letra bakoitza printeatzen
    public void setCharacterDelay(long m){
        myDelay = m;
    }
}
