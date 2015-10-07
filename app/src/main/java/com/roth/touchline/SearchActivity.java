package com.roth.touchline;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    List<Controller> controllerList;
    AllControllers allControllers;

    User user;

    final static String CONROLLER_ID_SENT = "CONROLLER_ID_SENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView controllerListView=(ListView)findViewById(R.id.listView);
        controllerList = new ArrayList<Controller>();


        ControllerListAdapter arrayAdapter = new ControllerListAdapter(this, R.layout.found_new_controller, controllerList);

        controllerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getApplicationContext(), RegisteControllerActivity.class);
                intent.putExtra(CONROLLER_ID_SENT, ( (Controller) (arg0.getItemAtPosition(position))).getID() );
                startActivity(intent);

            }
        });

        controllerListView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final ListView controllerListView=(ListView)findViewById(R.id.listView);

        Runnable run = new Runnable() {
            public void run() {

                ( (ControllerListAdapter) controllerListView.getAdapter()).clear();

                allControllers = AllControllers.readFromFile(getApplicationContext());
                user = User.readFromFile(getApplicationContext());

                for( int i=0; i<allControllers.getNumberOfControllers(); i++)
                    ((ControllerListAdapter) controllerListView.getAdapter()).add(allControllers.getController(i));


                for( int i=0; i<user.getNumberOfControllers(); i++)
                    ((ControllerListAdapter) controllerListView.getAdapter()).add(user.getController(i));

                controllerListView.invalidateViews();
                ( (ControllerListAdapter) controllerListView.getAdapter()).notifyDataSetInvalidated();
            }
        };

        runOnUiThread(run);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void openControllerProgram(View v) {

        int id = (Integer) ( (LinearLayout) v.getParent()).getTag();

        if( user.findControllerByID( id ).getMode() == 3 ) {

            Intent intent = new Intent(getApplicationContext(), EnterResetCode.class);
            intent.putExtra(CONROLLER_ID_SENT, ( id ));
            startActivity(intent);

        }
        else{

            Intent intent = new Intent(getApplicationContext(), ControllerOperatingModes.class);
            intent.putExtra(CONROLLER_ID_SENT, ( id ));
            startActivity(intent);

        }

    }

    public void openControllerThermostatList(View v) {

        int id = (Integer) ( (LinearLayout) v.getParent()).getTag();

        Intent intent = new Intent(getApplicationContext(), ThermostatOverviewMainMenu.class);
        intent.putExtra(CONROLLER_ID_SENT, ( id ));
        startActivity(intent);

    }

    public void infoButtonPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);

    }


}
