package com.onimus.courseimpacta.lab08.respository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotaSQLiteOpenHelper extends SQLiteOpenHelper {
    private static NotaSQLiteOpenHelper helper;

    private NotaSQLiteOpenHelper(Context context) {
        super(context, NotaSQLite.NAME_DB, null, NotaSQLite.INITIAL_VERSION);
    }

    static NotaSQLiteOpenHelper getInstance(Context context) {
        return helper = helper == null ? new
                NotaSQLiteOpenHelper(context) : helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotaSQLite.NotaTable.DDL.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int
            oldVersion, int newVersion) {
    }
}
