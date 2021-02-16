package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.maham.parking_android_app_maham_zilan.model.User;
import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button btnCancel;
    private Button btnUpdate;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtContact;
    private EditText edtCarPlate;

    private UserViewModel userViewModel;
    private String userID;
  //  private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        edtName = findViewById(R.id.edtUpdateName);
        btnCancel = findViewById(R.id.btnUpdateCancel);
        btnUpdate = findViewById(R.id.btnUpdateProfile);

        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.userViewModel.getUserRepository().getUserProfile(userID);
       // user = new User();
       // user.setName(this.userViewModel.getUserRepository().userName.getValue());
        if( this.userViewModel.getUserRepository().userName.getValue() != null  ){
            Log.e(" you " , "user.getName()");
           // edtName.setText(user.getName());
           // Log.e(" you " , this.userViewModel.getUserRepository().user.);
        }else{
            Log.e(" you " , "enemy");
        }


   //     Log.e(" Tag ", this.user.getName() );
//        if( (user.getName().isEmpty() || user.getEmail().isEmpty() ||
//        user.getContactNumber().isEmpty() || user.getCarPlateNumber().isEmpty())) {
//            Log.e(" empty ", "null");
//        }else {
//
//            edtEmail.setText(user.getEmail());
//            edtContact.setText(user.getContactNumber());
//            edtCarPlate.setText(user.getCarPlateNumber());
//        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userViewModel.updateUser(userID, edtName.getText().toString());

             //   Log.e(" Tag ", user.getEmail() );
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });


    }
    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}