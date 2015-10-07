package com.roth.touchline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThermostatOverviewListAdapter extends ArrayAdapter<Thermostat> implements DropListener {

    private List<Thermostat> mContent;

    public ThermostatOverviewListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    public ThermostatOverviewListAdapter(Context context, int resource, List<Thermostat> items) {
        super(context, resource, items);
        init(context, new int[]{android.R.layout.simple_list_item_1}, new int[]{android.R.id.text1}, items);
    }

    private void init(Context context, int[] layouts, int[] ids, List<Thermostat> content) {

        mContent = content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.thermostat_overview_list_item, parent , false);
        }

        Thermostat thermostat = getItem(position);

        ( (TextView) v.findViewById(R.id.textView20)).setText( String.format("%.1f", thermostat.getCurrentTemperature()) );
        ( (TextView) v.findViewById(R.id.textView21)).setText(thermostat.getName());
        ( (TextView) v.findViewById(R.id.textView22)).setText(String.format("%.1f", thermostat.getOperatingTemperature()));

        switch (thermostat.mode){

            case 0:
                ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource( R.mipmap.ic_normal );
                break;

            case 1:
                ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource( R.mipmap.ic_night );
                break;

            case 2:
                ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource( R.mipmap.ic_holiday );
                break;


            default:
                break;
        }


        return v;
    }

    public void onDrop(int from, int to) {
        Thermostat temp = mContent.get(from);
        mContent.remove(from);
        mContent.add(to, temp);
    }



}