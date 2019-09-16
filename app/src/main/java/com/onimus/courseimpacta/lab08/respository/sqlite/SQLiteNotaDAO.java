package com.onimus.courseimpacta.lab08.respository.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab08.domain.model.Nota;
import com.onimus.courseimpacta.lab08.respository.NotaDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SQLiteNotaDAO implements NotaDAO {

    private Context context;
    private SQLiteDatabase sqlite;
    private SQLiteOpenHelper helper;

    private SQLiteNotaDAO(Context context) {
        super();

        this.context = context;
        this.helper = NotaSQLiteOpenHelper.getInstance(context);
    }

    public static NotaDAO newInstance(Context context) {
        return new SQLiteNotaDAO(context);
    }

    @Override
    public void insert(Nota nota) {

        ContentValues values = new ContentValues();
        Long _id;

        try {
            nota.setDataCriacao(new Date());

            values.put(NotaSQLite.NotaTable.Columns.TITLE.toString(), nota.getTitulo());
            values.put(NotaSQLite.NotaTable.Columns.COMMENT.toString(), nota.getObservacao());
            values.put(NotaSQLite.NotaTable.Columns.DONE.toString(), nota.isFeita());
            values.put(NotaSQLite.NotaTable.Columns.DATE_CREATE.toString(), nota.getDataCriacaoInMillis());
            values.put(NotaSQLite.NotaTable.Columns.SCHEDULED.toString(), nota.isAgendada());

            if (nota.isAgendada()) {
                values.put(NotaSQLite.NotaTable.Columns.DATE_SCHEDULED.toString(), nota.getDataAgendaInMillis());
            }

            sqlite = helper.getWritableDatabase();
            _id = sqlite.insert(NotaSQLite.NotaTable.NAME, NotaSQLite.NotaTable.NULL_COLUMN_HACK, values);
            nota.setId(_id);
        } finally {
            helper.close();
            sqlite = null;
        }
    }

    @Override
    public void updateDone(Long id) {

        ContentValues values = new ContentValues();
        int rows;

        try {
            values.put(NotaSQLite.NotaTable.Columns.DONE.toString(), Boolean.TRUE);
            values.put(NotaSQLite.NotaTable.Columns.DATE_MODIFY.toString(), new Date().getTime());
            values.put(NotaSQLite.NotaTable.Columns.DATE_DONE.toString(), new Date().getTime());

            sqlite = helper.getWritableDatabase();
            rows = sqlite.update(NotaSQLite.NotaTable.NAME, values, NotaSQLite.NotaTable.WHERE_ID, new String[]{
                    id.toString()});

            if (rows == 0) {
                Log.w(TAG, context.getResources().getString(R.string.lab08_notas_nao_feitas));
            }
        } finally {
            helper.close();
            sqlite = null;
        }

    }

    @Override
    public void update(Nota nota) {

        ContentValues values = new ContentValues();

        try {
            nota.setDataModificacao(new Date());

            values.put(NotaSQLite.NotaTable.Columns._ID.toString(), nota.getId());
            values.put(NotaSQLite.NotaTable.Columns.TITLE.toString(), nota.getTitulo());
            values.put(NotaSQLite.NotaTable.Columns.COMMENT.toString(), nota.getObservacao());
            values.put(NotaSQLite.NotaTable.Columns.DONE.toString(), nota.isFeita());
            values.put(NotaSQLite.NotaTable.Columns.SCHEDULED.toString(), nota.isAgendada());
            values.put(NotaSQLite.NotaTable.Columns.DATE_MODIFY.toString(), nota.getDataModificacaoInMillis());
            values.put(NotaSQLite.NotaTable.Columns.DATE_SCHEDULED.toString(), nota.getDataAgendaInMillis());

            sqlite = helper.getWritableDatabase();
            sqlite.update(NotaSQLite.NotaTable.NAME, values, NotaSQLite.NotaTable.WHERE_ID, new String[]{
                    nota.getId().toString()
            });
        } finally {
            helper.close();
            sqlite = null;
        }

    }

    @Override
    public void delete(Nota nota) {
        try {
            sqlite = helper.getWritableDatabase();
            sqlite.delete(NotaSQLite.NotaTable.NAME, NotaSQLite.NotaTable.WHERE_ID, new String[]{
                    nota.getId().toString()
            });
        } finally {
            helper.close();
            sqlite = null;
        }

    }

    private List<Nota> read(Cursor cr) {
        ArrayList<Nota> l = new ArrayList<>();
        int i;
        Nota n;

        while (cr.moveToNext()) {
            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns._ID.toString());
            n = new Nota(cr.getLong(i));

            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.TITLE.toString());
            n.setTitulo(cr.getString(i));

            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.COMMENT.toString());
            n.setObservacao(cr.getString(i));
            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.SCHEDULED.toString());
            n.setAgendada(cr.getLong(i) == 1L);

            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.DATE_SCHEDULED.toString());
            n.setDataAgenda(cr.getLong(i));

            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.DONE.toString());
            n.setFeita(cr.getLong(i) == 1L);

            i = cr.getColumnIndex(NotaSQLite.NotaTable.Columns.DATE_DONE.toString());
            n.setDataAgendaFeita(cr.getLong(i));

            l.add(n);
        }
        return l;
    }

    @Override
    public List<Nota> selectLost() {

        Calendar c = Calendar.getInstance();
        Cursor cr = null;
        long timeInMillis;

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        timeInMillis = c.getTimeInMillis();
        try {
            sqlite = helper.getReadableDatabase();
            cr = sqlite.rawQuery(NotaSQLite.NotaTable.RawQuery.SELECT_NOTAS_PERDIDAS, new String[]{
                    Long.toString(timeInMillis)
            });

            return read(cr);
        } finally {
            if (cr != null) {
                cr.close();
            }
        }

    }

    @Override
    public List<Nota> selectToDo() {

        Calendar c = Calendar.getInstance();
        Cursor cr = null;

        try {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            sqlite = helper.getWritableDatabase();
            cr = sqlite.rawQuery(NotaSQLite.NotaTable.RawQuery.SELECT_NOTAS_AFAZER, new String[]{
                    String.valueOf(c.getTimeInMillis())
            });

            return read(cr);
        } finally {
            if (cr != null) {
                cr.close();
            }
        }

    }

    @Override
    public List<Nota> selectDone() {
        Cursor cr = null;

        try {
            sqlite = helper.getReadableDatabase();
            cr = sqlite.rawQuery(NotaSQLite.NotaTable.RawQuery.SELECT_NOTAS_FEITAS, new String[]{});

            return read(cr);
        } finally {
            if (cr != null) {
                cr.close();
            }
        }

    }
}
