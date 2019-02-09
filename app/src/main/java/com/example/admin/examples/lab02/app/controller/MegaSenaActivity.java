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

package com.example.admin.examples.lab02.app.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.admin.examples.R;
import com.example.admin.examples.lab01.app.controller.MainUtilitiesActivity;
import com.example.admin.examples.lab01.design.pattern.OnSeekBarChangeAdapter;
import com.example.admin.examples.lab02.domain.model.MegaSena;

import java.util.ArrayList;

public class MegaSenaActivity extends MainUtilitiesActivity {

    private TextView tv_sorteios;
    private SeekBar sb_sorteios;
    private ListView lv_sorteios;

    private ArrayList<String> jogos;

    //Ação de mudança de valor no SeekBar
    private class OnSeekBarChangeAction extends OnSeekBarChangeAdapter {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                tv_sorteios.setText(String.valueOf(progress));
            }
        }
    }

    //Ação de clique no botão sortear
    private class OnButtonClickAction implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int p = sb_sorteios.getProgress();
            jogos = MegaSena.sortear(p);
            //
            lv_sorteios.setAdapter(new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.lab02_jogo,
                    jogos));


        }
    }

    //Ação de copiar para area de transferencia o sorteio que sofrer um clique longo
     private class OnItemLongClickAction implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            final CharSequence jogo = parent.getItemAtPosition(position).toString();

            return copyToClipBoard(MegaSena.Bundle.JOGO, jogo);
        }
    }

    private void atualizarListView(Bundle bundle) {
        if (bundle != null) {
            jogos = bundle.getStringArrayList(MegaSena.Bundle.JOGOS);

            if (jogos != null) {
                lv_sorteios.setAdapter(new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.lab02_jogo,
                        jogos));
            }

            tv_sorteios.setText(bundle.getCharSequence(MegaSena.Bundle.JOGO));
        }
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lab02_megasena);

        lv_sorteios = findViewById(android.R.id.list);
        sb_sorteios = findViewById(R.id.lab02_sb_sorteios);
        tv_sorteios = findViewById(R.id.lab02_tv_sorteios);



        setOnClickAction(R.id.lab02_bt_sortear, new OnButtonClickAction());
        sb_sorteios.setOnSeekBarChangeListener(new
                OnSeekBarChangeAction());
        lv_sorteios.setOnItemLongClickListener(new
                OnItemLongClickAction());

        atualizarListView(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(android.os.Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList(MegaSena.Bundle.JOGOS, jogos);
        outState.putCharSequence(MegaSena.Bundle.JOGO, tv_sorteios.getText());
    }
}
