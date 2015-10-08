package com.roth.touchline;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class StartActivity extends AppCompatActivity {

    ProgressDialog dialog;

    User user;

    int lang = 0;

    final static String NO_BACK = "NO_BACK";

    final static String PREFS = "PREFS";
    final static String REMEMBER_PASS = "REMEMBER_PASS";

    private static boolean isFirstTimeShowingIntroSpinner = true;

    public static boolean getIsFirstTimeShowingIntroSpinner() {
        return isFirstTimeShowingIntroSpinner;
    }

    public static void setIsFirstTimeShowingIntroSpinner(boolean isFirstTimeShowingIntroSpinner) {
        StartActivity.isFirstTimeShowingIntroSpinner = isFirstTimeShowingIntroSpinner;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS, android.content.Context.MODE_PRIVATE);
        boolean remember_pass = preferences.getBoolean(REMEMBER_PASS, false);


        if(remember_pass){

            finish();
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //Setup ui
        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null )
            actionBar.hide();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        IntroFragment fragment =new IntroFragment();
        ft.add(R.id.fragment_holder, fragment, "IntroFragment");
        ft.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);

    }



    public void onForgotPassClick( View v ){


        Intent intent = new Intent(getApplicationContext(), UserForgotPass.class);
        startActivity(intent);


    }

    //When new user pressed, pass spinner data and create new fragment
    public void newUserPressed( View v ){

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Bundle bundl = new Bundle();
        bundl.putInt("pos", spinner.getSelectedItemPosition());

        NewUserFragment newUserFragment = new NewUserFragment();
        newUserFragment.setArguments(bundl);
        transaction.replace(R.id.fragment_holder, newUserFragment );
        transaction.addToBackStack(null);
        transaction.commit();



    }

    //Eisting user, creating new fragment, adding to stack
    public void existingUserPressed( View v ){

        lang = (((Spinner) findViewById(R.id.spinner)).getSelectedItemPosition());

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        ExistingUserFragment existingUserFragment = new ExistingUserFragment();
        transaction.replace(R.id.fragment_holder, existingUserFragment);
        transaction.addToBackStack(null);
        transaction.commit();



    }


    //Procede from existing user screen. Check if fields are completed, and mimic an http request by timer
    @SuppressLint("NewApi")
    public void okExistingUserPressed( View v ){

        boolean isGood = true;

        EditText editText = (EditText) findViewById(R.id.editText);

        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );



        }

        editText = (EditText) findViewById(R.id.editText2);

        if (editText.length() < 4) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );


        }


        if( isGood ){


            user = new User();
            user.setUserID(((EditText) findViewById(R.id.editText)).getText().toString());
            user.setLanguage(lang);

            if( ((CheckBox) findViewById(R.id.checkBox)).isChecked() ) {

                SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS, android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(REMEMBER_PASS, true);
                editor.commit();
            }


            /// HERE GOES CONTROLL DATA
            Controller temp = new Controller();
            temp.setID(0);
            temp.setConnected(true);
            temp.setName("Test1");
            temp.setStringID("Test1");

            Thermostat tempThermostat = new Thermostat();
            tempThermostat.setName("Room1");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(0);
            temp.addThermostat(tempThermostat);

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room2");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(1);
            temp.addThermostat(tempThermostat);

            user.addController(temp);



            temp = new Controller();
            temp.setID(1);
            temp.setConnected(true);
            temp.setName("Test2");
            temp.setStringID("Test2");

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room1");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(0);
            temp.addThermostat(tempThermostat);

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room2");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(1);
            temp.addThermostat(tempThermostat);

            user.addController(temp);


            user.saveToFile(this);


            AllControllers allControllers = new AllControllers();
            allControllers.saveToFile(this);

            ////Do the http request

            AsyncHttpClient client = new AsyncHttpClient();
            client.get("http://myroth.ininet.ch", new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    // called before request is started
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                }
                @Override
                public void onRetry(int retryNo) {
                    // called when request is retried
                }
            });


            //////


            dialog = ProgressDialog.show(this, "",
                    getString(R.string.loading), true);

            //mimic html request
            new Timer().schedule(new afterHtmlReqestExistingUser(), 1000);


        }


    }



    //Procede from new user screen. Check if fields are completed, and mimic an http request by timer
    @SuppressLint("NewApi")
    public void okNewUserPressed( View v ){

        boolean isGood = true;

        EditText editText = (EditText) findViewById(R.id.editText3);

        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );




        }

        editText = (EditText) findViewById(R.id.editText4);

        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );


        }

        editText = (EditText) findViewById(R.id.editText5);

        if (editText.length() <= 0) {

            isGood = false;

            int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_picker_red));
            } else {
                editText.setBackground(getResources().getDrawable(R.drawable.bg_picker_red, getTheme()));
            }

            editText.setPadding(  (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density), (int) (10 * getResources().getDisplayMetrics().density), (int) (4 * getResources().getDisplayMetrics().density) );


        }


        if( isGood ){

            user = new User();
            user.setName(((EditText) findViewById(R.id.editText3)).getText().toString());
            user.setEmail(((EditText) findViewById(R.id.editText4)).getText().toString());
            user.setMobileNr(((EditText) findViewById(R.id.editText5)).getText().toString());
            user.setLanguage(((Spinner) findViewById(R.id.spinner2)).getSelectedItemPosition());

            user.saveToFile(this);




            //Create list of controllers ready to connect
            /// HERE GOES CONTROLL DATA

            AllControllers allControllers = new AllControllers();

            Controller temp = new Controller();
            temp.setID(0);
            temp.setStringID("935.56");

            Thermostat tempThermostat = new Thermostat();
            tempThermostat.setName("Room1");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(0);
            temp.addThermostat(tempThermostat);

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room2");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(1);
            temp.addThermostat(tempThermostat);

            allControllers.addController(temp);



            temp = new Controller();
            temp.setID(0);
            temp.setStringID("935-56");

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room1");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(0);
            temp.addThermostat(tempThermostat);

            tempThermostat = new Thermostat();
            tempThermostat.setName("Room2");
            tempThermostat.setCurrentTemperature(23.5f);
            tempThermostat.setOperatingTemperature(24.5f);
            tempThermostat.setMode(1);
            tempThermostat.setID(1);
            temp.addThermostat(tempThermostat);

            allControllers.addController(temp);

            allControllers.saveToFile(this);



            dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);

            //mimic html request
            new Timer().schedule(new afterHtmlReqest(), 1000);


        }


    }



    //Called by timer to mimic http request
    class afterHtmlReqest extends TimerTask {
        public void run() {

            dialog.dismiss();

            finish();
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);


        }
    }


    //Called by timer to mimic http request
    class afterHtmlReqestExistingUser extends TimerTask {
        public void run() {

            dialog.dismiss();

            finish();
            Intent intent = new Intent(getApplicationContext(), ChangeControllerList.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra(NO_BACK, true);
            startActivity(intent);


        }
    }



    public void openAppHelp ( View v ){


        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        intent.putExtra( HelpActivity.intentChoiceName,  0 );
        startActivity(intent);
    }


}
