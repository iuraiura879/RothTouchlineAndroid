package com.roth.touchline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ControllerListAdapter extends ArrayAdapter<Controller> {

    public ControllerListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ControllerListAdapter(Context context, int resource, List<Controller> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        View v;

        if ( !getItem(position).getConnected() ){

            v = vi.inflate(R.layout.found_new_controller, parent, false);
            ( ( TextView ) v.findViewById(R.id.text1)).setText("ID " + getItem(position).getStringID() + getContext().getString(R.string.ready_connect) );
            ( (LinearLayout) v.findViewById(R.id.top_list)).setTag(getItem(position).getID());
        }

        else{

            v = vi.inflate(R.layout.controller_connected_list_item, parent, false);
            ( ( TextView ) v.findViewById(R.id.textView31)).setText(getItem(position).getName());
            ( (LinearLayout) v.findViewById(R.id.top_list)).setTag(getItem(position).getID());

            switch (getItem(position).getMode()){

                case 0:
                    ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource(R.mipmap.ic_normal);
                    break;

                case 1:
                    ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource(R.mipmap.ic_night);
                    break;

                case 2:
                    ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource(R.mipmap.ic_holiday);
                    break;

                case 3:
                    ( (ImageView) v.findViewById(R.id.imageView4)).setImageResource(R.mipmap.ic_triangle);
                    break;


                default:
                    break;
            }

        }

        return v;
    }







}