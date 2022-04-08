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
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class ProfileFragment extends AppCompatActivity {

    BottomNavigationView navigationView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://register-5fd23-default-rtdb.firebaseio.com/");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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