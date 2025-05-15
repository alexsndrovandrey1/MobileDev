package ru.mirea.alexsandrovaa.serviceapp;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "MusicChannel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();

        // Создание канала уведомлений
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Music Notification",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("Channel for music playback");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        // Создание уведомления
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("The LAst Of Us Александро Андрей")
                .setContentText("Playing music in background...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        // Запуск Foreground Service с типом
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
        } else {
            startForeground(1, notification);
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.tlou);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp -> stopSelf());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}