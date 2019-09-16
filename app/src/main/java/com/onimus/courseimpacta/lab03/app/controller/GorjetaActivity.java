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

package com.onimus.courseimpacta.lab03.app.controller;

import android.content.res.Resources;
import android.os.Bundle;


import android.widget.EditText;
import android.widget.SeekBar;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;
import com.onimus.courseimpacta.lab01.design.pattern.OnSeekBarChangeAdapter;
import com.onimus.courseimpacta.lab01.design.pattern.TextWatcherAdapter;
import com.onimus.courseimpacta.lab03.domain.model.Gorjeta;
import com.onimus.courseimpacta.lab01.app.controller.Constantes;

public class GorjetaActivity extends MainUtilitiesActivity {

    private EditText et_conta;
    private SeekBar sb_def;

    //Ação de tratar valor da conta
    private class ValorZeroTextWatcherAction extends TextWatcherAdapter {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null || s.length() == 0) {
                et_conta.setText(Gorjeta.VALOR_0);
                et_conta.selectAll();
            }
        }
    }

    //Ação de tratar o cálculo a 10% do valor da conta
    private class Calcular10PorcentoTextWatcherAction extends TextWatcherAdapter {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final Resources r = getResources();
            CharSequence v;
            Integer p;

            p = r.getInteger(R.integer.lab03_percent_1);
            v = Gorjeta.calcularGorjeta(s, p);
            setText(R.id.lab03_et_gorjeta_1, v);

            v = Gorjeta.calcularValor(s, p);
            setText(R.id.lab03_et_valor_1, v);

        }
    }

    //Ação de tratar o cálculo a 15% do valor da conta
    private class Calcular15PorcentoTextWatcherAction extends TextWatcherAdapter {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final Resources r = getResources();
            CharSequence v;
            Integer p;

            p = r.getInteger(R.integer.lab03_percent_2);
            v = Gorjeta.calcularGorjeta(s, p);
            setText(R.id.lab03_et_gorjeta_2, v);

            v = Gorjeta.calcularValor(s, p);
            setText(R.id.lab03_et_valor_2, v);

        }
    }

    //Ação de tratar o cálculo a 20% do valor da conta
    private class Calcular20PorcentoTextWatcherAction extends TextWatcherAdapter {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final Resources r = getResources();
            CharSequence v;
            Integer p;

            p = r.getInteger(R.integer.lab03_percent_3);
            v = Gorjeta.calcularGorjeta(s, p);
            setText(R.id.lab03_et_gorjeta_3, v);

            v = Gorjeta.calcularValor(s, p);
            setText(R.id.lab03_et_valor_3, v);

        }
    }

    //Ação de tratar o cálculo a 20% do valor da conta
    private class CalcularPercentagemDefinidaTextWatcherAction extends TextWatcherAdapter {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final Resources r = getResources();
            CharSequence v;
            Integer p;

            p = sb_def.getProgress();
            v = Gorjeta.calcularGorjeta(s, p);
            setText(R.id.lab03_et_gorjeta_def, v);

            v = Gorjeta.calcularValor(s, p);
            setText(R.id.lab03_et_valor_def, v);

        }
    }

    //Ação de tratar a escolha do valor de porcentagem definida pelo usuário
    private class OnSeekBarChangeAction extends OnSeekBarChangeAdapter {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            CharSequence v, s;

            s = et_conta.getText();
            v = Gorjeta.calcularGorjeta(s, progress);
            setText(R.id.lab03_et_gorjeta_def, v);

            v = Gorjeta.calcularValor(s, progress);
            setText(R.id.lab03_et_valor_def, v);


            //Feito por mim, apostila errada.
            CharSequence f = Integer.toString(progress);
            setText(R.id.lab03_tv_def, f + "%");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab03_gorjeta);


        et_conta = findViewById(R.id.lab03_et_conta);
        sb_def = findViewById(R.id.lab03_sb_def);

        et_conta.addTextChangedListener(new ValorZeroTextWatcherAction());
        et_conta.addTextChangedListener(new Calcular10PorcentoTextWatcherAction());
        et_conta.addTextChangedListener(new Calcular15PorcentoTextWatcherAction());
        et_conta.addTextChangedListener(new Calcular20PorcentoTextWatcherAction());
        et_conta.addTextChangedListener(new CalcularPercentagemDefinidaTextWatcherAction());

        sb_def.setOnSeekBarChangeListener(new OnSeekBarChangeAction());

        setOnLongClickAction(R.id.lab03_et_gorjeta_1, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));
        setOnLongClickAction(R.id.lab03_et_gorjeta_2, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));
        setOnLongClickAction(R.id.lab03_et_gorjeta_3, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));

        setOnLongClickAction(R.id.lab03_et_valor_1, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));
        setOnLongClickAction(R.id.lab03_et_valor_2, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));
        setOnLongClickAction(R.id.lab03_et_valor_3, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));

        setOnLongClickAction(R.id.lab03_et_gorjeta_def, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));
        setOnLongClickAction(R.id.lab03_et_valor_def, new copyToClipboardAction(Constantes.CLIPBOARD_GORJETA, R.string.lab03_copy));


    }




    //Classe que eu criei, ao segurar o botão ele faz uma cópia do TextView e guarda no clipboard.
   /* private class CopyToClipboardAction implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            TextView tv = (TextView) v;

            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(CLIPBOARD_GORJETA, tv.getText());
            myClipboard.setPrimaryClip(clip);

            Toast.makeText(getApplicationContext(), R.string.lab03_copy, Toast.LENGTH_SHORT).show();

            return true;
        }
    }
    */
}