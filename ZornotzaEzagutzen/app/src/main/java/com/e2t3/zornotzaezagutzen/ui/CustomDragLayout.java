package com.e2t3.zornotzaezagutzen.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e2t3.zornotzaezagutzen.R;

/**
 * This class is used to create a custom layout for the drag and drop it has an image and a text and contains the text of the dragable that can be dropped
 */
public class CustomDragLayout extends LinearLayout {

    private int image;

    private String erlijioa;

    private LinearLayout drop;

    public CustomDragLayout(Context context, int image, String erlijioa) {
        super(context);
        this.image = image;
        this.erlijioa = erlijioa;
        inflate(context, R.layout.custom_drag_layout, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ImageView imageView = findViewById(R.id.imageReligion);
        imageView.setImageResource(image);
        LinearLayout linearLayout = findViewById(R.id.linearLayoutDrag);
        // set the width half of the screen
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 2 -20;
        this.drop = findViewById(R.id.drop);
        this.drop.setOnDragListener(new MyDragListener());
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    TextView view = (TextView) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    if (erlijioa.equalsIgnoreCase(view.getText().toString())) {
                        owner.removeView(view);
                        TextView textView = new TextView(getContext());
                        textView.setText(erlijioa);
                        textView.setTextSize(20);
                        textView.setTextColor(getResources().getColor(R.color.black));
                        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                        textView.setGravity(Gravity.CENTER);
                        // change the background of the dragable so it doesn't change to the old one qhen the drag ends
                        normalShape = getResources().getDrawable(R.drawable.shape_dropeable);
                        drop.addView(textView);
                        DragFragment.checkfinish();
                    } else {
                        view.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                    v.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            return true;
        }
    }
}
