package com.e2t3.zornotzaezagutzen.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.GuneActivity;
import com.e2t3.zornotzaezagutzen.MainActivity;
import com.e2t3.zornotzaezagutzen.R;
import com.e2t3.zornotzaezagutzen.data.entities.Estadistikak;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * This fragment contains the navigation buttons for the GuneActivity
 */
public class NabButtonsFragment extends Fragment {

    private static GuneActivity guneActivity;
    private Button hasiGuneaButton;
    private FloatingActionButton atzeraButton;
    private static FloatingActionButton aurreraButton;
    private FloatingActionButton ateraButton;
    private static FloatingActionButton refreshButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nab_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        guneActivity = (GuneActivity) getActivity();

        // get the buttons
        hasiGuneaButton = (Button) view.findViewById(R.id.hasiGuneaButton);
        atzeraButton = (FloatingActionButton) view.findViewById(R.id.atzeraButton);
        aurreraButton = (FloatingActionButton) view.findViewById(R.id.aurreraButton);
        refreshButton = (FloatingActionButton) view.findViewById(R.id.refreshButton);

        hasiGuneaButton.setVisibility(View.VISIBLE);
        atzeraButton.setVisibility(View.GONE);
        aurreraButton.setVisibility(View.GONE);

        refreshButton.setVisibility(View.GONE);

        ateraButton = view.findViewById(R.id.ateraButton);
        // change button color
        ateraButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_berdea));
        hasiGuneaButton.setBackgroundTintList(getResources().getColorStateList(R.color.button_berdea));

        ateraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        hasiGuneaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guneActivity.aktibitateZenbakia = 1;
                hasiGuneaButton.setVisibility(View.GONE);
                ateraButton.setVisibility(View.GONE);
                atzeraButton.setVisibility(View.VISIBLE);
                aurreraButton.setVisibility(View.VISIBLE);
                changeFrame();
            }
        });

        atzeraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guneActivity.aktibitateZenbakia--;
                if (guneActivity.aktibitateZenbakia < 0 || 0 == guneActivity.aktibitateZenbakia) {
                    guneActivity.aktibitateZenbakia = 0;
                    atzeraButton.setVisibility(View.GONE);
                    aurreraButton.setVisibility(View.GONE);
                    hasiGuneaButton.setVisibility(View.VISIBLE);
                    ateraButton.setVisibility(View.VISIBLE);
                }
                aurreraButton.setClickable(true);
                changeFrame();
            }
        });

        aurreraButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                guneActivity.aktibitateZenbakia++;
                if (guneActivity.aktibitateZenbakia == guneActivity.aktibitateak.size()) {
                    MainActivity.sumar(guneActivity.gunea.zenbakia);

                    // update the statistics
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    String dateString = date.format(formatter);

                    // check if the statistics for this day already exist before inserting or updating
                    Estadistikak estadistika = guneActivity.appDatabase.estadistikakDao().findById(guneActivity.gunea.zenbakia, dateString);

                    if (estadistika != null) {
                        guneActivity.appDatabase.estadistikakDao().update(guneActivity.gunea.zenbakia, dateString);
                    } else {
                        guneActivity.appDatabase.estadistikakDao().insertAll(new Estadistikak(guneActivity.gunea.zenbakia, dateString, 1));
                    }
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    changeFrame();
                    guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).amaituta = true;
                }
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrame();
            }
        });
    }

    /**
     * Change the frame to the next or previous game
     */
    private void changeFrame() {
        // prepare the buttons
        aurreraButton.setEnabled(guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).amaituta);
        if (guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).jokoaDa)
            refreshButton.setEnabled(false);
        else refreshButton.setVisibility(View.GONE);

        // java magic
        Class myClass = null;
        Object instanceOfMyClass = null;
        try {
            myClass = Class.forName(guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).clazz);
            // Generate a new instance of the class
            instanceOfMyClass = myClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // change the frame
        getParentFragmentManager().beginTransaction().replace(R.id.aktibitatea_fragment_container, (Fragment) instanceOfMyClass).commit();
    }

    /**
     * Enable the next button of the navigation "bar"
     */
    public static void enableNextButton() {
        aurreraButton.setEnabled(true);
        if (guneActivity.aktibitateak.get(guneActivity.aktibitateZenbakia).jokoaDa) {
            refreshButton.setVisibility(View.VISIBLE);
            refreshButton.setEnabled(true);
        }
    }

}
