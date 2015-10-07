package com.roth.touchline;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;


public class NewUserFragment extends Fragment {

    final static String defaultTextForSpinner = "Choose language";
    final static String[] arrayForSpinner = {"DE", "UK", "FR", "ES", "DK", "NO", "SE", "FI"};
    int chosenLanguage = -1;

    public NewUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Setup UI
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        Bundle bundle=getArguments();

        chosenLanguage = bundle.getInt("pos", -1);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner2);
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, arrayForSpinner, defaultTextForSpinner);
        spinner.setAdapter(adapter);


        if( chosenLanguage != -1 ) {
            spinner.setSelection(chosenLanguage);
        }

        return view;

    }


}
