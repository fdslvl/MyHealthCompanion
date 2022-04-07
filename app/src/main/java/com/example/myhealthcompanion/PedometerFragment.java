package com.example.myhealthcompanion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PedometerFragment extends AppCompatActivity implements SensorEventListener {

    BottomNavigationView navigationView;

    SensorManager mSensorManager = null;
    Sensor mAccelerometer;

    boolean running = false;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pedometer);

        loadData();
        resetSteps();

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_pedometer);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (item.getItemId()) {

                    case R.id.nav_pedometer:
                        break;

                    case R.id.nav_alarm:
                        Intent alarm = new Intent(PedometerFragment.this, AlarmFragment.class);
                        startActivity(alarm);
                        break;

                    case R.id.nav_bmi:
                        Intent bmi = new Intent(PedometerFragment.this, BmiFragment.class);
                        startActivity(bmi);
                        break;

                    case R.id.nav_profile:
                        Intent profile = new Intent(PedometerFragment.this, ProfileFragment.class);
                        startActivity(profile);
                        break;
                }

                return true;
            }
        });
    }

    protected void onResume() {
        super.onResume();
        running = true;
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (mAccelerometer == null){
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        }
        else {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);

        if (running){
            totalSteps = event.values[0];

            float currentSteps = totalSteps - previousTotalSteps;
            tv_stepsTaken.setText((int) currentSteps);
        }
    }

    private void resetSteps() {
        TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);
        tv_stepsTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PedometerFragment.this, "Long tap to reset steps", Toast.LENGTH_SHORT).show();
            }
        });

        tv_stepsTaken.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                tv_stepsTaken.setText(0);
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat("key1", previousTotalSteps);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        previousTotalSteps = sharedPreferences.getFloat("key1", 0f);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}