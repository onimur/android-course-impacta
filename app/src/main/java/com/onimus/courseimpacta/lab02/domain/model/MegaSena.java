/*
 *
 *  * Created by Murillo Comino on 09/02/19 15:39
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 13:57
 *
 */

package com.onimus.courseimpacta.lab02.domain.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public final class MegaSena {

    public interface Bundle {
        String JOGO = "lab02.megasena.jogo";
        String JOGOS = "lab02.megasena.jogos";

    }

    //formata a sequencia de valores para String
    public interface Formato {
        String JOGO = "%02d ";
    }

    //Cria 6 números randomicos, não repetidos em ordem numérica.
    private MegaSena() {
        super();
    }

    //Ação que gera 6 números aleatórios entre 1 e 60 em ordem.
    private static String sortear() {



        Set<String> numberset = new HashSet<>();
        Random random = new Random();

        while (numberset.size() < 6) {
            String numero = String.format(Locale.getDefault(),Formato.JOGO, (random.nextInt(60) + 1) );
            numberset.add(numero);
        }

        ArrayList<String> jogo = new ArrayList<>(numberset);
        Collections.sort(jogo);


        return Arrays.toString(jogo.toArray());



    }

    //Adiciona os jogos indicado pelo usuário em uma ArrayList
    public static ArrayList<String> sortear(int jogos) {
        final ArrayList<String> js = new ArrayList<>();


        for (int i = 0; i < jogos; i++) {
            js.add(sortear());

        }
        Collections.sort(js);
        for (String s : js) {
            System.out.println(s);
        }
        return js;


    }


}