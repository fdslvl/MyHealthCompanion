package com.example.myhealthcompanion;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AlarmFragment extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alarm);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_alarm);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (item.getItemId()) {

                    case R.id.nav_pedometer:
                        Intent i = new Intent(AlarmFragment.this, PedometerFragment.class);
                        startActivity(i);
                        break;

                    case R.id.nav_alarm:

                        break;

                    case R.id.nav_bmi:
                        Intent bmi = new Intent(AlarmFragment.this, BmiFragment.class);
                        startActivity(bmi);
                        break;

                    case R.id.nav_profile:
                        Intent profile = new Intent(AlarmFragment.this, ProfileFragment.class);
                        startActivity(profile);
                        break;
                }

                return true;
            }
        });
    }
}