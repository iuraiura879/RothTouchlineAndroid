package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class UserSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText editText = (EditText) findViewById(R.id.editText3);

        User user = User.readFromFile(this);
        editText.setText(user.getName());

        editText = (EditText) findViewById(R.id.editText4);
        editText.setText(user.getEmail());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_setup, menu);
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


    public void onOkPressed( View v ){

        EditText editText = (EditText) findViewById(R.id.editText3);

        User user = User.readFromFile(this);
        user.setName(editText.getText().toString());

        editText = (EditText) findViewById(R.id.editText4);
        user.setEmail(editText.getText().toString());

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        user.setLanguage(spinner.getSelectedItemPosition());

        user.saveToFile(this);

        onBackPressed();

    }


    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  4 );
        startActivity(intent);


    }
}
