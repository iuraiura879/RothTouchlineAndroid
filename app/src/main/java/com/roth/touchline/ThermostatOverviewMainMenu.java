package com.roth.touchline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThermostatOverviewMainMenu extends AppCompatActivity {

    ArrayList<Thermostat> ThermostatList;

    private String[] helpMenuList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private ThermostatOverviewListAdapter arrayAdapter;
    TouchInterceptor thermostatListView;

    boolean sortingEnabled = false;

    User user;
    Controller controller;
    int ID;

    final static String THERMOSTAT_ID_SENT = "THERMOSTAT_ID_SENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat_overview_main_menu);


        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);

            setTitle(controller.getName());


        }

        helpMenuList = getResources().getStringArray(R.array.help_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0 ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, helpMenuList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = convertView;

                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.drawer_list_item, null);

                TextView textView = ((TextView) v.findViewById(R.id.text1));
                textView.setText(getItem(position));

                if (position == 0 || position == 5 || position == 9 || position == 12)
                    textView.setBackgroundResource(R.drawable.bg_listview);

                return v;
            }

        });


        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle.syncState();

        ///////


        thermostatListView=(TouchInterceptor)findViewById(R.id.listView);
        ThermostatList = new ArrayList<Thermostat>();

        arrayAdapter = new ThermostatOverviewListAdapter(this, R.layout.thermostat_overview_list_item, ThermostatList);
        thermostatListView.setAdapter(arrayAdapter);

        thermostatListView.setDragListener(new DragListener() {

            public void onDrag(int x, int y, ListView listView) {

            }

            public void onStartDrag(View itemView) {

                if(itemView == null)
                    return;
                itemView.setVisibility(View.INVISIBLE);

                LinearLayout iv = (LinearLayout)itemView.findViewById(R.id.lay);

                if (iv != null)
                    iv.setVisibility(View.INVISIBLE);

            }

            public void onStopDrag(View itemView) {

                if(itemView == null)
                    return;

                itemView.setVisibility(View.VISIBLE);

                LinearLayout iv = (LinearLayout)itemView.findViewById(R.id.lay);

                if (iv != null)
                    iv.setVisibility(View.VISIBLE);

            }

        });

        thermostatListView.setDropListener(new DropListener() {

            @Override
            public void onDrop(int from, int to) {

                ListAdapter adapter = arrayAdapter;
                if (adapter instanceof ThermostatOverviewListAdapter) {
                    ((ThermostatOverviewListAdapter)adapter).onDrop(from, to);
                    thermostatListView.invalidateViews();
                    ((ThermostatOverviewListAdapter)adapter).notifyDataSetChanged();
                }

            }
        });


        thermostatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(getApplicationContext(), ThermostatOverviewAdvanced.class);
                intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
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
    protected void onResume() {
        super.onResume();

        final ListView thermostatListView=(ListView)findViewById(R.id.listView);
        ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).clear();

        user = User.readFromFile(this);
        controller = user.findControllerByID(ID);

        setTitle(controller.getName());

        if(controller.getMode() == 3){

            ImageButton b =  (ImageButton) findViewById(R.id.imageButton2);
            b.setImageResource(R.mipmap.ic_triangle);

        }

        Runnable run = new Runnable() {
            public void run() {
                ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).clear();

                for( int i=0; i<controller.getNumberOfThermostats(); i++)
                    ((ThermostatOverviewListAdapter) thermostatListView.getAdapter()).add(controller.getThermostat(i));

                ( (ThermostatOverviewListAdapter) thermostatListView.getAdapter()).notifyDataSetInvalidated();
                thermostatListView.invalidateViews();
                thermostatListView.refreshDrawableState();

            }
        };

        runOnUiThread(run);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {


            Intent intent = new Intent(getApplicationContext(), HelpActivity.class);

            switch (position){

                case 1:

                    intent = new Intent(getApplicationContext(), ControllerOperatingModes.class);
                    intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
                    startActivity(intent);

                    break;

                case 2:

                    intent = new Intent(getApplicationContext(), NameController.class);
                    intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
                    startActivity(intent);

                    break;

                case 3:


                    SharedPreferences preferences = getApplicationContext().getSharedPreferences(StartActivity.PREFS, android.content.Context.MODE_PRIVATE);
                    boolean remember_pass = preferences.getBoolean(StartActivity.REMEMBER_PASS, false);


                    if(remember_pass){

                        intent = new Intent(getApplicationContext(), ChangeControllerList.class);
                        startActivity(intent);

                    }
                    else {

                        intent = new Intent(getApplicationContext(), UserLogin.class);
                        startActivity(intent);

                    }

                    break;

                case 4:

                    intent = new Intent(getApplicationContext(), ControllerInfo.class);
                    intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
                    startActivity(intent);

                    break;



                case 6:

                    intent = new Intent(getApplicationContext(), ChangeUserID.class);
                    startActivity(intent);

                    break;

                case 7:

                    intent = new Intent(getApplicationContext(), ChangePassword.class);
                    intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
                    startActivity(intent);

                    break;

                case 8:

                    intent = new Intent(getApplicationContext(), UserSetup.class);
                    startActivity(intent);

                    break;



                case 10:

                    intent = new Intent(getApplicationContext(), ChangeThermostatName.class);
                    intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
                    startActivity(intent);

                    break;

                case 11:

                    intent = new Intent(getApplicationContext(), ThermostatOverview.class);
                    startActivity(intent);

                    break;



                case 13:

                    intent.putExtra( HelpActivity.intentChoiceName,  2 );
                    startActivity(intent);

                    break;

                case 14:

                    intent.putExtra( HelpActivity.intentChoiceName,  4 );
                    startActivity(intent);

                    break;

                case 15:

                    intent.putExtra( HelpActivity.intentChoiceName,  1 );
                    startActivity(intent);

                    break;

                case 16:

                    intent.putExtra( HelpActivity.intentChoiceName,  2 );
                    startActivity(intent);

                    break;

                case 17:

                    intent.putExtra( HelpActivity.intentChoiceName,  3 );
                    startActivity(intent);

                    break;

                default:
                    break;
            }

        }
    }

    public void onSortPressed( View v ){

        sortingEnabled = !sortingEnabled;
        if(sortingEnabled){

            final int newMargin = 100;
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) thermostatListView.getLayoutParams();
                    mlp.leftMargin = (int)(newMargin * interpolatedTime);
                    mlp.rightMargin = (int)(newMargin * interpolatedTime);
                    thermostatListView.setLayoutParams(mlp);
                }
            };
            a.setDuration(200); // in ms
            thermostatListView.startAnimation(a);

        }
        else{

            final int newMargin = 0;
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) thermostatListView.getLayoutParams();
                    mlp.leftMargin = (int)(newMargin * interpolatedTime);
                    mlp.rightMargin = (int)(newMargin * interpolatedTime);
                    thermostatListView.setLayoutParams(mlp);
                }
            };
            a.setDuration(200); // in ms
            thermostatListView.startAnimation(a);


            ArrayList<Thermostat> thermostats = new ArrayList<Thermostat>();
            for (int i = 0; i <  thermostatListView.getAdapter().getCount(); i++)
                thermostats.add(((ThermostatOverviewListAdapter) thermostatListView.getAdapter()).getItem(i));


            user.replaceThermostatListForControllerID( ID , thermostats);
            user.saveToFile(this);
        }


        thermostatListView.setMDragEnabled( sortingEnabled );

    }

    public void onHomePressed( View v ){
        onBackPressed();
    }

    public void onInfoPressed( View v ){

        if(controller.getMode() == 3){

            Intent intent = new Intent(getApplicationContext(), EnterResetCode.class);
            intent.putExtra(SearchActivity.CONROLLER_ID_SENT, ( ID ));
            startActivity(intent);
            return;

        }

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  3 );
        startActivity(intent);
    }





}
