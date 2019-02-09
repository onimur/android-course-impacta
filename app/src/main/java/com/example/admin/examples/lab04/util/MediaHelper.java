/*
 *
 *  * Created by Murillo Comino on 09/02/19 15:39
 *  * Github: github.com/MurilloComino
 *  * StackOverFlow: pt.stackoverflow.com/users/128573
 *  * Email: murillo_comino@hotmail.com
 *  *
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 09/02/19 15:39
 *
 */

package com.example.admin.examples.lab04.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.admin.examples.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class MediaHelper {
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    private MediaHelper() {
        super();
    }

    //Determina o local e nome do arquivo gerado
    private static File getOutputMediaFile(final int type) {
        File path;
        File dir;
        String timeStamp;
        File mediaFile = null;

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        dir = new File(path, "image1");

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d("LAB04", "Pasta não criada");
                return mediaFile;
            }
        }

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        switch (type) {
            case TYPE_IMAGE:
                mediaFile = new File(String.format("%s%sIMG_%s.jpg",
                        dir.getPath(), File.separator, timeStamp));
                break;

            case TYPE_VIDEO:
                mediaFile = new File(String.format("%s%sVID_%s.mp4",
                        dir.getPath(), File.separator, timeStamp));
                break;
            default:
                mediaFile = null;
                break;
        }
        return mediaFile;
    }

    public static Uri getOutputMediaVideoFileUri(Context context) {
        return getUri(context, TYPE_VIDEO);
    }
    public  static Uri getOutputMediaImageFileUri (Context context) {
        return getUri(context, TYPE_IMAGE);
    }

    //finalidade de separar o tipo de chamada, pela versão do android no dispositivo:
    public static Uri getUri(Context context, int type) {
        Uri uri;
        if (isAndroidMarshmallowOrSuperiorVersion()) {
            uri = getUriFrom(context, getOutputMediaFile(type));
        } else {
            uri = Uri.fromFile(getOutputMediaFile(type));
        }
        return uri;
    }

    private static Uri getUriFrom(Context context, File file) {
        return FileProvider.getUriForFile(context, getAuthority(), file);
    }
    @NonNull
    private static String getAuthority() {
        return BuildConfig.APPLICATION_ID + ".provider";
    }

    private static boolean isAndroidMarshmallowOrSuperiorVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


}
