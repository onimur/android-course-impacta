/*
 *
 *  * Created by Murillo Comino on 09/02/19 15:39
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 14:52
 *
 */

package com.onimus.courseimpacta.lab01.design.pattern;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

//Código para pedir permissão ao usuário
public class Permission {
    public static boolean hasPermissoes(Context context, String... permissoes) {
        if (context != null && permissoes != null) {
            for (String permissao : permissoes) {
                if (ActivityCompat.checkSelfPermission(context, permissao) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
