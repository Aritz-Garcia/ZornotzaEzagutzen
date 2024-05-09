package com.e2t3.zornotzaezagutzen.Guneak;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.ui.PuzzleFragment;

public class TouchListener implements View.OnTouchListener {
    private float xDelta;
    private float yDelta;
    private PuzzleFragment fragment; //Puzzle fragmentua

    public TouchListener(PuzzleFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();
        final double tolerance = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;
        MediaPlayer mp = MediaPlayer.create(fragment.getContext(), R.raw.sonido);//Pieza bere lekuan jartzerakoan soinu bat eragiten du

        //Bere balorea false bada deritzo pieza bere lekuan dagoela
        PuzzlePiece piece = (PuzzlePiece) view;
        if (!piece.canMove) {
            return true;
        }
        //Layout honen barruan piezak bakarrik mugitu ahal dira
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                //Click egiterakon pieza batean, aurrera eramanten du mugitzeko
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                piece.bringToFront();
                break;
                //Pieza mugitzerakoan bre paramentroak aldatzen dira Layout-ean
            case MotionEvent.ACTION_MOVE:
                lParams.leftMargin = (int) (x - xDelta);
                lParams.topMargin = (int) (y - yDelta);
                view.setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_UP:
                int xDiff = abs(piece.xCoord - lParams.leftMargin);
                int yDiff = abs(piece.yCoord - lParams.topMargin);
                //Kondizio honekin pieza bere lekuan geratu egiten da
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = piece.xCoord;
                    lParams.topMargin = piece.yCoord;
                    piece.setLayoutParams(lParams);
                    piece.canMove = false;
                    mp.start();//hemen soinua azten da
                    sendViewToBack(piece);
                    fragment.checkGameOver(view);
                }
                break;
        }

        return true;
    }

    //Bere lekura ailegatzerakoan atzera bidaltzen du
    public void sendViewToBack(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}
