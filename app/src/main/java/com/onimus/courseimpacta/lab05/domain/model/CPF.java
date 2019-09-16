/*
 *
 *  * Created by Murillo Comino on 09/02/19 18:23
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 18:23
 *
 */

package com.onimus.courseimpacta.lab05.domain.model;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab05.domain.Documento;
import com.onimus.courseimpacta.lab05.domain.exception.DocumentoException;

public class CPF implements Documento {

    private CharSequence valor;

    public CPF(CharSequence valor) {
        super();

        this.valor = valor;
    }

    private boolean verificarDigitosDiferentes() {
        char digito = ' ', atual;

        for (int i = 0; i < valor.length(); i++) {
            atual = valor.charAt(i);
            if (i == 0) {
                digito = atual;
            } else if (atual != digito) {
                return true;
            }
        }

        return false;
    }

    private boolean verificarDigito(int digito) {
        int posicao = 9, resultado = 0, numero;

        posicao += digito;

        for (int i = 0; i < (8 + digito); i++) {
            numero = Character.getNumericValue(valor.charAt(i));

            resultado += numero * posicao--;
        }

        posicao = 8 + digito;

        resultado = 11 - (resultado % 11);
        resultado = (resultado > 9) ? 0 : resultado;

        return resultado == Character.getNumericValue(valor.
                charAt(posicao));
    }

    private boolean isValido() {
        if (verificarDigitosDiferentes()) {
            if (verificarDigito(1)) {
                return verificarDigito(2);
            }
        }

        return false;
    }

    private boolean isNotValido() {
        return !isValido();
    }

    @Override
    public void validar() throws DocumentoException {
        if (valor == null) {
            throw new DocumentoException(R.string.cpf_nulo);
            }

        if (valor.length() == 0) {
            throw new DocumentoException(R.string.cpf_vazio);
            }

        if (valor.length() != 11) {
            throw new DocumentoException(R.string.cpf_11);
            }

        if (isNotValido()) {
            throw new DocumentoException(R.string.cpf_invalido);
            }
    }
}
