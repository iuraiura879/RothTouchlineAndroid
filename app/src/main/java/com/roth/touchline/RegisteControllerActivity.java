package com.roth.touchline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisteControllerActivity extends AppCompatActivity {

    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe_controller);

        ID = getIntent().getIntExtra(SearchActivity.CONROLLER_ID_SENT , -1 );

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoSpecialInputFilter(),new InputFilter.LengthFilter(10)});

        editText = (EditText) findViewById(R.id.editText6);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        editText = (EditText) findViewById(R.id.editText7);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_registe_controller, menu);
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

    public void infoButtonPressed( View v ){

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);

    }

    @SuppressLint("NewApi")
    public void onOkPressed ( View v ){

        boolean isGood = true;


        EditText editText = (EditText) findViewById(R.id.editText);
        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red ));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );


        }


        editText = (EditText) findViewById(R.id.editText6);
        EditText editText2 = (EditText) findViewById(R.id.editText7);

        if (editText.length() <= 3 || editText.getText().toString().compareTo(editText2.getText().toString()) != 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red ));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );




            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                editText2.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText2.setBackground(getResources().getDrawable(R.drawable.bg_picker_red ));
            }

            editText2.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );


        }



        if( isGood && ID >= 0 ){

            AllControllers allControllers = AllControllers.readFromFile(this);
            Controller controller = allControllers.findControllerByID(ID);

            controller.setName(((EditText) findViewById(R.id.editText)).getText().toString());
            controller.setPassword(((EditText) findViewById(R.id.editText6)).getText().toString());
            controller.setConnected(true);

            allControllers.deleteController( ID );

            User user = User.readFromFile(this);
            user.addController( controller );

            allControllers.saveToFile(this);
            user.saveToFile(this);

            onBackPressed();

        }


    }

}
