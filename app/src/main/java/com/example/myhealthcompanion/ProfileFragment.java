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

public class ProfileFragment extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_profile);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (item.getItemId()) {

                    case R.id.nav_pedometer:
                        Intent pedometer = new Intent(ProfileFragment.this, PedometerFragment.class);
                        startActivity(pedometer);
                        break;

                    case R.id.nav_alarm:
                        Intent alarm = new Intent(ProfileFragment.this, AlarmFragment.class);
                        startActivity(alarm);
                        break;

                    case R.id.nav_bmi:
                        Intent bmi = new Intent(ProfileFragment.this, BmiFragment.class);
                        startActivity(bmi);
                        break;

                    case R.id.nav_profile:

                        break;
                }

                return true;
            }
        });

    }

}