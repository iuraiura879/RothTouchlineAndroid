package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChangeThermostatName extends AppCompatActivity {


    List<Thermostat> thermostatList;
    ListView thermostatListView;

    User user;
    Controller controller;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_thermostat_name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thermostatListView=(ListView)findViewById(R.id.listView);
        thermostatList = new ArrayList<Thermostat>();

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);

            for( int i=0; i<controller.getNumberOfThermostats(); i++)
                thermostatList.add(controller.getThermostat(i));
        }


        ThermostatNameListAdapter arrayAdapter = new ThermostatNameListAdapter(this, R.layout.thermostat_name_list_item, thermostatList);
        thermostatListView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_change_thermostat_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onOkPressed( View v ){

        for( int i = 0; i < thermostatListView.getAdapter().getCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) thermostatListView.getChildAt(i);
            TextView textView = (TextView) linearLayout.findViewById(R.id.editText10);
            user.findControllerByID( ID ).getThermostat(i).setName(textView.getText().toString());
        }

        user.saveToFile(this);
        onBackPressed();

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);         //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  3 );
        startActivity(intent);

    }

}
