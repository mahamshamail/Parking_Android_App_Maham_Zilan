package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maham.parking_android_app_maham_zilan.model.ParkingList;
import com.maham.parking_android_app_maham_zilan.viewmodel.ParkingListViewModel;
import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingDetailsActivity extends AppCompatActivity {

    private TextView parkingAddress;
    private TextView parkingDate;
    private TextView parkingDuration;
    private TextView buildingCode;
    private TextView carPlate;
    private TextView suitNoOfHosts;
    private Button viewParkingListButton;
    private Button viewParkingLocationButton;

    private ParkingListViewModel parkingListViewModel;
    private UserViewModel userViewModel;
    private ParkingList parkingList;
    private String pl;
    private  String date;
    private String userID;
    private ArrayList<ParkingList> parkingListArrayList;
    private Date date1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_details);
        viewParkingListButton = findViewById(R.id.viewPList);
        viewParkingLocationButton = findViewById(R.id.btnViewMap);
        viewParkingLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMap();
            }
        });

        viewParkingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
        parkingAddress = findViewById(R.id.parkingAddress2);
        parkingDate = findViewById(R.id.parkingHours);
        parkingDuration = findViewById(R.id.parkingDuration);
        buildingCode = findViewById(R.id.buildingCodeT);
        carPlate = findViewById(R.id.carPlate);
        suitNoOfHosts = findViewById(R.id.suitNoOfHost);

        this.parkingListViewModel = ParkingListViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.parkingListArrayList = new ArrayList<>();
        date = getIntent().getStringExtra("parkingDate");
        Log.e("Date showing", date);


        date1 = new Date(date);
        Log.e("Date1: ", date1.toString());


       // parkingListViewModel.validateParkingDetail(userID, date1);

            //    this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();
       // this.pl = this.parkingListViewModel.getParkingListRepository().parkingListItem.getValue();
        this.parkingListViewModel.getAllParkingListItems(userID);


        this.parkingListViewModel.getParkingListRepository().parkingListItems.observe(this, new Observer<List<ParkingList>>() {
            @Override
            public void onChanged(List<ParkingList> parkingList) {
                if (parkingList != null){
                    Log.e("Inside details activity", "Data Changed : " + parkingList.toString());
                    parkingListArrayList.clear();
                    parkingListArrayList.addAll(parkingList);
                    for (int i = 0; i< parkingListArrayList.size(); i++){
                        if (parkingListArrayList.get(i).getDate().toString().equals(date1.toString())){
                            Log.e(" found parking ", parkingListArrayList.get(i).toString());
                            parkingAddress.setText(parkingListArrayList.get(i).getAddress());
                            parkingDuration.setText(parkingListArrayList.get(i).getHours());
                            parkingDate.setText(parkingListArrayList.get(i).getDate().toString());
                            buildingCode.setText(parkingListArrayList.get(i).getBuildingCode());
                            carPlate.setText(parkingListArrayList.get(i).getCarPlateNumber());
                            suitNoOfHosts.setText(parkingListArrayList.get(i).getSuitNumOfHosts());
                            break;
                        }
                    }
                   // parkingListAdapter.notifyDataSetChanged();
                }
            }
        });



    }
    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
    private void goToMap(){
        this.finish();
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }
}