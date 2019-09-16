package com.onimus.courseimpacta.lab08.respository.sqlite;

import org.jetbrains.annotations.NotNull;

public interface NotaSQLite {
    String NAME_DB = "impacta-notas";
    Integer INITIAL_VERSION = 1;
    String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    String DATE_FORMAT = "dd/MM/yyyy";
    String TIME_FORMAT = "HH:mm";

    interface NotaTable {
        String NAME = "nota";
        String NULL_COLUMN_HACK = String.format("%s, %s", Columns.COMMENT, Columns.DATE_SCHEDULED);
        String WHERE_ID = String.format("%s = ?", Columns._ID);

        interface DDL {
            String CREATE = String.format("CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s, %s)", NAME,
                    String.format("%s INTEGER PRIMARY KEY AUTOINCREMENT", Columns._ID),
                    String.format("%s VARCHAR(20) NOT NULL", Columns.TITLE),
                    String.format("%s TEXT NULL", Columns.COMMENT),
                    String.format("%s CHAR(1) NOT NULL", Columns.DONE),
                    String.format("%s CHAR(1) NOT NULL", Columns.SCHEDULED),
                    String.format("%s NUMERIC NOT NULL", Columns.DATE_CREATE),
                    String.format("%s NUMERIC NULL", Columns.DATE_MODIFY),
                    String.format("%s NUMERIC NULL", Columns.DATE_DONE),
                    String.format("%s NUMERIC NULL", Columns.DATE_SCHEDULED));
        }

        enum Columns {
            _ID("_id"),
            TITLE("ds_titulo"),
            COMMENT("ds_observacao"),
            DONE("fg_feita"),
            DATE_CREATE("dh_criada"),
            DATE_MODIFY("dh_modificada"),
            SCHEDULED("fg_agendada"), DATE_SCHEDULED("dh_agendada"),
            DATE_DONE("dh_feita");

            private String field;

            Columns(java.lang.String field) {
                this.field = field;
            }

            @NotNull
            @Override
            public String toString() {
                return field;
            }
        }

        interface RawQuery {
            String SELECT_NOTAS_AFAZER = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM nota %s ORDER BY %s ASC",
                    Columns._ID, Columns.TITLE, Columns.COMMENT,
                    Columns.SCHEDULED, Columns.DONE,
                    Columns.DATE_SCHEDULED, Columns.DATE_DONE,
                    "WHERE fg_feita = '0' AND (dh_agendada IS NULL OR dh_agendada > ?)",
                    Columns.DATE_CREATE);
            String SELECT_NOTAS_FEITAS = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM nota %s ORDER BY %s ASC",
                    Columns._ID, Columns.TITLE, Columns.COMMENT,
                    Columns.SCHEDULED, Columns.DONE,
                    Columns.DATE_SCHEDULED, Columns.DATE_DONE,
                    "WHERE fg_feita = '1' AND dh_feita NOT NULL",
                    Columns.DATE_DONE);
            String SELECT_NOTAS_PERDIDAS = String.format("SELECT %s, %s, %s, %s, %s, %s, %s FROM nota %s ORDER BY %s DESC",
                    Columns._ID, Columns.TITLE, Columns.COMMENT,
                    Columns.SCHEDULED, Columns.DONE,
                    Columns.DATE_SCHEDULED, Columns.DATE_DONE,
                    "WHERE fg_feita = '0' AND dh_agendada < ?",
                    Columns.DATE_CREATE);
        }
    }

}
