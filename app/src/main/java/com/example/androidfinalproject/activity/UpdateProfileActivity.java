package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidfinalproject.R;
import com.example.androidfinalproject.viewModels.UserViewModel;

public class UpdateProfileActivity extends AppCompatActivity {
    private final String TAG ="UPDATE";
    private String updateItem;
    private String updateContent;
    private TextView updateTitle;
    private EditText updateField;
    private Button updateButton;
    private UserViewModel userViewModel = new UserViewModel();
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        userId = userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.d(TAG, "usr id" + userId);
        userViewModel.getUserInfo(userId);
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                updateItem = null;
                updateContent = null;
            }else{
                updateItem = extras.getString("UpdateItem");
                updateContent = extras.getString("UpdateContent");

            }
        }
        else {
            updateItem = (String) savedInstanceState.getSerializable("UpdateItem");
            updateContent = (String) savedInstanceState.getSerializable("UpdateContent");
        }

        updateTitle = findViewById(R.id.updateItem);
        updateField = findViewById(R.id.edUpdate);
        updateButton = findViewById(R.id.updateBtn);
        updateTitle.setText(updateItem);
        updateField.setText(updateContent);
        updateButton.setText("Update " +updateItem);
    }
}