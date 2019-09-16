package com.onimus.courseimpacta.lab08.io;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.onimus.courseimpacta.R;
import com.onimus.courseimpacta.lab08.app.controller.NotaDrawerActivity;
import com.onimus.courseimpacta.lab08.domain.model.Nota;
import com.onimus.courseimpacta.lab08.respository.NotaDAO;
import com.onimus.courseimpacta.lab08.respository.sqlite.SQLiteNotaDAO;

import java.util.List;

public class NotaHojeService extends Service {

    private Resources r;

    private NotaDAO dao;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        r = getResources();


        dao = SQLiteNotaDAO.newInstance(this);
        createNotificationChannel();
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        List<Nota> notas = dao.selectToDo();

        if (!notas.isEmpty()) {
            //notificationManager = NotificationManagerCompat.from(this);
            createNotificationChannel();
            showNotification();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(this, NotaDrawerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, i, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.lab08_nota_titulo))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentInfo(r.getString(R.string.lab08_nota_titulo))
                .setContentTitle(r.getString(R.string.lab08_nota_fazer))
//                .setContentText(r.getString(R.string.lab08_nota_hoje, String.valueOf(notas.size())))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the i that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId("Examples");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.example.admin.examples",
                    "examples",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }


        if (notificationManager != null) {
            notificationManager.notify(10, mBuilder.build());
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.lab08_nota_titulo);
            // String description = getString(R.string.no);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.lab08_nota_titulo), name, importance);
            // channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
