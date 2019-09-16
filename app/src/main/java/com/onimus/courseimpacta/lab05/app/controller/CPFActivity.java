/*
 *
 *  * Created by Murillo Comino on 09/02/19 18:11
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 18:11
 *
 */

package com.onimus.courseimpacta.lab05.app.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;
import com.onimus.courseimpacta.lab05.domain.Documento;
import com.onimus.courseimpacta.lab05.domain.model.CPF;

import java.util.Objects;

public class CPFActivity extends MainUtilitiesActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lab05_cpf);
    }

    public void validar(View v) {
        EditText valor;
        Documento documento;

        try {
            valor = findViewById(R.id.lab05_et_cpf);
            documento = new CPF(valor.getText());

            documento.validar();
            Toast.makeText(this, getString(Documento.VALIDO), Toast.LENGTH_LONG).show();
        } catch (Exception cause) {
            Toast.makeText(this, getString(Integer.parseInt(Objects.requireNonNull(cause.getMessage()))), Toast.LENGTH_LONG).show();
        }
    }
}
