package com.roth.touchline;


import android.graphics.Paint;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class ExistingUserFragment extends Fragment {


    public ExistingUserFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Setup UI
        View view = inflater.inflate(R.layout.fragment_existing_user, container, false);

        Button button = (Button) view.findViewById(R.id.button4);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        EditText editText = (EditText) view.findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter() ,new InputFilter.LengthFilter(10)});

        editText = (EditText) view.findViewById(R.id.editText2);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        return view;



    }


}
