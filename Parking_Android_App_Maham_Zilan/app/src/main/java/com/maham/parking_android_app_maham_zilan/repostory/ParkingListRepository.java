package com.maham.parking_android_app_maham_zilan.repostory;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.maham.parking_android_app_maham_zilan.model.ParkingList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingListRepository{
       private final String TAG = this.getClass().getCanonicalName();
        private FirebaseFirestore db;
        private final String COLLECTION_NAME_PARKING_LIST = "parkingList";
        private final String COLLECTION_NAME_USERS = "users";

        public MutableLiveData<String> parkingListItem = new MutableLiveData<String>();


    public MutableLiveData<List<ParkingList>> parkingListItems = new MutableLiveData<List<ParkingList>>();

        public ParkingListRepository(){
            this.db = FirebaseFirestore.getInstance();
        }

        public void addParkingListItem(String userID, ParkingList parkingList){
            try{
                db.collection(COLLECTION_NAME_USERS)
                        .document(userID)
                        .collection(COLLECTION_NAME_PARKING_LIST)
                        .add(parkingList)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Parking list item added with ID : "+ documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error adding checklist document");
                            }
                        });

            }catch (Exception ex){
                Log.e(TAG, ex.toString());
                Log.e(TAG, ex.getLocalizedMessage());
            }
        }

        public void getAllParkingListItems(String userID){
            try{
                db.collection(COLLECTION_NAME_USERS)
                        .document(userID)
                        .collection(COLLECTION_NAME_PARKING_LIST)
                        .orderBy("date", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                                if (error != null){
                                    Log.e(TAG, "Listening to parking list changes FAILED", error);
                                    return;
                                }

                                List<ParkingList> tempParkingListItems = new ArrayList<>();

                                if (snapshot != null){
                                    Log.d(TAG, "Current data : " + snapshot.getDocumentChanges());

                                    for (DocumentChange documentChange : snapshot.getDocumentChanges()){

                                        ParkingList checklist = documentChange.getDocument().toObject(ParkingList.class);
                                        checklist.setId(documentChange.getDocument().getId());

                                        switch (documentChange.getType()){
                                            case ADDED:
                                                tempParkingListItems.add(checklist);
                                                break;
                                            case MODIFIED:
                                                break;
                                            case REMOVED:
                                                tempParkingListItems.remove(checklist);
                                                break;
                                        }
                                    }

                                    Log.e(TAG, tempParkingListItems.toString());
                                    parkingListItems.postValue(tempParkingListItems);

                                }else{
                                    Log.e(TAG, "No changes in checklist");
                                }
                            }
                        });

            }catch(Exception ex){
                Log.e(TAG, ex.getLocalizedMessage());
                Log.e(TAG, ex.toString());
            }
        }

        public void deleteParkingList(String userID, String checklistID){
            db.collection(COLLECTION_NAME_USERS)
                    .document(userID)
                    .collection(COLLECTION_NAME_PARKING_LIST)
                    .document(checklistID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "Document deleted successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failure deleting document");
                        }
                    });
        }

        public void updateParkingList(String userID, String checklistID, Boolean completion){
            db.collection(COLLECTION_NAME_USERS)
                    .document(userID)
                    .collection(COLLECTION_NAME_PARKING_LIST)
                    .document(checklistID)
                    .update("completion", completion)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "Document updated successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Failure updating document");
                        }
                    });
        }

        //TASK : complete the subsequent calls for updateChecklist
        // method for updating the completion status.

    public void getParking(String userID, Date date) {
    }
}
