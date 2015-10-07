package com.roth.touchline;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

public class ThermostatNameListAdapter extends ArrayAdapter<Thermostat> {

    public ThermostatNameListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ThermostatNameListAdapter(Context context, int resource, List<Thermostat> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v = vi.inflate(R.layout.thermostat_name_list_item, null);

        ( (EditText) v.findViewById(R.id.editText10)).setText(getItem(position).getName());

        EditText editText = (EditText) v.findViewById(R.id.editText10);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(10)});

        return v;
    }



}