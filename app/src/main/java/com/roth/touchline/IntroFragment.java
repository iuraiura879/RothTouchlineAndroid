package com.roth.touchline;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class IntroFragment extends Fragment {


    String defaultTextForSpinner = "Choose language";

    boolean isInitialSetup = true;
    boolean defaultChoise = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Setup UI

        final View view = inflater.inflate(R.layout.activity_intro, container, false);


        final Button button = (Button) view.findViewById(R.id.button3);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        final NDSpinner spinner = (NDSpinner) view.findViewById(R.id.spinner);

        if( StartActivity.getIsFirstTimeShowingIntroSpinner() ) {

            spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, NewUserFragment.arrayForSpinner, defaultTextForSpinner));
            StartActivity.setIsFirstTimeShowingIntroSpinner(false);
        }
        else {

            spinner.setAdapter(new CustomSpinnerAdapter(getActivity(), R.layout.spinner_item, NewUserFragment.arrayForSpinner, ""));
        }


        SharedPreferences preferences = getContext().getSharedPreferences(StartActivity.PREFS, android.content.Context.MODE_PRIVATE);
        boolean locale_set = preferences.getBoolean(StartActivity.LOCALE_SET, false);

        if(locale_set)
        {

            String lang = preferences.getString(StartActivity.LANGUAGE, "en");
            int selection = 0;

            if( lang.compareToIgnoreCase("en") == 0)
                selection = 0;

            if( lang.compareToIgnoreCase("da") == 0)
                selection = 1;

            if( lang.compareToIgnoreCase("de") == 0)
                selection = 2;

            if( lang.compareToIgnoreCase("fr") == 0)
                selection = 3;

            if( lang.compareToIgnoreCase("es") == 0)
                selection = 4;

            if( lang.compareToIgnoreCase("no") == 0)
                selection = 5;

            if( lang.compareToIgnoreCase("sv") == 0)
                selection = 6;

            if( lang.compareToIgnoreCase("fi") == 0)
                selection = 7;

            if( lang.compareToIgnoreCase("pl") == 0)
                selection = 8;

            if( lang.compareToIgnoreCase("pt") == 0)
                selection = 9;

            if( lang.compareToIgnoreCase("nl") == 0)
                selection = 10;



            spinner.setSelection(selection,false);
            defaultChoise = false;

            AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(300);
            animation.setFillAfter(true);

            Button button1 = (Button) view.findViewById(R.id.button);
            Button button2 = (Button) view.findViewById(R.id.button2);

            //if (button1.getAlpha() == 0.0f && button2.getAlpha() == 0.0f) {

                button1.setAlpha(1);
                button1.setEnabled(true);

                button2.setAlpha(1);
                button2.setEnabled(true);

                button1.startAnimation(animation);
                button2.startAnimation(animation);

            //}


        }


        //Set language dropdown and fade animation for buttons
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                /*if (!isInitialSetup) {


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
                */

                //CHANGE LANG
                String languageToLoad;

                switch (position) {
                    case 0:
                        languageToLoad = "en";
                        break;


                    case 1:
                        languageToLoad = "da";
                        break;

                    case 2:
                        languageToLoad = "de";
                        break;


                    case 3:
                        languageToLoad = "fr";
                        break;


                    case 4:
                        languageToLoad = "es";
                        break;


                    case 5:
                        languageToLoad = "no";
                        break;


                    case 6:
                        languageToLoad = "sv";
                        break;


                    case 7:
                        languageToLoad = "fi";
                        break;


                    case 8:
                        languageToLoad = "pl";
                        break;


                    case 9:
                        languageToLoad = "pt";
                        break;


                    case 10:
                        languageToLoad = "nl";
                        break;


                    default:
                        languageToLoad = "en";
                        break;
                }


                SharedPreferences preferences = getContext().getSharedPreferences(StartActivity.PREFS, android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(StartActivity.LANGUAGE, languageToLoad);
                editor.commit();

                //RESTART APP

                if (!defaultChoise) {

                    getContext().getSharedPreferences(StartActivity.PREFS, android.content.Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putBoolean(StartActivity.LOCALE_SET, true);
                    editor.commit();

                    Intent i = getContext().getPackageManager()
                            .getLaunchIntentForPackage(getContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
                else
                {

                    defaultChoise = false;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });





      /*  // Hide buttons
        Button button1 = (Button) view.findViewById(R.id.button);
        Button button2 = (Button) view.findViewById(R.id.button2);

        button1.setAlpha(0.0f);
        button1.setEnabled(false);

        button2.setAlpha(0.0f);
        button2.setEnabled(false);
    */
        return view;
    }






}
