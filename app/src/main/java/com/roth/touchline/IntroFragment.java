package com.roth.touchline;


import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class IntroFragment extends Fragment {


    String defaultTextForSpinner = "Choose language";
    String[] arrayForSpinner = {"DE", "UK", "FR", "ES", "DK", "NO", "SE", "FI"};
    boolean isInitialSetup = true;
    boolean isFirstTimeShowingIntroSpinner=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Setup UI

        final View view = inflater.inflate(R.layout.activity_intro, container, false);


        final Button button = (Button) view.findViewById(R.id.button3);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        if( StartActivity.getIsFirstTimeShowingIntroSpinner() ) {

            spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, arrayForSpinner, defaultTextForSpinner));
            StartActivity.setIsFirstTimeShowingIntroSpinner(false);
        }
        else {

            spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, arrayForSpinner, ""));
        }

        //Set language dropdown and fade animation for buttons
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (!isInitialSetup) {



                    AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(300);
                    animation.setFillAfter(true);

                    Button button1 = (Button) view.findViewById(R.id.button);
                    Button button2 = (Button) view.findViewById(R.id.button2);

                    if (button1.getAlpha() == 0.0f && button2.getAlpha() == 0.0f) {

                        button1.setAlpha(1);
                        button1.setEnabled(true);

                        button2.setAlpha(1);
                        button2.setEnabled(true);

                        button1.startAnimation(animation);
                        button2.startAnimation(animation);

                    }

                } else
                    isInitialSetup = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        // Hide buttons
        Button button1 = (Button) view.findViewById(R.id.button);
        Button button2 = (Button) view.findViewById(R.id.button2);

        button1.setAlpha(0.0f);
        button1.setEnabled(false);

        button2.setAlpha(0.0f);
        button2.setEnabled(false);

        return view;
    }






}
