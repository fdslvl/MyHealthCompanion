package com.example.myhealthcompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://register-5fd23-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        register = (TextView) findViewById(R.id.Register);
        register.setOnClickListener(this);

        final EditText password = findViewById(R.id.pwd_login);
        final EditText phone = findViewById(R.id.phonelogin);
        final Button loginbtn = findViewById(R.id.btnLogin);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phonetxt = phone.getText().toString();
                final String passwordtxt = password.getText().toString();

                if (phonetxt.isEmpty() || passwordtxt.isEmpty()){
                    Toast.makeText(LoginUser.this,"Please enter your email or password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phonetxt)){
                                final String getPassword = snapshot.child(phonetxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordtxt)){
                                    Toast.makeText(LoginUser.this,"Successfully logged in !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginUser.this,PedometerFragment.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginUser.this,"Wrong password entered !", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginUser.this,"Wrong password entered !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
        }
    }
}