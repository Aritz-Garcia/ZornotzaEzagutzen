package com.e2t3.zornotzaezagutzen.shared;

import android.graphics.Color;

import com.e2t3.zornotzaezagutzen.R;

import java.util.HashMap;
import java.util.Map;

public class Values {
    public static final int fillColor = Color.argb(50, 41, 150, 230);
    public static final int strokeColor = Color.argb(255, 41, 150, 230);
    public static final String PACKAGE_PATH = "com.e2t3.zornotzaezagutzen";
    public static final String UI_PATH = PACKAGE_PATH + ".ui";

    public static final Map<Integer,Integer> BACKGROUNDS = new HashMap<>() {{
        put(1, R.drawable.fondo_berdea);
        put(2, R.drawable.fondo_gorria);
        put(3, R.drawable.fondo_urdina);
        put(4, R.drawable.fondo_morea);
        put(5, R.drawable.fondo_larrosa);
        put(6, R.drawable.fondo_horia);
        put(7, R.drawable.fondo_pistatxo);
        put(8, R.drawable.fondo_turkesa);
        put(9, R.drawable.fondo_berdea);
    }};
}
