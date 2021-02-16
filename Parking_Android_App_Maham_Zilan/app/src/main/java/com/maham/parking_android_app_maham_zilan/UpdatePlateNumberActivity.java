package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

public class UpdatePlateNumberActivity extends AppCompatActivity {
    private final String TAG = "UPDATE PLATE";
    private EditText editField;
    private Button updateBtn;
    private Button cancelBtn;
    private UserViewModel userViewModel;
    private String updateContent;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_plate_number);
        this.userViewModel = UserViewModel.getInstance();
        userId = userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.d(TAG, "usr id" + userId);
        this.editField = findViewById(R.id.edUpdate);
        this.updateBtn = findViewById(R.id.updateBtn);
        this.cancelBtn = findViewById(R.id.cancelBtn);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                updateContent = null;
            }else{
                updateContent = extras.getString("UpdateContent");

            }
        }
        else {
            updateContent = (String) savedInstanceState.getSerializable("UpdateContent");
        }
        this.editField.setText(updateContent);
        this.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editField.getText().toString();
                userViewModel.updateUserPlate(userId, newEmail);
                Toast toast = Toast.makeText(getApplicationContext(), "User plate number updated successfully", Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        });
        this.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}