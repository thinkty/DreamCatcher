package com.thinkty.myapplication;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
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

}
