package com.e2t3.zornotzaezagutzen.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.e2t3.zornotzaezagutzen.R;

public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
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

    VideoView video;
    MediaController media;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);
        video = view.findViewById(R.id.video);
        video.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.urazikloa));

        media = new MediaController(getContext());
        video.setMediaController(media);
        media.setAnchorView(video);

        video.setOnPreparedListener(mp -> {
            Log.d("videoView", "ready to play");
            // make the video start automatically
            video.start();
        });
        video.setOnCompletionListener(mp -> NabButtonsFragment.enableNextButton());
        return view;
    }
}