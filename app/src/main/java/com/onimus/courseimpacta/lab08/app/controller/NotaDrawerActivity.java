package com.onimus.courseimpacta.lab08.app.controller;

import android.app.ActionBar;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab01.app.controller.MainUtilitiesActivity;
import com.onimus.courseimpacta.lab08.domain.model.Nota;
import com.onimus.courseimpacta.lab08.respository.NotaDAO;
import com.onimus.courseimpacta.lab08.respository.sqlite.NotaSQLite;
import com.onimus.courseimpacta.lab08.respository.sqlite.SQLiteNotaDAO;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class NotaDrawerActivity extends MainUtilitiesActivity {

    private DrawerLayout layout;
    private TextView titleView;
    private ListView drawerView;
    private ListView notasView;
    private Menu menu;
    private ActionBar ab;
    private ActionBarDrawerToggle toggle;
    private NotaDAO dao;
    private Resources r;

    private int drawerPosition;
    private String[] drawerItems;

    private int notasSelecionadas = 0;
    private int notaPosition;
    private List<Nota> notas = Collections.emptyList();

    private class NotaCursorAdapter extends ArrayAdapter<Nota> {

        private LayoutInflater li;
        private int layout;

        NotaCursorAdapter(Context context, int layout, List<Nota> notas) {
            super(context, layout, notas);

            this.li = LayoutInflater.from(context);
            this.layout = layout;
        }

        @NotNull
        @Override
        public View getView(int position, View convertView, @NotNull ViewGroup parent) {
            SimpleDateFormat sdf = new SimpleDateFormat(NotaSQLite.DATETIME_FORMAT,
                    Locale.getDefault());
            CheckBox cb;
            TextView tv;
            Nota n;
            convertView = convertView == null ? li.inflate(layout, null) : convertView;

            cb = convertView.findViewById(R.id.lab08_nota_fazer);
            tv = convertView.findViewById(R.id.lab08_nota_titulo);
            n = notas.get(position);

            cb.setEnabled(!n.isFeita());
            cb.setChecked(n.isFeita());
            tv.setText(n.getTitulo());

            if (n.isAgendada()) {
                tv = convertView.findViewById(R.id.lab08_nota_agenda);
                tv.setText(r.getString(R.string.lab08_nota_agenda_em, sdf.format(n.getDataAgenda())));
            }

            if (n.isFeita()) {
                tv =  convertView.findViewById(R.id.lab08_nota_feita);
                tv.setText(r.getString(R.string.lab08_nota_feita_em, sdf.format(n.getDataAgendaFeita())));
            }

            convertView.setEnabled(NotaDrawerActivity.this.drawerPosition != 1);

            return convertView;
        }
    }

    private ListView.OnItemClickListener onItemClickAction = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            NotaDrawerActivity.this.drawerPosition = position;
            NotaDrawerActivity.this.notasSelecionadas = 0;
            onResume();
            layout.closeDrawer(drawerView);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lab08_nota_drawer);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (nm != null) {
            nm.cancel(10);
        }

        r = getResources();
        drawerItems = r.getStringArray(R.array.lab08_itens);
        dao = SQLiteNotaDAO.newInstance(this);
        ab = getActionBar();
        layout =findViewById(R.id.lab08_drawer_layout);

        drawerView =  findViewById(R.id.lab08_left_drawer);
        notasView = findViewById(android.R.id.list);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.app_name, R.string.lab08_acoes) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (ab != null) {
                    ab.setTitle(R.string.lab08_acoes);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                if (ab != null) {
                    ab.setTitle(R.string.app_name);
                }
            }
        };

        layout.addDrawerListener(toggle);

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        drawerView.setAdapter(new ArrayAdapter<>(this,
                R.layout.lab08_nota_drawer_item,
                R.id.lab08_item,
                drawerItems));

        drawerView.setOnItemClickListener(onItemClickAction);

        notasView.setOnItemClickListener(new AdapterView.
                OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.isEnabled()) {
                    CheckBox cb = view.findViewById(R.id.lab08_nota_fazer);
                    Nota n = notas.get(position);

                    cb.setChecked(!cb.isChecked());
                    n.setFeita(cb.isChecked());

                    if (n.isFeita()) {
                        notasSelecionadas++;
                    } else {
                        notasSelecionadas--;
                    }
                }
            }
        });

        notasView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                titleView = view.findViewById(R.id.lab08_nota_titulo);
                notaPosition = position;
                return false;
            }
        });

        registerForContextMenu(notasView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerView.setItemChecked(0, true);

        switch (drawerPosition) {
            case 0:
                notas = dao.selectToDo();
                break;
            case 1:
                notas = dao.selectDone();
                break;
            case 2:
                notas = dao.selectLost();
                break;
        }

        if (menu != null) {
            menu.findItem(R.id.lab08_nota_apagar).setVisible(drawerPosition != 1);
            menu.findItem(R.id.lab08_nota_feita).setVisible(drawerPosition != 1);
            menu.findItem(R.id.lab08_nota_nova).setShowAsAction(drawerPosition != 1 ?
                    MenuItem.SHOW_AS_ACTION_NEVER : MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        titleView = findViewById(R.id.lab01_bt_sqlite);
        titleView.setText(drawerItems[drawerPosition]);
        drawerView.setItemChecked(drawerPosition, true);

        ListAdapter a = new NotaCursorAdapter(this, R.layout.lab08_nota_fragment_item, notas);

        notasView.setAdapter(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mi = new MenuInflater(this);

        mi.inflate(R.menu.lab08_nota, menu);
        this.menu = menu;

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater mi = new MenuInflater(this);

        mi.inflate(R.menu.lab08_nota_context, menu);

        menu.setHeaderTitle(titleView.getText());
        menu.findItem(R.id.lab08_nota_atualizar).
                setVisible(drawerPosition != 1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, NotaActivity.class);

        switch (item.getItemId()) {
            case R.id.lab08_nota_feita:
                if (!notas.isEmpty()) {
                    for (Nota n : notas) {

                        if (n.isFeita()) {
                            dao.updateDone(n.getId());
                        }
                    }

                    Toast.makeText(this, R.string.lab08_nota_feita, Toast.LENGTH_SHORT).show();
                    onResume();
                }
                break;
            case R.id.lab08_nota_detalhe:
                i.putExtra(NotaActivity.LAYOUT, R.layout.lab08_nota);
                startActivity(i);
            case R.id.lab08_nota_atualizar:
                i.putExtra(NotaActivity.ITEM_SELECTED, notas.get(notaPosition));
                startActivity(i);
            case R.id.lab08_nota_nova:
                startActivity(i);
                break;
            case R.id.lab08_nota_apagar:
                if (notasSelecionadas > 0) {
                    AlertDialog alert = new AlertDialog.Builder(this)
                            .setMessage(R.string.lab08_nota_apagar)
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!notas.isEmpty()) {
                                        for (Nota n : notas) {
                                            if (n.isFeita()) {
                                                dao.delete(n);
                                            }
                                        }

                                        Toast.makeText(NotaDrawerActivity.this, R.string.lab08_nota_apagada, Toast.LENGTH_SHORT).show();
                                        onResume();
                                    }
                                }
                            })
                            .create();

                    alert.show();
                } else {
                    Toast.makeText(NotaDrawerActivity.this,
                            R.string.lab08_nota_selecione,
                            Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return toggle.onOptionsItemSelected(item);
    }


}
