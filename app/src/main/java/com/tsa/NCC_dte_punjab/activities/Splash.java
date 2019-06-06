package com.tsa.NCC_dte_punjab.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.tsa.NCC_dte_punjab.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends CustomActivity {

    //Set Duration of the Splash Screen
    long Delay = 2000;
    private Intent i;
    private Context context;
    SharedPreferences sharedPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove the Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        context = Splash.this;
        sharedPref = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        i = new Intent(Splash.this, HomeActivity.class);

        // Get the view from splash_activity.xml
        setContentView(R.layout.activitysplash);

        Timer RunSplash = new Timer();

        // Task to do when the timer ends
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
                if (i != null)
                    startActivity(i);
                finish();
            }
        };

        //Start the timer
        RunSplash.schedule(ShowSplash, Delay);
    }

}

