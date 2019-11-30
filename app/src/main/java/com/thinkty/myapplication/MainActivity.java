package com.thinkty.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thinkty.myapplication.ui.alarm.AlarmReceiver;
import com.thinkty.myapplication.ui.alarm.AlarmService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In activity_main.xml
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // NavController is responsible for replacing the contents of the NavHost fragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Set up the bottom navigation bar
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AlarmReceiver.ringtone != null && AlarmReceiver.ringtone.isPlaying()) {
            // Stop the ringtone
            AlarmReceiver.ringtone.stop();
            Log.d("MainActivity", "Alarm ringtone stopped");
            // Stop the service
            Intent intent = new Intent(getApplicationContext(), AlarmService.class);
            intent.addCategory(AlarmService.TAG);
            stopService(intent);
        }
    }
}
