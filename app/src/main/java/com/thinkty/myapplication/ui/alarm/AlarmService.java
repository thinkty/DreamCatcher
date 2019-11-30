package com.thinkty.myapplication.ui.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.thinkty.myapplication.MainActivity;
import com.thinkty.myapplication.R;

public class AlarmService extends Service {

    private PowerManager.WakeLock wakeLock;
    public static final String TAG = "DreamCatcher:AlarmService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Wake up the screen to indicate alarm
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
        wakeLock.acquire(5*60*1000L /* 5 minutes */);
        Log.d("AlarmService", "Service started");


        // Create notification channel
        NotificationChannel channel = new NotificationChannel(TAG, TAG, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Make the notification
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TAG)
                .setSmallIcon(R.drawable.clock)
                .setContentTitle("Alarm")
                .setContentText("Alarm")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Send notification
        notificationManager.notify(69, builder.build());
        Log.d("AlarmService", "Notification sent");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();
        Log.d("AlarmService", "Service destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
