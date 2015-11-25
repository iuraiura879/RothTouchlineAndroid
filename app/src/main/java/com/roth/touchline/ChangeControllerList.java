package com.roth.touchline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeControllerList extends AppCompatActivity {

    //Controllers stored here
    List<Controller> controllerList;
    ListView controllerListView;
    ControllerListDeleteAdapter arrayAdapter;

    //Saving data
    User user;
    Controller controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_controller_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if( getIntent().getBooleanExtra(StartActivity.NO_BACK,false) ){
            if(getSupportActionBar() != null){

                getSupportActionBar().setHomeButtonEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);

            }
        }

        controllerListView=(ListView)findViewById(R.id.listView);
        controllerList = new ArrayList<Controller>();


        user = User.readFromFile(this);
        if( user != null ){
            for( int i=0; i<user.getNumberOfControllers(); i++)
                controllerList.add(user.getController(i));
        }


        arrayAdapter = new ControllerListDeleteAdapter(this, R.layout.controller_delete_list_item, controllerList);
        controllerListView.setAdapter(arrayAdapter);

        controllerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                controllerList.get(position).setChecked(!controllerList.get(position).getChecked());
                ((ControllerListDeleteAdapter) arg0.getAdapter()).notifyDataSetChanged();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_thermostat_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onOkPressed( View v ){

        for( int i = 0; i < controllerListView.getAdapter().getCount(); i++) {

            Controller controller = (Controller) controllerListView.getAdapter().getItem(i);

            if( !controller.getChecked() ){
                user.deleteControllerWithID(controller.getID());

            }

        }

        user.saveToFile(this);
        onHomePressed( null );
        finish();

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }

    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);


    }

}

