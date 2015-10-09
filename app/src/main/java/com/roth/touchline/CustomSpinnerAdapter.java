package com.roth.touchline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    String[] objects;
    String firstElement;
    boolean isFirstTime = false;


    public CustomSpinnerAdapter(Context context, int textViewResourceId, String[] objects, String defaultText) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;


        if(defaultText.length() > 0 ){

            this.isFirstTime = true;
            setDefaultText(defaultText);

        }

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if(isFirstTime) {

            objects[0] = firstElement;
            isFirstTime = false;

        }

        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        notifyDataSetChanged();
        return getCustomViewClosed(position, convertView, parent);

    }

    public void setDefaultText(String defaultText) {

        this.firstElement = objects[0];
        objects[0] = defaultText;

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = null;
        row = inflater.inflate(R.layout.spinner_item, parent, false);


        TextView label = (TextView) row.findViewById(R.id.text1);
        label.setText(objects[position]);

        return row;

    }

    public View getCustomViewClosed(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = null;
        row = inflater.inflate(R.layout.spinner_item_min, parent, false);


        TextView label = (TextView) row.findViewById(R.id.text1);
        label.setText(objects[position]);

        return row;

    }



}