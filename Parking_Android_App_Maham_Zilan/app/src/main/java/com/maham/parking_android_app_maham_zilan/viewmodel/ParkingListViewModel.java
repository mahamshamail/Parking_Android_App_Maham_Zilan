package com.maham.parking_android_app_maham_zilan.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.maham.parking_android_app_maham_zilan.model.ParkingList;
import com.maham.parking_android_app_maham_zilan.repostory.ParkingListRepository;

import java.util.Date;

public class ParkingListViewModel extends ViewModel {
    private final String TAG = this.getClass().getCanonicalName();
    private static final ParkingListViewModel ourInstance = new ParkingListViewModel();
    private final ParkingListRepository parkingListRepository = new ParkingListRepository();

    public static ParkingListViewModel getInstance(){
        return ourInstance;
    }

    public ParkingListRepository getParkingListRepository(){
        return parkingListRepository;
    }

    private ParkingListViewModel(){}

    public void addParkingListItem(String userID, ParkingList parkingList){
        this.parkingListRepository.addParkingListItem(userID, parkingList);
    }

    public void getAllParkingListItems(String userID){
        this.parkingListRepository.getAllParkingListItems(userID);
    }

    public void deleteParkingListItem(String userID, String checklistID){
        this.parkingListRepository.deleteParkingList(userID, checklistID);
    }

    public void validateParkingDetail(String userID, Date date){

        if (date != null){
            Log.e("!date.isEmpty() ", date.toString());
                this.parkingListRepository.getParking(userID, date);

        }
    }
}
