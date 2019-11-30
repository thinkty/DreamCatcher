package com.thinkty.myapplication.ui.alarm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;

import com.thinkty.myapplication.ui.alarm.AlarmsFragment;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {

    public static Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Reset the alarm after reboot since rebooting removes the alarm
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            AlarmsFragment.setAlarm(AlarmsFragment.hour, AlarmsFragment.minute);
            return;
        }

        String tag = intent.getStringExtra("tag");
        if (tag != null) {
            if (tag.equals("alarm")) {

                // Start the ringtone
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                ringtone = RingtoneManager.getRingtone(context, alarmUri);
                ringtone.play();
                // The ring will stop when the user start the app

                // Start the alarm service
                Intent alarmService = new Intent(context, AlarmService.class);
                context.startService(alarmService);
                setResultCode(Activity.RESULT_OK);
            }
        }

    }
}
