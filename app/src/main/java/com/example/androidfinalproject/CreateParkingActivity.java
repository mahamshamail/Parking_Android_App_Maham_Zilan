package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class CreateParkingActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking);
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
        LaunchScreenActivity.redirectActivity(this, ParkingHistory.class);
    }
    public void ClickAddNewParking(View view){
        recreate();
    }
    public void ClickLogOut(View view){
        LaunchScreenActivity.logout(this);
    }
}