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

package com.example.admin.examples.lab05.domain;

import com.example.admin.examples.R;
import com.example.admin.examples.lab05.domain.exception.DocumentoException;

public interface Documento {

    int VALIDO = R.string.doc_valido;

       void validar() throws DocumentoException;
}
