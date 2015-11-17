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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private int touchPositionX;

    int mode = 0;

    List<Controller> controllerList;
    AllControllers allControllers;
    TouchInterceptor controllerListView;

    boolean sortingEnabled = false;

    ControllerListAdapter arrayAdapter;

    User user;

    final static String CONROLLER_ID_SENT = "CONROLLER_ID_SENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        controllerListView=(TouchInterceptor)findViewById(R.id.listView);
        controllerList = new ArrayList<Controller>();


        arrayAdapter = new ControllerListAdapter(this, R.layout.found_new_controller, controllerList);
        controllerListView.setAdapter(arrayAdapter);


        if( mode == 1){

            ImageButton img= (ImageButton) findViewById(R.id.imageButton1);
            img.setImageResource(R.mipmap.sort);

        }


        controllerListView.setDragListener(new DragListener() {

            public void onDrag(int x, int y, ListView listView) {

            }

            public void onStartDrag(View itemView) {

                if (itemView == null)
                    return;
                itemView.setVisibility(View.INVISIBLE);

                LinearLayout iv = (LinearLayout) itemView.findViewById(R.id.lay);

                if (iv != null)
                    iv.setVisibility(View.INVISIBLE);

            }

            public void onStopDrag(View itemView) {

                if (itemView == null)
                    return;

                itemView.setVisibility(View.VISIBLE);

                LinearLayout iv = (LinearLayout) itemView.findViewById(R.id.lay);

                if (iv != null)
                    iv.setVisibility(View.VISIBLE);

            }

        });

        controllerListView.setDropListener(new DropListener() {

            @Override
            public void onDrop(int from, int to) {

                ListAdapter adapter = arrayAdapter;
                if (adapter instanceof ControllerListAdapter) {
                    ((ControllerListAdapter) adapter).onDrop(from, to);
                    controllerListView.invalidateViews();
                    ((ControllerListAdapter) adapter).notifyDataSetChanged();
                }

            }
        });


        controllerListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                touchPositionX = (int) event.getX();
                return false;
            }
        });


        controllerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if(((ControllerListAdapter) controllerListView.getAdapter()).getItem(position).getConnected() == false) {

                    Intent intent = new Intent(getApplicationContext(), RegisteControllerActivity.class);
                    intent.putExtra(CONROLLER_ID_SENT, ((Controller) (arg0.getItemAtPosition(position))).getID());
                    startActivity(intent);
                }
                else{

                    View lay = ( (LinearLayout) arg1.findViewById(R.id.lay_mode) );



                    if (touchPositionX < lay.getWidth()) {
                    openControllerProgram(((Controller) (arg0.getItemAtPosition(position))).getID());

                    }
                    else
                        openControllerThermostatList(((Controller) (arg0.getItemAtPosition(position))).getID());

                 }
            }

        });

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

                if( mode == 0 )
                    for( int i=0; i<allControllers.getNumberOfControllers(); i++)
                        ((ControllerListAdapter) controllerListView.getAdapter()).add(allControllers.getController(i));

                if( mode == 1 )
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

    public void openControllerProgram(int id) {





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

    public void openControllerThermostatList(int id) {






        Intent intent = new Intent(getApplicationContext(), ThermostatOverviewMainMenu.class);
        intent.putExtra(CONROLLER_ID_SENT, ( id ));
        startActivity(intent);

    }

    public void infoButtonPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);

    }


    public void onSortPressed( View v ){

        if( mode == 0 )
            return;



        sortingEnabled = !sortingEnabled;

        if(sortingEnabled){

            final int newMargin = (int) (40 * getResources().getDisplayMetrics().density);
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) controllerListView.getLayoutParams();
                    mlp.leftMargin = (int)(newMargin * interpolatedTime);
                    mlp.rightMargin = (int)(newMargin * interpolatedTime);
                    controllerListView.setLayoutParams(mlp);
                }
            };
            a.setDuration(200); // in ms
            controllerListView.startAnimation(a);

        }
        else{

            final int newMargin = 0;
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) controllerListView.getLayoutParams();
                    mlp.leftMargin = (int)(newMargin * interpolatedTime);
                    mlp.rightMargin = (int)(newMargin * interpolatedTime);
                    controllerListView.setLayoutParams(mlp);
                }
            };
            a.setDuration(200); // in ms
            controllerListView.startAnimation(a);


            ArrayList<Controller> controllers = new ArrayList<Controller>();
            for (int i = 0; i <  controllerListView.getAdapter().getCount(); i++)
                controllers.add(((ControllerListAdapter) controllerListView.getAdapter()).getItem(i));


            //user.replaceThermostatListForControllerID( ID , thermostats);

           while(user.getNumberOfControllers() > 0){

                int temp = user.getController(0).getID();
                user.deleteControllerWithID(temp);
            }


            for (int i = 0; i <  controllerListView.getAdapter().getCount(); i++)
                user.addController(((ControllerListAdapter) controllerListView.getAdapter()).getItem(i));



            user.saveToFile(this);
        }


        controllerListView.setMDragEnabled(sortingEnabled);




    }

}
