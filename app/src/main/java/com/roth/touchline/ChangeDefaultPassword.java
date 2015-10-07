package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ChangeDefaultPassword extends AppCompatActivity {


    User user;
    Controller controller;
    int ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_default_password);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(8)});

        editText = (EditText) findViewById(R.id.editText11);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(8)});

        editText = (EditText) findViewById(R.id.editText12);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(8)});


        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){
            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_default_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onOkPressed( View v ){

        user.getController(ID).setMode(0);
        user.saveToFile(this);
        onHomePressed( null );

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

}
