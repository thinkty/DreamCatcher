package com.thinkty.myapplication.ui.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.thinkty.myapplication.R;

import java.util.Calendar;
import java.util.Objects;

public class AlarmsFragment extends Fragment {

    private AlarmsViewModel alarmsViewModel;
    private TimePicker timePicker;
    private ToggleButton toggleButton;
    private static AlarmManager alarmManager;
    private static PendingIntent alarmIntent;

    public static int hour;
    public static int minute;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        alarmsViewModel = ViewModelProviders.of(this).get(AlarmsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alarms, container, false);

        timePicker = (TimePicker) root.findViewById(R.id.time_picker);
        toggleButton = (ToggleButton) root.findViewById(R.id.alarmToggle);

        // Set alarm manager
        initAlarmManager();

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    // Turn on the alarm
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();

                    // Save the current values to shared preferences
                    if (getActivity() != null) {
                        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("hour", hour);
                        editor.putInt("minute", minute);
                        editor.putBoolean("toggleOn", true);
                        editor.apply();
                    }
                    setAlarm(hour, minute);
                    Toast.makeText(getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
                    Log.d("AlarmsFragment", "Alarm set");
                } else {
                    // Turn off the alarm
                    if (alarmManager != null) {
                        Intent intent = new Intent(getContext(), AlarmReceiver.class);
                        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                        alarmManager.cancel(alarmIntent);
                        Toast.makeText(getContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
                        Log.d("AlarmsFragment", "Alarm canceled");
                    }
                    // Update the toggle option
                    if (getActivity() != null) {
                        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("toggleOn", false);
                        editor.apply();
                    }
                }
            }
        });


        return root;
    }

    private void initAlarmManager() {
        alarmManager = (AlarmManager) Objects.requireNonNull(getContext()).getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("tag", "alarm");
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public static void setAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Show the current set time to the time picker
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            if (preferences.getInt("hour", -1) != -1) {
                timePicker.setHour(preferences.getInt("hour", -1));
                timePicker.setMinute(preferences.getInt("minute", -1));
            }
            // Also set toggle
            toggleButton.setChecked(preferences.getBoolean("toggleOn", false));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save the current set time to the time picker
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("hour", timePicker.getHour());
            editor.putInt("minute", timePicker.getMinute());
            editor.apply();
        }
    }
}