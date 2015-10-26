package com.roth.touchline;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;


public class NewUserFragment extends Fragment {

    //final static String defaultTextForSpinner = "Choose language";
    //final static String[] arrayForSpinner = {"EN", "DK", "DE", "FR", "ES", "NO", "SE", "FI", "PL", "PT", "NL"};
    //int chosenLanguage = -1;

    public NewUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Setup UI
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);




        return view;

    }


}
