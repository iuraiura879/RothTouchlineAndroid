package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ThermostatOverview extends AppCompatActivity {

    ArrayList<Thermostat> controllerList;

    private ThermostatOverviewListAdapter arrayAdapter;
    TouchInterceptor controllerListView;



    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat_overview);


        controllerListView=(TouchInterceptor)findViewById(R.id.listView);
        controllerList = new ArrayList<Thermostat>();
        arrayAdapter = new ThermostatOverviewListAdapter(this, R.layout.thermostat_overview_list_item, controllerList);


        controllerListView.setAdapter(arrayAdapter);
        controllerListView.setDragListener(new DragListener() {


            public void onDrag(int x, int y, ListView listView) {

            }

            public void onStartDrag(View itemView) {
                if(itemView == null)
                    return;

                itemView.setVisibility(View.INVISIBLE);

                LinearLayout iv = (LinearLayout)itemView.findViewById(R.id.lay);
                if (iv != null) iv.setVisibility(View.INVISIBLE);
            }

            public void onStopDrag(View itemView) {
                if(itemView == null)
                    return;

                itemView.setVisibility(View.VISIBLE);

                LinearLayout iv = (LinearLayout)itemView.findViewById(R.id.lay);
                if (iv != null) iv.setVisibility(View.VISIBLE);
            }


        });



        controllerListView.setDropListener(new DropListener() {

            @Override
            public void onDrop(int from, int to) {

                ListAdapter adapter = arrayAdapter;
                if (adapter instanceof ThermostatOverviewListAdapter) {
                    ((ThermostatOverviewListAdapter) adapter).onDrop(from, to);
                    controllerListView.invalidateViews();
                    ((ThermostatOverviewListAdapter) adapter).notifyDataSetChanged();
                }

            }
        });


        controllerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getApplicationContext(), ThermostatOverviewAdvanced.class);
                intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( (((ThermostatOverviewListAdapter) arg0.getAdapter()).getItem(position).getControllerID()) ));
                intent.putExtra(ThermostatOverviewMainMenu.THERMOSTAT_ID_SENT, (((ThermostatOverviewListAdapter) arg0.getAdapter()).getItem(position).getID()) );
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_thermostat_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();

        final ListView thermostatListView=(ListView)findViewById(R.id.listView);
        ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).clear();

        user = User.readFromFile(this);

        Runnable run = new Runnable() {
            public void run() {
                ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).clear();

                for( int i=0; i<user.getNumberOfControllers(); i++)
                    for( int j=0; j<user.getController(i).getNumberOfThermostats(); j++)
                         ((ThermostatOverviewListAdapter) thermostatListView.getAdapter()).add(user.getController(i).getThermostat(j));

                ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).notifyDataSetInvalidated();
                thermostatListView.invalidateViews();
                thermostatListView.refreshDrawableState();

            }
        };

        runOnUiThread(run);

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
