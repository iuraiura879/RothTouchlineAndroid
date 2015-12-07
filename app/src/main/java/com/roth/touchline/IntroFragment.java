package com.roth.touchline;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class IntroFragment extends Fragment {






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        //Setup UI

        final View view = inflater.inflate(R.layout.activity_intro, container, false);


        final Button button = (Button) view.findViewById(R.id.button3);
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        /*
        TextView txt = (TextView) view.findViewById(R.id.Touchline);
        //Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Kokila.ttf");
         txt.setText(Html.fromHtml("<b>"+getString(R.string.touchline)+"<sup>+</sup></b>"));
        //txt.setTypeface(type);
*/


        return view;
    }






}
