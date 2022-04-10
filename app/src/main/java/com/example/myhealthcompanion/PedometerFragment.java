package com.example.myhealthcompanion;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PedometerFragment extends AppCompatActivity implements SensorEventListener {

    BottomNavigationView navigationView;

    SensorManager mSensorManager = null;
    Sensor mAccelerometer;
    int progr = 0;

    boolean running = false;
    float totalSteps = 0f;
    float previousTotalSteps = 0f;

    Dialog mDialog;
    ImageView popupinfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pedometer);

        loadData();
        resetSteps();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_pedometer);

        popupinfo = findViewById(R.id.information);
        mDialog = new Dialog(this);

        popupinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });

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

            tv_stepsTaken.setText(String.valueOf(Math.round(currentSteps)));
            if (currentSteps < 7000 && progr < 100) {
                if (currentSteps >= 1000 && currentSteps < 1001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 2000 && currentSteps < 2001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 3000 && currentSteps < 3001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 4000 && currentSteps < 4001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 5000 && currentSteps < 5001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 6000 && currentSteps < 6001) {
                    progr += 10;
                    updateProgressBar();
                }if (currentSteps >= 7000 && currentSteps < 7001) {
                    progr += 10;
                    updateProgressBar();
                }
            }
        }
    }

    private void resetSteps() {
        TextView tv_stepsTaken = findViewById(R.id.tv_stepsTaken);
        TextView button = findViewById(R.id.btnResetSteps);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousTotalSteps = totalSteps;
                tv_stepsTaken.setText(String.valueOf(0));
                saveData();
                progr = 0;
                updateProgressBar();
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


    private void updateProgressBar(){
        ProgressBar p = findViewById(R.id.progress_bar);
        p.setProgress(progr);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}