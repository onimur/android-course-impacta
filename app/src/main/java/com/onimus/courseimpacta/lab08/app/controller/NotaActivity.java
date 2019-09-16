package com.onimus.courseimpacta.lab08.app.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;
import com.onimus.courseimpacta.lab08.domain.model.Nota;
import com.onimus.courseimpacta.lab08.respository.NotaDAO;
import com.onimus.courseimpacta.lab08.respository.sqlite.NotaSQLite;
import com.onimus.courseimpacta.lab08.respository.sqlite.SQLiteNotaDAO;
import com.onimus.courseimpacta.lab08.util.DateHelper;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class NotaActivity extends MainUtilitiesActivity {

    public static final String TAG = "NOTA NOVA";
    public static final String ITEM_SELECTED = "nota selecionada";
    public static final String LAYOUT = "nota layout";

    private int layout = R.layout.lab08_nota_edit;
    private TextView titleView;
    private TextView commentView;
    @SuppressLint("StaticFieldLeak")
    private static TextView scheduledDateView;
    private CheckBox scheduledView;
    private NotaDAO dao;

    private boolean isChecked;
    private Nota nota;
    private static CharSequence date;

    private DateHelper dateHelper;

    private CheckBox.OnClickListener onCheckedClickAction = new CheckBox.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isChecked) {

                        timeDialog.show(getSupportFragmentManager(), TAG);
                        dateDialog.show(getSupportFragmentManager(), TAG);
                    } else {
                        scheduledDateView.setText(null);
                    }
                }
            };
    private CheckBox.OnCheckedChangeListener onCheckedChangeAction = new CheckBox.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NotaActivity.this.isChecked = isChecked;
        }
    };


    DialogFragment timeDialog = new TimePickerFragment();

    DialogFragment dateDialog = new DatePickerFragment();


    public static class TimePickerFragment  extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            SimpleDateFormat sdf = new SimpleDateFormat(NotaSQLite.TIME_FORMAT, Locale.getDefault());
            Calendar c = Calendar.getInstance();

            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);


            scheduledDateView.setText(String.format("%s %s", date, sdf.format(c.getTime())));
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            SimpleDateFormat sdf = new SimpleDateFormat(NotaSQLite.DATE_FORMAT, Locale.getDefault());

            Calendar c = Calendar.getInstance();

            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            date = sdf.format(c.getTime());
            scheduledDateView.setText(date);

        }
    }




        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle b = getIntent().getExtras();

            if (b != null) {
                layout = b.getInt(LAYOUT);
                layout = layout == 0 ? R.layout.lab08_nota_edit : layout;
                nota = (Nota) b.get(ITEM_SELECTED);
            }

            setContentView(layout);

            dao = SQLiteNotaDAO.newInstance(getBaseContext());
            dateHelper = DateHelper.getInstance();
            titleView = findViewById(R.id.lab08_nota_titulo);
            commentView = findViewById(R.id.lab08_nota_observacao);
            scheduledDateView = findViewById(R.id.lab08_nota_data);
            scheduledView = findViewById(R.id.lab08_agenda_check);

            scheduledView.setOnClickListener(onCheckedClickAction);
            scheduledView.setOnCheckedChangeListener(onCheckedChangeAction);
        }

        @Override
        protected void onResume() {
            super.onResume();

            if (nota != null) {
                titleView.setText(nota.getTitulo());
                commentView.setText(nota.getObservacao());
                scheduledView.setChecked(nota.isAgendada());

                if (nota.isAgendada()) {
                    scheduledDateView.setText(dateHelper.format(NotaSQLite.DATETIME_FORMAT, nota.getDataAgenda()));
                }
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(Menu.NONE, R.id.lab08_nota_atualizar, 0, R.string.lab08_nota_atualizar)
                    .setIcon(android.R.drawable.ic_menu_save)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            return layout == R.layout.lab08_nota_edit;
        }

        @Override
        public boolean onOptionsItemSelected(@NotNull MenuItem item) {
            try {
                if (nota == null) {
                    nota = new Nota(titleView.getText(), commentView.getText());
                } else {
                    nota.setTitulo(titleView.getText());
                    nota.setObservacao(commentView.getText());
                }

                nota.setAgendada(scheduledView.isChecked());

                if (scheduledView.isChecked()) {
                    String dt = scheduledDateView.getText().toString();
                    nota.setDataAgenda(dateHelper.convertToTimeInMillis(NotaSQLite.DATETIME_FORMAT, dt));
                }

                if (nota.isNullId()) {
                    dao.insert(nota);
                } else {
                    dao.update(nota);
                }

                Toast.makeText(getBaseContext(), "Salvo", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception cause) {
                Log.e(TAG, "Conversão de data", cause);
            }

            return true;
        }


    }
