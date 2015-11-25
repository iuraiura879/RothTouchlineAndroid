package com.roth.touchline;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HelpActivity extends AppCompatActivity {

    int goHome = 1;

    public static final String intentChoiceName = "helpChoice";
    public static final String intentGoHome = "intentGoHome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int help_menu = intent.getIntExtra(intentChoiceName, 0);
        goHome = intent.getIntExtra(intentGoHome, 1);

        switch ( help_menu ){
            case 0:
                setContentView(R.layout.activity_help_app);
                break;

            case 1:
                setContentView(R.layout.activity_help_user);
                break;

            case 2:
                setContentView(R.layout.activity_help_password);
                break;

            case 3:
                setContentView(R.layout.activity_help_thermostat);
                break;

            case 4:
                setContentView(R.layout.activity_help_controller);
                break;

            case 5:
                setContentView(R.layout.activity_help_triangle);
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_help, menu);
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


    public void onHomePressed( View v ){

        if( goHome == 0 ){

            onBackPressed();

        }else{

            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);         //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);


        }

    }


}
