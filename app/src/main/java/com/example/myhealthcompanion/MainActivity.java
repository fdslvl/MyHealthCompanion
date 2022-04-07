package com.example.myhealthcompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashScreenTheme);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_pedometer);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (item.getItemId()){

                    case R.id.nav_pedometer:
                        Intent pedometer = new Intent(MainActivity.this, PedometerFragment.class);
                        startActivity(pedometer);
                        break;

                    case R.id.nav_alarm:
                        Intent alarm = new Intent(MainActivity.this, AlarmFragment.class);
                        startActivity(alarm);
                        break;

                    case R.id.nav_bmi:
                        Intent bmi = new Intent(MainActivity.this, BmiFragment.class);
                        startActivity(bmi);
                        break;

                    case R.id.nav_profile:
                        Intent profile = new Intent(MainActivity.this, ProfileFragment.class);
                        startActivity(profile);
                        break;
                }
                return true;
            }
        });
    }
}