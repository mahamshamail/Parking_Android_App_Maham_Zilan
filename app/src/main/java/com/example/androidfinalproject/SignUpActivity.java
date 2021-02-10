package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {
    ImageView backButton;
    EditText fullName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    EditText phoneNo;
    EditText plateNo;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.backButton = findViewById(R.id.backButton);
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.fullName = findViewById(R.id.fullNameInput);
        this.email = findViewById(R.id.signUpEmailInput);
        this.password = findViewById(R.id.passwordInput);
        this.confirmPassword = findViewById(R.id.confirmPasswordInput);
        this.phoneNo = findViewById(R.id.phoneNumberInput);
        this.plateNo = findViewById(R.id.plateNoInput);
        this.submitButton = findViewById(R.id.submitButton);
        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}