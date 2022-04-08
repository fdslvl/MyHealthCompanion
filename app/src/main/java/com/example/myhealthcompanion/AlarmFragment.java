package com.example.myhealthcompanion;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AlarmFragment extends AppCompatActivity {

    BottomNavigationView navigationView;
    boolean image1Displaying = false;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alarm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.nav_alarm);

        Button button = findViewById(R.id.btnsetalarm);


        createNotificationChannel();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AlarmFragment.this, "You will receive a notification in 1 hour !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AlarmFragment.this, Reminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmFragment.this, 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();

                long oneHour = 1000 * 10;
//                long oneHour = 1000 * 3600;
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + oneHour, pendingIntent);

                changeImage();

            }
        });

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "MHCReminderChannel";
        String description = "Channel for My Health Companion";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyHealth", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void changeImage() {
        ImageView emptybottle = findViewById(R.id.emptybottle);

        if (image1Displaying) {
            emptybottle.setImageResource(R.drawable.emptybottle);
            image1Displaying = false;
        } else {
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.water)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                            resource.setLoopCount(1);
                            image1Displaying = true;
                            return false;
                        }
                    })
                    .into(emptybottle);

        }
    }

}
