package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidfinalproject.R;
import com.example.androidfinalproject.model.User;
import com.example.androidfinalproject.viewModels.UserViewModel;

public class SignUpActivity extends AppCompatActivity {
    private final String TAG = "SIGN UP";
    ImageView backButton;
    EditText fullName;
    EditText email;
    EditText password;
    EditText confirmPassword;
    EditText phoneNo;
    EditText plateNo;
    Button submitButton;
    UserViewModel userViewModel = new UserViewModel();
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
        this.password = findViewById(R.id.signUpPasswordInput);
        this.confirmPassword = findViewById(R.id.confirmPasswordInput);
        this.phoneNo = findViewById(R.id.phoneNumberInput);
        this.plateNo = findViewById(R.id.plateNoInput);
        this.submitButton = findViewById(R.id.submitButton);
        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null){
                    Log.d(TAG, "onClick: " + password.getText().toString() + "," + confirmPassword.getText().toString());
                    if(password.getText().toString().equals(confirmPassword.getText().toString()) && validateData()){
                        String fname = fullName.getText().toString();
                        String signUpEmail = email.getText().toString();
                        String signUpPwd = password.getText().toString();
                        String signUpPhoneNum = phoneNo.getText().toString();
                        String signUpPlateNum = plateNo.getText().toString();
                        User newUser = new User(fname, signUpEmail, signUpPwd, signUpPhoneNum, signUpPlateNum);
                        Boolean response = userViewModel.addNewUser(newUser);
                        if(response){
                            Toast successToast = Toast.makeText(getApplicationContext(), "Creating new account successful!", Toast.LENGTH_SHORT);
                            successToast.show();
                            finish();
                        }else{
                            Toast failToast = Toast.makeText(getApplicationContext(), "Creating new account failed!", Toast.LENGTH_LONG);
                            failToast.show();
                        }
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "password and confirmation password do not match!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            }
        });
    }

    private Boolean validateData(){
        if (this.email.getText().toString().isEmpty()){
            this.email.setError("Please enter email");
            return false;
        }
        if (this.password.getText().toString().isEmpty()){
            this.password.setError("Password cannot be empty");
            return false;
        }

        if (this.confirmPassword.getText().toString().isEmpty()){
            this.confirmPassword.setError("Please provide confirm password");
            return false;
        }

        return true;
    }
}