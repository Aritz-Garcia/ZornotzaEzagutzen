package com.e2t3.zornotzaezagutzen.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.R;

import java.sql.Struct;

public class DragFragment extends Fragment {

    private static GridLayout gridLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drag, container, false);
        return view;
    }

    public static void checkfinish() {
        if (gridLayout.getChildCount() == 4) NabButtonsFragment.enableNextButton();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the elements
        gridLayout = view.findViewById(R.id.dragGrid);
        gridLayout.setOnDragListener(new MyDragListener());

        LinearLayout dragLayout = view.findViewById(R.id.dragLayout);
        dragLayout.setOnDragListener(new MyDragListener());

        // add the custom drag elements to the grid
        gridLayout.addView(new CustomDragLayout(getContext(), R.drawable.buda, getString(R.string.budismoa)));
        gridLayout.addView(new CustomDragLayout(getContext(), R.drawable.jesus, getString(R.string.kristautasuna)));
        gridLayout.addView(new CustomDragLayout(getContext(), R.drawable.castillo, getString(R.string.islamismoa)));
        gridLayout.addView(new CustomDragLayout(getContext(), R.drawable.seisbrazos, getString(R.string.hinduismoa)));

        // using a custom method to create the textviews all similar
        TextView textView = customTextView(R.string.hinduismoa);
        TextView textView2 = customTextView(R.string.kristautasuna);
        TextView textView3 = customTextView(R.string.islamismoa);
        TextView textView4 = customTextView(R.string.budismoa);
        gridLayout.addView(textView);
        gridLayout.addView(textView2);
        gridLayout.addView(textView3);
        gridLayout.addView(textView4);
    }

    public TextView customTextView(int text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(20);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // this touch listener is used to make the textview draggable using a shadow
        textView.setOnTouchListener(new MyTouchListener());
        textView.setBackgroundResource(R.drawable.shape_dropeable);
        textView.setWidth(getResources().getDisplayMetrics().widthPixels / 2 -40);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        textView.setHeight((int) (64 * scale + 0.5f)); // set height to 64dp instead of 64px
        textView.setGravity(Gravity.CENTER);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(params);
        return textView;
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("hecho", "pan"); // data needed for the shadow
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    TextView view = (TextView) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            return true;
        }
    }

}