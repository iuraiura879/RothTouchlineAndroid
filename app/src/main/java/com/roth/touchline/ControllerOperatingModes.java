package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ControllerOperatingModes extends AppCompatActivity {

    int mode = 0;

    User user;
    Controller controller;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_operating_modes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);

            mode = controller.getMode();
            ((TextView) findViewById(R.id.textView15)).setText(controller.getName());

        }

        setMode( mode );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_controller_operating_modes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void setMode( int newMode ){

        ((ImageButton) findViewById(R.id.imageButton3)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton4)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton5)).setBackgroundResource(R.drawable.white_with_border_black);

        switch (newMode){
            case 0:
                ((ImageButton) findViewById(R.id.imageButton3)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 1:
                ((ImageButton) findViewById(R.id.imageButton4)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 2:
                ((ImageButton) findViewById(R.id.imageButton5)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;
            default:
                break;
        }

    }

    public void mode_1_pressed ( View v ){

        mode = 0;
        setMode(mode);
        onOkPressed(null);

    }

    public void mode_2_pressed ( View v ){

        mode = 1;
        setMode(mode);
        onOkPressed(null);

    }

    public void mode_3_pressed ( View v ){

        mode = 2;
        setMode(mode);
        onOkPressed(null);

    }

    public void onOkPressed( View v ){

        user.findControllerByID(ID).setMode( mode );
        user.saveToFile(this);

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
