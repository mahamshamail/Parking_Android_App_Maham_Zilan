package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.maham.parking_android_app_maham_zilan.adapter.ParkingListAdapter;
import com.maham.parking_android_app_maham_zilan.model.ParkingList;
import com.maham.parking_android_app_maham_zilan.viewmodel.ParkingListViewModel;
import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnParkingListClickListener, OnParkingListLongClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private Button fabAddParking;
    private Button updateUserProfile;
    private Button signOutbtn;
    private RecyclerView rvParkingLists;
    private LinearLayoutManager viewManager;
    private ParkingListAdapter parkingListAdapter;
    private ArrayList<ParkingList> parkingListArrayList;

    private ParkingListViewModel parkingListViewModel;
    private UserViewModel userViewModel;
    private String userID;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fabAddParking = findViewById(R.id.fabAddParking);
        this.fabAddParking.setOnClickListener(this);

        this.updateUserProfile = findViewById(R.id.updateProfile);
        this.updateUserProfile.setOnClickListener(this);

        this.signOutbtn = findViewById(R.id.signOut);
        this.signOutbtn.setOnClickListener(this);

        this.parkingListViewModel = ParkingListViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.rvParkingLists = findViewById(R.id.rvTodos);
        this.parkingListArrayList = new ArrayList<>();
        this.parkingListAdapter = new ParkingListAdapter(this.getApplicationContext(), parkingListArrayList, this, this);
        this.viewManager = new LinearLayoutManager(this.getApplicationContext());

        this.rvParkingLists.setAdapter(this.parkingListAdapter);
        this.rvParkingLists.setLayoutManager(this.viewManager);
        this.rvParkingLists.setHasFixedSize(true);
        this.rvParkingLists.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL));

        this.parkingListViewModel.getAllParkingListItems(userID);

        this.userViewModel.getUserRepository().signInStatus.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String status) {

            }
        });
        this.parkingListViewModel.getParkingListRepository().parkingListItems.observe(this, new Observer<List<ParkingList>>() {
            @Override
            public void onChanged(List<ParkingList> parkingList) {
                if (parkingList != null){
                    Log.e(TAG, "Data Changed : " + parkingList.toString());
                    parkingListArrayList.clear();
                    parkingListArrayList.addAll(parkingList);
                    parkingListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            if (view.getId() == this.fabAddParking.getId()) {
              //  this.showDialog();
                Intent updateIntent = new Intent(this, AddParkingActivity.class);
                startActivity(updateIntent);
            }
            if (view.getId() == this.updateUserProfile.getId()) {
               // new screen
                Intent updateIntent = new Intent(this, UpdateProfileActivity.class);
                startActivity(updateIntent);

            }
            if (view.getId() == this.signOutbtn.getId()) {
                // new screen
                this.showSignOutDialog();

            }
        }
    }

    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_todo, null);

        AlertDialog addDialog = new AlertDialog.Builder(this)
                .setTitle("Add New Parking")
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //read the given task info
                        EditText edtBuildingCode = dialogView.findViewById(R.id.edtBuildingCode);
                        EditText edtCarPlate = dialogView.findViewById(R.id.edtCarPlateNumber);
                        EditText edtSuitNumHost = dialogView.findViewById(R.id.edtSuitNumberOfHosts);
                        EditText edtParkingLocation = dialogView.findViewById(R.id.edtParkingLocation);
                        RadioGroup radioGroup = (RadioGroup)dialogView.findViewById(R.id.radioGroup);
                        radioGroup.clearCheck();


                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                radioButton = (RadioButton)dialogView.findViewById(checkedId);

                            }
                        });
                        Log.d(TAG, " Building Code  : " + edtBuildingCode.getText().toString()
                                        +"\n edtCarPlate: " + edtCarPlate.getText().toString()
                                +"\n Suit Num Host: " + edtSuitNumHost.getText().toString()
                                +"\n Parking Location: " + edtParkingLocation.getText().toString()
                                +"\n Hours to park : " + radioButton.getText().toString()
                        );

                       // ParkingList newParkingList = new ParkingList(edtNewTodo.getText().toString());

                        //save todo to db
                    //   parkingListViewModel.addParkingListItem(userID, newParkingList);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        addDialog.show();
    }

    private void showSignOutDialog(){
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.sign_out_alert, null);

        AlertDialog addDialog = new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to sign out?")
                .setView(dialogView)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Log.e(TAG, "Signing out. " );
                        Toast.makeText(getApplicationContext(), "Signing out...", Toast.LENGTH_LONG ).show();
//                        Intent signOutIntent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//                        signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(signOutIntent);
                        logOut();

                        // ParkingList newParkingList = new ParkingList(edtNewTodo.getText().toString());

                        //save todo to db
                        //  parkingListViewModel.addParkingListItem(userID, newParkingList);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        addDialog.show();
    }
    @Override
    public void onParkingListClickListener(ParkingList parkingList1) {
        Log.e(TAG, "Item to be shown : " + parkingList1.getDate().toString());

        Intent detailsIntent = new Intent(this, ParkingDetailsActivity.class);
        detailsIntent.putExtra("parkingDate", parkingList1.getDate().toString());
        startActivity(detailsIntent);

    }

    @Override
    public void onParkingListLongClickListener(ParkingList parkingList1) {
        Log.e(TAG, "Item to be deleted : " + parkingList1.toString());
        this.parkingListViewModel.deleteParkingListItem(userID, parkingList1.getId());

        //get the deletion status
        //add the observer for status
        //if success then only perform teh next line

       this.parkingListArrayList.remove(parkingList1);
    }
    public void logOut(){
        userViewModel.getUserRepository().signInStatus.postValue("LOGOUT");
        finishAndRemoveTask();
        Intent signOutIntent = new Intent(this , SignInActivity.class);
        startActivity(signOutIntent);
    }
}