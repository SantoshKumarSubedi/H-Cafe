package com.hauday.hauday1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hauday.hauday1.Helper.GlobalState;

/**
 * Created by Hauday on 7/9/2016.
 */
public class Splash extends AppCompatActivity {
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash_screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalState globalState = GlobalState.singleton;
                if (globalState.getPrefscheckedLogin().equals("true")) {
                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent openMainActivity = new Intent(Splash.this, Loginact.class);
                    startActivity(openMainActivity);
                    finish();


                }
            }
        },5000);

    }

    }
