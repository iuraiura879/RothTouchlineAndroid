package com.roth.touchline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ControllerListDeleteAdapter extends ArrayAdapter<Controller> {

    public ControllerListDeleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ControllerListDeleteAdapter(Context context, int resource, List<Controller> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v = vi.inflate(R.layout.controller_delete_list_item, parent, false);


        ( (TextView) v.findViewById(R.id.textView26)).setText(getItem(position).getName());

        if(!getItem(position).getChecked()){
            ( (ImageView) v.findViewById(R.id.imageView10)).setImageDrawable(null);
        }

        return v;
    }



}