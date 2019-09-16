/*
 *
 *  * Created by Murillo Comino on 09/02/19 15:39
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 14:25
 *
 */

package com.onimus.courseimpacta.lab01.app.controller;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.onimus.courseimpacta.R;

import android.os.Handler;

public class SplashActivity extends MainUtilitiesActivity {

    private Handler h;
    private Resources r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lab01_splash);

        h = new Handler();
        r = getResources();

    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent i = new Intent (this, MainActivity.class);
        final int d = r.getInteger(R.integer.lab01_handler_delay);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
            }
        }, d);



    }
}
