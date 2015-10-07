package com.roth.touchline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ControllerInfoAdapter extends ArrayAdapter<Controller> {

    public ControllerInfoAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ControllerInfoAdapter(Context context, int resource, List<Controller> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v = vi.inflate(R.layout.controller_info_list_item, null);


        ( (TextView) v.findViewById(R.id.textView24)).setText(getItem(position).getName());
        ( (TextView) v.findViewById(R.id.textView25)).setText(getItem(position).getNumberOfThermostats()+" x");

        return v;
    }



}