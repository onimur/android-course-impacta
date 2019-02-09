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

package com.example.admin.examples.lab01.app.controller;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.admin.examples.R;
import com.example.admin.examples.lab01.design.pattern.Permission;
import com.example.admin.examples.lab02.app.controller.MegaSenaActivity;
import com.example.admin.examples.lab03.app.controller.GorjetaActivity;
import com.example.admin.examples.lab04.app.controller.IntentActivity;
import com.example.admin.examples.lab05.app.controller.CPFActivity;

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
        setToastOnClickAction(R.id.lab01_bt_gps);
        setToastOnClickAction(R.id.lab01_bt_sqlite);
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
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
                //   android.Manifest.permission.READ_CONTACTS,
                //  android.Manifest.permission.WRITE_CONTACTS,
                //   android.Manifest.permission.READ_SMS,
        };
        if (!Permission.hasPermissoes(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
    }
}
