package com.example.androidfinalproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidfinalproject.R;
import com.example.androidfinalproject.viewModels.UserViewModel;
import com.google.firebase.firestore.auth.User;

public class LaunchScreenActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private final String TAG = "LAUNCH";
    private UserViewModel userViewModel;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        userViewModel = UserViewModel.getInstance();
        this.userId = this.userViewModel.getUserRepository().loggedInUserID.getValue();
        Log.d(TAG, "THIS IS USER ID " + this.userId);
        this.drawerLayout = findViewById(R.id.drawerLayout);
//        Log.d(TAG, "onCreate: " + this.userViewModel.getUserRepository().loggedInUserID.getValue());

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickHistory(View view){
        //TODO: redirect to parking history activity
        redirectActivity(this, ParkingHistory.class);

    }

    public void ClickAddNewParking(View view){
        //TODO: redirect to create new parking activity
        redirectActivity(this, CreateParkingActivity.class);
    }

    public void ClickUserProfile(View view){
        //TODO: redirect to user profile activity
        redirectActivity(this, UserProfileActivity.class);
    }

    public static void redirectActivity(Activity activity, Class redirectClass) {
        Intent intent = new Intent(activity, redirectClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    public void ClickLogOut(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Duh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
}