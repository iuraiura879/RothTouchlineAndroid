package com.roth.touchline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ChangeUserID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_id);

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(), new InputFilter.LengthFilter(10)});

        editText = (EditText) findViewById(R.id.editText9);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(),new InputFilter.LengthFilter(10)});

        editText = (EditText) findViewById(R.id.editText8);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_change_user_id, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    @SuppressLint("NewApi")
    public void onOkPressed( View v ){

        boolean isGood = true;


        EditText editText = (EditText) findViewById(R.id.editText);
        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            else
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );

        }

        editText = (EditText) findViewById(R.id.editText9);
        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            else
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );

        }


        editText = (EditText) findViewById(R.id.editText8);
        if (editText.length() < 4) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            else
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );
        }


        if ( isGood ){

            editText = (EditText) findViewById(R.id.editText9);

            User user = User.readFromFile(this);
            user.setUserID(editText.getText().toString());
            user.saveToFile(this);

            onBackPressed();
        }

    }

    public void onHomePressed( View v ){

        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);         //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    public void onInfoPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  1 );
        startActivity(intent);

    }

}
