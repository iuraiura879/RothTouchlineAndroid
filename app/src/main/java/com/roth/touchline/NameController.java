package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class NameController extends AppCompatActivity {


    User user;
    Controller controller;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_controller);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(10)});

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );
        if( ID >= 0){

            user = User.readFromFile(this);
            controller = user.findControllerByID(ID);

            editText.setText(controller.getName());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_name_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    public void onOkPressed( View v ){

        EditText editText = (EditText) findViewById(R.id.editText);

        user = User.readFromFile(this);
        controller = user.findControllerByID(ID);
        controller.setName(editText.getText().toString());
        user.saveToFile(this);

        onBackPressed();

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);         //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  4 );
        startActivity(intent);

    }

}
