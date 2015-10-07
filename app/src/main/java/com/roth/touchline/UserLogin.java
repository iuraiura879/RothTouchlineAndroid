package com.roth.touchline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter() ,new InputFilter.LengthFilter(10)});

        editText = (EditText) findViewById(R.id.editText2);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    public void okUserLoginPressed ( View view ){

        Intent intent = new Intent(getApplicationContext(), ChangeControllerList.class);
        startActivity(intent);

    }


    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);

    }


    public void onForgotPassClick( View v ){

        Intent intent = new Intent(getApplicationContext(), UserForgotPass.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);

    }

}
