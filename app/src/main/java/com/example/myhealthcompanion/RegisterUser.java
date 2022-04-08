package com.example.myhealthcompanion;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://register-5fd23-default-rtdb.firebaseio.com/");

    private TextView registerTitle, registerUser;
    private EditText editTextPhone, editTextFullName, editTextEmail, editTextPassword;
    private ProgressBar progressBarRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registerTitle = (TextView) findViewById(R.id.TitleRegister);
        registerTitle.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.btnRegister);
        registerUser.setOnClickListener(this);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBarRegister = (ProgressBar) findViewById(R.id.progress_bar_register);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.TitleRegister:
                startActivity(new Intent(this, LoginUser.class));
                break;
            case R.id.btnRegister:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String fullname = editTextFullName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required !");
            editTextPhone.requestFocus();
            return;
        }

        if (fullname.isEmpty()) {
            editTextFullName.setError("Full name is required !");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required !");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher((email)).matches()) {
            editTextEmail.setError("Please provide a valid email !");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required !");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password length should be at least 6 characters !");
            editTextPassword.requestFocus();
            return;
        }
        else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(phone)){
                        Toast.makeText(RegisterUser.this, "Account with this email already exists !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        databaseReference.child("users").child(phone).child("fullname").setValue(fullname);
                        databaseReference.child("users").child(phone).child("email").setValue(email);
                        databaseReference.child("users").child(phone).child("password").setValue(password);
                        Toast.makeText(RegisterUser.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }

    }}