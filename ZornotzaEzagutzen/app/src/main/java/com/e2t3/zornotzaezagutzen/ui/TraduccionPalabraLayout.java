package com.e2t3.zornotzaezagutzen.ui;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.R;

/**
 * This class is used to create a layout for each word to be translated.
 */
public class TraduccionPalabraLayout extends LinearLayout {

    private String batuaz;
    private String bizkaieraz;
    private EditText editText;

    public TraduccionPalabraLayout(Context context, String bizkaieraz, String batuaz) {
        super(context);
        this.batuaz = batuaz;
        this.bizkaieraz = bizkaieraz;
        inflate(context, R.layout.traduccion_palabra_layout, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // set the text of the word to be translated
        TextView textView = findViewById(R.id.bizkaieraz);
        textView.setText(bizkaieraz);
        editText = findViewById(R.id.batueraz);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (ondo()) {
                editText.setEnabled(false);
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setVisibility(VISIBLE);
                TraduccionFragment.checkfinish();
            }
            return false;
        });
    }

    public boolean ondo() {
        return batuaz.equalsIgnoreCase(editText.getText().toString());
    }
}