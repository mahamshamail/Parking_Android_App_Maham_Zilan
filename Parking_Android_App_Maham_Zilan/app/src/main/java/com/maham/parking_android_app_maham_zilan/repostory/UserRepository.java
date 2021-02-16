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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.maham.parking_android_app_maham_zilan.model.ParkingList;
import com.maham.parking_android_app_maham_zilan.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final String COLLECTION_NAME = "users";
    private final FirebaseFirestore db;

    public MutableLiveData<String> signInStatus = new MutableLiveData<String>();
    public MutableLiveData<String> loggedInUserID = new MutableLiveData<String>();
    public MutableLiveData<String> userName = new MutableLiveData<String>();
    public MutableLiveData<String> userContact = new MutableLiveData<String>();
    public MutableLiveData<String> userEmail = new MutableLiveData<String>();
    public MutableLiveData<String> userCar = new MutableLiveData<String>();
    public MutableLiveData<User> userDetail = new MutableLiveData<User>();


    // public MutableLiveData<User> user = new MutableLiveData<User>();

    public UserRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user){
        try {
            db.collection(COLLECTION_NAME)
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "Document added with ID : " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding document to the store " + e);
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void getUser(String email, String password){
        this.signInStatus.postValue("LOADING");

        try{
            db.collection(COLLECTION_NAME)
                    .whereEqualTo("email", email)
//                    .whereEqualTo("password", password)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                if (task.getResult().getDocuments().size() != 0){
                                    if (task.getResult().getDocuments().get(0).toObject(User.class).getPassword().equals(password)){
                                        signInStatus.postValue("SUCCESS");

                                        //get the Id of currently logged in user
                                        loggedInUserID.postValue(task.getResult().getDocuments().get(0).getId());
                                        Log.d(TAG, "Logged in user document ID : " + loggedInUserID);
                                        // ParkingList checklist = documentChange.getDocument().toObject(ParkingList.class);


                                    }else{
                                        signInStatus.postValue("FAILURE");
                                    }
                                }else{
                                    signInStatus.postValue("FAILURE");
                                }
                            }else{
                                Log.e(TAG, "Error fetching document" + task.getException());
                                signInStatus.postValue("FAILURE");
                            }
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
            signInStatus.postValue("FAILURE");
        }
    }

    public void updateUserName(String userID, String name){
        Log.e("update name", "updateUserName: " + name );
        db.collection(COLLECTION_NAME)
                .document(userID)
                .update("name", name)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Name updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure updating Name");
                    }
                });
    }

    public void updateUserEmail(String userID, String email){
        db.collection(COLLECTION_NAME)
                .document(userID)

                .update("email", email)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Email updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure updating Email");
                    }
                });
    }

    public void updateUserContactNumber(String userID, String contactNumber){
        db.collection(COLLECTION_NAME)
                .document(userID)
                .update("contactNumber", contactNumber)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "contactNum updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure updating contactNum");
                    }
                });
    }

    public void updateUserCarPlateNumber(String userID, String carPlateNumber){
        db.collection(COLLECTION_NAME)
                .document(userID)
                .update("carPlateNumber", carPlateNumber)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "carPlateNumber updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failure updating carPlateNumber");
                    }
                });
    }

    public void getUserProfile(String userID){
//        Log.e(" here user profile ", userID);
        try{
            db.collection(COLLECTION_NAME)
                    .document(userID)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if(error == null){
//                               // user.postValue(value.toObject(User.class));
//
//                                Log.e(" TAH ", "TAH");
////                                tempUser.setEmail(value.get("email").toString());
////                                tempUser.setName(value.get("name").toString());
////                                tempUser.setContactNumber(value.get("contactNumber").toString());
////                                tempUser.setCarPlateNumber(value.get("carPlateNumber").toString());
////                                tempUser.setPassword(value.get("password").toString());
//
////                               Log.e(" inside getUserProfile", tempUser.getName());
////                                user.postValue(tempUser);
//                                String name = value.get("name").toString();
//                                userName.postValue("jkbhkjhbk");
//                                userCar.postValue(value.get("carPlateNumber").toString());
//                                userContact.postValue(value.get("contactNumber").toString());
//                                userEmail.postValue(value.get("email").toString());
////                                Log.e(" inside getUserProfile  ", userName.getValue());
//                            }else {
//                                Log.e(" inside getUserProfile error ", "error.toString()");
//
//                            }
                            if(value != null && value.exists()){
                                Log.e("getUserProfile", "onEvent: " + value.getData().get("name"));
                                String fullName = value.getData().get("name").toString();
                                String email = value.getData().get("email").toString();
                                String password = value.getData().get("password").toString();
                                String plateNo = value.getData().get("carPlateNumber").toString();
                                String phoneNo = value.getData().get("contactNumber").toString();
                                userName.setValue(fullName);
                                userContact.setValue(phoneNo);
                                userEmail.setValue(email);
                                userCar.setValue(plateNo);
                                userDetail.setValue(new User(email, fullName, password, phoneNo, plateNo));
                            }
                            else {
                                Log.d(TAG, "Current data: null");
                            }
                        }
                    });

        }catch(Exception ex){
            Log.e(TAG, ex.getLocalizedMessage());
            Log.e(TAG, ex.toString());

        }
    }

    public void deleteUser(String userID){
        try{
            db.collection(COLLECTION_NAME).document(userID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "onSuccess: deleting " + userID);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "onFailure: deleting " + userID);
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "deleteUser: ", ex.fillInStackTrace());
        }
    }
}
