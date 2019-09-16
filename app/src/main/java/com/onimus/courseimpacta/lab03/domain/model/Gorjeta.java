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

package com.onimus.courseimpacta.lab03.domain.model;

import java.util.Locale;

public final class Gorjeta {

    public static final String VALOR_0 = "0.00";

    public interface Formats {
        String VALUE = "%.02f";

    }

    private Gorjeta() {
        super();

    }

    private static double calcularGorjeta(double valor, double percentual) {
        return valor * percentual;

    }

    private static double toPercent(Integer value) {
        return value / 100.0;
    }

    private static CharSequence calcularValor(double valor, double percentual) {
        return String.format(Locale.getDefault(), Formats.VALUE, (valor + calcularGorjeta(valor, percentual)));
    }

    public static CharSequence calcularGorjeta(CharSequence valor, Integer percentual) {
        valor = valor == null || valor.length() == 0 ? VALOR_0 : valor;

        return calcularGorjeta(Double.valueOf(valor.toString()), percentual);
    }

    private static CharSequence calcularGorjeta(Double valor, Integer percentual) {
        percentual = percentual == null ? Integer.valueOf(VALOR_0) : percentual;

        return String.format(Locale.getDefault(), Formats.VALUE, calcularGorjeta(valor, toPercent(percentual)));
    }

    public static CharSequence calcularValor(CharSequence valor, Integer percentual) {
        valor = valor == null || valor.length() == 0 ? VALOR_0 : valor;

        return calcularValor(Double.valueOf(valor.toString()), percentual);

    }

    private static CharSequence calcularValor(Double valor, Integer percentual) {
        percentual = percentual == null ? Integer.valueOf(VALOR_0) : percentual;
        return calcularValor(valor, toPercent(percentual));

    }

}
