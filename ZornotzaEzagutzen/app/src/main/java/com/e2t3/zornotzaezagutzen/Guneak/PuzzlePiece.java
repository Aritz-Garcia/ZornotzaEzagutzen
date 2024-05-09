package com.e2t3.zornotzaezagutzen.Guneak;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

//Puzzlearen piezak klasea deritzo
public class PuzzlePiece extends AppCompatImageView {
    //pantailatik mugitzerakoan kordenatuak gordetzeko
    public int xCoord;
    public int yCoord;
    //Piezaren altuera eta zabalera gordetzeko
    public int pieceWidth;
    public int pieceHeight;

    public boolean canMove = true;//aldatzen da bere lekuan kokatzerakoan

    public PuzzlePiece(Context context) {
        super(context);
    }
}
