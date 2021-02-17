//Group 10
//101328732 Saiyeda Maham Shamail
//101226318 Zilan Ouyang
package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Toast;

import com.maham.parking_android_app_maham_zilan.adapter.UserProfileItemAdapter;
import com.maham.parking_android_app_maham_zilan.model.User;
import com.maham.parking_android_app_maham_zilan.model.UserProfile;
import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

import java.util.ArrayList;

public class UpdateProfileActivity extends AppCompatActivity {

//    private Button btnCancel;
//    private Button btnUpdate;
//    private EditText edtName;
//    private EditText edtEmail;
//    private EditText edtContact;
//    private EditText edtCarPlate;
    private final String TAG = "SETTINGS";
    ListView listView;
    UserProfileItemAdapter adapter;
    ArrayList<UserProfile> optionList = new ArrayList<>();
    private User user;
    private Button cancelBtn;
    private Button deleteBtn;
    private UserViewModel userViewModel;
    private String userID;
  //  private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
//        edtName = findViewById(R.id.edtUpdateName);
//        btnCancel = findViewById(R.id.btnUpdateCancel);
//        btnUpdate = findViewById(R.id.btnUpdateProfile);
        cancelBtn = findViewById(R.id.cancelBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.userViewModel.getUserRepository().getUserProfile(userID);
       // user = new User();
       // user.setName(this.userViewModel.getUserRepository().userName.getValue());
//        if( this.userViewModel.getUserRepository().userName.getValue() != null  ){
//            deleteBtn.setVisibility(View.VISIBLE);
//            Log.e(" WHAT " , this.userViewModel.getUserRepository().userName.getValue());
//           // edtName.setText(user.getName());
//           // Log.e(" you " , this.userViewModel.getUserRepository().user.);
//        }else{
//            deleteBtn.setVisibility(View.INVISIBLE);
//            Log.e(" you " , "enemy");
//        }
        this.userViewModel.getUserRepository().userDetail.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User obUser) {
                if (obUser != null){
                    optionList.clear();
                    deleteBtn.setVisibility(View.VISIBLE);
                    Log.e(TAG, "Data Changed : " + obUser.getName());
                    user = obUser;
                    Log.d(TAG, "PLATE " + obUser.getCarPlateNumber());
                    UserProfile fullName = new UserProfile("Full Name", obUser.getName());
                    UserProfile email = new UserProfile("Email", obUser.getEmail());
//                    UserProfile password = new UserProfile("Password", "********");
                    UserProfile phoneNumber = new UserProfile("Phone Number", obUser.getContactNumber());
                    UserProfile plate = new UserProfile("Plate Number", obUser.getCarPlateNumber());

                    optionList.add(fullName);
                    optionList.add(email);
                    optionList.add(phoneNumber);
                    optionList.add(plate);
                    user = obUser;
                    adapter.notifyDataSetChanged();
                }else{
                    deleteBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.listView = findViewById(R.id.userProfileListView);
        adapter = new UserProfileItemAdapter(this, R.layout.update_item_row, optionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(UpdateProfileActivity.this, UpdateNameActivity.class);
                    String updateContent = optionList.get(position).getContent();
                    i.putExtra("UpdateContent", updateContent);
                    startActivity(i);
                }
                else if(position == 1){
                    Intent i = new Intent(UpdateProfileActivity.this, UpdateEmailActivity.class);
                    String updateContent = optionList.get(position).getContent();
                    i.putExtra("UpdateContent", updateContent);
                    startActivity(i);
                }
                else if(position == 2){
                    Intent i = new Intent(UpdateProfileActivity.this, UpdateContactNumberActivity.class);
                    String updateContent = optionList.get(position).getContent();
                    i.putExtra("UpdateContent", updateContent);
                    startActivity(i);
                }
                else if(position == 3){
                    Intent i = new Intent(UpdateProfileActivity.this, UpdatePlateNumberActivity.class);
                    String updateContent = optionList.get(position).getContent();
                    i.putExtra("UpdateContent", updateContent);
                    startActivity(i);
                }
            }
        });

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


//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //userViewModel.updateUser(userID, edtName.getText().toString());
//
//             //   Log.e(" Tag ", user.getEmail() );
//                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
//            }
//        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UpdateProfileActivity.this)
                        .setTitle("Are you sure you want to delete your account?")
                        .setMessage("This behaviour is dangerous and irreversible?")
                        .setPositiveButton("Delete anyway", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAndLogOut();

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

    }
    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
    private void deleteAndLogOut(){
        userViewModel.deleteUser(userID);
        userViewModel.getUserRepository().signInStatus.postValue("LOGOUT");
        finishAndRemoveTask();
        Intent i = new Intent(this, SignInActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}