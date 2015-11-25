package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ControllerInfo extends AppCompatActivity {


    List<Controller> controllerList;

    User user;
    Controller controller;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView controllerListView=(ListView)findViewById(R.id.listView);
        controllerList = new ArrayList<Controller>();

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);

            for( int i=0; i<user.getNumberOfControllers(); i++)
                controllerList.add(user.getController(i));

        }


        ControllerInfoAdapter arrayAdapter = new ControllerInfoAdapter(this, R.layout.controller_info_list_item, controllerList);
        controllerListView.setAdapter(arrayAdapter);

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

        onBackPressed();

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }

    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  3 );
        startActivity(intent);
    }

}
