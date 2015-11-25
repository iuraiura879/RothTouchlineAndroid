package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ThermostatOverviewAdvanced extends AppCompatActivity {

    float temp = 23.0f;
    int mode1 = 0;
    int mode2 = 0;

    String name = "";

    User user;
    Controller controller;
    Thermostat thermostat;
    int ID;
    int thermostatID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostat_overview_advanced);

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        thermostatID = getIntent().getIntExtra(ThermostatOverviewMainMenu.THERMOSTAT_ID_SENT , -1 );

        if( ID >= 0 && thermostatID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);
            thermostat = controller.findThermostatByID(thermostatID);

            name = thermostat.getName();
            temp = thermostat.getOperatingTemperature();
            mode1 = thermostat.getMode();
            mode2 = thermostat.getModeSecondary();

        }


        setMode1( mode1 );
        setMode2(mode2);
        ((TextView) findViewById(R.id.textView15)).setText(String.format("%.1f", temp) + "°C");
        ((TextView) findViewById(R.id.textView23)).setText(name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_thermostat_overview_advanced, menu);
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

    public void tempUpPressed( View v ){

        temp += .5;
        ((TextView) findViewById(R.id.textView15)).setText(String.format("%.1f", temp)+"°C");

    }

    public void tempDownPressed( View v ){

        temp -= .5;
        ((TextView) findViewById(R.id.textView15)).setText(String.format("%.1f", temp)+"°C");
    }

    public void setMode1( int newMode ){

        ((ImageButton) findViewById(R.id.imageButton6)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton7)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton8)).setBackgroundResource(R.drawable.white_with_border_black);

        switch (newMode){
            case 0:
                ((ImageButton) findViewById(R.id.imageButton6)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 1:
                ((ImageButton) findViewById(R.id.imageButton7)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 2:
                ((ImageButton) findViewById(R.id.imageButton8)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;
            default:
                break;
        }

    }

    public void setMode2( int newMode ){

        ((ImageButton) findViewById(R.id.imageButton9)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton10)).setBackgroundResource(R.drawable.white_with_border_black);
        ((ImageButton) findViewById(R.id.imageButton11)).setBackgroundResource(R.drawable.white_with_border_black);

        switch (newMode){
            case 0:
                ((ImageButton) findViewById(R.id.imageButton9)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 1:
                ((ImageButton) findViewById(R.id.imageButton10)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;

            case 2:
                ((ImageButton) findViewById(R.id.imageButton11)).setBackgroundResource(R.drawable.white_with_border_black_thick);
                break;
            default:
                break;
        }

    }

    public void mode_1_option_1_pressed ( View v ){

        mode1 = 0;
        setMode1(mode1);

    }

    public void mode_1_option_2_pressed ( View v ){

        mode1 = 1;
        setMode1(mode1);

    }

    public void mode_1_option_3_pressed ( View v ){

        mode1 = 2;
        setMode1( mode1 );

    }

    public void mode_2_option_1_pressed ( View v ){

        mode2 = 0;
        setMode2(mode2);

    }

    public void mode_2_option_2_pressed ( View v ){

        mode2 = 1;
        setMode2(mode2);

    }

    public void mode_2_option_3_pressed ( View v ){

        mode2 = 2;
        setMode2(mode2);

    }

    public void onOkPressed( View v ){

        user.findControllerByID(ID).findThermostatByID(thermostatID).setName(((EditText)findViewById(R.id.textView23)).getText().toString());
        user.findControllerByID(ID).findThermostatByID(thermostatID).setOperatingTemperature(temp);
        user.findControllerByID(ID).findThermostatByID(thermostatID).setMode(mode1);
        user.findControllerByID(ID).findThermostatByID(thermostatID).setModeSecondary(mode2);
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
