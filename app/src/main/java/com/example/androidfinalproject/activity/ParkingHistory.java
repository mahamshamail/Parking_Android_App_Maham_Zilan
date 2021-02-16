package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.androidfinalproject.R;

public class ParkingHistory extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_history);
        this.drawerLayout = findViewById(R.id.drawerLayout);
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
        LaunchScreenActivity.redirectActivity(this, UserProfileActivity.class);
    }
    public void ClickHistory(View view){
        recreate();
    }
    public void ClickAddNewParking(View view){
        LaunchScreenActivity.redirectActivity(this, CreateParkingActivity.class);
    }
    public void ClickLogOut(View view){
        LaunchScreenActivity.logout(this);
    }
}