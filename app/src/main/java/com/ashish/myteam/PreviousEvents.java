package com.ashish.myteam;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousEvents extends Fragment {


    public PreviousEvents() {
        // Required empty public constructor
    }
    String mail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View previousVieew = inflater.inflate(R.layout.fragment_previous_events, container, false);

        mail = getArguments().getString("edttext");

        return previousVieew;
    }

}
