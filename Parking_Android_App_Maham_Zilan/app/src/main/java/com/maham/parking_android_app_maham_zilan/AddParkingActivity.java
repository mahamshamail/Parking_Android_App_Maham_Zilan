package com.maham.parking_android_app_maham_zilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;
import com.maham.parking_android_app_maham_zilan.model.ParkingList;
import com.maham.parking_android_app_maham_zilan.viewmodel.ParkingListViewModel;
import com.maham.parking_android_app_maham_zilan.viewmodel.UserViewModel;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddParkingActivity extends AppCompatActivity {

    private EditText edtBuildingCode;
    private EditText edtCarPlate;
    private EditText edtSuitNumHost;
    private EditText edtParkingLocation;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button useCurrentLocationBtn;
    private Button submitButton;
    private Button cancelButton;
    private UserViewModel userViewModel;
    private ParkingListViewModel parkingListViewModel;
    private String userID;

    private LocationManager locationManager;
    private LatLng location;
    private LocationCallback locationCallback;

    Geocoder geocoder;
    List<Address> addresses;
    List<Address> addresses1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);

        this.parkingListViewModel = ParkingListViewModel.getInstance();
        this.userViewModel = UserViewModel.getInstance();
        edtBuildingCode = findViewById(R.id.edtBuildingCode);
         edtCarPlate = findViewById(R.id.edtCarPlateNumber);
         edtSuitNumHost = findViewById(R.id.edtSuitNumberOfHosts);
         edtParkingLocation = findViewById(R.id.edtParkingLocation);
         radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
         radioGroup.clearCheck();
         useCurrentLocationBtn = findViewById(R.id.useCurrentLocationBtn);
         submitButton = findViewById(R.id.submitButton);
         cancelButton = findViewById(R.id.cancelParking);

        this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();

        this.locationManager = LocationManager.getInstance();
        this.locationManager.checkPermissions(this);


        geocoder = new Geocoder(this,Locale.getDefault());


        useCurrentLocationBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               //  edtParkingLocation.setText("Current Location");
                 if (locationManager.locationPermissionGranted) {
                     Log.e("Location TAG", "Permission granted");
                     locationCallback = new LocationCallback() {
                         @Override
                         public void onLocationResult(LocationResult locationResult) {
                             if (locationResult == null) {
                                 return;
                             }

                             for (Location loc : locationResult.getLocations()) {
                                 String address ="Lat : " + loc.getLatitude() + "\nLng : " + loc.getLongitude();
                                 try {
                                     addresses = geocoder.getFromLocation(loc.getLatitude(),  loc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                      address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                     String city = addresses.get(0).getLocality();
                                     String state = addresses.get(0).getAdminArea();
                                     String country = addresses.get(0).getCountryName();
                                     String postalCode = addresses.get(0).getPostalCode();
                                     String knownName = addresses.get(0).getFeatureName(); //
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                     Log.e("addresses TAG",e.getLocalizedMessage());
                                 }




                                 edtParkingLocation.setText(address);
                                 Log.e("TAG", "Lat : " + loc.getLatitude() + "\nLng : " + loc.getLongitude());
                                 Log.e("TAG", "Number of locations" + locationResult.getLocations().size());
                             }
                         }
                     };

                    locationManager.requestLocationUpdates(getApplicationContext(), locationCallback);
                 }

             }
         });
         submitButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 Boolean validationCheck = validateData();
                 //save todo to db
                 if(validationCheck) {
                     //add to list
                     //(String id, Date date, String address, String hours, String buildingCode, String carPlateNumber, String suitNumOfHosts)
                     ParkingList newParkingList = new ParkingList(new Date(),
                             edtParkingLocation.getText().toString(),
                             radioButton.getText().toString(),
                             edtBuildingCode.getText().toString(),
                             edtCarPlate.getText().toString(),
                             edtSuitNumHost.getText().toString());
                     Log.e("HERE: ",newParkingList.toString());
                     //validate and then save data
                     parkingListViewModel.addParkingListItem(userID, newParkingList);
                     Toast.makeText(getApplicationContext(), "Parking Added", Toast.LENGTH_LONG).show();
                     goToMain();
                 }
             }
         });
         cancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               goToMain();
             }
         });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)group.findViewById(checkedId);

                Log.e("Hours: ", radioButton.getText().toString());
            }
        });

    }

    private void goToMain(){
        this.finish();
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private Boolean validateData(){

        if (this.edtBuildingCode.getText().toString().isEmpty()){
            this.edtBuildingCode.setError("Please Enter Building Code");
            return false;
        }
        if (this.edtBuildingCode.getText().toString().length() != 5){
            this.edtBuildingCode.setError("Building Code must have\nexactly 5 characters");
            return false;
        }
        if (this.edtCarPlate.getText().toString().isEmpty()){
            this.edtCarPlate.setError("Please Enter Car Plate");
            return false;
        }
        if(edtCarPlate.getText().toString().length() < 2
                || edtCarPlate.getText().toString().length() > 8){
            this.edtCarPlate.setError("Car plate length min is 2\nand max is 8)");

            return false;
        }
        if (this.edtSuitNumHost.getText().toString().isEmpty()){
            this.edtSuitNumHost.setError("Please Suit Number of Host");
            return false;
        }

        if(edtSuitNumHost.getText().toString().length() < 2
                || edtSuitNumHost.getText().toString().length() > 5){
            this.edtSuitNumHost.setError("Number of host\nlength min is 2\nand max is 8)");

            return false;
        }

        if (this.edtParkingLocation.getText().toString().isEmpty()){
            this.edtParkingLocation.setError("Please Enter Parking Location");
            return false;
        }

        if (this.radioButton == null){
            Log.e("Hours: ", "Radio Button is null");
            Toast.makeText(getApplicationContext(), "Select number of hours!", Toast.LENGTH_LONG ).show();
            return false;
        }
        if (this.edtParkingLocation == null){
            Log.e("Hours: ", "Radio Button is null");
            this.edtParkingLocation.setError("Enter Parking Location");
            return false;
        }else{
            try{
                //getLocationFromAddress(edtParkingLocation.getText().toString());
               addresses1 = geocoder.getFromLocationName(edtParkingLocation.getText().toString(), 1);
               double lat = addresses1.get(0).getLatitude();
               Log.e(" latitude ", lat +" " );
            }
            catch ( Exception e){
                Log.e(" Add parking activity ", "reverse geocoding fail.");
                Toast.makeText(getApplicationContext(), "Invalid Parking location", Toast.LENGTH_LONG ).show();
                return false;
            }
        }
        return true;
    }

    public GeoPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(" Add parking activity ", "reverse geocoding fail.");
            return null;
        }
    }
}

