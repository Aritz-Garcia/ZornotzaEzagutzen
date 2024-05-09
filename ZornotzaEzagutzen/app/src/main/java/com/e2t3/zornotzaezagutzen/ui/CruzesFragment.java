package com.e2t3.zornotzaezagutzen.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CruzesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CruzesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean haga, biga, higa = false; // booleans to check if the crosses have been clicked

    public CruzesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CruzesFragment newInstance(String param1, String param2) {
        CruzesFragment fragment = new CruzesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ImageView cruz1, cruz2, cruz3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_cruzes, container, false);

        // get the elements from the layout
        cruz1 = view.findViewById(R.id.cruz1);
        cruz2 = view.findViewById(R.id.cruz2);
        cruz3 = view.findViewById(R.id.cruz3);

        cruz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Lehenengo Gurutzea");
                builder.setMessage("Kalbario izeneko plazan kokatuta daude gurutzeak");
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                haga = true;
                if (haga && biga && higa){
                    NabButtonsFragment.enableNextButton();
                }
            }
        });

        cruz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bigarren Gurutzea");
                builder.setMessage("XVIII. (18.) mendean sortu ziren, Benita Zelaietak egindako dohaintzari esker.");
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                biga = true;
                if (haga && biga && higa){
                    NabButtonsFragment.enableNextButton();
                }
            }
        });

        cruz3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // build the alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Hirugarren Gurutzea");
                builder.setMessage("Gurutze hauek, Kristau erlijioaren arabera, Jerusalemgo mendia da, non Jesus gurutziltzatzea sinbolizatzen dute.");
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                higa = true;
                if (haga && biga && higa){
                    NabButtonsFragment.enableNextButton();
                }
            }
        });
        return view;
    }
}