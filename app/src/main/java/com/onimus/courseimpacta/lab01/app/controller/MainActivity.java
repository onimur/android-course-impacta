/*
 *
 *  * Created by Murillo Comino on 09/02/19 15:39
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 14:53
 *
 */

package com.onimus.courseimpacta.lab01.app.controller;

import android.Manifest;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.design.pattern.Permission;
import com.onimus.courseimpacta.lab02.app.controller.MegaSenaActivity;
import com.onimus.courseimpacta.lab03.app.controller.GorjetaActivity;
import com.onimus.courseimpacta.lab04.app.controller.IntentActivity;
import com.onimus.courseimpacta.lab05.app.controller.CPFActivity;
import com.onimus.courseimpacta.lab06.JogoVelhaActivity;
import com.onimus.courseimpacta.lab07.app.controller.GPSActivity;
import com.onimus.courseimpacta.lab08.app.controller.NotaDrawerActivity;

public class MainActivity extends MainUtilitiesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lab01_main);

        setPermissions();

        setToastOnClickAction(R.id.lab01_bt_megasena);
        setOnClickActivityAction(R.id.lab01_bt_megasena, MegaSenaActivity.class);

        setToastOnClickAction(R.id.lab01_bt_gorjeta);
        setOnClickActivityAction(R.id.lab01_bt_gorjeta, GorjetaActivity.class);

        setToastOnClickAction(R.id.lab01_bt_intent);
        setOnClickActivityAction(R.id.lab01_bt_intent, IntentActivity.class);

        setToastOnClickAction(R.id.lab01_bt_cpf);
        setOnClickActivityAction(R.id.lab01_bt_cpf, CPFActivity.class);

        setToastOnClickAction(R.id.lab01_bt_cep);

        setToastOnClickAction(R.id.lab01_bt_game01);
        setOnClickActivityAction(R.id.lab01_bt_game01, JogoVelhaActivity.class);

        setToastOnClickAction(R.id.lab01_bt_gps);
        setOnClickActivityAction(R.id.lab01_bt_gps, GPSActivity.class);

        setToastOnClickAction(R.id.lab01_bt_sqlite);
        setOnClickActivityAction(R.id.lab01_bt_sqlite, NotaDrawerActivity.class);

        setToastOnClickAction(R.id.lab01_bt_game02);
        setToastOnClickAction(R.id.lab01_bt_service);
        setToastOnClickAction(R.id.lab01_bt_bluetooth);
        setToastOnClickAction(R.id.lab01_bt_sms);
        setToastOnClickAction(R.id.lab01_bt_camera);
        setToastOnClickAction(R.id.lab01_bt_game03);
        setToastOnClickAction(R.id.lab01_bt_share_preferences);
    }


    private void setPermissions() {
        // O código de solicitação usado em ActivityCompat.requestPermissions ()
        // e retornado no onRequestPermissionsResult da Activity ()
        String[] PERMISSIONS;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PERMISSIONS = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,

                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE

                    //   android.Manifest.permission.READ_CONTACTS,
                    //  android.Manifest.permission.WRITE_CONTACTS,
                    //   android.Manifest.permission.READ_SMS,
            };
        } else {
            PERMISSIONS = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,

                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,

                    //   android.Manifest.permission.READ_CONTACTS,
                    //  android.Manifest.permission.WRITE_CONTACTS,
                    //   android.Manifest.permission.READ_SMS,
            };
        }
        if (!Permission.hasPermissoes(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }
}
