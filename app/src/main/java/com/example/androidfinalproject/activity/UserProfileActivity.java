package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidfinalproject.R;
import com.example.androidfinalproject.adapter.UserProfileItemAdapter;
import com.example.androidfinalproject.model.UserProfile;
import com.example.androidfinalproject.viewModels.UserViewModel;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {
    private final String TAG = "SETTINGS";
    DrawerLayout drawerLayout;
    ListView listView;
    ArrayList<UserProfile> optionList = new ArrayList<>();
    private UserViewModel userViewModel;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        this.userViewModel = UserViewModel.getInstance();
        this.userId = this.userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.d(TAG, "THIS IS USER ID " + this.userId);
        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.listView = findViewById(R.id.userProfileListView);
        //TODO: REPLACE PLACEHOLDERS WITH USER DATA FETCHED FROM FIREBASE
        UserProfile fullName = new UserProfile("Full Name", "Zilan Ouyang");
        UserProfile email = new UserProfile("Email", "zilan@gg.com");
        UserProfile password = new UserProfile("Password", "********");
        UserProfile phoneNumber = new UserProfile("Phone Number", "437-123-4567");
        UserProfile plate = new UserProfile("Plate Number", "WER345");
        optionList.add(fullName);
        optionList.add(email);
        optionList.add(password);
        optionList.add(phoneNumber);
        optionList.add(plate);

        UserProfileItemAdapter adapter = new UserProfileItemAdapter(this, R.layout.user_setting_options_row, optionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(UserProfileActivity.this, UpdateProfileActivity.class);
                String updateItem = optionList.get(position).getTitle();
                String updateContent = optionList.get(position).getContent();
                i.putExtra("UpdateItem", updateItem);
                i.putExtra("UpdateContent", updateContent);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        LaunchScreenActivity.closeDrawer(drawerLayout);
    }
    public void ClickMenu(View view){
        LaunchScreenActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        LaunchScreenActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        LaunchScreenActivity.redirectActivity(this, LaunchScreenActivity.class);
    }
    public void ClickUserProfile(View view){
        recreate();
    }
    public void ClickHistory(View view){
        LaunchScreenActivity.redirectActivity(this, ParkingHistory.class);
    }
    public void ClickAddNewParking(View view){
        //TODO: redirect to create new parking activity
        //redirectActivity();
    }
    public void ClickLogOut(View view){
        LaunchScreenActivity.logout(this);
    }
}