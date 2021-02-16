package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfinalproject.R;
import com.example.androidfinalproject.viewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private final String TAG ="LOG IN";
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView signUp;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.emailInput = findViewById(R.id.usernameInput);
        this.passwordInput = findViewById(R.id.passwordInput);
        this.loginButton = findViewById(R.id.logInButton);
        userViewModel = UserViewModel.getInstance();
        userViewModel.getUserRepository().signInStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Success")){
                    Intent signInIntent = new Intent(getApplicationContext(), LaunchScreenActivity.class);
                    startActivity(signInIntent);
                }
                else if(s.equals("Loading")){
                    Log.d(TAG, "onChanged: nothing");
                }
                else if(s.equals("NO")){
                    Toast failedLogIn = Toast.makeText(getApplicationContext(), "Login Credentials are incorrect!", Toast.LENGTH_LONG);
                    failedLogIn.show();
                }
            }
        });
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please use your email and password to sign in!", Toast.LENGTH_LONG);
                    toast.show();
                }
                userViewModel.userLogIn(emailInput.getText().toString(), passwordInput.getText().toString());
            }
        });
        this.signUp = findViewById(R.id.signUpLink);
        this.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

}